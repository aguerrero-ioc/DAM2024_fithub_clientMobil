package antonioguerrero.ioc.fithub.menu.installacions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.peticions.installacions.ConsultarTotesInstallacions;

/**
 * Activitat que permet a l'usuari administrador gestionar les instal·lacions.
 * <p>
 * Aquesta activitat mostra una llista de totes les instal·lacions disponibles.
 * <p>
 * Aquesta activitat permet a l'usuari crear, modificar i eliminar instal·lacions.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioInstallacionsActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesInstallacions.ConsultarTotesInstallacionsListener, GestioInstallacionsAdapter.OnInstallacioEliminadaListener {
        private RecyclerView recyclerView;

        /**
         * Mètode que s'executa en la creació de l'activitat.
         * <p>
         * @param savedInstanceState Estat de l'activitat
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_gestio_installacions);

                recyclerView = findViewById(R.id.rvInstallacions);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                String nomUsuari = preferences.getString(Constants.NOM_USUARI, "Nom d'Usuari");
                String correuElectronic = preferences.getString(Constants.CORREU_USUARI, "correu@fithub.es");

                // Actualitzar el text de les vistes amb les dades de l'usuari
                tvNomUsuari.setText(nomUsuari);
                tvCorreuElectronic.setText(correuElectronic);

                // Configura el botó flotant de missatges
                FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
                botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

                // Botó "Crear nova instal·lació"
                Button btnCrearNovaInstallacio = findViewById(R.id.btnCrearNovaInstallacio);
                btnCrearNovaInstallacio.setOnClickListener(v -> {
                        // Obrir l'activitat CrearInstallacioActivity
                        Intent intent = new Intent(GestioInstallacionsActivity.this, CrearInstallacioActivity.class);
                        startActivity(intent);
                });

                // Obtenir sessioID de l'usuari
                preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
                String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
                ConsultarTotesInstallacions consulta = new ConsultarTotesInstallacions(this, this, sessioID) {
                };

                consulta.consultarTotesInstallacions();
        }

        /**
         * Mètode que s'executa quan s'obté la llista de les instal·lacions
         * <p>
         * @param installacions Llista de les instal·lacions
         */
        @Override
        public void onInstallacionsObtingudes(List<HashMap<String, String>> installacions) {
                if (installacions != null && !installacions.isEmpty()) {
                        GestioInstallacionsAdapter adapter = new GestioInstallacionsAdapter(this, installacions);
                        recyclerView.setAdapter(adapter);
                } else {
                        Utils.mostrarToast(GestioInstallacionsActivity.this, "No hi ha instal·lacions disponibles");
                }
        }
}