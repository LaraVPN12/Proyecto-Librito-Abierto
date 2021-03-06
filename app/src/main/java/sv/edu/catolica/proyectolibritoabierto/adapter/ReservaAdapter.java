package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.fragment.AgregarPrestamoFragment;
import sv.edu.catolica.proyectolibritoabierto.fragment.AgregarPrestamosAdminFragment;
import sv.edu.catolica.proyectolibritoabierto.model.Reserva;

public class ReservaAdapter extends FirestoreRecyclerAdapter<Reserva, ReservaAdapter.ViewHolder> {
    View vista;
    AppCompatActivity activity;
    Bundle tituloEnviar;

    public ReservaAdapter(@NonNull FirestoreRecyclerOptions<Reserva> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReservaAdapter.ViewHolder viewHolder, int i, @NonNull Reserva reserva) {
        tituloEnviar = new Bundle();
        viewHolder.transaction_type.setText(reserva.getTransaction_type());
        viewHolder.book_title.setText(reserva.getBook_title());
        viewHolder.copy_quantity.setText(String.valueOf(reserva.getCopy_quantity()));
        viewHolder.fecha_prestamo.setText(reserva.getFecha_prestamo());
        viewHolder.fecha_devolucion.setText(reserva.getFecha_devolucion());
        viewHolder.state.setText(reserva.getState());
        viewHolder.user.setText(reserva.getEmail());

        viewHolder.efectuarPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tituloEnviar.putString("titulo", reserva.getBook_title());
                activity = (AppCompatActivity) view.getContext();
                AgregarPrestamosAdminFragment fragment = new AgregarPrestamosAdminFragment();
                fragment.setArguments(tituloEnviar);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.adminView, fragment).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public ReservaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserva_card, parent, false);
        return new ViewHolder(vista);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_title, fecha_prestamo, fecha_devolucion, transaction_type, copy_quantity, state, user;
        Button efectuarPrestamo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title = itemView.findViewById(R.id.book_title_reservation);
            fecha_prestamo = itemView.findViewById(R.id.fecha_prestamo_reservation);
            fecha_devolucion = itemView.findViewById(R.id.fecha_devolucion_reservation);
            transaction_type = itemView.findViewById(R.id.transaction_type_reservation);
            copy_quantity = itemView.findViewById(R.id.copy_quantity_reservation);
            state = itemView.findViewById(R.id.state_reservation);
            user = itemView.findViewById(R.id.usuario);
            efectuarPrestamo = itemView.findViewById(R.id.efectuarPrestamo);
        }
    }
}
