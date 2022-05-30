package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.model.Loan;

public class LoanAdminAdapter extends FirestoreRecyclerAdapter<Loan, LoanAdminAdapter.ViewHolder> {
    View vista;
    FirebaseFirestore firestore;
    public LoanAdminAdapter(@NonNull FirestoreRecyclerOptions<Loan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Loan loan) {
        firestore = FirebaseFirestore.getInstance();
        viewHolder.transaction_type.setText(loan.getTransaction_type());
        viewHolder.book_title.setText(loan.getBook_title());
        viewHolder.copy_quantity.setText(String.valueOf(loan.getCopy_quantity()));
        viewHolder.fecha_prestamo.setText(loan.getFecha_prestamo());
        viewHolder.fecha_devolucion.setText(loan.getFecha_devolucion());
        viewHolder.state.setText(loan.getState());
        viewHolder.user.setText(loan.getEmail());
        viewHolder.efectuarDevolucion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTransactionState(loan.getBook_title(), loan.getEmail());
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.prestamo_admin_card, parent, false);
        return new ViewHolder(vista);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView book_title, fecha_prestamo, fecha_devolucion, transaction_type, copy_quantity, state, user;
        Button efectuarDevolucion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            book_title = itemView.findViewById(R.id.book_title);
            fecha_prestamo = itemView.findViewById(R.id.fecha_prestamo);
            fecha_devolucion = itemView.findViewById(R.id.fecha_devolucion);
            transaction_type = itemView.findViewById(R.id.transaction_type);
            copy_quantity = itemView.findViewById(R.id.copy_quantity);
            state = itemView.findViewById(R.id.state);
            user = itemView.findViewById(R.id.usuario);
            efectuarDevolucion = itemView.findViewById(R.id.efectuarDevolucion);
        }
    }
    private void changeTransactionState(String book_title, String email){
        DocumentReference documentReference = firestore.collection("transaction").document(book_title+ "-" + email);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("state","DESACTIVO");
                        firestore.collection("transaction").document(book_title+ "-" + email).update(updateMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });
                    }

                } else {
                    Log.v("MENSAJE", "FAILED ", task.getException());
                }
            }
        });
    }
}
