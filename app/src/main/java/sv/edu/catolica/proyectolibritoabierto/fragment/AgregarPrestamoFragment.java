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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import sv.edu.catolica.proyectolibritoabierto.R;

public class AgregarPrestamoFragment extends Fragment{
    View vista;
    Bundle tituloRecuperado;
    TextView titulo;
    Button fechaPr, fechaDev, ejecutarPrestamos;
    FirebaseFirestore firestore;

    //DatePicker
    MaterialDatePicker.Builder builder1 = MaterialDatePicker.Builder.datePicker();
    MaterialDatePicker materialDatePicker1;
    MaterialDatePicker.Builder builder2 = MaterialDatePicker.Builder.datePicker();
    MaterialDatePicker materialDatePicker2;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    //Calendar
    Calendar calendar;
    long today;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();

        //Date Picker
        calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        //Toma el dia actual
        today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);
        //Limites del calendario
        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        calendarConstraints.setValidator(DateValidatorPointForward.now());

        builder1.setTitleText("SELECCIONE UNA FECHA");
        builder1.setSelection(today);
        builder1.setCalendarConstraints(calendarConstraints.build());
        materialDatePicker1 = builder1.build();

        builder2.setTitleText("SELECCIONE UNA FECHA");
        builder2.setCalendarConstraints(calendarConstraints.build());
        materialDatePicker2 = builder1.build();

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inicialización de Componentes
        vista = inflater.inflate(R.layout.fragment_agregar_prestamo, container, false);
        titulo = vista.findViewById(R.id.tituloLibro);
        tituloRecuperado = getArguments();
        String tituloSeleccionado = tituloRecuperado.getString("titulo");
        titulo.setText(tituloSeleccionado);
        fechaPr = vista.findViewById(R.id.fechaPr);
        fechaDev = vista.findViewById(R.id.fechaDev);
        ejecutarPrestamos = vista.findViewById(R.id.ejecutarPrestamo);

        //Retornar Date Pickers
        clickListeners();

        return vista;
    }
    public void clickListeners(){
        fechaPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker1.show(getParentFragmentManager(),"DATE_PICKER_1");
            }
        });
        fechaDev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker2.show(getParentFragmentManager(),"DATE_PICKER_2");
            }
        });

        materialDatePicker1.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fechaPr.setText(materialDatePicker1.getHeaderText());
            }
        });
        materialDatePicker2.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fechaDev.setText(materialDatePicker2.getHeaderText());
            }
        });

        ejecutarPrestamos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String book_title = titulo.getText().toString().trim();
                String fechaInicio = fechaPr.getText().toString().trim();
                String fechaFin = fechaDev.getText().toString().trim();
                int ejemplares = 1;
                if(book_title.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()){
                    Toast.makeText(view.getContext(),"Datos Incompletos", Toast.LENGTH_LONG).show();
                } else{
                    postLoan(book_title, fechaInicio, fechaFin, ejemplares);

                    hideFragment(view);
                }
            }
        });
    }
    private void postLoan(String book_title, String fechaInicio, String fechaFin, int ejemplares){
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        Map<String, Object> map = new HashMap<>();
        map.put("transaction_type", "PRESTAMO");
        map.put("book_title", book_title);
        map.put("fecha_prestamo", fechaInicio);
        map.put("fecha_devolucion", fechaFin);
        map.put("email", email);
        map.put("copy_quantity", ejemplares);
        map.put("state", "ACTIVO");
        firestore.collection("transaction").document(book_title).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Referencia al documento
                updateBook(ejemplares, book_title);
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
    private void hideFragment(View view){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}