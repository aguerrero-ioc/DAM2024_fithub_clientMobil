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

import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

public abstract class BaseActivity extends AppCompatActivity{

    private LinearLayout layoutMenuPerfil;

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

    /**
     * Mètode per mostrar o ocultar el menú del perfil.
     */
    public void toggleMenu() {
        layoutMenuPerfil.setVisibility(layoutMenuPerfil.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }


    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció 1 del perfil.
     */
    public void opcioPerfilClicat(Class<? extends AppCompatActivity> activityClass) {
        startActivity(new Intent(this, activityClass));
    }


public void gestioRespostaServidor(Object respuesta) {
    if (respuesta instanceof Object[] && ((Object[]) respuesta).length >= 2) {
        Object[] arrayResposta = (Object[]) respuesta;
        String IDSessio = (String) arrayResposta[0];
        HashMap<String, String> usuariMap = (HashMap<String, String>) arrayResposta[1];

        Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);

        if (usuari != null) {
            // Obtener SharedPreferences y su editor
            SharedPreferences preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencies.edit();

            // Guardar los datos del usuario en SharedPreferences
            editor.putString("IDSessio", IDSessio);
            editor.putString("nomUsuari", usuari.getNomUsuari());
            editor.putString("IDUsuari", String.valueOf(usuari.getIDusuari()));
            editor.putString("tipusUsuari", String.valueOf(usuari.getTipusUsuari()));

            // Aplicar los cambios
            editor.apply();
        } else {
            Log.e("Resposta inici", "Error en la transformació del HashMap a Usuari");
        }
    } else {
        Log.e("Resposta inici", "Error en la resposta del servidor");
    }
}

    /**
     * Mètode per actualitzar les dades del usuari.
     */
    public void actualitzarDadesUsuari() {

        // Obtenir el nom, ID i tipus de client
        String nomUsuari = obtenirDadaUsuari(this, "nomUsuari");
        String IDUsuari = obtenirDadaUsuari(this, "IDUsuari");
        String tipusUsuari = obtenirDadaUsuari(this, "tipusUsuari");

        // Concatenar el nom d'usuari i l'ID d'usuari
        String textUsuari = nomUsuari + " (" + IDUsuari + ")";

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
     * Mètode per mostrar l'activitat de missatges.
     */
    public void obrirActivity(Context context, Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }


    public abstract void onRespostaServidorMultiple(Object resposta);
}
