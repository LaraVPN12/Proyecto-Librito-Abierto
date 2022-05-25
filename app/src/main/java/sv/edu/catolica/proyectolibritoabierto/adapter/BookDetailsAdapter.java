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
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.fragment.AgregarPrestamoFragment;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BookDetailsAdapter extends FirestoreRecyclerAdapter <Book, BookDetailsAdapter.ViewHolder> implements View.OnClickListener{
    private ImageView image;
    private String url;
    View vista;
    View.OnClickListener listener;
    FirebaseFirestore  bFirestore;

    public BookDetailsAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookDetailsAdapter.ViewHolder viewHolder, int i, @NonNull Book book) {
        bFirestore = FirebaseFirestore.getInstance();

        viewHolder.title.setText(book.getTitle());
        viewHolder.author.setText(book.getAuthor());
        viewHolder.description.setText(book.getSummary());
        viewHolder.copy_quantity.setText(String.valueOf(book.getCopy_quantity()));
        url = book.getBook_image();
        Glide.with(vista)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(image);
        DocumentReference docRef = bFirestore.collection("loan").document(book.getTitle());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document =task.getResult();
                    if (document.exists()){
                        String estado = String.valueOf(document.getString("state"));
                        if (estado.equals("ACTIVO")){
                            viewHolder.btnPrestar.setEnabled(false);
                            viewHolder.btnReservar.setEnabled(false);
                        } else{
                            viewHolder.btnPrestar.setEnabled(true);
                            viewHolder.btnReservar.setEnabled(true);
                        }
                    } else{
                        viewHolder.btnPrestar.setEnabled(true);
                        viewHolder.btnReservar.setEnabled(true);
                    }
                } else{
                    Log.v("MENSAJE", "FAILED ", task.getException());
                }
            }
        });
        viewHolder.btnPrestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle tituloEnviar = new Bundle();
                tituloEnviar.putString("titulo", book.getTitle());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                AgregarPrestamoFragment fragment = new AgregarPrestamoFragment();
                fragment.setArguments(tituloEnviar);
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
        TextView title, author, description, copy_quantity;
        Button btnPrestar, btnReservar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.libroDetalle);
            author = itemView.findViewById(R.id.autorDetalle);
            description = itemView.findViewById(R.id.descripDetalle);
            image = itemView.findViewById(R.id.imagenDetalle);
            copy_quantity = itemView.findViewById(R.id.ejemplares);
            btnPrestar = itemView.findViewById(R.id.btnPrestar);
            btnReservar = itemView.findViewById(R.id.btnReservar);
        }
    }
}
