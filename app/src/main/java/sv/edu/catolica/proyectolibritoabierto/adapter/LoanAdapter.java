package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.model.Loan;

public class LoanAdapter extends FirestoreRecyclerAdapter <Loan, LoanAdapter.ViewHolder> {
    View vista;

    public LoanAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull LoanAdapter.ViewHolder viewHolder, int i, @NonNull Loan loan) {
        viewHolder.transaction_type.setText(loan.getTransaction_type());
        viewHolder.book_title.setText(loan.getBook_title());
        viewHolder.copy_quantity.setText(String.valueOf(loan.getCopy_quantity()));
        viewHolder.fecha_prestamo.setText(loan.getFecha_prestamo());
        viewHolder.fecha_devolucion.setText(loan.getFecha_devolucion());
        viewHolder.state.setText(loan.getState());
    }

    @NonNull
    @Override
    public LoanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.prestamo_card, parent, false);
        return new ViewHolder(vista);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_title, fecha_prestamo, fecha_devolucion, transaction_type, copy_quantity, state;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title = itemView.findViewById(R.id.book_title);
            fecha_prestamo = itemView.findViewById(R.id.fecha_prestamo);
            fecha_devolucion = itemView.findViewById(R.id.fecha_devolucion);
            transaction_type = itemView.findViewById(R.id.transaction_type);
            copy_quantity = itemView.findViewById(R.id.copy_quantity);
            state = itemView.findViewById(R.id.state);
        }
    }
}
