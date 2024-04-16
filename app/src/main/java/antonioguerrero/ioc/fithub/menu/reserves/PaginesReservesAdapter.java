/*
PENDENT D'IMPLEMENTAR

package antonioguerrero.ioc.fithub.menu.reserves;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class PaginesReservesAdapter extends FragmentPagerAdapter {
    private final List<HashMap<String, String>> reserves;

    public PaginesReservesAdapter(FragmentManager fm, List<HashMap<String, String>> reserves) {
        super(fm);
        this.reserves = reserves;
    }

    @Override
    public Fragment getItem(int position) {
        // Pasar les dades de les reserves a cada fragment
        Fragment fragment = switch (position) {
            case 0 -> new ReservesRealitzadesFragment();
            case 1 -> new ReservesPasadesFragment();
            default -> null;
        };
        Bundle args = new Bundle();
        args.putSerializable("reserves", (Serializable) reserves);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 0;
    }

 */