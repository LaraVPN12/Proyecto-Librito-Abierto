package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import sv.edu.catolica.proyectolibritoabierto.R;

public class ReservarFragment extends DialogFragment {
    View view;
    Bundle tituloRecuperado;
    TextView texto;
    Button cancelar, aceptar;
    String tituloSeleccionado;
    Calendar calendar;

    //Firebase
    FirebaseFirestore firestore;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_reservar, container, false);
        texto = view.findViewById(R.id.pregunta);
        cancelar = view.findViewById(R.id.cancelar);
        aceptar = view.findViewById(R.id.aceptar);
        tituloRecuperado = getArguments();
        tituloSeleccionado = tituloRecuperado.getString("titulo");
        texto.setText("¿Desea reservar el libro\n" + tituloSeleccionado + " ?");

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postReservation();
                getDialog().dismiss();
            }
        });
        return view;
    }
    private void postReservation(){
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        //Fecha de Reserva Detalle
        Date dateNow = new Date();
        //Fecha de Reserva Simple
        String stringDateReserva = DateFormat.getDateInstance().format(dateNow);
        //Fecha de Devolucion Detalle
        Date dateTomorrow;
        calendar.setTime(dateNow);
        calendar.add(Calendar.DATE,1);
        dateTomorrow = calendar.getTime();
        //Fecha de Devolucion Simple
        String stringDateDevolucion = DateFormat.getDateInstance().format(dateTomorrow);
        int ejemplares = 1;
        Map<String, Object>map = new HashMap<>();
        map.put("transaction_type", "RESERVA");
        map.put("book_title", tituloSeleccionado);
        map.put("fecha_prestamo", stringDateReserva);
        map.put("fecha_prestamo_detalle", dateNow);
        map.put("fecha_devolucion", stringDateDevolucion);
        map.put("fecha_devolucion_detalle", dateTomorrow);
        map.put("email", email);
        map.put("copy_quantity", ejemplares);
        map.put("state", "ACTIVO");

        firestore.collection("transaction").document(tituloSeleccionado + "-" + email).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                updateBook(ejemplares, tituloSeleccionado);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void updateBook(int ejemplares, String book_title){
        DocumentReference documentReference = firestore.collection("book").document(book_title);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        //Actualizacion del libro
                        double bd_copy_quantity = document.getDouble("copy_quantity");
                        double updated_quantity = bd_copy_quantity - ejemplares;

                        Map<String, Object> updateMap = new HashMap<>();
                        updateMap.put("copy_quantity",updated_quantity);
                        firestore.collection("book").document(book_title).update(updateMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    } else {
                        Log.v("MENSAJE", "No such document");
                    }
                } else {
                    Log.v("MENSAJE", "FAILED ", task.getException());
                }
            }
        });
    }
}