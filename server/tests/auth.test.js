const request = require('supertest');
const app = require('../server');
const myPool = require('../config/db.config');

// Mock de la conexión a la base de datos
jest.mock('../config/db.config', () => ({
  query: jest.fn(),
}));

// Test para Health Check updateado
describe('Health Check', () => {
  test('Servidor en funcionamiento', async () => {
    const response = await request(app)
      .get('/auth/health');

    expect(response.statusCode).toBe(200);
    expect(response.body).toHaveProperty('message', 'Servidor corriendo correctamente');
  });
});

describe('Wallatest API Integration Tests', () => {
  
  // Tests para autenticación
  describe('Autenticación', () => {
    test('Registro de usuario exitoso', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [] });
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });

      const response = await request(app)
        .post('/auth/register')
        .send({
          email: 'nuevwqedqwedo@ejemplo.com',
          password: 'contwedeedwraseña123',
          role_id: 2
        });

      expect(response.statusCode).toBe(201);
      expect(response.body).toHaveProperty('message', 'Registro exitoso');
    }); 
  });

  // Tests para productos
  describe('Productos', () => {
    test('Subir producto normal', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });

      const response = await request(app)
        .post('/products/upload/normal')
        .send({
          imagen: 'http://ejemplo.com/imagen.jpg',
          descripcion: 'Producto de prueba',
          precio: 100,
          user_id: 1,
          category_id: 1,
          palabras_clave: 'prueba, test'
        });

      expect(response.statusCode).toBe(201);
      expect(response.body).toHaveProperty('message', 'Producto normal subido exitosamente');
    });

    test('Obtener todos los productos', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1, imagen: 'url', descripcion: 'desc', precio: 100 }] });

      const response = await request(app)
        .get('/products');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('products');
      expect(Array.isArray(response.body.products)).toBeTruthy();
    });

    test('Editar producto', async () => {
      myPool.query.mockResolvedValueOnce({ rowCount: 1 });

      const response = await request(app)
        .put('/products/edit')
        .send({
          id: 1,
          imagen: 'http://ejemplo.com/nueva-imagen.jpg',
          descripcion: 'Producto actualizado',
          precio: 150,
          category_id: 2,
          palabras_clave: 'actualizado, test'
        });

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('message', 'Producto actualizado exitosamente.');
    });
  });

  // Tests para compras
  describe('Compras', () => {
    test('Realizar compra', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });

      const response = await request(app)
        .post('/purchases')
        .send({
          id_usuario: 1,
          id_producto: 1
        });

      expect(response.statusCode).toBe(201);
      expect(response.body).toHaveProperty('message', 'Compra realizada exitosamente.');
    });

    test('Confirmar compra', async () => {
      myPool.query.mockResolvedValueOnce({ rowCount: 1 });

      const response = await request(app)
        .post('/purchases/1/confirmar');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('message', 'Compra confirmada exitosamente.');
    });

    test('Obtener productos comprados', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1, descripcion: 'Producto comprado' }] });

      const response = await request(app)
        .get('/purchases/1/confirmadas');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('productos');
      expect(Array.isArray(response.body.productos)).toBeTruthy();
    });
  });

  // Tests para ofertas
  describe('Ofertas', () => {
    test('Subir oferta', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });

      const response = await request(app)
        .post('/purchases/upload-offer')
        .send({
          imagen: 'http://ejemplo.com/oferta.jpg',
          descripcion: 'Oferta de prueba',
          precio: 100,
          precio_oferta: 80,
          fecha_inicio_oferta: '2023-01-01',
          fecha_fin_oferta: '2023-12-31',
          user_id: 1,
          category_id: 1,
          palabras_clave: 'oferta, test'
        });

      expect(response.statusCode).toBe(201);
      expect(response.body).toHaveProperty('message', 'Oferta subida exitosamente');
    });

    test('Obtener ofertas de usuario', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1, descripcion: 'Oferta activa' }] });

      const response = await request(app)
        .get('/purchases/user-offers');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('offers');
      expect(Array.isArray(response.body.offers)).toBeTruthy();
    });

    test('Comprar oferta', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1 }] });

      const response = await request(app)
        .post('/purchases/comprar-oferta')
        .send({
          id_usuario: 1,
          id_oferta: 1
        });

      expect(response.statusCode).toBe(201);
      expect(response.body).toHaveProperty('message', 'Compra de oferta realizada exitosamente.');
    });
  });

  // Tests adicionales
  describe('Funcionalidades adicionales', () => {
    test('Obtener top vendedores', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ email: 'vendedor@ejemplo.com', total_productos: 10 }] });

      const response = await request(app)
        .get('/products/top-vendors');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('topVendors');
      expect(Array.isArray(response.body.topVendors)).toBeTruthy();
    });

    test('Obtener productos por categoría', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1, descripcion: 'Producto de categoría' }] });

      const response = await request(app)
        .get('/products/category/1');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('products');
      expect(Array.isArray(response.body.products)).toBeTruthy();
    });

    test('Buscar productos por palabras clave', async () => {
      myPool.query.mockResolvedValueOnce({ rows: [{ id: 1, descripcion: 'Producto encontrado' }] });

      const response = await request(app)
        .get('/products/keywords/test');

      expect(response.statusCode).toBe(200);
      expect(response.body).toHaveProperty('products');
      expect(Array.isArray(response.body.products)).toBeTruthy();
    });
  });
});

// Forzar la salida después de que todas las pruebas se hayan completado
afterAll(async () => {
  await new Promise(resolve => setTimeout(() => resolve(), 500)); // Espera 500ms para asegurar que todas las operaciones asíncronas se hayan completado
  console.log('Forzando salida de los tests');
  process.exit(0);
});