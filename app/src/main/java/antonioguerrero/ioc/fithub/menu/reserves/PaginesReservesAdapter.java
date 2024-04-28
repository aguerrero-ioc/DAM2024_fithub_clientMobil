package antonioguerrero.ioc.fithub.menu.reserves;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.List;

public class PaginesReservesAdapter extends FragmentPagerAdapter {
    public PaginesReservesAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ReservesRealitzadesFragment();
            case 1:
                return new ReservesPassadesFragment();
            default:
                throw new IllegalArgumentException("Posició no vàlida: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Reserves Realitzades";
        } else if (position == 1) {
            return "Reserves Passades";
        } else {
            return null;
        }
    }

    public void actualitzarReserves(List<HashMap<String, String>> reserves) {
    }
}