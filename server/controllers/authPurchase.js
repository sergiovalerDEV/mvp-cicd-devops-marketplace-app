const myPool = require("../config/db.config");

//Realización de una compra para un producto normal
const realizarCompra = async (req, res) => {
    const { id_usuario, id_producto } = req.body;

    if (!id_usuario || !id_producto) {
        return res.status(400).json({ message: "ID de usuario y producto son obligatorios." });
    }

    try {
        const { rows } = await myPool.query(
            "INSERT INTO compra (id_usuario, id_producto, confirmado) VALUES ($1, $2, FALSE) RETURNING *",
            [id_usuario, id_producto]
        );

        const nuevaCompra = rows[0];
        return res.status(201).json({ message: "Compra realizada exitosamente.", compra: nuevaCompra });

    } catch (error) {
        console.error("Error al realizar la compra:", error);
        return res.status(500).json({ message: "Error en el servidor.", error: error.message });
    }
};

//Realización de una compra para una de las ofertas disponibles del momento
const comprarOferta = async (req, res) => {
    const { id_usuario, id_oferta } = req.body;

    if (!id_usuario || !id_oferta) {
        return res.status(400).json({ message: "ID de usuario y ID de oferta son obligatorios." });
    }

    try {
        // Verificamos si la oferta existe y está activa
        const { rows: ofertaRows } = await myPool.query(
            "SELECT * FROM product WHERE id = $1 AND precio_oferta IS NOT NULL AND current_date BETWEEN fecha_inicio_oferta AND fecha_fin_oferta",
            [id_oferta]
        );

        if (ofertaRows.length === 0) {
            return res.status(404).json({ message: "Oferta no encontrada o no está activa." });
        }

        // Realizamos la compra, pues la consulta determina que sí vamos a comprar una oferta habilitada
        const { rows: compraRows } = await myPool.query(
            "INSERT INTO compra (id_usuario, id_producto, confirmado) VALUES ($1, $2, TRUE) RETURNING *",
            [id_usuario, id_oferta]
        );

        const nuevaCompra = compraRows[0];

        return res.status(201).json({
            message: "Compra de oferta realizada exitosamente.",
            compra: nuevaCompra
        });

    } catch (error) {
        console.error("Error al comprar la oferta:", error);
        return res.status(500).json({ message: "Error en el servidor.", error: error.message });
    }
};

//Confifmración de una compra normal
const confirmarCompra = async (req, res) => {
    const { id_compra } = req.params;

    try {
        // Actualización del estado de la compra a 'confirmado' = TRUE
        const { rowCount } = await myPool.query(
            "UPDATE compra SET confirmado = TRUE WHERE id_compra = $1",
            [id_compra]
        );

        if (rowCount === 0) {
            return res.status(404).json({ message: "Compra no encontrada." });
        }

        return res.status(200).json({ message: "Compra confirmada exitosamente." });

    } catch (error) {
        console.error("Error al confirmar la compra:", error);
        return res.status(500).json({ message: "Error en el servidor.", error: error.message });
    }
};

//Obtención de aquellos productos aquiridos por un determinado usuario
const obtenerProductosComprados = async (req, res) => {
    const { id_usuario } = req.params;

    if (!id_usuario || isNaN(id_usuario)) {
        return res.status(400).json({ message: "ID de usuario es obligatorio y debe ser un número válido." });
    }

    try {
        const userId = parseInt(id_usuario, 10);

        const { rows } = await myPool.query(
            "SELECT p.* FROM product p JOIN compra c ON p.id = c.id_producto WHERE c.id_usuario = $1 AND c.confirmado = TRUE",
            [userId]
        );

        if (rows.length === 0) {
            return res.status(404).json({ message: "No se encontraron productos comprados confirmados para este usuario." });
        }

        return res.status(200).json({
            message: "Productos comprados confirmados obtenidos exitosamente.",
            productos: rows
        });

    } catch (error) {
        console.error("Error al obtener productos comprados:", error);
        return res.status(500).json({
            message: "Error en el servidor al obtener productos comprados.",
            error: error.message || "Error desconocido."
        });
    }
};

//Subida de una oferta (En el front tendrá u propia interfaz)
const uploadOffer = async (req, res) => {
    const { imagen, descripcion, precio, precio_oferta, fecha_inicio_oferta, fecha_fin_oferta, user_id, category_id, palabras_clave } = req.body;

    try {
        const { rows } = await myPool.query(
            "INSERT INTO product (imagen, descripcion, precio, precio_oferta, fecha_inicio_oferta, fecha_fin_oferta, user_id, category_id, palabras_clave) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9) RETURNING *",
            [imagen, descripcion, precio, precio_oferta, fecha_inicio_oferta, fecha_fin_oferta, user_id, category_id, palabras_clave]
        );

        res.status(201).json({
            message: "Oferta subida exitosamente",
            product: rows[0]
        });
    } catch (error) {
        console.error("Error al subir oferta:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Obtención de las ofertas adquiridas por un usuario en concreto
const getUserOffers = async (req, res) => {
    try {
        const currentDate = new Date().toISOString().split('T')[0];

        const { rows } = await myPool.query(`
            SELECT *,
                   precio_oferta AS precio_actual
            FROM product
            WHERE precio_oferta IS NOT NULL
              AND fecha_inicio_oferta <= $1
              AND fecha_fin_oferta >= $1
            ORDER BY id
        `, [currentDate]);

        if (rows.length === 0) {
            return res.status(404).json({ message: "No se encontraron ofertas activas." });
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

//Lo mismo, exportación de módulos
module.exports = {
    realizarCompra,
    confirmarCompra,
    obtenerProductosComprados,
    uploadOffer,
    getUserOffers,
    comprarOferta
};