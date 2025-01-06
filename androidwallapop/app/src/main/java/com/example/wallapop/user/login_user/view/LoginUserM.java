package com.example.wallapop.user.login_user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wallapop.R;
import com.example.wallapop.user.admin_first_screen.view.AdminFirstScreen;
import com.example.wallapop.beans.User;
import com.example.wallapop.user.buyer_first_screen.view.BuyerFirstScreen;
import com.example.wallapop.user.login_user.ContractLoginUser;
import com.example.wallapop.user.login_user.presenter.LoginUserPresenter;
import com.example.wallapop.user.register_user.view.RegisterUserView; // Asegúrate de que este es el paquete correcto
import com.example.wallapop.user.seller_first_screen.view.SellerFirstScreen;
import com.example.wallapop.utils.UserSession;

public class LoginUserM extends AppCompatActivity implements ContractLoginUser.View {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;
    private TextView edtRegister; // Cambiado a TextView
    private LoginUserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_m);
        presenter = new LoginUserPresenter(this);
        initComponents();
    }

    private void initComponents() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        edtRegister = findViewById(R.id.edtRegister); // Inicializa el TextView

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                // Crear el objeto User con el email y password
                User user = new User(0, email, password, null); // ID inicial como 0 y role_id como null
                presenter.login(user);
            }
        });

        // Manejar el clic en el TextView de registro
        edtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUserM.this, RegisterUserView.class); // Asegúrate de que RegisterUserView es tu actividad de registro
                startActivity(intent);
            }
        });
    }

    @Override
    public void successLogin(User user) {
        if (user != null) {
            Integer userId = user.getId();
            if (userId != null) {
                // Guardar userId y roleId en UserSession
                UserSession.getInstance().setUserId(userId);
                UserSession.getInstance().setRole_id(user.getRole_id());

                Toast.makeText(this, "Login successful: User ID " + userId + ", Role ID " + user.getRole_id(), Toast.LENGTH_SHORT).show();

                Intent intent;
                switch (UserSession.getInstance().getRole_id()) {
                    case 1:
                        intent = new Intent(this, SellerFirstScreen.class);
                        break;
                    case 2:
                        intent = new Intent(this, BuyerFirstScreen.class);
                        break;
                    default:
                        intent = new Intent(this, AdminFirstScreen.class);
                        break;
                }
                startActivity(intent);


            } else {
                Toast.makeText(this, "Login successful but no user ID assigned", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Login error: user data is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failureLogin(String err) {
        Toast.makeText(this, "Login failed: " + err, Toast.LENGTH_SHORT).show();
    }
}