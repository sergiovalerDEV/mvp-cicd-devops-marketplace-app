package com.example.wallapop.utils;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.top_10_rated_products.data.DataTop10RatedProducts;
import com.example.wallapop.product.top_10_sellers_sales.data.DataTop10SellersSales;
import com.example.wallapop.purchase.buy_offer.data.BuyOfferData;
import com.example.wallapop.product.delete_product.data.DeleteProductData;
import com.example.wallapop.product.edit_offer.data.EditOfferData;
import com.example.wallapop.product.edit_product.data.EditProductData;
import com.example.wallapop.product.lst_admin_offers.data.DataAdminOffers;
import com.example.wallapop.product.lst_all_offers.data.DataAllOffers;
import com.example.wallapop.product.lst_all_products.data.DataAllProducts;
import com.example.wallapop.beans.Product;
import com.example.wallapop.beans.Rating;
import com.example.wallapop.beans.User;
import com.example.wallapop.purchase.buy_product.data.BuyProductData;
import com.example.wallapop.product.category_selection.data.DataCategories;
import com.example.wallapop.product.filter_word.data.FilterWordData;
import com.example.wallapop.product.filtered_products.data.ProductData;
import com.example.wallapop.user.login_user.data.MyData;
import com.example.wallapop.purchase.lst_user_products.data.DataProducts;
import com.example.wallapop.product.purchased_products.data.DataPurchasedProducts;
import com.example.wallapop.product.top_10_products.data.DataTop10Products;
import com.example.wallapop.product.top_10_sellers.data.DataTop10Sellers;
import com.example.wallapop.purchase.upload_offer.data.DataUploadOffer;
import com.example.wallapop.product.upload_product.data.DataUpload;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {


    // Login: Inicia sesión un usuario y devuelve los datos del usuario.
    @POST("auth/login")
    Call<MyData> login(@Body User user);

    // Registro: Registra un nuevo usuario y devuelve los datos del usuario.
    @POST("auth/register")
    Call<MyData> register(@Body User user);

    // Subida de producto: Permite a un usuario subir un nuevo producto.
    @POST("products/upload")
    Call<DataUpload> uploadProduct(@Body Product product);

    // Listado de productos de un usuario determinado: Obtiene la lista de productos de un usuario específico.
    @GET("products/user/{userId}")
    Call<DataProducts> getUserProducts(@Path("userId") int userId);

    // Listado de los 10 vendedores con más productos: Devuelve los 10 vendedores que tienen más productos disponibles.
    @GET("products/top-vendors")
    Call<DataTop10Sellers> getTop10Sellers();

    //Listado de los 10 vendedores con más ventas: Devuelve los 10 vendedores con un mayor número de ventas
    @GET("products/top-vendors-sales")
    Call<DataTop10SellersSales> getTop10SellersSales();

    // Listado de todos los productos: Obtiene una lista de todos los productos disponibles.
    @GET("products")
    Call<DataAllProducts> getAllProducts();

    // Listado de los 10 productos más vendidos: Devuelve los 10 productos con un mayor número de ventas.
    @GET("products/top-sales")
    Call<DataTop10Products> getTop10Products();

    //Listadom de los 10 productos mejor valorados: Devuelve los 10 productos mejor valorados.
    @GET("products/top-rated")
    Call<DataTop10RatedProducts> getTop10RatedProducts();

    // Valorar producto: Permite a un usuario valorar un producto y devuelve los datos del usuario.
    @POST("products/rate")
    Call<MyData> rateProduct(@Body Rating rating);

    // Obtener categorías: Devuelve la lista de categorías disponibles.
    @GET("products/categories")
    Call<DataCategories> getCategories();

    // Obtener productos por categoría: Devuelve los productos que pertenecen a una categoría específica.
    @GET("products/category/{categoryId}")
    Call<ProductData> getProductsByCategory(@Path("categoryId") int categoryId);

    // Obtener productos por palabras clave: Devuelve productos que coinciden con las palabras clave dadas.
    @GET("products/keywords/{keywords}")
    Call<FilterWordData> getProductsByKeywords(@Path("keywords") String keywords);

    // Realizar compra: Inicia el proceso de compra con la información de la compra proporcionada.
    @POST("purchases")
    Call<BuyProductData> realizarCompra(@Body BuyProductData.PurchaseInfo purchaseInfo);

    // Confirmar compra: Confirma una compra existente usando su ID.
    @POST("purchases/{id}/confirmar")
    Call<BuyProductData> confirmarCompra(@Path("id") int purchaseId);

    // Mostrar compras de un usuario: Muestra las compras confirmadas de un usuario
    @GET("purchases/{userId}/confirmadas")
    Call<DataPurchasedProducts> getPurchasedProducts(@Path("userId") int userId);

    // Editar producto: Edita un producto subido por el usuario
    @PUT("products/edit")
    Call<EditProductData> editProduct(@Body Product product);

    // Borrar producto: Borra un producto subido por el usuario
    @DELETE("products/{id}/delete")
    Call<DeleteProductData> deleteProduct(@Path("id") int productId);

    //Borrar oferta: Borra una oferta subida por un admin
    @DELETE("products/{id}/delete")
    Call<DeleteProductData> deleteOffer(@Path("id") int offerId);


    //Listar ofertas: Lista las ofertas disponibles
    @GET("products/list/offers")
    Call<DataAllOffers> listOffers();

    //Listar ofertas de admin: Lista las ofertas del admin para poder editarlas
    @GET("products/list/offers")
    Call<DataAdminOffers> listAdminOffers();

    //Editar ofertas: Interfaz para editar las ofertas
    @PUT("products/edit/offer")
    Call<EditOfferData> editOffer(@Body Offer offer);

    //Comprar oferta: Adquirir una oferta. Todavía falta verificar la compra y sus mensajes
    @POST("purchases/comprar-oferta")
    Call<BuyOfferData> comprarOferta(@Body BuyOfferData.PurchaseInfo purchaseInfo);

    //Subir oferta: Permite a un administrador subir un nuevo producto del tipo oferta
    @POST("purchases/upload-offer")
    Call<DataUploadOffer> uploadOffer(@Body Offer offer);
}