package com.example.wallapop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.example.wallapop.user.login_user.view.LoginUserM;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Solicitamos permiso de notificación si es necesario
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission();
        } else {
            // Si la versión es anterior a Android 13, iniciamos la actividad de login directamente
            startLoginActivity();
        }
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    NOTIFICATION_PERMISSION_CODE);
        } else {
            // El permiso ya está concedido, inicia la actividad de login
            startLoginActivity();
        }
    }

    @Override //Debemos solicitar permiso de notificaciones
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de notificación concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de notificación denegado", Toast.LENGTH_SHORT).show();
            }
            // Independientemente del resultado, inicia la actividad de login
            startLoginActivity();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginUserM.class);
        startActivity(intent);
        finish(); // Cierra MainActivity para que no se pueda volver atrás
    }
}