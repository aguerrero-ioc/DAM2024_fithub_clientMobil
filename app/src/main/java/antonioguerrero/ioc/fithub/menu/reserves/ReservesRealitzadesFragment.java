/* PENDENT D'IMPLEMENTAR
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Reserva;

public class ReservesRealitzadesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar la vista del fragmento
        View view = inflater.inflate(R.layout.fragment_reserves_realitzades, container, false);

        // Obtener las reservas de los argumentos del fragmento
        Bundle arguments = getArguments();
        List<HashMap<String, String>> reserves = null;
        if (arguments != null) {
            reserves = (List<HashMap<String, String>>) arguments.getSerializable("reserves");
        }

        // Inicializar el RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_reserves_realitzades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear y configurar el adaptador con las reservas
        ReservesAdapter adapter = new ReservesAdapter(reserves);
        recyclerView.setAdapter(adapter);

        return view;
    }

    /**
     * Crea la vista del fragment amb el RecyclerView que mostra les reserves realitzades
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vista del fragment
     */
    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserves_realitzades, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_reserves_realitzades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // PENDENT: Implementar la lògica per obtenir les reserves realitzades
        // Per ara, un ArrayList bidimensional buit
        List<Reserva> reservesRealitzades = new ArrayList<>();
        Activitat[][] llistaActivitats = new Activitat[7][14]; // 7 díes a la setmana i 14 franges horàries/dia
        adapter = new ReservesAdapter(reservesRealitzades, llistaActivitats);
        recyclerView.setAdapter(adapter);

        return view;
    }*/
