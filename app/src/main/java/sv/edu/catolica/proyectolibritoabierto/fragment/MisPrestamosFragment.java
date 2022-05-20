package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.adapter.BookAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Book;
import sv.edu.catolica.proyectolibritoabierto.interfaces.*;

public class MisPrestamosFragment extends Fragment{
    RecyclerView recycler;
    BookAdapter bAdapter;
    FirebaseFirestore bFirestore;
    View vista;
    Query query;
    FloatingActionButton fab;
    Activity activity;
    IComunicarFragments interfaceComunicator;



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

        //Inicializar Recycler
        recycler = vista.findViewById(R.id.rvDatos);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        query = bFirestore.collection("book");
        FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
        bAdapter = new BookAdapter(fro);
        bAdapter.notifyDataSetChanged();
        recycler.setAdapter(bAdapter);

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
        bAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bAdapter.stopListening();
    }

}