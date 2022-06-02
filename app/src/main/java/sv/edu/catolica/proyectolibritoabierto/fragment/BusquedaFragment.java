package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.adapter.BookAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BusquedaFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recycler;
    BookAdapter bAdapter;
    FirebaseFirestore bFirestore;
    androidx.appcompat.widget.SearchView buscar;
    View vista;
    String busqueda;
    Query query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        busqueda = "";
        bFirestore = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_busqueda, container, false);
        //SearchView
        buscar = vista.findViewById(R.id.searchView);
        initListener();
        recycler = vista.findViewById(R.id.rvDatos);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        queryStructure(busqueda);
        return vista;
    }

    public void initListener(){
        buscar.setOnQueryTextListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        queryStructure(busqueda);
        bAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bAdapter.stopListening();

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        busqueda = s;
        queryStructure(busqueda);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        busqueda = s;
        return false;
    }
    private void queryStructure(String busqueda){
        if (busqueda.isEmpty()){
            query = bFirestore.collection("book");
        } else {
            query = bFirestore.collection("book").whereEqualTo("title", busqueda);
        }
        FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
        bAdapter = new BookAdapter(fro);
        bAdapter.notifyDataSetChanged();
        recycler.setAdapter(bAdapter);
    }
}