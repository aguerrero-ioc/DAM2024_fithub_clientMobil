package antonioguerrero.ioc.fithub.menu.activitats;

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
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.peticions.activitats.CrearActivitat;

/**
 * Activitat que permet a l'usuari crear una nova activitat.
 * <p>
 * Aquesta activitat conté camps per a introduir el nom, la descripció, l'aforament i el tipus d'instal·lació de la nova activitat.
 * <p>
 * Aquesta activitat envia una petició al servidor per a crear la nova activitat amb els valors introduïts.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class CrearActivitatActivity extends BaseActivity {

    private EditText etNomActivitat, etDescripcioActivitat, etAforamentActivitat, etTipusInstallacio;
    private Button btnCrearActivitat;

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

        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Referenciar els elements del layout
        etNomActivitat = findViewById(R.id.et_nom_activitat);
        etDescripcioActivitat = findViewById(R.id.et_descripcio_activitat);
        etAforamentActivitat = findViewById(R.id.et_aforament_activitat);
        etTipusInstallacio = findViewById(R.id.et_tipus_installacio);
        btnCrearActivitat = findViewById(R.id.btn_creacio);

        // Configurar el clic del botó
        btnCrearActivitat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
                String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

                // Crear un objecte Activitat amb els valors obtinguts
                Activitat activitat = new Activitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio);

                // Crear i executar la petició al servidor per a crear la activitat
                CrearActivitat crearActivitat = new CrearActivitat(new ConnexioServidor.respostaServidorListener() {
                    @Override
                    public void respostaServidor(Object resposta) {
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                }, activitat, CrearActivitatActivity.this, sessioID) {
                    /**
                     * Mètode que s'executa en segon pla
                     *
                     * @param voids
                     * @return null
                     */
                    @Override
                    protected Object doInBackground(Void... voids) {
                        return null;
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                };

                // Ejecutar la petición en un hilo en segundo plano
                try {
                    crearActivitat.execute();
                } catch (ConnectException e) {
                    e.printStackTrace();
                }

                // Netejar els EditText
                etNomActivitat.setText("");
                etDescripcioActivitat.setText("");
                etAforamentActivitat.setText("");
                etTipusInstallacio.setText("");
            }
        });
    }
}
