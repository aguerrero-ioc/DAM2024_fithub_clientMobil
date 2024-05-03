package antonioguerrero.ioc.fithub.menu.installacions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.peticions.installacions.CrearInstallacio;

/**
 * Activitat que permet a l'usuari crear una nova instal·lació.
 * <p>
 * Aquesta activitat conté camps per a introduir el nom, la descripció i el tipus de la nova instal·lació.
 * <p>
 * Aquesta activitat envia una petició al servidor per a crear la nova instal·lació amb els valors introduïts.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class CrearInstallacioActivity extends BaseActivity {

    private EditText etNomInstallacio, etDescripcioInstallacio, etTipusInstallacio;
    private Button btnCrearInstallacio;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     *
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_installacio);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Referenciar els elements del layout
        etNomInstallacio = findViewById(R.id.et_nom_installacio);
        etDescripcioInstallacio = findViewById(R.id.et_descripcio_installacio);
        etTipusInstallacio = findViewById(R.id.et_tipus_installacio);
        btnCrearInstallacio = findViewById(R.id.btn_creacio_installacio);

        // Configurar el clic del botó
        btnCrearInstallacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenció dels valors dels EditText
                String nomInstallacio = etNomInstallacio.getText().toString().trim();
                String descripcioInstallacio = etDescripcioInstallacio.getText().toString().trim();

                // Obtener el tipo de instalación
                String tipusInstallacioInput = etTipusInstallacio.getText().toString().trim().toLowerCase();
                int tipusInstallacio;

                // Convertir la entrada del usuario en el tipo de instalación correspondiente
                if (tipusInstallacioInput.equals("1") || tipusInstallacioInput.equals("sala")) {
                    tipusInstallacio = 1; // Sala
                } else if (tipusInstallacioInput.equals("2") || tipusInstallacioInput.equals("piscina")) {
                    tipusInstallacio = 2; // Piscina
                } else {
                    // Si la entrada no es válida, mostrar un mensaje de error y salir del método
                    Toast.makeText(CrearInstallacioActivity.this, "Tipus d'instal·lació no vàlid", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Obtenir sessioID de l'usuari
                SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
                String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

                // Crear un objecte Installacio amb els valors obtinguts
                Installacio installacio = new Installacio(nomInstallacio, descripcioInstallacio, tipusInstallacio);

                // Crear i executar la petició al servidor per a crear la instal·lació
                CrearInstallacio crearInstallacio = new CrearInstallacio(new ConnexioServidor.respostaServidorListener() {
                    @Override
                    public void respostaServidor(Object resposta) {
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                }, installacio, CrearInstallacioActivity.this, sessioID) {
                    @Override
                    protected Object doInBackground(Void... voids) {
                        return null;
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                };

                // Executar la petició en un fil en segon pla
                try {
                    crearInstallacio.execute();
                } catch (ConnectException e) {
                    e.printStackTrace();
                }

                // Netegem els EditText
                etNomInstallacio.setText("");
                etDescripcioInstallacio.setText("");
                etTipusInstallacio.setText("");
            }
        });
    }
}