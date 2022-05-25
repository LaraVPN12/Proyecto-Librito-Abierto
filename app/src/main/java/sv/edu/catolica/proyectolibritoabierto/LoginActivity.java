package sv.edu.catolica.proyectolibritoabierto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements TextWatcher {
    private TextInputLayout email;
    private TextInputLayout pass;

    //Firebase
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLayout);
        pass = findViewById(R.id.passwordLayout);

        //Firebase
        firestore = FirebaseFirestore.getInstance();
    }

    public void volverPaginaPrincipal(View view) {
        finish();
        Intent backToHomePage = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(backToHomePage);
    }

    private boolean emailFormat (String email){
        Pattern patron = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@”]+(\\.[^<>()\\[\\]\\\\.,;:\\s@”]+)*)|(“.+”))@((\\[[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}\\.[0–9]{1,3}])|(([a-zA-Z\\-0–9]+\\.)+[a-zA-Z]{2,}))$");
        if(patron.matcher(email).matches()){
            return true;
        }
        return false;
    }
    private boolean passFormat(String pass){
        Pattern patron = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}");
        if(patron.matcher(pass).matches()){
            return true;
        }
        return false;
    }
    private boolean isValidEmail(){
        String mail = email.getEditText().getText().toString().trim();
        if(mail.isEmpty()){
            email.setError("El Correo es requerido");
            return false;
        }
        else if(!emailFormat(mail)){
            email.setError("Formato de Correo no válido");
            return false;
        }
        else{
            email.setError(null);
            return true;
        }
    }
    private boolean isValidPass(){
        String password = pass.getEditText().getText().toString().trim();
        if(password.isEmpty()){
            pass.setError("La Contraseña es requerida");
            return false;
        }
        else if(password.length()<8){
            pass.setError("La Contraseña debe contener 8 caracteres");
            return false;
        }
        else if(password.length()>8){
            pass.setError("La Contraseña sobrepasa los 8 caracteres");
            return false;
        }
        else if(password.length()==8){
            if(!passFormat(password)){
                pass.setError("Debe contener al menos un numero y una mayúscula");
                return false;
            } else{
                pass.setError(null);
                return true;
            }
        }
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        isValidEmail();
        isValidPass();
    }
    public void iniciarSesion(View view) {
    }
}