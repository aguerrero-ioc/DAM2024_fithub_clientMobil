package antonioguerrero.ioc.fithub.menu.reserves;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.List;

/**
 * Classe que representa l'adaptador de les pàgines de reserves.
 * <p>
 * Aquesta classe permet gestionar les pàgines de reserves realitzades i passades.
 *
 * @version 1.0
 */
public class PaginesReservesAdapter extends FragmentPagerAdapter {
    private List<HashMap<String, String>> reservas;

    public PaginesReservesAdapter(FragmentManager fm, List<HashMap<String, String>> reserves) {
        super(fm);
        this.reservas = reserves;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReservesRealitzadesFragment reservesRealitzadesFragment = ReservesRealitzadesFragment.newInstance(reservas);
                return reservesRealitzadesFragment;
            case 1:
                ReservesPasadesFragment reservesPasadesFragment = ReservesPasadesFragment.newInstance(reservas);
                return reservesPasadesFragment;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }



    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return switch (position) {
            case 0 -> "Reserves Realitzades";
            case 1 -> "Reserves Pasades";
            default -> null;
        };
    }
}
