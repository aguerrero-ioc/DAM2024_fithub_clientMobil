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
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Reserva;

public class ReservesPasadesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservesAdapter adapter;

    /**
     * Crea la vista del fragment amb el RecyclerView que mostra les reserves passades
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la vista del fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserves_pasades, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_reserves_pasades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // PENDENT: Implementar la lògica per obtenir les reserves passades
        // Per ara, un ArrayList bidimensional buit
        List<Reserva> reservesPasades = new ArrayList<>();
        Activitat[][] llistaActivitats = new Activitat[7][14]; // 7 díes a la setmana i 14 franges horàries/dia
        adapter = new ReservesAdapter(reservesPasades, llistaActivitats);
        recyclerView.setAdapter(adapter);

        return view;
    }
}