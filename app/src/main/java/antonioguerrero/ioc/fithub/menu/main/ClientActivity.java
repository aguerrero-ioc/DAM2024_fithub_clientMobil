package antonioguerrero.ioc.fithub.menu.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;

import antonioguerrero.ioc.fithub.menu.reserves.ReservesPasadesFragment;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesRealitzadesFragment;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Configurar ViewPager y TabLayout per a les reserves
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Crear una llista de reserves falses per a les proves
        List<HashMap<String, String>> reservasFalsas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> reservaFalsa = new HashMap<>();
            reservaFalsa.put("id", "reserva" + i);
            reservaFalsa.put("fecha", "data" + i);
            reservaFalsa.put("hora", "hora" + i);
            reservasFalsas.add(reservaFalsa);
        }


        PaginesReservesAdapter reservasPagerAdapter = new PaginesReservesAdapter(getSupportFragmentManager(), reservasFalsas);
        viewPager.setAdapter(reservasPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Utils.PENDENT_IMPLEMENTAR));

        // Configura el menú lateral
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.nav_view);

        navigation.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_perfil_usuari) {
                obrirActivity(PerfilActivity.class);
            } else if(id == R.id.nav_activitats) {
                Utils.mostrarToast(ClientActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_serveis) {
                Utils.mostrarToast(ClientActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_installacions) {
                obrirActivity(InstallacionsActivity.class);
            } else if (id == R.id.nav_reserves) {
                Utils.mostrarToast(ClientActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_tancar_sessio) {
                tancarSessioClicat();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }


    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }



    private static class PaginesReservesAdapter extends FragmentPagerAdapter {
        public PaginesReservesAdapter(FragmentManager fm, List<HashMap<String, String>> reserves) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ReservesRealitzadesFragment reservesRealitzadesFragment = new ReservesRealitzadesFragment();
                    return reservesRealitzadesFragment;
                case 1:
                    ReservesPasadesFragment reservesPasadesFragment = new ReservesPasadesFragment();
                    // Aquí puedes añadir argumentos al fragmento si es necesario
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
}
