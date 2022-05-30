package sv.edu.catolica.proyectolibritoabierto.fragment;

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
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.adapter.BookDetailsAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BookDetailFragment extends Fragment {
    RecyclerView recycler;
    BookDetailsAdapter bookDetailsAdapter;
    FirebaseFirestore bFirestore;
    View vista;
    Query query;
    Bundle tituloRecuperado;
    String email, tituloSeleccionado;
    Button btnPrestar, btnReservar;

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
        vista = inflater.inflate(R.layout.fragment_book_detail, container, false);

        //Inicializacion de Elementos
        recycler = vista.findViewById(R.id.rvDetails);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        tituloRecuperado = getArguments();
        tituloSeleccionado = tituloRecuperado.getString("titulo");
        btnPrestar = vista.findViewById(R.id.btnPrestar);
        btnReservar = vista.findViewById(R.id.btnReservar);
        getBookDetails(tituloSeleccionado);
        return vista;
    }

    public void getBookDetails(String titulo){
        query = bFirestore.collection("book").whereEqualTo("title", titulo);
        FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
        bookDetailsAdapter = new BookDetailsAdapter(fro);
        bookDetailsAdapter.notifyDataSetChanged();
        recycler.setAdapter(bookDetailsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        bookDetailsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bookDetailsAdapter.stopListening();
    }

}