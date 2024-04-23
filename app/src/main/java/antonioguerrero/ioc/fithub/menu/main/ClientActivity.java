package antonioguerrero.ioc.fithub.menu.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPassadesFragment;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesRealitzadesFragment;

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

        // Configurar ViewPager i TabLayout per a les pàgines de reserves
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);


        // Crear el adaptador
        PaginesReservesAdapter reservesAdapter = new PaginesReservesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(reservesAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // CODI PENDENT D'IMPLEMENTAR PER A OBTENIR LES RESERVES
        /*List<HashMap<String, String>> reservesPassades = new ArrayList<>();
        List<HashMap<String, String>> reservesRealitzades = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> reservaFalsa = new HashMap<>();
            reservaFalsa.put("id", "reserva" + i);
            reservaFalsa.put("data", "data" + i);
            reservaFalsa.put("hora", "hora" + i);
            // Afegeix la reserva a la llista corresponent
            if (esReservaPassada(reservaFalsa.get("data"))) { // Comprovar si la reserva és passada
                reservesPassades.add(reservaFalsa);
            } else {
                reservesRealitzades.add(reservaFalsa);
            }
        }

        // Crear el adaptador de pàgines de reserves passades
        PaginesReservesAdapter reservesPassadesAdapter = new PaginesReservesAdapter(getSupportFragmentManager(), reservesPassades);
        // Crear el adaptador de pàgines de reserves realitzades
        PaginesReservesAdapter reservesRealitzadesAdapter = new PaginesReservesAdapter(getSupportFragmentManager(), reservesRealitzades);
        viewPager.setAdapter(reservesAdapter);
        tabLayout.setupWithViewPager(viewPager);

    private boolean esReservaPassada(HashMap<String, String> reserva) {
        // Obtenir la data actual
        Calendar calendar = Calendar.getInstance();
        Date dataActual = calendar.getTime();

        // Obtenir la data de la reserva
        String fechaReservaString = reserva.get("data");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dataReserva;
        try {
            dataReserva = dateFormat.parse(dataReservaString);
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // La reserva no es passada
        }

        // Comparar les dates
        return dataReserva.before(dataActual);
    }*/


        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Utils.PENDENT_IMPLEMENTAR));


        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
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
        public PaginesReservesAdapter(FragmentManager fm) {
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
                    ReservesPassadesFragment reservesPasadesFragment = new ReservesPassadesFragment();
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
            if (position == 0) {
                return "Reserves Realitzades";
            } else if (position == 1) {
                return "Reserves Pasades";
            } else {
                return null;
            }
        }
    }
}
