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

    /**
     * Constructor de la classe.
     *
     * @param fm       FragmentManager.
     * @param reserves Llista de reserves.
     */
    public PaginesReservesAdapter(FragmentManager fm, List<HashMap<String, String>> reserves) {
        super(fm);
        this.reservas = reserves;
    }

    /**
     * Mètode que retorna la pàgina de reserves corresponent a la posició indicada.
     *
     * @param position Posició de la pàgina.
     * @return Fragment de la pàgina.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ReservesRealitzadesFragment reservesRealitzadesFragment = ReservesRealitzadesFragment.newInstance(reservas);
                return reservesRealitzadesFragment;
            case 1:
                ReservesPassadesFragment reservesPasadesFragment = ReservesPassadesFragment.newInstance(reservas);
                return reservesPasadesFragment;
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }


    /**
     * Mètode que retorna el nombre de pàgines.
     *
     * @return Nombre de pàgines.
     */
    @Override
    public int getCount() {
        return 2;
    }

    /**
     * Mètode que retorna el títol de la pàgina.
     *
     * @param position Posició de la pàgina.
     * @return Títol de la pàgina.
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Reserves Realitzades";
        } else if (position == 1) {
            return "Reserves Pasades";
        } else {
            return null;
        }
    }
}
