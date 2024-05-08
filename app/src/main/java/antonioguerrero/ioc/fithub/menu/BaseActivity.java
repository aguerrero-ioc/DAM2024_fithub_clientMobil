package antonioguerrero.ioc.fithub.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.activitats.GestioActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.classesdirigides.ClassesPerDiaActivity;
import antonioguerrero.ioc.fithub.menu.classesdirigides.ClassesPerNomActivity;
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;
import antonioguerrero.ioc.fithub.menu.serveis.ServeisActivity;
import antonioguerrero.ioc.fithub.menu.usuaris.GestioUsuarisActivity;
import antonioguerrero.ioc.fithub.menu.usuaris.PerfilActivity;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

/**
 * Activitat base de la qual hereten la resta d'activitats de l'aplicació.
 * Conté mètodes comuns a totes les activitats.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public Context context;
    private DrawerLayout drawerLayout;

    public TextView tvNomUsuari;
    public TextView tvCorreuElectronic;

    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });
        // Inflar el layout de la capçalera del NavigationView

        View headerView = navigationView.getHeaderView(0);

        // Obtenir referències a les vistes en el nav_header
        tvNomUsuari = headerView.findViewById(R.id.tvNomUsuari);
        tvCorreuElectronic = headerView.findViewById(R.id.tvCorreuElectronic);

        // Obtenir les dades de l'usuari de SharedPreferences
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String nomUsuari = preferences.getString(Constants.NOM_USUARI, Constants.NOM_DEFAULT);
        String correuElectronic = preferences.getString(Constants.CORREU_USUARI, Constants.CORREU_DEFAULT);

        // Actualitzar el text de les vistes amb les dades de l'usuari
        tvNomUsuari.setText(nomUsuari);
        tvCorreuElectronic.setText(correuElectronic);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        handleNavigationItemSelected(menuItem);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Mètode per gestionar la selecció d'un element del menú de navegació.
     *
     * @param menuItem L'element del menú seleccionat.
     */
    public void handleNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        int tipusUsuari = obtenirTipusUsuari();

        if (id == R.id.nav_menu) {
            if (tipusUsuari == 1) {
                obrirActivity(AdminActivity.class);
            } else if (tipusUsuari == 2) {
                obrirActivity(ClientActivity.class);
            }
        } else if (id == R.id.nav_perfil_usuari) {
            obrirActivity(PerfilActivity.class);
        } else if (id == R.id.nav_usuaris) {
            obrirActivity(GestioUsuarisActivity.class);
        } else if (id == R.id.nav_activitats) {
            if (tipusUsuari == 1) {
                obrirActivity(GestioActivitatsActivity.class);
            } else if (tipusUsuari == 2) {
                obrirActivity(ActivitatsActivity.class);
            }
        } else if (id == R.id.nav_serveis) {
            if (tipusUsuari == 1) {
                obrirActivity(GestioServeisActivity.class);
            } else if (tipusUsuari == 2) {
                obrirActivity(ServeisActivity.class);
            }
        } else if (id == R.id.nav_installacions) {
            if (tipusUsuari == 1) {
                obrirActivity(GestioInstallacionsActivity.class);
            } else if (tipusUsuari == 2) {
                obrirActivity(InstallacionsActivity.class);
            }
        } else if (id == R.id.nav_reserves_per_dia) {
            if (tipusUsuari == 1) {
                Utils.mostrarToast(this, Constants.NO_ADMIN);
            } else if (tipusUsuari == 2) {
                obrirActivity(ClassesPerDiaActivity.class);
            }
        } else if (id == R.id.nav_reserves_per_activitat) {
            if (tipusUsuari == 1) {
                Utils.mostrarToast(this, Constants.NO_ADMIN);
            } else if (tipusUsuari == 2) {
                obrirActivity(ClassesPerNomActivity.class);
            }
        } else if (id == R.id.nav_reserves_usuari) {
            if (tipusUsuari == 1) {
                Utils.mostrarToast(this, Constants.NO_ADMIN);
            } else if (tipusUsuari == 2) {
                obrirActivity(ReservesActivity.class);
            }
        } else if (id == R.id.nav_tancar_sessio) {
            tancarSessioClicat();
        }
    }

    /**
     * Mètode per obtenir el tipus d'usuari.
     *
     * @return El tipus d'usuari.
     */
    private int obtenirTipusUsuari() {
        SharedPreferences preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);

        // Obtenir el tipus d'usuari emmagatzemat a SharedPreferences
        Object tipusUsuariObject = preferencies.getAll().get(Constants.TIPUS_USUARI);
        int tipusUsuari;

        // Verificar si el tipus d'usuari és una cadena o un enter
        if (tipusUsuariObject instanceof String tipusUsuariString) {
            // Convertir la cadena a enter
            if (tipusUsuariString.equals("Administrador") || tipusUsuariString.equals("1")) {
                tipusUsuari = 1;
            } else if (tipusUsuariString.equals("Client") || tipusUsuariString.equals("2")) {
                tipusUsuari = 2;
            } else {
                try {
                    tipusUsuari = Integer.parseInt(tipusUsuariString);
                } catch (NumberFormatException e) {
                    tipusUsuari = 0;
                }
            }
        } else if (tipusUsuariObject instanceof Integer) {
            // Si ja és un enter, simplement assignar el seu valor
            tipusUsuari = (int) tipusUsuariObject;
        } else {
            // En cas d'un altre tipus o si no es troba, assignar un valor predeterminat
            tipusUsuari = 0;
        }

        return tipusUsuari;
    }

    /**
     * Mètode per accedir al panell de control.
     *
     * @param view La vista que ha generat l'event.
     */
    public void accedirPanell(View view) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * Mètode per obrir una nova activitat.
     *
     * @param activityClass La classe de l'activitat a obrir.
     */
    public void obrirActivity(Class<? extends AppCompatActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }

    /**
     * Mètode per tancar la sessió de l'usuari.
     */
    public void tancarSessioClicat() {
        SharedPreferences preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        Object IDUsuariObject = preferencies.getAll().get("IDusuari");

        if (IDUsuariObject != null) {
            String IDUsuariStr;

            if (IDUsuariObject instanceof Integer) {
                // Si el valor es un enter, conviértelo a cadena
                IDUsuariStr = String.valueOf((int) IDUsuariObject);
            } else if (IDUsuariObject instanceof String) {
                // Si el valor es una cadena, úsalo directamente
                IDUsuariStr = (String) IDUsuariObject;
            } else {
                // Si el valor no es ni entero ni cadena, maneja el caso según tus necesidades
                Log.e("ETIQUETA", "Tipus de dades no suportat per a IDusuari");
                return;
            }

            if (!IDUsuariStr.equals("-1")) {
                PeticioLogout peticioLogout = new PeticioLogout((ConnexioServidor.respostaServidorListener) this, this, IDUsuariStr, Constants.SESSIO_ID);
                peticioLogout.execute();
            } else {
                Log.e("ETIQUETA", "IDusuari no definit");
            }
        } else {
            Log.e("ETIQUETA", "IDusuari no trobat en SharedPreferences");
        }
    }
}
