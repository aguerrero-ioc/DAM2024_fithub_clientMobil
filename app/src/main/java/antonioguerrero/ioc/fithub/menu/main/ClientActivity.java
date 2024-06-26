package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import antonioguerrero.ioc.fithub.menu.classesdirigides.ClassesPerDiaActivity;
import antonioguerrero.ioc.fithub.menu.classesdirigides.ClassesPerNomActivity;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com gestionar les seves reserves.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener {
    private TextView tvNomUsuari;
    private TextView tvCorreuElectronic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Infla el layout de la capçalera del NavigationView
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

        // Inicialitza els botons de reserva d'activitats
        Button botoReserva1 = findViewById(R.id.boto_reserva1);
        Button botoReserva2 = findViewById(R.id.boto_reserva2);
        Button botoReserva3 = findViewById(R.id.boto_reserva3);

        // Configura els listeners pels botons de reserva d'activitats
        botoReserva1.setOnClickListener(v -> obrirActivity(ClassesPerDiaActivity.class));
        botoReserva2.setOnClickListener(v -> obrirActivity(ClassesPerNomActivity.class));
        botoReserva3.setOnClickListener(v -> obrirActivity(ReservesActivity.class));

        // Configura el botó flotant de perfil
        FloatingActionButton botoPerfil = findViewById(R.id.boto_perfil);
        botoPerfil.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));
    }
}