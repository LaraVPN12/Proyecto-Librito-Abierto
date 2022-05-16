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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recycler;
    BookAdapter bAdapter;
    FirebaseFirestore bFirestore;
    androidx.appcompat.widget.SearchView buscar;
    View vista;
    String busqueda;
    Query query;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusquedaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusquedaFragment newInstance(String param1, String param2) {
        BusquedaFragment fragment = new BusquedaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        busqueda = "";
        bFirestore = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_busqueda, container, false);
        //SearchView
        buscar = vista.findViewById(R.id.searchView);
        initListener();
        recycler = vista.findViewById(R.id.rvDatos);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getBusqueda().isEmpty()){
            query = bFirestore.collection("book");
        } else {
            Log.v("MENSAJE", "Entre");
            query = bFirestore.collection("book")
                    .whereEqualTo("author", "Viola Davis")
            ;
        }
        FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
        bAdapter = new BookAdapter(fro);
        bAdapter.notifyDataSetChanged();
        recycler.setAdapter(bAdapter);
        return vista;
    }

    public void initListener(){
        buscar.setOnQueryTextListener(this);
    }

    public String getBusqueda(){
        return busqueda;
    }
    public void filter(){
        if (busqueda.isEmpty()){
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            Query query = bFirestore.collection("book");
            FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
            bAdapter = new BookAdapter(fro);
            bAdapter.notifyDataSetChanged();
            recycler.setAdapter(bAdapter);
        } else{
            Log.v("MENSAJE", "Entre");
            recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            Query query = bFirestore.collection("book")
                    .whereEqualTo("author", "Viola Davis")
                    ;
            FirestoreRecyclerOptions<Book> fro = new FirestoreRecyclerOptions.Builder<Book>().setQuery(query, Book.class).build();
            bAdapter = new BookAdapter(fro);
            bAdapter.notifyDataSetChanged();
            recycler.setAdapter(bAdapter);
        }
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

    @Override
    public boolean onQueryTextSubmit(String s) {
        busqueda = s;
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        busqueda = s;
        //Log.v("MENSAJE", busqueda);
        return false;
    }

}