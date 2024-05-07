package antonioguerrero.ioc.fithub.menu.activitats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.peticions.activitats.CrearActivitat;

/**
 * Activitat que permet a l'usuari administrador crear una nova activitat.
 * <p>
 * Aquesta activitat envia una petició al servidor per a crear la nova activitat amb els valors introduïts.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class CrearActivitatActivity extends BaseActivity {
    private static final String ETIQUETA = "CrearActivitatActivity";
    private EditText etNomActivitat, etDescripcioActivitat, etAforamentActivitat, etTipusInstallacio;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     *
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_activitat);

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
        etNomActivitat = findViewById(R.id.et_nom_activitat);
        etDescripcioActivitat = findViewById(R.id.et_descripcio_activitat);
        etAforamentActivitat = findViewById(R.id.et_aforament_activitat);
        etTipusInstallacio = findViewById(R.id.et_tipus_installacio);
        Button btnCrearActivitat = findViewById(R.id.btn_creacio);

        // Configurar el clic del botó
        btnCrearActivitat.setOnClickListener(v -> {
            // Obtenir els valors dels EditText
            String nomActivitat = etNomActivitat.getText().toString().trim();
            String descripcioActivitat = etDescripcioActivitat.getText().toString().trim();
            int aforamentActivitat = Integer.parseInt(etAforamentActivitat.getText().toString().trim());

            // Obtenir el tipus d'instal·lació
            String tipusInstallacioInput = etTipusInstallacio.getText().toString().trim().toLowerCase();
            int tipusInstallacio;

            // Convertir l'entrada de l'usuari en el tipus d'instal·lació corresponent
            if (tipusInstallacioInput.equals("1") || tipusInstallacioInput.equals("sala")) {
                tipusInstallacio = 1; // Sala
            } else if (tipusInstallacioInput.equals("2") || tipusInstallacioInput.equals("piscina")) {
                tipusInstallacio = 2; // Piscina
            } else {
                // Si l'entrada no és vàlida, mostrar un missatge d'error i sortir del mètode
                Toast.makeText(CrearActivitatActivity.this, "Tipus d'instal·lació no vàlid", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtenir sessioID de l'usuari
            SharedPreferences preferences1 = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
            String sessioID = preferences1.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

            // Crear un objecte Activitat amb els valors obtinguts
            Activitat activitat = new Activitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio);

            // Crear i executar la petició al servidor per a crear la activitat
            CrearActivitat crearActivitat = new CrearActivitat(new ConnexioServidor.respostaServidorListener() {
            }, activitat, CrearActivitatActivity.this, sessioID) {
            };

            // Executar la petició al servidor per a crear la activitat
            try {
                crearActivitat.execute();
            } catch (ConnectException e) {
                Log.e(ETIQUETA, "Error en la creació de l'activitat", e);
            }

            // Netejar els EditText
            etNomActivitat.setText("");
            etDescripcioActivitat.setText("");
            etAforamentActivitat.setText("");
            etTipusInstallacio.setText("");
        });
    }
}