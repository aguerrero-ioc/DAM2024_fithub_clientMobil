package antonioguerrero.ioc.fithub.menu.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.peticions.serveis.CrearServei;

/**
 * Activitat que permet a l'usuari administrador crear una nou servei.
 * <p>
 * Aquesta activitat envia una petició al servidor per a crear el nou servei amb els valors introduïts.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class CrearServeiActivity extends BaseActivity {
    private static final String ETIQUETA = "CrearServei";
    private EditText etNomServei, etDescripcioServei, etPreuServei;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_servei);

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

        // Referenciar els elements del layout
        etNomServei = findViewById(R.id.et_nom_servei);
        etDescripcioServei = findViewById(R.id.et_descripcio_servei);
        etPreuServei = findViewById(R.id.et_preu_servei);
        Button btnCrearServei = findViewById(R.id.btn_creacio);

        // Configurar el clic del botó
        btnCrearServei.setOnClickListener(v -> {
            // Obtenció dels valors dels EditText
            String nomServei = etNomServei.getText().toString().trim();
            String descripcioServei = etDescripcioServei.getText().toString().trim();
            String preuServei = etPreuServei.getText().toString().trim();

            // Obtenir sessioID de l'usuari
            SharedPreferences preferences1 = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
            String sessioID = preferences1.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

            // Crear un objecte Servei amb els valors obtinguts
            Servei servei = new Servei(nomServei, descripcioServei, preuServei);

            // Crear i executar la petició al servidor per a crear el servei
            CrearServei crearServei = new CrearServei(new ConnexioServidor.respostaServidorListener() {
            }, servei, CrearServeiActivity.this, sessioID) {
            };

            // Executar la petició en un fil en segon pla
            try {
                crearServei.execute();
            } catch (ConnectException e) {
                Log.e(ETIQUETA, "Error en la creació del servei: " + e);
            }
            // Netegem els EditText
            etNomServei.setText("");
            etDescripcioServei.setText("");
            etPreuServei.setText("");
        });
    }
}