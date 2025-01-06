const express = require("express");
const {
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
} = require("../controllers/authProduct");

const errorHandler = require("../middleware/errorHandler");

const router = express.Router();

// GET
router.get("/", getAllProducts);
router.get("/category/:categoryId", getProductsByCategory);
router.get("/keywords/:keywords", getUserProductsKeyWords);
router.get("/top-vendors", getTopVendors);
router.get("/top-vendors-sales", getTopVendorsSales);
router.get("/top-sales", getTopSales);
router.get("/top-rated", getTopRatedProducts);
router.get("/categories", getAllCategories);
router.get("/user/:userId", getUserProducts);
router.get("/list/offers", listOffers);

// POST
router.post("/upload", uploadProduct);
router.post("/rate", rateProduct);
router.post("/upload/normal", uploadNormalProduct);

// PUT
router.put("/edit", editProduct);
router.put("/edit/offer", editOffer);

// DELETE
router.delete("/:id/delete", deleteProduct);

router.use(errorHandler);

module.exports = router;