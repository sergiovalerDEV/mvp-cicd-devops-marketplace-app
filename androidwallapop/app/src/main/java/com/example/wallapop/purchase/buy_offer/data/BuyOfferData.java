package com.example.wallapop.purchase.buy_offer.data;

public class BuyOfferData {
    private String message;
    private PurchaseInfo compra;

    public static class PurchaseInfo {
        private int id_compra;
        private int id_usuario;
        private int id_oferta;
        private boolean confirmado;

        public int getIdCompra() { return id_compra; }
        public void setIdCompra(int id_compra) { this.id_compra = id_compra; }
        public int getIdUsuario() { return id_usuario; }
        public void setIdUsuario(int id_usuario) { this.id_usuario = id_usuario; }
        public int getIdOferta() { return id_oferta; }
        public void setIdOferta(int id_oferta) { this.id_oferta = id_oferta; }
        public boolean isConfirmado() { return confirmado; }
        public void setConfirmado(boolean confirmado) { this.confirmado = confirmado; }
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public PurchaseInfo getCompra() { return compra; }
    public void setCompra(PurchaseInfo compra) { this.compra = compra; }
}