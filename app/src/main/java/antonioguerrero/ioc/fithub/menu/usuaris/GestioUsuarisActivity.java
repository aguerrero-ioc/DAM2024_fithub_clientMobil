package antonioguerrero.ioc.fithub.menu.usuaris;

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
import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarTotsUsuaris;

/**
 * Activitat que permet a l'usuari administrador gestionar els usuaris.
 * <p>
 * Aquesta activitat mostra una llista de tots els usuaris disponibles.
 * <p>
 * Aquesta activitat permet a l'usuari crear, modificar i eliminar usuaris.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioUsuarisActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotsUsuaris.ConsultarTotsUsuarisListener, GestioUsuarisAdapter.OnUsuariEliminatListener {

    private RecyclerView recyclerView;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestio_usuaris);

        recyclerView = findViewById(R.id.rvUsuaris);
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
        String nomUsuari = preferences.getString(Constants.NOM_USUARI, Constants.NOM_DEFAULT);
        String correuElectronic = preferences.getString(Constants.CORREU_USUARI, Constants.CORREU_DEFAULT);

        // Actualitzar el text de les vistes amb les dades de l'usuari
        tvNomUsuari.setText(nomUsuari);
        tvCorreuElectronic.setText(correuElectronic);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Botó "Crear nou usuari"
        Button btnCrearNouUsuari = findViewById(R.id.btnCrearNouUsuari);
        btnCrearNouUsuari.setOnClickListener(v -> {
            // Obrir l'activitat CrearUsuariActivity
            Intent intent = new Intent(GestioUsuarisActivity.this, CrearUsuariActivity.class);
            startActivity(intent);
        });

        // Obtenir sessioID de l'usuari
        preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotsUsuaris consulta = new ConsultarTotsUsuaris(this, this, sessioID) {
        };

        consulta.consultarTotsUsuaris();
    }

    /**
     * Mètode que s'executa quan s'obté la llista d'usuaris.
     * <p>
     * @param usuaris Llista d'usuaris
     */
    @Override
    public void onUsuarisObtinguts(List<HashMap<String, String>> usuaris) {
        if (usuaris != null && !usuaris.isEmpty()) {
            GestioUsuarisAdapter adapter = new GestioUsuarisAdapter(this, usuaris);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(GestioUsuarisActivity.this, "No hi ha usuaris disponibles");
        }
    }
}