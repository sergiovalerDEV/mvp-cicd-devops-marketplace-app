const express = require("express");
const {
    realizarCompra,
    confirmarCompra,
    obtenerProductosComprados,
    uploadOffer,
    getUserOffers,
    comprarOferta
} = require("../controllers/authPurchase");

const errorHandler = require("../middleware/errorHandler");

const router = express.Router();

// GET
router.get("/:id_usuario/confirmadas", obtenerProductosComprados);
router.get("/user-offers", getUserOffers);

// POST
router.post("/:id_compra/confirmar", confirmarCompra);
router.post("/", realizarCompra);
router.post("/upload-offer", uploadOffer);
router.post("/comprar-oferta", comprarOferta);

router.use(errorHandler);

module.exports = router;