package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.fragment.BookDetailFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.BusquedaFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.MiPerfilFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.MisPrestamosFragment;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BookAdapter extends FirestoreRecyclerAdapter <Book, BookAdapter.ViewHolder> implements View.OnClickListener{
    private ImageView image;
    private String url;
    View v;
    private View.OnClickListener listener;




    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BookAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull BookAdapter.ViewHolder viewHolder, int i, @NonNull Book book) {
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        viewHolder.description.setText(book.getSummary());
        url = book.getBook_image();
        Glide.with(v)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle tituloEnviar = new Bundle();
                tituloEnviar.putString("titulo", book.getTitle());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                BookDetailFragment details = new BookDetailFragment();
                details.setArguments(tituloEnviar);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rec, details).addToBackStack(null).commit();
            }
        });
    }
    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }


    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.libro);
            author = itemView.findViewById(R.id.autor);
            description = itemView.findViewById(R.id.descrip);
            image = itemView.findViewById(R.id.imagen);
        }
    }


}
