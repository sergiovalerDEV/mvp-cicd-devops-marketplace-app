const cors = require("cors");
const express = require("express");
const bodyParser = require("body-parser");
const dotenv = require("dotenv");

// Importación de las rutas
const authUserRoutes = require("./routes/authUser.routes");
const authProductRoutes = require("./routes/authProduct.routes");
const authPurchaseRoutes = require("./routes/authPurchase.routes");

// Middleware para manejar errores
const errorHandler = require("./middleware/errorHandler");

// Cargar las variables de entorno desde el archivo .env
dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware para habilitar CORS
app.use(cors());

// Middleware para parsear el cuerpo de las solicitudes en formato JSON
app.use(bodyParser.json());

// Rutas de autenticación de usuario
app.use("/auth", authUserRoutes);

// Rutas de productos
app.use("/products", authProductRoutes);

// Rutas de compras
app.use("/purchases", authPurchaseRoutes);

// Middleware para el manejo de errores (debe ir después de todas las rutas)
app.use(errorHandler); 

// Exportar la aplicación para poder utilizarla en otros módulos o pruebas
module.exports = app;

// Iniciar el servidor, indicando el puerto en el que funciona
app.listen(PORT, () => {
    console.log(`Servidor corriendo en el puerto ${PORT}`);
});
