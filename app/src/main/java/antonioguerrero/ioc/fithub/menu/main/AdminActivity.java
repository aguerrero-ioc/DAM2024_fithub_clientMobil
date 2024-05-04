package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.activitats.GestioActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPerDiaActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPerNomActivity;
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;
import antonioguerrero.ioc.fithub.menu.serveis.ServeisActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;

/**
 * Classe que representa l'activitat de l'administrador a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als administradors realitzar diverses operacions com gestionar usuaris, activitats i instal·lacions.
 * També poden veure els missatges rebuts.
 * <p>
 * Aquesta classe hereta de BaseActivity.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class AdminActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener {

    private TextView tvNomUsuari;
    private TextView tvCorreuElectronic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });
        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Obtenir referències a les vistes en el nav_header
        tvNomUsuari = headerView.findViewById(R.id.tvNomUsuari);
        tvCorreuElectronic = headerView.findViewById(R.id.tvCorreuElectronic);

        // Obtenir les dades de l'usuari de SharedPreferences
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String nomUsuari = preferences.getString(Constants.NOM_USUARI, "Nom d'Usuari");
        String correuElectronic = preferences.getString(Constants.CORREU_USUARI, "correu@fithub.es");

        // Actualitzar el text de les vistes amb les dades de l'usuari
        tvNomUsuari.setText(nomUsuari);
        tvCorreuElectronic.setText(correuElectronic);

        // Inicialitza els botons de reserva d'activitats
        Button botoGestio1 = findViewById(R.id.boto_gestio1);
        Button botoGestio2 = findViewById(R.id.boto_gestio2);
        Button botoGestio3 = findViewById(R.id.boto_gestio3);
        Button botoGestio4 = findViewById(R.id.boto_gestio4);

        // Configura els listeners pels botons de reserva d'activitats
        botoGestio1.setOnClickListener(v -> Utils.mostrarToast(AdminActivity.this, Constants.PENDENT_IMPLEMENTAR));
        botoGestio2.setOnClickListener(v -> ferGestio(GestioActivitatsActivity.class));
        botoGestio3.setOnClickListener(v -> ferGestio(GestioInstallacionsActivity.class));
        botoGestio4.setOnClickListener(v -> ferGestio(GestioServeisActivity.class));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));
    }

    /**
     * Mètode que obre l'activitat corresponent a la gestió seleccionada.
     * <p>
     * @param activityACarregar Activitat a carregar.
     */
    private void ferGestio(Class<?> activityACarregar) {
        Intent intent = new Intent(this, activityACarregar);
        startActivity(intent);
    }
    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}