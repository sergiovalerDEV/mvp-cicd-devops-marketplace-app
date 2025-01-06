const bcrypt = require('bcryptjs');
// Biblioteca utilizada para encriptar contraseñas
const myPool = require("../config/db.config"); // Pool de conexión a la base de datos PostgreSQL

//Prueba de salud
const healthCheck = (req, res) => {
    res.status(200).json({ message: "Servidor corriendo correctamente" });
};

// Registro de usuario
const registerUser = async (req, res) => {
    const { email, password, role_id } = req.body;

    try {
        // Verificamos si el usuario ya existe
        const { rows: existingUser } = await myPool.query("SELECT * FROM \"usuario\" WHERE email = $1", [email]);

        if (existingUser.length > 0) {
            return res.status(400).json({ message: "El usuario ya existe" });
        }

        // Encriptamos la contraseña
        const hashedPassword = await bcrypt.hash(password, 10);

        // Insertamos el nuevo usuario en la base de datos
        await myPool.query("INSERT INTO \"usuario\" (email, password, role_id) VALUES ($1, $2, $3)", [email, hashedPassword, role_id]);

        res.status(201).json({ message: "Registro exitoso" });
    } catch (error) {
        console.error("Error durante el registro:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

// Inicio de sesión
const loginUser = async (req, res) => {
    const { email, password } = req.body;

    try {
        // Verificamos si el usuario existe
        const { rows: users } = await myPool.query("SELECT * FROM \"usuario\" WHERE email = $1", [email]);

        if (users.length === 0) {
            return res.status(400).json({ message: "Credenciales inválidas" });
        }

        const user = users[0];

        // Verificamos la contraseña
        const isPasswordMatch = await bcrypt.compare(password, user.password);

        if (!isPasswordMatch) {
            return res.status(400).json({ message: "Credenciales inválidas" });
        }

        // Enviar respuesta al cliente
        res.status(200).json({
            message: "Sesión iniciada correctamente",
            user: {
                id: user.id,
                email: user.email,
                role_id: user.role_id // Añadimos el role_id
            }
        });
    } catch (error) {
        console.error("Error durante el login:", error);
        res.status(500).json({ message: "Error en el servidor", error: error.message });
    }
};

//Exportación de los módulos correspondientes
module.exports = {
    healthCheck,
    registerUser,
    loginUser,
}