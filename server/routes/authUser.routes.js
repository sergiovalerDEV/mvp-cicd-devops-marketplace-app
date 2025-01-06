const express = require("express");
const { registerUser, loginUser, healthCheck } = require("../controllers/authUser");
const errorHandler = require("../middleware/errorHandler");

const router = express.Router();

// POST
router.post("/register", registerUser);
router.post("/login", loginUser);

// GET route for health check
router.get("/health", healthCheck);

router.use(errorHandler);

module.exports = router;