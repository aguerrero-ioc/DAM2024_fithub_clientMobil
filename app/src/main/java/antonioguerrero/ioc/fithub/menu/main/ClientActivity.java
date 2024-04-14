package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

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
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.missatges.MissatgesActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPasadesFragment;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesRealitzadesFragment;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends BaseActivity implements BasePeticions.respostaServidorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Actualitza les dades del usuari quan s'obre l'activitat
        actualitzarDadesUsuari();

        // Configurar ViewPager y TabLayout per a les reserves
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        PaginesReservesAdapter reservasPagerAdapter = new PaginesReservesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(reservasPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, "Pendent d'implementar. Aviat dispobible!"));

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.nav_view);


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.nav_perfil_usuari) {
                    opcioPerfilClicat(PerfilActivity.class);
                } else if(id == R.id.nav_activitats) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar. Aviat dispobible!");
                    //PENDENT
                    /*intent = new Intent(BaseActivity.this, ActivitatsActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_serveis) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar. Aviat dispobible!");
                    //PENDENT
                    /*intent = new Intent(BaseActivity.this, ServeisActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_installacions) {
                    obrirActivity(ClientActivity.this, InstallacionsActivity.class);
                } else if (id == R.id.nav_reserves) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar. Aviat dispobible!");
                    //PENDENT
                    /*intent = new Intent(BaseActivity.this, ReservesActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_tancar_sessio) {
                    tancarSessioClicat();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    public void tancarSessioClicat() {
        SharedPreferences preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);

        // Obtén el ID del usuario actual como un entero
        String IDUsuariStr = preferencies.getString("IDusuari", "-1");

        // Verifica que IDUsuari no sea -1 antes de intentar usarlo
        if (!IDUsuariStr.equals("-1")) {

            // Crea una instancia de PeticioLogout
            PeticioLogout peticioLogout = new PeticioLogout((BasePeticions.respostaServidorListener) this, this, IDUsuariStr, Utils.SESSIO_ID);

            // Inicia la petición de cierre de sesión
            peticioLogout.execute();
        } else {
            Log.e("ClientActivity", "IDusuari no está definido en SharedPreferences");
        }
    }


    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }

    @Override
    public void onRespostaServidorMultiple(Object resposta) {

    }

    private static class PaginesReservesAdapter extends FragmentPagerAdapter {
        public PaginesReservesAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return switch (position) {
                case 0 -> new ReservesRealitzadesFragment();
                case 1 -> new ReservesPasadesFragment();
                default -> null;
            };
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
