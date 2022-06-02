package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.adapter.BookAdapter;
import sv.edu.catolica.proyectolibritoabierto.adapter.LoanAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Book;
import sv.edu.catolica.proyectolibritoabierto.model.Loan;

public class MisPrestamosFragment extends Fragment{
    RecyclerView recycler;
    LoanAdapter loanAdapter;
    FirebaseFirestore bFirestore;
    View vista;
    Query query;
    String email;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        bFirestore = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_mis_prestamos, container, false);

        //Inicializar componentes
        recycler = vista.findViewById(R.id.rvDatos);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        //Cargar Datos
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        query = bFirestore.collection("transaction").whereEqualTo("email", email).whereEqualTo("state", "ACTIVO");
        FirestoreRecyclerOptions<Loan> fro = new FirestoreRecyclerOptions.Builder<Loan>().setQuery(query, Loan.class).build();
        loanAdapter = new LoanAdapter(fro);
        loanAdapter.notifyDataSetChanged();
        recycler.setAdapter(loanAdapter);
        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
        loanAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        loanAdapter.stopListening();
    }
}