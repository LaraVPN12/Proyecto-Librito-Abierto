package sv.edu.catolica.proyectolibritoabierto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import sv.edu.catolica.proyectolibritoabierto.R;
import sv.edu.catolica.proyectolibritoabierto.model.Transaccion;

public class TransaccionAdapter extends BaseAdapter {

    private Context context;
    private List<Transaccion> listaTransaccion;

    public TransaccionAdapter(Context context, List<Transaccion> listaTransaccion){
        this.context = context;
        this.listaTransaccion = listaTransaccion;
    }

    @Override
    public int getCount() {
        return listaTransaccion.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTransaccion.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista = view;
        LayoutInflater inflater = LayoutInflater.from(context);
        vista = inflater.inflate(R.layout.item_spinner, null);
        TextView text = (TextView) vista.findViewById(R.id.txtOpcion);
        text.setText(listaTransaccion.get(i).getTransaction_type());
        return vista;
    }
}
