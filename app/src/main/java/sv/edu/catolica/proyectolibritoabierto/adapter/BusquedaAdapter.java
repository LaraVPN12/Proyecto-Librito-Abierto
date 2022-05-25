package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BusquedaAdapter extends FirestoreRecyclerAdapter <Book, BusquedaAdapter.ViewHolder> implements SearchView.OnQueryTextListener {

    public BusquedaAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BusquedaAdapter.ViewHolder viewHolder, int i, @NonNull Book book) {

    }

    @NonNull
    @Override
    public BusquedaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
