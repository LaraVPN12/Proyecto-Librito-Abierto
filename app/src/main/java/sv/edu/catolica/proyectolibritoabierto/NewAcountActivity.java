package sv.edu.catolica.proyectolibritoabierto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sv.edu.catolica.proyectolibritoabierto.model.Categorie;

public class NewAcountActivity extends AppCompatActivity implements TextWatcher {
    private TextInputLayout fname;
    private TextInputLayout lname;
    private TextInputLayout email;
    private TextInputLayout pass;

    //Firebase
    private FirebaseFirestore firestore;
    private boolean state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_acount);
        fname = findViewById(R.id.nameLayout);
        lname = findViewById(R.id.lastNameLayout);
        email = findViewById(R.id.emailLayout);
        pass = findViewById(R.id.passwordLayout);



        //Adding Listener
        fname.getEditText().addTextChangedListener(this);
        lname.getEditText().addTextChangedListener(this);
        email.getEditText().addTextChangedListener(this);
        pass.getEditText().addTextChangedListener(this);

        //Firebase
        firestore = FirebaseFirestore.getInstance();
        state = false;

    }

    public void volverPaginaPrincipal(View view) {
        finish();
        Intent backToHomePage = new Intent(NewAcountActivity.this, PrincipalActivity.class);
        startActivity(backToHomePage);
    }

    private boolean nameFormat (String name){
        Pattern patron = Pattern.compile("^[A-Z].*");
        if(patron.matcher(name).matches()){
            return true;
        }
        return false;
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

    private boolean isValidName(){
        String name = fname.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            fname.setError("El Nombre es requerido");
            return false;
        }
        else if(!nameFormat(name)){
            fname.setError("Debe tener inicial mayúscula");
            return false;
        }
        else{
            fname.setError(null);
            return true;
        }
    }

    private boolean isValidLastName(){
        String lastname = lname.getEditText().getText().toString().trim();
        if(lastname.isEmpty()){
            lname.setError("El Apellido es requerido");
            return false;
        }
        else if(!nameFormat(lastname)){
            lname.setError("Debe tener iniciar mayúscula");
            return false;
        }
        else {
            lname.setError(null);
            return true;
        }
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
    public void afterTextChanged(Editable s) {
        isValidName();
        isValidLastName();
        isValidEmail();
        isValidPass();
    }

    public void crearCuenta(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!isValidName() || !isValidLastName() || !isValidEmail() || !isValidPass()){
            builder.setMessage("Asegurese de completar correctamente los campos");
            builder.setTitle("ALERTA");
            builder.setPositiveButton("OK",null);
            builder.create();
            builder.show();
        }else {
            getValuesForPost();

        }
    }

    private void getValuesForPost() {
        try{
            state = true;
            String id_user = UUID.randomUUID().toString();
            String nombre = fname.getEditText().getText().toString().trim();
            String apellido = lname.getEditText().getText().toString().trim();
            String correo = email.getEditText().getText().toString().trim();
            String contra = encryptPassword(pass.getEditText().getText().toString().trim());

            //INSERT INTO user
            Map<String, Object> UsersMap = new HashMap<>();
            UsersMap.put("id_user",id_user);
            UsersMap.put("first_name", nombre);
            UsersMap.put("last_name", apellido);
            UsersMap.put("email", correo);
            UsersMap.put("password", contra);
            firestore.collection("user").add(UsersMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    finish();
                    Intent newAccountView = new Intent(NewAcountActivity.this, HomeActivity.class);
                    startActivity(newAccountView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Password Encrypt
    private String encryptPassword(String password) throws Exception{
        SecretKeySpec secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] datosEncriptadosByte = cipher.doFinal();
        String datosEncriptadosString = Base64.encodeToString(datosEncriptadosByte, Base64.DEFAULT);
        return datosEncriptadosString;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        MessageDigest code = MessageDigest.getInstance("SHA-256");
        byte[] key = password.getBytes("UTF-8");
        key = code.digest(key);
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        return secretKey;
    }
}