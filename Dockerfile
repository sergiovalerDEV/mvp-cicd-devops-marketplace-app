# Usa la imagen oficial de Node.js con la versión 20.17.0
FROM node:20.17.0

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el package.json y package-lock.json del servidor al contenedor
COPY server/package*.json ./server/

# Establece el directorio de trabajo para la instalación de dependencias
WORKDIR /app/server

# Instala herramientas de construcción necesarias para los módulos nativos
RUN apt-get update && apt-get install -y build-essential

# Desinstala el módulo bcrypt actual (si está instalado)
RUN npm uninstall bcrypt

# Instala bcryptjs como reemplazo de bcrypt
RUN npm install bcryptjs

# Copia el resto de los archivos del servidor al contenedor
COPY server/ ./server/

# Vuelve al directorio principal del contenedor
WORKDIR /app

# Copia el resto de los archivos del proyecto al contenedor
COPY . .

# Expone el puerto en el que el servidor escucha (3000 en este caso)
EXPOSE 3000

# Comando para iniciar la aplicación en modo producción
CMD ["node", "server/server.js"]
