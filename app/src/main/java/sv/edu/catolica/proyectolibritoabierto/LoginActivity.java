package sv.edu.catolica.proyectolibritoabierto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity{
    private TextInputLayout email;
    private TextInputLayout pass;
    boolean emailState, passwordState;
    String correo, contraseña;

    //Firebase
    FirebaseFirestore firestore;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.correoLayout);
        pass = findViewById(R.id.passLayout);
        emailState = false;
        passwordState = false;

        //Inicializar elementos



        //Firebase
        firestore = FirebaseFirestore.getInstance();

        //SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
    }

    public void volverPaginaPrincipal(View view) {
        finish();
        Intent backToHomePage = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(backToHomePage);
    }

    public void iniciarSesion(View view) {
        correo = email.getEditText().getText().toString().trim();
        contraseña = pass.getEditText().getText().toString().trim();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (correo.isEmpty() || contraseña.isEmpty()){
            builder.setMessage("Los campos no pueden estar vacios");
            builder.setTitle("ALERTA");
            builder.setPositiveButton("OK",null);
            builder.create();
            builder.show();
        }
        else {
            DocumentReference documentReference = firestore.collection("user").document(correo);
            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()){
                            String bdEmail = String.valueOf(document.getString("email"));
                            if (correo.equals(bdEmail)){
                                String passwordEncriptado = String.valueOf(document.getString("password"));
                                try {
                                    String passwordDesencriptado = desEncryptPassword(passwordEncriptado, correo);
                                    if (contraseña.equals(passwordDesencriptado)){
                                        //Añadiendo correo al la sharedpreference
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString(KEY_EMAIL, correo);
                                        editor.apply();
                                        finish();

                                        if (correo.equals("kjlmenjivar@gmail.com")){
                                            Intent newAccountView = new Intent(LoginActivity.this, AdminActivity.class);
                                            startActivity(newAccountView);
                                        } else{
                                            Intent newAccountView = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(newAccountView);
                                        }
                                    } else {
                                        builder.setMessage("La contraseña ingresada es incorrecta");
                                        builder.setTitle("ALERTA");
                                        builder.setPositiveButton("OK",null);
                                        builder.create();
                                        builder.show();
                                        pass.getEditText().setText("");

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        } else {
                            builder.setMessage("No se encontró un Usuario con ese correo");
                            builder.setTitle("ALERTA");
                            builder.setPositiveButton("OK",null);
                            builder.create();
                            builder.show();
                        }
                    } else {
                        Log.v("MENSAJE", "FAILED ", task.getException());
                    }
                }
            });
        }
    }
    private String desEncryptPassword(String password, String email) throws Exception{
        SecretKeySpec secretKey = generateKey(email);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte [] datosDescodificados = Base64.decode(password, Base64.DEFAULT);
        byte [] datosDesencriptadosByte = cipher.doFinal(datosDescodificados);
        String datosDesencriptadosString = new String(datosDesencriptadosByte);
        return datosDesencriptadosString;
    }
    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest code = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = code.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
}