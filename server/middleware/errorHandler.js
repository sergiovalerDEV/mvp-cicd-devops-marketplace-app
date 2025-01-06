// middleware/errorHandler.js
const errorHandler = (err, req, res, next) => {
    console.error(err.stack); // Imprime el stack trace del error en la consola
    res.status(500).json({ message: "Error en el servidor" }); // Responde con un mensaje genérico
};

module.exports = errorHandler;