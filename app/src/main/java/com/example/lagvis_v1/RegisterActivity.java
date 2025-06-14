package com.example.lagvis_v1;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends BaseActivity {

    private EditText emailTextView, passwordTextView, passwordTextView2;
    private Button button;
    private FirebaseAuth auth;

    Drawable errorIcon;

    Drawable checkIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
         errorIcon = getDrawable(R.drawable.ic_error_outline);
         checkIcon = getDrawable(R.drawable.ic_check_circle);
        auth = FirebaseAuth.getInstance();
        emailTextView = findViewById(R.id.email_edittext);
        passwordTextView = findViewById(R.id.password_edittext);
        passwordTextView2 = findViewById(R.id.password_edittext2);
        button = findViewById(R.id.register_button);
        button.setOnClickListener(v -> registerNewUser());
    }

    private void registerNewUser() {
       
        String email = emailTextView.getText().toString().trim();
        String password = passwordTextView.getText().toString().trim();
        String password2 = passwordTextView2.getText().toString().trim();



        if (!password.equals(password2)) {
            
            showCustomToast("Por favor, introduce la misma contraseña!", errorIcon);
            return;
        }

// Validar credenciales vacías
        if (email.isEmpty() || password.isEmpty()) {
            showCustomToast("Por favor, introduce unas credenciales!", errorIcon);
            return;
        }
// Validar contraseña con mínimo 6 caracteres, una mayúscula, una minúscula y un número
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{6,}$";
        if (!password.matches(passwordPattern)) {
            showCustomToast("La contraseña debe tener al menos 6 caracteres, incluir una mayúscula, una minúscula y un número!", errorIcon);
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Iniciar MainActivity si se ha registrado correctamente
                            startActivity(new Intent(RegisterActivity.this, AdvancedFormRegister.class));
                            finish();

                            FirebaseUser user = auth.getCurrentUser();

                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //Toast.makeText(RegisterActivity.this, "EMAIL ENVIADO SOLO DEPURACION!!!!", Toast.LENGTH_LONG).show();

                                            }
                                        }
                                    });

                        } else {
                            // Error al registrarse
                            showCustomToast("ERROR ¡Por favor revisa todos los campos!", errorIcon);
                        }
                    }
                });
    }

}
