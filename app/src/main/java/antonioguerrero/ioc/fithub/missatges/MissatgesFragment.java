package antonioguerrero.ioc.fithub.missatges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

public class MissatgesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MissatgesAdapter adapter;
    private List<Missatge> mensajesList;

    public MissatgesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_missatges, container, false);

        // Inicializar RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mensajesList = new ArrayList<>();

        // Inicializar adaptador
        adapter = new MissatgesAdapter(getActivity(), mensajesList); // Pasar el contexto y la lista de mensajes al adaptador
        recyclerView.setAdapter(adapter);

        // Agregar mensajes de ejemplo a la lista
        mensajesList.add(new Missatge("Remitente 1", "Contenido 1", "Fecha 1"));
        mensajesList.add(new Missatge("Remitente 2", "Contenido 2", "Fecha 2"));
        mensajesList.add(new Missatge("Remitente 3", "Contenido 3", "Fecha 3"));

        // Notificar al adaptador sobre el cambio en los datos
        adapter.notifyDataSetChanged();

        /*// Aquí deberías recuperar los mensajes de la base de datos y agregarlos a la lista de mensajes
        cargarMensajesDesdeBaseDeDatos();*/

        return rootView;
    }

    // Método para cargar mensajes desde la base de datos
    /*private void cargarMensajesDesdeBaseDeDatos() {
        // Aquí deberías implementar la lógica para recuperar los mensajes de la base de datos
        // Por ahora, solo agregaremos mensajes de ejemplo a la lista
        mensajesList.add(new Missatge("Remitente 1", "Contenido 1", "Fecha 1"));
        mensajesList.add(new Missatge("Remitente 2", "Contenido 2", "Fecha 2"));
        mensajesList.add(new Missatge("Remitente 3", "Contenido 3", "Fecha 3"));
        // Notificar al adaptador sobre el cambio en los datos
        adapter.notifyDataSetChanged();
    }*/
}
