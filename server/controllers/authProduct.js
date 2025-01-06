const myPool = require("../config/db.config");

//Subida de productos
const uploadProduct = async (req, res) => {
    const { imagen, descripcion, precio, user_id, category_id, palabras_clave } = req.body;

    try {
        const result = await myPool.query(
            "INSERT INTO product (imagen, descripcion, precio, user_id, category_id, palabras_clave) VALUES ($1, $2, $3, $4, $5, $6) RETURNING id",
            [imagen, descripcion, precio, user_id, category_id, palabras_clave]
        );

        const productId = result.rows[0].id;

        res.status(201).json({
            message: "Producto subido exitosamente",
            productId: productId
        });
    } catch (error) {
        console.error("Error al subir el producto:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Edición de productos
const editProduct = async (req, res) => {
    const { id, imagen, descripcion, precio, category_id, palabras_clave } = req.body;

    try {
        const result = await myPool.query(
            `UPDATE product 
             SET imagen = $1, descripcion = $2, precio = $3, category_id = $4, palabras_clave = $5
             WHERE id = $6`,
            [imagen, descripcion, precio, category_id, palabras_clave, id]
        );

        if (result.rowCount === 0) {
            return res.status(404).json({ message: "Producto no encontrado." });
        }

        res.status(200).json({ message: "Producto actualizado exitosamente." });
    } catch (error) {
        console.error("Error al editar el producto:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Eliminación de productos
const deleteProduct = async (req, res) => {
    const productId = req.params.id;

    if (!productId || isNaN(productId)) {
        return res.status(400).json({ message: "ID de producto inválido" });
    }

    try {
        const result = await myPool.query("DELETE FROM product WHERE id = $1", [productId]);

        if (result.rowCount === 0) {
            return res.status(404).json({ message: "Producto no encontrado" });
        }

        res.status(200).json({ message: "Producto borrado exitosamente" });
    } catch (error) {
        console.error("Error al borrar el producto:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
}

//valoración de un producto
const rateProduct = async (req, res) => {
    const { product_id, user_id, rating } = req.body;

    if (rating < 1 || rating > 5) {
        return res.status(400).json({ message: "La puntuación debe estar entre 1 y 5" });
    }

    try {
        const { rows: existingRating } = await myPool.query(
            "SELECT * FROM product_rating WHERE product_id = $1 AND user_id = $2",
            [product_id, user_id]
        );

        if (existingRating.length > 0) {
            return res.status(403).json({ message: "No puedes valorar este producto más de una vez." });
        }

        await myPool.query(
            "INSERT INTO product_rating (product_id, user_id, rating) VALUES ($1, $2, $3)",
            [product_id, user_id, rating]
        );
        return res.status(201).json({ message: "Valoración añadida exitosamente" });

    } catch (error) {
        console.error("Error al valorar el producto:", error);

        if (error.code === '23503') {
            return res.status(400).json({ message: "Error de referencia: verifica que el producto y el usuario existen en la base de datos." });
        } else {
            return res.status(500).json({ message: "Error en el servidor", error: error.message });
        }
    }
};

//Conseguir productos por categoría
const getProductsByCategory = async (req, res) => {
    const categoryId = req.params.categoryId;

    if (!categoryId || isNaN(categoryId)) {
        return res.status(400).json({ message: "ID de categoría inválido" });
    }

    try {
        const { rows: products } = await myPool.query(
            `SELECT id, imagen, descripcion, precio 
             FROM product 
             WHERE category_id = $1 
             AND (NOW() < fecha_inicio_oferta OR NOW() > fecha_fin_oferta OR fecha_inicio_oferta IS NULL OR fecha_fin_oferta IS NULL)
             ORDER BY id`,
            [categoryId]
        );

        if (products.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos para esta categoría" });
        }

        res.status(200).json({ products });
    } catch (error) {
        console.error("Error al obtener productos por categoría:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Filtrado de productos por palabras clave
const getUserProductsKeyWords = async (req, res) => {
    const keywords = req.params.keywords;

    if (!keywords) {
        return res.status(400).json({ message: "Se deben proporcionar palabras clave para la búsqueda." });
    }

    try {
        const query = `
            SELECT * FROM product 
            WHERE palabras_clave ILIKE $1
            AND (NOW() < fecha_inicio_oferta OR NOW() > fecha_fin_oferta OR fecha_inicio_oferta IS NULL OR fecha_fin_oferta IS NULL)
            ORDER BY id
        `;
        const params = [`%${keywords}%`];

        const { rows: products } = await myPool.query(query, params);

        if (products.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos" });
        }

        return res.status(200).json({ products });
    } catch (error) {
        console.error("Error al obtener productos:", error);
        return res.status(500).json({ message: "Error interno del servidor" });
    }
};

//Recepción de todos los productos
const getAllProducts = async (req, res) => {
    try {
        const { rows: products } = await myPool.query(`
            SELECT * 
            FROM product 
            WHERE (NOW() < fecha_inicio_oferta OR NOW() > fecha_fin_oferta OR fecha_inicio_oferta IS NULL OR fecha_fin_oferta IS NULL)
            ORDER BY id
        `);

        if (products.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos." });
        }

        const formattedProducts = products.map(product => ({
            id: product.id,
            imagen: product.imagen,
            descripcion: product.descripcion,
            precio: product.precio,
            user_id: product.user_id,
        }));

        res.status(200).json({
            message: "Productos obtenidos exitosamente",
            products: formattedProducts
        });
    } catch (error) {
        console.error("Error al obtener productos:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Listado del top 10 de vendedores con más productos a la venta
const getTopVendors = async (req, res) => {
    try {
        const { rows: topVendors } = await myPool.query(
            `SELECT u.email, COUNT(p.id) AS total_productos
             FROM usuario u
             JOIN role r ON u.role_id = r.id
             LEFT JOIN product p ON u.id = p.user_id
             WHERE r.name = 'Vendedor'
             GROUP BY u.email
             ORDER BY total_productos DESC
             LIMIT 10`
        );

        if (topVendors.length === 0) {
            return res.status(404).json({ message: "No se encontraron vendedores." });
        }

        res.status(200).json({
            message: "Top 10 vendedores con el total de productos",
            topVendors
        });
    } catch (error) {
        console.error("Error al obtener el top de vendedores:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Listado de los 10 vendedores con más ventas
const getTopVendorsSales = async (req, res) => {
    try {
        const { rows: topVendors } = await myPool.query(`
            SELECT u.id, u.email, COUNT(c.id_compra) AS total_ventas
            FROM usuario u
            JOIN product p ON u.id = p.user_id
            JOIN compra c ON p.id = c.id_producto
            WHERE c.confirmado = true
            GROUP BY u.id, u.email
            ORDER BY total_ventas DESC
            LIMIT 10
        `);

        if (topVendors.length === 0) {
            return res.status(404).json({ message: "No se encontraron vendedores con ventas." });
        }

        res.status(200).json({
            message: "Top 10 vendedores con más ventas obtenidos exitosamente",
            topVendors
        });
    } catch (error) {
        console.error("Error al obtener el top de vendedores:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Listado de los 10 productos más vendidos
const getTopSales = async (req, res) => {
    try {
        const { rows: topRatedProducts } = await myPool.query(`
            SELECT 
                p.id, 
                p.imagen, 
                p.descripcion, 
                p.precio, 
                COALESCE(COUNT(c.id_compra), 0) AS total_ventas
            FROM 
                product p
            LEFT JOIN 
                compra c ON p.id = c.id_producto AND c.confirmado = true
            WHERE 
                (NOW() < p.fecha_inicio_oferta OR NOW() > p.fecha_fin_oferta OR p.fecha_inicio_oferta IS NULL OR p.fecha_fin_oferta IS NULL)
            GROUP BY 
                p.id, p.imagen, p.descripcion, p.precio
            ORDER BY 
                total_ventas DESC
            LIMIT 10
        `);

        if (topRatedProducts.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos puntuados." });
        }

        res.status(200).json({ 
            message: "Productos más puntuados obtenidos exitosamente",
            topRatedProducts 
        });
    } catch (error) {
        console.error("Error al obtener los productos más puntuados:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Listado del top 10 de productos mejor valorados
const getTopRatedProducts = async (req, res) => {
    try {
      const { rows: topRatedProducts } = await myPool.query(`
        SELECT 
          p.id, 
          p.imagen, 
          p.descripcion, 
          p.precio, 
          AVG(pr.rating) as average_rating,
          COUNT(pr.id) as total_ratings
        FROM 
          product p
        LEFT JOIN 
          product_rating pr ON p.id = pr.product_id
        GROUP BY 
          p.id, p.imagen, p.descripcion, p.precio
        HAVING 
          COUNT(pr.id) > 0
        ORDER BY 
          average_rating DESC, total_ratings DESC
        LIMIT 10
      `);
  
      if (topRatedProducts.length === 0) {
        return res.status(404).json({ message: "No se encontraron productos con valoraciones." });
      }
  
      res.status(200).json({ 
        message: "Top 10 productos mejor valorados obtenidos exitosamente",
        topRatedProducts 
      });
    } catch (error) {
      console.error("Error al obtener los productos mejor valorados:", error);
      res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
  };

//Recepción de todas las categorías
const getAllCategories = async (req, res) => {
    try {
        const { rows: categories } = await myPool.query("SELECT * FROM category ORDER BY id");

        if (categories.length === 0) {
            return res.status(404).json({ message: "No se encontraron categorías." });
        }

        const formattedCategories = categories.map(category => ({
            id: category.id,
            name: category.name,
        }));

        res.status(200).json({
            message: "Categorías obtenidas exitosamente",
            categories: formattedCategories
        });
    } catch (error) {
        console.error("Error al obtener categorías:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Conseguir los productos de un usuario
const getUserProducts = async (req, res) => { // Aquí no restringimos a solo productos para mejorar a expriencia de usuario
    const userId = req.params.userId;

    if (!userId || isNaN(userId)) {
        return res.status(400).json({ message: "ID de usuario inválido" });
    }

    try {
        const { rows: products } = await myPool.query(
            "SELECT id, imagen, descripcion, precio, user_id, category_id, palabras_clave FROM product WHERE user_id = $1 AND precio_oferta IS NULL ORDER BY id;",
            [userId]
        );

        if (products.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos para este usuario" });
        }

        res.status(200).json({ products });
    } catch (error) {
        console.error("Error al obtener los productos del usuario:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Subida de un producto normal
const uploadNormalProduct = async (req, res) => {
    const { imagen, descripcion, precio, user_id, category_id, palabras_clave } = req.body;

    try {
        const { rows } = await myPool.query(
            "INSERT INTO product (imagen, descripcion, precio, user_id, category_id, palabras_clave) VALUES ($1, $2, $3, $4, $5, $6) RETURNING *",
            [imagen, descripcion, precio, user_id, category_id, palabras_clave]
        );

        res.status(201).json({
            message: "Producto normal subido exitosamente",
            product: rows[0]
        });
    } catch (error) {
        console.error("Error al subir producto normal:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Listado de las ofertas que en este momento se tratan como tal
const listOffers = async (req, res) => {
    // Las ofertas deben haber tenido o tener precio especial, así que eso las identifica (Esto se ve mejor en la base de datos)
    try {
        const { rows } = await myPool.query(`
            SELECT * 
            FROM product 
            WHERE NOW() BETWEEN fecha_inicio_oferta AND fecha_fin_oferta 
            ORDER BY id;
        `);

        if (rows.length === 0) {
            return res.status(404).json({ message: "No se encontraron ofertas." });
        }

        res.status(200).json({
            message: "Ofertas obtenidas exitosamente",
            offers: rows
        });
    } catch (error) {
        console.error("Error al obtener ofertas:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Edición de ofertas 
const editOffer = async (req, res) => {
    try {
        const { id, imagen, descripcion, precio, user_id, category_id, palabras_clave, precio_oferta, fecha_inicio_oferta, fecha_fin_oferta } = req.body;

        const { rows } = await myPool.query(`
            UPDATE product 
            SET imagen = $1, descripcion = $2, precio = $3, user_id = $4, category_id = $5, palabras_clave = $6, 
                precio_oferta = $7, fecha_inicio_oferta = $8, fecha_fin_oferta = $9
            WHERE id = $10
            RETURNING *;
        `, [imagen, descripcion, precio, user_id, category_id, palabras_clave, precio_oferta, fecha_inicio_oferta, fecha_fin_oferta, id]);

        if (rows.length === 0) {
            return res.status(404).json({ message: "Oferta no encontrada." });
        }

        res.status(200).json({
            message: "Oferta actualizada exitosamente",
            offer: rows[0]
        });
    } catch (error) {
        console.error("Error al editar oferta:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Exportación de módulos, nos ayuda luego a separar responsabilidades
module.exports = {
    getAllProducts,
    uploadProduct,
    editProduct,
    deleteProduct,
    rateProduct,
    getProductsByCategory,
    getUserProductsKeyWords,
    getTopVendors,
    getTopVendorsSales,
    getTopSales,
    getTopRatedProducts,
    getAllCategories,
    getUserProducts,
    uploadNormalProduct,
    listOffers,
    editOffer
};