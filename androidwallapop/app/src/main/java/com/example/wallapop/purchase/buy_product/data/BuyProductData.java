package com.example.wallapop.purchase.buy_product.data;

public class BuyProductData {
    private String message;
    private PurchaseInfo compra;

    // Nueva propiedad para la descripción del producto
    private String descripcionProducto;

    // Anidamos las dos clases ya que no necesitamos separarlas
    public static class PurchaseInfo {
        private int id_compra;
        private int id_usuario;
        private int id_producto;
        private boolean confirmado;

        // Getters y setters
        public int getIdCompra() { return id_compra; }
        public void setIdCompra(int id_compra) { this.id_compra = id_compra; }
        public int getIdUsuario() { return id_usuario; }
        public void setIdUsuario(int id_usuario) { this.id_usuario = id_usuario; }
        public int getIdProducto() { return id_producto; }
        public void setIdProducto(int id_producto) { this.id_producto = id_producto; }
        public boolean isConfirmado() { return confirmado; }
        public void setConfirmado(boolean confirmado) { this.confirmado = confirmado; }
    }

    // Getters y setters para BuyProductData
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public PurchaseInfo getCompra() { return compra; }
    public void setCompra(PurchaseInfo compra) { this.compra = compra; }

    // Getter y setter para la descripción del producto
    public String getDescripcionProducto() { return descripcionProducto; }
    public void setDescripcionProducto(String descripcionProducto) { this.descripcionProducto = descripcionProducto; }
}
