package antonioguerrero.ioc.fithub.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

public class BaseActivity extends AppCompatActivity{

    public LinearLayout layoutMenuPerfil;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


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



    public void obrirActivity(Class<? extends AppCompatActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }


    /**
     * Mètode per obtenir una dada de l'usuari a partir de la clau.
     */
    public static String obtenirDadaUsuari(Context context, String clau) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String valor;
        try {
            valor = preferencies.getString(clau, null);
            if (valor == null) {
                int valorInt = preferencies.getInt(clau, Integer.MIN_VALUE);
                if (valorInt != Integer.MIN_VALUE) {
                    valor = String.valueOf(valorInt);
                } else {
                    valor = Utils.VALOR_DEFAULT;
                }
            }
        } catch (ClassCastException e) {
            int valorInt = preferencies.getInt(clau, Integer.MIN_VALUE);
            if (valorInt != Integer.MIN_VALUE) {
                valor = String.valueOf(valorInt);
            } else {
                valor = Utils.VALOR_DEFAULT;
            }
        }
        return valor;
    }


    /**
     * Mètode per tancar la sessió de l'usuari.
     */
    public void tancarSessioClicat() {
        SharedPreferences preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String IDUsuariStr = preferencies.getString("IDusuari", "-1");

        if (!IDUsuariStr.equals("-1")) {
            PeticioLogout peticioLogout = new PeticioLogout((BasePeticions.respostaServidorListener) this, this, IDUsuariStr, Utils.SESSIO_ID);
            peticioLogout.execute();
        } else {
            Log.e("ClientActivity", "IDusuari no definit");
        }
    }
}