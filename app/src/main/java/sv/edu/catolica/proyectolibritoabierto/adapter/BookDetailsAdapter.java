package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import sv.edu.catolica.proyectolibritoabierto.HomeActivity;
import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.fragment.AgregarPrestamoFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.BookDetailFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.MisPrestamosFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.ReservarFragment;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class BookDetailsAdapter extends FirestoreRecyclerAdapter <Book, BookDetailsAdapter.ViewHolder> implements View.OnClickListener{
    private ImageView image;
    private String url;
    View vista;
    View.OnClickListener listener;
    FirebaseFirestore  bFirestore;
    AppCompatActivity activity;
    Bundle tituloEnviar;
    String email;
    Context context;


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";
    public BookDetailsAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookDetailsAdapter.ViewHolder viewHolder, int i, @NonNull Book book) {
        bFirestore = FirebaseFirestore.getInstance();
        tituloEnviar = new Bundle();
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
        buttonState(viewHolder, book);
        //Boton Prestar
        viewHolder.btnPrestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloEnviar.putString("titulo", book.getTitle());
                activity = (AppCompatActivity) view.getContext();
                AgregarPrestamoFragment fragment = new AgregarPrestamoFragment();
                fragment.setArguments(tituloEnviar);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.detail, fragment).addToBackStack(null).commit();
            }
        });
        //Boton Reservar
        viewHolder.btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloEnviar.putString("titulo", book.getTitle());
                activity = (AppCompatActivity) view.getContext();
                ReservarFragment fragment = new ReservarFragment();
                fragment.setArguments(tituloEnviar);
                fragment.show(activity.getSupportFragmentManager(), "Navegar a Fragment");
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
    private void buttonState(BookDetailsAdapter.ViewHolder viewHolder, Book book){
        context = vista.getContext();
        //Shared Preferences
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        DocumentReference docRef = bFirestore.collection("transaction").document(book.getTitle() + "-" + email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document =task.getResult();
                    if (document.exists()){
                        String correo = String.valueOf(document.getString("email"));
                        if (correo.equals(email)){
                            String estado = String.valueOf(document.getString("state"));
                            if (estado.equals("ACTIVO")){
                                viewHolder.btnPrestar.setEnabled(false);
                                viewHolder.btnReservar.setEnabled(false);
                            } else{
                                viewHolder.btnPrestar.setEnabled(true);
                                viewHolder.btnReservar.setEnabled(true);
                            }
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
    }
}
