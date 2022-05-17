package sv.edu.catolica.proyectolibritoabierto;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    String id;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        firestore =FirebaseFirestore.getInstance();
        Query query = firestore.collection("session_state").orderBy("state").whereEqualTo("id_phone", id.toString());

        //SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                if (email != null){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 1500);
    }
}