package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.UUID;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.model.Categorie;


public class CategorieAdapter extends FirestoreRecyclerAdapter <Categorie, CategorieAdapter.ViewHolder>{
    ImageView image;
    String url, id_categorie;
    View view;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategorieAdapter(@NonNull FirestoreRecyclerOptions<Categorie> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Categorie categorie) {
        id_categorie = UUID.randomUUID().toString();
        viewHolder.texto.setText(categorie.getCategorie());
        url = categorie.getImage_categorie();
        Glide.with(view)
                .load(url)
                .centerCrop()
                .into(image);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categorie_card, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView texto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cardImage);
            texto = itemView.findViewById(R.id.cardText);
        }
    }
}
