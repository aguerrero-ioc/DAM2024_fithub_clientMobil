
package antonioguerrero.ioc.fithub.menu.reserves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Reserva;

public class ReservesPasadesFragment extends Fragment {

    private RecyclerView recyclerView;
    private PaginesReservesAdapter adapter;


    public static ReservesPasadesFragment newInstance(List<HashMap<String, String>> reserves) {
    ReservesPasadesFragment fragment = new ReservesPasadesFragment();
    Bundle args = new Bundle();
    args.putSerializable("reserves", (Serializable) reserves);
    fragment.setArguments(args);
    return fragment;
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserves_pasades, container, false);

        // Recuperar las reservas falsas de los argumentos
        List<HashMap<String, String>> reserves = new ArrayList<>();
        Bundle args = getArguments();
        if (args != null && args.containsKey("reserves")) {
            reserves = (List<HashMap<String, String>>) args.getSerializable("reserves");
        }

        // Inicializar el RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_reserves_pasades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear y configurar el adaptador con las reservas
        ReservesRecyclerViewAdapter adapter = new ReservesRecyclerViewAdapter(reserves);
        recyclerView.setAdapter(adapter);

        return view;
    }

}


