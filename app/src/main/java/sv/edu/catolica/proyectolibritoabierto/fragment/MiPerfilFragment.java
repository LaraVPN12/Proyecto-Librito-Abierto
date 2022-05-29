package sv.edu.catolica.proyectolibritoabierto.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sv.edu.catolica.proyectolibritoabierto.HomeActivity;
import sv.edu.catolica.proyectolibritoabierto.NewAcountActivity;
import sv.edu.catolica.proyectolibritoabierto.PrincipalActivity;
import sv.edu.catolica.proyectolibritoabierto.R;

public class MiPerfilFragment extends Fragment {
    View vista;
    Button cerrarSesion;

    //SharedPreferences
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_mi_perfil, container, false);
        cerrarSesion = vista.findViewById(R.id.cerrarSesion);

        //SharedPreferences
        sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

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