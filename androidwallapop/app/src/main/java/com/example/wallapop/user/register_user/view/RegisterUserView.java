package com.example.wallapop.user.register_user.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.wallapop.R;
import com.example.wallapop.beans.User;
import com.example.wallapop.user.login_user.view.LoginUserM;
import com.example.wallapop.user.register_user.ContractRegisterUser;
import com.example.wallapop.user.register_user.presenter.RegisterUserPresenter;

public class RegisterUserView extends AppCompatActivity implements ContractRegisterUser.View {
    private EditText edtRegisterEmail;
    private EditText edtRegisterPassword;
    private Button btnRegister;
    private Spinner userRole;
    private RegisterUserPresenter presenter;
    private Button btnLoginRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_m);
        presenter = new RegisterUserPresenter(this);
        initComponents();
    }

    private void initComponents() {
        // Inicializa los componentes de la interfaz de usuario
        edtRegisterEmail = findViewById(R.id.edtRegisterEmail);
        edtRegisterPassword = findViewById(R.id.edtRegisterPassword);
        btnRegister = findViewById(R.id.btnRegister);
        userRole = findViewById(R.id.spinnerUserRole);
        btnLoginRedirect = findViewById(R.id.tvLoginRedirect);

        // Configurar el Spinner con los roles
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userRole.setAdapter(adapter);

        // Configura el evento onClick para el botón de registro
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtRegisterEmail.getText().toString();
                String password = edtRegisterPassword.getText().toString();

                int selectedRolePosition = userRole.getSelectedItemPosition();
                int roleId;

                // Asignar el ID del rol basado en la posición seleccionada
                switch (selectedRolePosition) {
                    case 0:
                        roleId = 1; // Vendedor
                        break;
                    case 1:
                        roleId = 2; // Comprador
                        break;
                    case 2:
                        roleId = 3; // Administrador
                        break;
                    default:
                        roleId = 1; // Por defecto, asignamos el rol de vendedor
                }

                User user = new User(email, password, roleId);
                presenter.register(user);
            }
        });

        btnLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crea el Intent para iniciar la actividad de inicio de sesión
                Intent intent = new Intent(RegisterUserView.this, LoginUserM.class);
                startActivity(intent); // Inicia la nueva actividad
            }
        });
    }

    @Override
    public void successRegister(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        Intent successfulRegister = new Intent(RegisterUserView.this, LoginUserM.class);
        startActivity(successfulRegister);
    }

    @Override
    public void failureRegister(String err) {
        // Muestra un mensaje de error cuando el registro falla
        Toast.makeText(this, "Error de registro: " + err, Toast.LENGTH_SHORT).show();
    }
}