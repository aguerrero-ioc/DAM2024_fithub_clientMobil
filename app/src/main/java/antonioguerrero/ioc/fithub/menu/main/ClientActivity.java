package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.serveis.ServeisActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPasadesFragment;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesRealitzadesFragment;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends AppCompatActivity {

    private LinearLayout layoutMenuPerfil; // Per mostrar/ocultar el menú desplegable
    private FloatingActionButton botoMostrarMissatges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        // Actualitza les dades del usuari quan s'obre l'activitat
        actualitzarDadesUsuari();

        // Referencias a los elementos del layout
        layoutMenuPerfil = findViewById(R.id.layoutPerfilMenu);



        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.nav_activitats) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
                    //PENDENT
                    /*intent = new Intent(ClientActivity.this, ActivitatsActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_classes) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
                    //PENDENT
                    /*intent = new Intent(ClientActivity.this, ClassesActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_serveis) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
                    //PENDENT
                    /*intent = new Intent(ClientActivity.this, ServeisActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_installacions) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
                    //PENDENT
                    /*intent = new Intent(ClientActivity.this, InstallacionsActivity.class);
                    startActivity(intent);*/
                } else if (id == R.id.nav_reserves) {
                    Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
                    //PENDENT
                    /*intent = new Intent(ClientActivity.this, ReservesActivity.class);
                    startActivity(intent);*/
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Configura el botó de perfil
        ImageButton botoPerfil = findViewById(R.id.boto_perfil_client);
        botoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        // Trobar les referències als elements del menú
        TextView opcioPerfil1 = findViewById(R.id.opcio_perfil1);
        TextView opcioLogout = findViewById(R.id.opcio_logout_client);

        // Configurar els listeners dels elements del menú
        opcioPerfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioPerfilClicat();
            }
        });

        opcioLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioTancarSessioClicat();
            }
        });


        // Configurar ViewPager y TabLayout per a les reserves
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ReservasPagerAdapter reservasPagerAdapter = new ReservasPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(reservasPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Configura el botó flotant de missatges
        botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obrirMissatgesActivity();
            }
        });
    }

    private class ReservasPagerAdapter extends FragmentPagerAdapter {
        public ReservasPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new ReservesRealitzadesFragment();
                case 1:
                    return new ReservesPasadesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Reserves Realitzades";
                case 1:
                    return "Reserves Pasades";
                default:
                    return null;
            }
        }
    }
    /**
     * Mètode per accedir al panell de control.
     *
     * @param view La vista que ha generat l'event.
     */
    public void accedirPanell(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START); // Abre el panel lateral
    }

    /**
     * Mètode per mostrar o ocultar el menú del perfil.
     */
    public void toggleMenu() {
        layoutMenuPerfil.setVisibility(layoutMenuPerfil.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció 1 del perfil.
     */
    public void opcioPerfilClicat() {
        startActivity(new Intent(ClientActivity.this, PerfilActivity.class));
    }

    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció de tancar la sessió.
     * Aquest mètode redirigeix l'usuari a la pantalla d'inici de sessió i finalitza l'activitat actual.
     */
    public void opcioTancarSessioClicat() {
        startActivity(new Intent(ClientActivity.this, LoginActivity.class));
        finish(); // Finalitzar l'activitat actual
    }

    /**
     * Mètode per actualitzar les dades del usuari.
     */
    private void actualitzarDadesUsuari() {


        // Obtenir el nom, ID i tipus de client
        String nomUsuari = obtenirNomUsuari(this);
        String idUsuari = obtenirIdUsuari(this);
        String tipusClient = obtenirTipusClient(this);

        // Concatenar el nom d'usuari i l'ID d'usuari
        String textUsuari = nomUsuari + " (" + idUsuari + ")";

        // Trobar el textViewUsuari i establir el text
        TextView textViewUsuari = findViewById(R.id.tv_usuari);
        textViewUsuari.setText(textUsuari);

        // Trobar el textViewTipusClient i establir el text
        TextView textViewTipusClient = findViewById(R.id.tv_tipus_client);
        textViewTipusClient.setText(tipusClient);

    }

    /**
     * Mètode per obtenir el nom de l'usuari.
     * @param context Context de l'aplicació.
     * @return El nom de l'usuari.
     */
    public static String obtenirNomUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("nomUsuari", "");
    }

    /**
     * Mètode per obtenir l'ID de l'usuari.
     * @param context Context de l'aplicació.
     * @return L'ID de l'usuari.
     */
    public static String obtenirIdUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("idUsuari", "");
    }

    /**
     * Mètode per obtenir el tipus de client.
     *
     * @param context Context de l'aplicació.
     * @return El tipus de client.
     */
    public static String obtenirTipusClient(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("tipusClient", "");
    }

    /**
     * Mètode per mostrar l'activitat de missatges.
     */
    private void obrirMissatgesActivity() {
        Utils.mostrarToast(ClientActivity.this, "Pendent d'implementar");
        //Intent intent = new Intent(this, MissatgesActivity.class);
        //startActivity(intent);
    }
}