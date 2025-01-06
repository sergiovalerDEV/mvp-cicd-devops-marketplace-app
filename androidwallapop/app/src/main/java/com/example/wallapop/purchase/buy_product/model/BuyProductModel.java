package com.example.wallapop.purchase.buy_product.model;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.wallapop.MainActivity;
import com.example.wallapop.R;
import com.example.wallapop.purchase.buy_product.ContractBuyProduct;
import com.example.wallapop.purchase.buy_product.data.BuyProductData;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.EmailSender;
import com.example.wallapop.utils.RetrofitCliente;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyProductModel implements ContractBuyProduct.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";
    private static final String CHANNEL_ID = "PurchaseConfirmation";
    private static final int NOTIFICATION_ID = 1;

    private final ContractBuyProduct.Model.OnBuyProductListener listener;
    private final Context context;
    private boolean isConfirmed = false;
    private int userId;
    private int productId;
    private String productDescription;
    private String emailRecipient;

    public BuyProductModel(ContractBuyProduct.Model.OnBuyProductListener listener, Context context, String emailRecipient) {
        this.listener = listener;
        this.context = context;
        this.emailRecipient = emailRecipient;
        createNotificationChannel();
    }

    @Override
    public void purchaseProduct(int userId, int productId, String productDescription) {
        if (userId <= 0 || productId <= 0) {
            listener.onPurchaseFailure("ID de usuario o producto inválido.");
            return;
        }

        this.userId = userId;
        this.productId = productId;
        this.productDescription = productDescription;

        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        BuyProductData.PurchaseInfo purchaseInfo = new BuyProductData.PurchaseInfo();
        purchaseInfo.setIdUsuario(userId);
        purchaseInfo.setIdProducto(productId);

        Call<BuyProductData> call = apiService.realizarCompra(purchaseInfo);

        call.enqueue(new Callback<BuyProductData>() {
            @Override
            public void onResponse(@NonNull Call<BuyProductData> call, @NonNull Response<BuyProductData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showConfirmationDialog(response.body().getCompra().getIdCompra());
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(@NonNull Call<BuyProductData> call, @NonNull Throwable t) {
                listener.onPurchaseFailure("Error de red: " + t.getMessage());
            }
        });
    }

    private void handleErrorResponse(Response<BuyProductData> response) {
        String errorMsg;
        try {
            errorMsg = response.errorBody() != null ? response.errorBody().string() : "Error desconocido";
        } catch (IOException e) {
            errorMsg = "Error al procesar la respuesta: " + e.getMessage();
        }
        listener.onPurchaseFailure("Error al realizar la compra: " + errorMsg);
    }

    private void showConfirmationDialog(int purchaseId) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmar Compra")
                .setMessage("¿Estás seguro que quieres confirmar la compra de: " + productDescription + "?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    isConfirmed = true;
                    confirmPurchase(purchaseId);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    isConfirmed = false;
                    listener.onPurchaseFailure("Compra cancelada por el usuario");
                })
                .show();
    }

    @Override
    public void confirmPurchase(int purchaseId) {
        if (!isConfirmed) {
            listener.onPurchaseFailure("La compra no ha sido confirmada por el usuario");
            return;
        }

        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<BuyProductData> call = apiService.confirmarCompra(purchaseId);

        call.enqueue(new Callback<BuyProductData>() {
            @Override
            public void onResponse(@NonNull Call<BuyProductData> call, @NonNull Response<BuyProductData> response) {
                if (response.isSuccessful()) {
                    sendConfirmationEmail(purchaseId);
                    listener.onPurchaseSuccess("Compra confirmada exitosamente");
                } else {
                    listener.onPurchaseFailure("Error al confirmar la compra");
                }
            }

            @Override
            public void onFailure(@NonNull Call<BuyProductData> call, @NonNull Throwable t) {
                listener.onPurchaseFailure("Error de red: " + t.getMessage());
            }
        });
    }

    private void sendConfirmationEmail(int purchaseId) {
        String emailBody = String.format(
                "Confirmación de compra:\n\n" +
                        "ID de Compra: %d\n" +
                        "ID de Usuario: %d\n" +
                        "ID de Producto: %d\n" +
                        "Descripción del Producto: %s\n\n" +
                        "La compra ha sido confirmada exitosamente.",
                purchaseId, userId, productId, productDescription
        );

        EmailSender.sendEmail(
                emailRecipient,
                "Confirmación de compra - ID: " + purchaseId,
                emailBody,
                new EmailSender.EmailCallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("BuyProductModel", "Correo de confirmación enviado correctamente");
                        showConfirmationNotification(purchaseId);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e("BuyProductModel", "Error al enviar el correo de confirmación: " + error);
                        showConfirmationNotification(purchaseId);
                    }
                }
        );
    }

    private void showConfirmationNotification(int purchaseId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Compra Confirmada")
                .setContentText("ID de Compra: " + purchaseId)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(String.format(
                                "Compra confirmada:\n" +
                                        "ID de Compra: %d\n" +
                                        "ID de Usuario: %d\n" +
                                        "ID de Producto: %d\n" +
                                        "Descripción: %s",
                                purchaseId, userId, productId, productDescription)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                notificationManager.notify(NOTIFICATION_ID, builder.build());
            } else {
                Log.w("BuyProductModel", "Notification permission not granted");
                Toast.makeText(context, "No se puede mostrar la notificación debido a permisos", Toast.LENGTH_SHORT).show();
            }
        } else {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Purchase Confirmation";
            String description = "Channel for Purchase Confirmation";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}