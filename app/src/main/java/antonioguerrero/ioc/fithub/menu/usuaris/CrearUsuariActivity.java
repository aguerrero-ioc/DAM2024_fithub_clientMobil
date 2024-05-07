package antonioguerrero.ioc.fithub.menu.usuaris;

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
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.CrearUsuari;

/**
 * Activitat que permet a l'usuari crear un nou usuari.
 * <p>
 * Aquesta activitat conté camps per a introduir el correu electrònic i la contrasenya del nou usuari.
 * A més, envia una petició al servidor per a crear el nou usuari amb els valors introduïts.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class CrearUsuariActivity extends BaseActivity {
    private static final String ETIQUETA = "CrearUsuari";
    private EditText etNomUsuari, etCognomsUsuari, etTelefonUsuari, etCorreuUsuari, etContrasenyaUsuari;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuari);

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
        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etCognomsUsuari = findViewById(R.id.et_cognoms_usuari);
        etTelefonUsuari = findViewById(R.id.et_telefon_usuari);
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etContrasenyaUsuari = findViewById(R.id.et_contrasenya_usuari);
        Button btnCrearUsuari = findViewById(R.id.btn_creacio_usuari);

        // Configurar el clic del botó
        btnCrearUsuari.setOnClickListener(v -> {
            // Obtenció dels valors dels EditText
            String nomUsuari1 = etNomUsuari.getText().toString().trim();
            String cognomsUsuari = etCognomsUsuari.getText().toString().trim();
            String telefonUsuari = etTelefonUsuari.getText().toString().trim();
            String correuUsuari = etCorreuUsuari.getText().toString().trim();
            String contrasenyaUsuari = etContrasenyaUsuari.getText().toString().trim();

            // Verificar si algun camp està buit
            if (nomUsuari1.isEmpty() || cognomsUsuari.isEmpty() || telefonUsuari.isEmpty() || correuUsuari.isEmpty() || contrasenyaUsuari.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Si us plau, completa tots els camps");
                return;
            }

            // Comprovar el format correcte del nom
            if (!nomUsuari1.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                Utils.mostrarToast(getApplicationContext(), "El nom només pot contenir lletres");
                return;
            }

            // Comprovar el format correcte dels cognoms
            if (!cognomsUsuari.matches("[a-zA-ZÀ-ÿ\\s]+")) {
                Utils.mostrarToast(getApplicationContext(), "Els cognoms només poden contenir lletres");
                return;
            }

            // Comprovar el format correcte del telèfon
            if (!telefonUsuari.matches("\\d{9}")) {
                Utils.mostrarToast(getApplicationContext(), "El telèfon ha de tenir 9 dígits");
                return;
            }

            // Verificar si el correu electrònic està en el format correcte
            if (!Utils.esEmailValid(correuUsuari)) {
                Utils.mostrarToast(getApplicationContext(), "Format de correu electrònic invàlid");
                return;
            }

            // Comprovar la longitud i la complexitat de la contrasenya
            if (contrasenyaUsuari.length() < 8 || !contrasenyaUsuari.matches(".*\\d.*") || !contrasenyaUsuari.matches(".*[a-zA-Z].*")) {
                Utils.mostrarToast(getApplicationContext(), "La contrasenya ha de tenir almenys 8 caràcters alfanumèrics");
                return;
            }

            // Crear un objecte Usuari amb els valors obtinguts
            Usuari usuari = new Usuari(correuUsuari, contrasenyaUsuari, nomUsuari1, cognomsUsuari, telefonUsuari);

            // Crear i executar la petició al servidor per a crear l'usuari
            CrearUsuari crearUsuari = new CrearUsuari(new ConnexioServidor.respostaServidorListener() {
            }, usuari, CrearUsuariActivity.this) {
            };

            // Executar la petició en un fil en segon pla
            try {
                crearUsuari.execute();
            } catch (ConnectException e) {
                Log.e(ETIQUETA, "Error en la creació de l'usuari: " + e);
            }

            // Netegem els EditText
            etNomUsuari.setText("");
            etCognomsUsuari.setText("");
            etTelefonUsuari.setText("");
            etCorreuUsuari.setText("");
            etContrasenyaUsuari.setText("");
        });
    }
}