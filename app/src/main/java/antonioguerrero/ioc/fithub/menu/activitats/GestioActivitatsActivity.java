package antonioguerrero.ioc.fithub.menu.activitats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import antonioguerrero.ioc.fithub.peticions.activitats.ConsultarTotesActivitats;

/**
 * Activitat que permet a l'usuari gestionar les activitats.
 * <p>
 * Aquesta activitat mostra una llista de totes les activitats disponibles.
 * <p>
 * Aquesta activitat permet a l'usuari crear, modificar i eliminar activitats.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioActivitatsActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesActivitats.ConsultarTotesActivitatsListener, GestioActivitatsAdapter.OnActivitatEliminadaListener {

    private RecyclerView recyclerView;
    private GestioActivitatsAdapter adapter;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     *
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestio_activitats);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        recyclerView = findViewById(R.id.rvActivitats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Botó "Crear nova activitat"
        Button btnCrearNovaActivitat = findViewById(R.id.btnCrearNovaActivitat);
        btnCrearNovaActivitat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad CrearActivitatActivity
                Intent intent = new Intent(GestioActivitatsActivity.this, CrearActivitatActivity.class);
                startActivity(intent);
            }
        });

        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotesActivitats consulta = new ConsultarTotesActivitats(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotesActivitats();
    }


    /**
     * Mètode que s'executa quan s'obté la llista d'activitats.
     *
     * @param activitats La llista d'activitats.
     */
    @Override
    public void onActivitatsObtingudes(List<HashMap<String, String>> activitats) {
        if (activitats != null && !activitats.isEmpty()) {
            adapter = new GestioActivitatsAdapter(this, activitats);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(GestioActivitatsActivity.this, "No hi ha activitats disponibles");
        }
    }

    /**
     * Mètode que gestiona la resposta del servidor.
     * @param resposta La resposta del servidor.
     */

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {
    }

    /**
     * Mètode que s'executa quan s'obté la llista d'activitats.
     *
     * @param resposta La llista d'activitats.
     */
    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }

    /**
     * Mètode que s'executa quan s'elimina una activitat.
     */
    @Override
    public void onActivitatEliminada() {

    }
}
