package antonioguerrero.ioc.fithub.menu.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.peticions.serveis.CrearServei;

/**
 * Activitat que permet a l'usuari crear un nou servei.
 * <p>
 * Aquesta activitat conté camps per a introduir el nom, la descripció i el preu del nou servei.
 * <p>
 * Aquesta activitat envia una petició al servidor per a crear el nou servei amb els valors introduïts.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class CrearServeiActivity extends BaseActivity {

    private EditText etNomServei, etDescripcioServei, etPreuServei;
    private Button btnCrearServei;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     *
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
        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Referenciar els elements del layout
        etNomServei = findViewById(R.id.et_nom_servei);
        etDescripcioServei = findViewById(R.id.et_descripcio_servei);
        etPreuServei = findViewById(R.id.et_preu_servei);
        btnCrearServei = findViewById(R.id.btn_creacio);

        // Configurar el clic del botó
        btnCrearServei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenció dels valors dels EditText
                String nomServei = etNomServei.getText().toString().trim();
                String descripcioServei = etDescripcioServei.getText().toString().trim();
                String preuServei = etPreuServei.getText().toString().trim();

                // Obtenir sessioID de l'usuari
                SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
                String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

                // Crear un objecte Servei amb els valors obtinguts
                Servei servei = new Servei(nomServei, descripcioServei, preuServei);

                // Crear i executar la petició al servidor per a crear el servei
                CrearServei crearServei = new CrearServei(new ConnexioServidor.respostaServidorListener() {
                    @Override
                    public void respostaServidor(Object resposta) {
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                }, servei, CrearServeiActivity.this, sessioID) {
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
                    crearServei.execute();
                } catch (ConnectException e) {
                    e.printStackTrace();
                }

                // Netegem els EditText
                etNomServei.setText("");
                etDescripcioServei.setText("");
                etPreuServei.setText("");
            }
        });
    }
}
