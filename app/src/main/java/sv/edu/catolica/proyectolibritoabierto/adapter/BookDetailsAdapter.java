package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.fragment.AgregarPrestamoFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.BookDetailFragment;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BookDetailsAdapter extends FirestoreRecyclerAdapter <Book, BookDetailsAdapter.ViewHolder> implements View.OnClickListener{
    private ImageView image;
    private String url;
    View vista;
    View.OnClickListener listener;

    public BookDetailsAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookDetailsAdapter.ViewHolder viewHolder, int i, @NonNull Book book) {
        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        viewHolder.description.setText(book.getSummary());
        url = book.getBook_image();
        Glide.with(vista)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
        viewHolder.btnPrestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                AgregarPrestamoFragment fragment = new AgregarPrestamoFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.detail, fragment).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public BookDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_detail_card, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, author, description;
        Button btnPrestar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.libroDetalle);
            author = itemView.findViewById(R.id.autorDetalle);
            description = itemView.findViewById(R.id.descripDetalle);
            image = itemView.findViewById(R.id.imagenDetalle);
            btnPrestar = itemView.findViewById(R.id.btnPrestar);
        }
    }
}
