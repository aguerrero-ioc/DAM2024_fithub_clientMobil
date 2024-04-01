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
import antonioguerrero.ioc.fithub.objectes.Missatge;

/**
 * Fragment per mostrar missatges en un RecyclerView.
 */
public class MissatgesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MissatgesAdapter adapter;
    private List<Missatge> mensajesList;

    /**
     * Constructor per defecte del Fragment MissatgesFragment.
     */
    public MissatgesFragment() {
        // Constructor buit
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_missatges, container, false);

        // Inicialitzar RecyclerView
        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mensajesList = new ArrayList<>();

        // Inicialitzar adaptador
        adapter = new MissatgesAdapter(getActivity(), mensajesList); // Passar el context i la llista de missatges a l'adaptador
        recyclerView.setAdapter(adapter);

        // Càrrega de missatges des del servidor
        carregarMissatgesServidor();

        return rootView;
    }

    /**
     * Mètode per carregar els missatges des del servidor.
     */
    private void carregarMissatgesServidor() {
        // implementar la lògica per recuperar els missatges del servidor
        // Per ara, afegirem missatges d'exemple a la llista
        // Suposem que tens un mètode per obtenir una llista de missatges del servidor, anomenat obtenirMissatgesServidor()
        List<Missatge> missatgesDelServidor = obtenirMissatgesServidor();

        // Afegim els missatges obtinguts del servidor a la llista de missatges
        mensajesList.addAll(missatgesDelServidor);

        // Notificar a l'adaptador sobre el canvi en les dades
        adapter.notifyDataSetChanged();
    }

    /**
     * Mètode per obtenir els missatges del servidor (exemple).
     * @return Llista de missatges obtinguda del servidor.
     */
    private List<Missatge> obtenirMissatgesServidor() {
        // Aquí hauries de fer la crida al servidor per obtenir els missatges reals
        // Per ara, retornarem una llista de missatges d'exemple
        List<Missatge> missatgesDelServidor = new ArrayList<>();
        missatgesDelServidor.add(new Missatge("Remitente 1", "Contenido 1", "Fecha 1", "Hora 1"));
        missatgesDelServidor.add(new Missatge("Remitente 2", "Contenido 2", "Fecha 2", "Hora 2"));
        missatgesDelServidor.add(new Missatge("Remitente 3", "Contenido 3", "Fecha 3", "Hora 3"));
        return missatgesDelServidor;
    }
}
