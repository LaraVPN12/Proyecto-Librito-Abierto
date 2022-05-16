package sv.edu.catolica.proyectolibritoabierto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import sv.edu.catolica.proyectolibritoabierto.adapter.CategorieAdapter;
import sv.edu.catolica.proyectolibritoabierto.model.Categorie;

public class Categories extends AppCompatActivity {
    private RecyclerView recycler;
    private CategorieAdapter cAdapter;
    private FirebaseFirestore cFirestore;
    MaterialCardView card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        cFirestore = FirebaseFirestore.getInstance();
        recycler = findViewById(R.id.cards);
        recycler.setLayoutManager(new GridLayoutManager(this,2));
        Query query = cFirestore.collection("categorie");
        FirestoreRecyclerOptions<Categorie> fro = new FirestoreRecyclerOptions.Builder<Categorie>().setQuery(query, Categorie.class).build();
        cAdapter = new CategorieAdapter(fro);
        cAdapter.notifyDataSetChanged();
        recycler.setAdapter(cAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        cAdapter.stopListening();
    }


}