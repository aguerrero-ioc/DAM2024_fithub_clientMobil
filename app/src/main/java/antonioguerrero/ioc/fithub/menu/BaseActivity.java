package antonioguerrero.ioc.fithub.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

/**
 * Activitat base de la que hereten la resta d'activitats de l'aplicació.
 * Conté mètodes comuns a totes les activitats.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class BaseActivity extends AppCompatActivity{

    public LinearLayout layoutMenuPerfil;

    public Context context;

    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * Mètode per gestionar la selecció d'un element del menú de navegació.
     *
     * @param menuItem L'element del menú seleccionat.
     */
    public void handleNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_perfil_usuari) {
            obrirActivity(PerfilActivity.class);
        } else if(id == R.id.nav_activitats) {
            obrirActivity(ActivitatsActivity.class);
        } else if (id == R.id.nav_serveis || id == R.id.nav_reserves) {
            Utils.mostrarToast(this, Utils.PENDENT_IMPLEMENTAR);
        } else if (id == R.id.nav_installacions) {
            obrirActivity(InstallacionsActivity.class);
        } else if (id == R.id.nav_tancar_sessio) {
            tancarSessioClicat();
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
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
        SharedPreferences preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        Object IDUsuariObject = preferencies.getAll().get("IDusuari");

        if (IDUsuariObject != null) {
            String IDUsuariStr;

            if (IDUsuariObject instanceof Integer) {
                // Si el valor es un entero, conviértelo a cadena
                IDUsuariStr = String.valueOf((int) IDUsuariObject);
            } else if (IDUsuariObject instanceof String) {
                // Si el valor es una cadena, úsalo directamente
                IDUsuariStr = (String) IDUsuariObject;
            } else {
                // Si el valor no es ni entero ni cadena, maneja el caso según tus necesidades
                Log.e("ETIQUETA", "Tipo de dato no soportado para IDusuari");
                return;
            }

            if (!IDUsuariStr.equals("-1")) {
                PeticioLogout peticioLogout = new PeticioLogout((ConnexioServidor.respostaServidorListener) this, this, IDUsuariStr, Utils.SESSIO_ID);
                peticioLogout.execute();
            } else {
                Log.e("ETIQUETA", "IDusuari no definit");
            }
        } else {
            Log.e("ETIQUETA", "IDusuari no encontrado en SharedPreferences");
        }
    }
}