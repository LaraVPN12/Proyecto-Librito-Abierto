package sv.edu.catolica.proyectolibritoabierto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void crearCuenta(View view) {
        finish();
        Intent newAccountView = new Intent(PrincipalActivity.this, NewAcountActivity.class);
        startActivity(newAccountView);
    }

    public void inicioSesion(View view) {
        finish();
        Intent newAccountView = new Intent(PrincipalActivity.this, LoginActivity.class);
        startActivity(newAccountView);
    }
}