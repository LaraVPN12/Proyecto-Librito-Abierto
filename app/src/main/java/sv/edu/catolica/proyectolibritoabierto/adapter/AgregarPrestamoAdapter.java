package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.model.Book;

public class AgregarPrestamoAdapter extends FirestoreRecyclerAdapter <Book, AgregarPrestamoAdapter.ViewHolder> implements View.OnClickListener{
    View vista;
    View.OnClickListener listener;
    public AgregarPrestamoAdapter(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Book book) {
        viewHolder.titulo.setText(book.getTitle());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_agregar_prestamo, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onClick(View view) {
        if (listener != null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        Button prestamo, devolucion, realizarPrestamo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloLibro);
            prestamo = itemView.findViewById(R.id.fechaPr);
            devolucion = itemView.findViewById(R.id.fechaDev);
            realizarPrestamo = itemView.findViewById(R.id.ejecutarPrestamo);
        }
    }
}
