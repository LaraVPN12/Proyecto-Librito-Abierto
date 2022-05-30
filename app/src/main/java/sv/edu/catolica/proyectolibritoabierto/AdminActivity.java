package sv.edu.catolica.proyectolibritoabierto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import sv.edu.catolica.proyectolibritoabierto.adapter.LoanAdapter;
import sv.edu.catolica.proyectolibritoabierto.adapter.LoanAdminAdapter;
import sv.edu.catolica.proyectolibritoabierto.adapter.ReservaAdapter;
import sv.edu.catolica.proyectolibritoabierto.adapter.TransaccionAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Loan;
import sv.edu.catolica.proyectolibritoabierto.model.Reserva;
import sv.edu.catolica.proyectolibritoabierto.model.Transaccion;

public class AdminActivity extends AppCompatActivity {
    private Spinner transact;
    private Transaccion objeto;
    private TransaccionAdapter adapter;
    private ArrayList<Transaccion> listaTransaccion;
    private Button cerrarSesion;

    RecyclerView recycler;
    LoanAdminAdapter loanAdminAdapter;
    ReservaAdapter reservaAdapter;
    FirebaseFirestore bFirestore;
    Query query;
    String email;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //Intsnacia a BD
        bFirestore = FirebaseFirestore.getInstance();

        //Inicializacion de Componentes
        transact = findViewById(R.id.spTransact);
        cargarDatosSpinner();
        adapter = new TransaccionAdapter(this, listaTransaccion);
        transact.setAdapter(adapter);
        recycler = findViewById(R.id.rvDatosAdmin);

        //Shared Preferences
        recycler.setLayoutManager(new LinearLayoutManager(this));
        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        cerrarSesion = findViewById(R.id.cerrarSesionAdmin);

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                HomeActivity activity = new HomeActivity();
                activity.finish();
                Intent intent = new Intent(AdminActivity.this, PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }
    private void cargarDatosSpinner(){
        listaTransaccion = new ArrayList<>();
        listaTransaccion.add(new Transaccion("Prestamos"));
        listaTransaccion.add(new Transaccion("Reservas"));
    }

    @Override
    protected void onStart() {
        super.onStart();

        transact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    query = bFirestore.collection("transaction").whereEqualTo("transaction_type", "PRESTAMO").whereEqualTo("state", "ACTIVO");
                    FirestoreRecyclerOptions<Loan> fro = new FirestoreRecyclerOptions.Builder<Loan>().setQuery(query, Loan.class).build();
                    loanAdminAdapter = new LoanAdminAdapter(fro);
                    loanAdminAdapter.notifyDataSetChanged();
                    recycler.setAdapter(loanAdminAdapter);
                    loanAdminAdapter.startListening();
                }
                else if(i == 1){
                    query = bFirestore.collection("transaction").whereEqualTo("transaction_type", "RESERVA");
                    FirestoreRecyclerOptions<Reserva> fro = new FirestoreRecyclerOptions.Builder<Reserva>().setQuery(query, Reserva.class).build();
                    reservaAdapter = new ReservaAdapter(fro);
                    reservaAdapter.notifyDataSetChanged();
                    recycler.setAdapter(reservaAdapter);
                    reservaAdapter.startListening();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        transact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0){
                    loanAdminAdapter.stopListening();
                }
                else if(i == 1){
                    reservaAdapter.stopListening();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}