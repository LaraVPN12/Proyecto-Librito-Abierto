package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import sv.edu.catolica.proyectolibritoabierto.HomeActivity;
import sv.edu.catolica.proyectolibritoabierto.NewAcountActivity;
import sv.edu.catolica.proyectolibritoabierto.PrincipalActivity;
import sv.edu.catolica.proyectolibritoabierto.R;

public class MiPerfilFragment extends Fragment {
    View vista;
    TextView cerrarSesion, nombre, correo;
    FirebaseFirestore firestore;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_EMAIL = "email";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firestore = FirebaseFirestore.getInstance();
        vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        cerrarSesion = vista.findViewById(R.id.cerrarSesion);
        nombre = vista.findViewById(R.id.nombre_usuario);
        correo = vista.findViewById(R.id.correo_usuario);

        //SharedPreferences
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(KEY_EMAIL, null);

        DocumentReference documentReference = firestore.collection("user").document(email);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        String first_name = document.getString("first_name");
                        String last_name = document.getString("last_name");
                        String complete_name = first_name + " " + last_name;
                        nombre.setText(complete_name);
                        correo.setText(email);
                    }
                } else {
                    Log.v("MENSAJE", "FAILED ", task.getException());
                }
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                HomeActivity activity = new HomeActivity();
                activity.finish();
                Intent intent = new Intent(getContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });
        return vista;
    }

}