package antonioguerrero.ioc.fithub.menu.serveis;

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
import antonioguerrero.ioc.fithub.peticions.serveis.ConsultarTotsServeis;

/**
 * Activitat que permet a l'usuari gestionar els serveis.
 * <p>
 * Aquesta activitat mostra una llista de tots els serveis disponibles.
 * <p>
 * Aquesta activitat permet a l'usuari crear, modificar i eliminar serveis.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioServeisActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotsServeis.ConsultarTotsServeisListener, GestioServeisAdapter.OnServeiEliminatListener {

    private RecyclerView recyclerView;
    private GestioServeisAdapter adapter;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     *
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestio_serveis);

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        recyclerView = findViewById(R.id.rvServeis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));



        // Botó "Crear nou servei"
        Button btnCrearNouServei = findViewById(R.id.btnCrearNouServei);
        btnCrearNouServei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir l'activitat CrearServeiActivity
                Intent intent = new Intent(GestioServeisActivity.this, CrearServeiActivity.class);
                startActivity(intent);
            }
        });

        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotsServeis consulta = new ConsultarTotsServeis(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotsServeis();
    }


    /**
     * Mètode que s'executa quan s'obté la llista de serveis.
     *
     * @param serveis La llista de serveis.
     */
    @Override
    public void onServeisObtinguts(List<HashMap<String, String>> serveis) {
        if (serveis != null && !serveis.isEmpty()) {
            adapter = new GestioServeisAdapter(this, serveis);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(GestioServeisActivity.this, "No hi ha serveis disponibles");
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
     * Mètode que s'executa quan s'obté la llista de serveis.
     *
     * @param resposta La llista de serveis.
     */
    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }

    /**
     * Mètode que s'executa quan s'elimina un servei.
     */
    @Override
    public void onServeiEliminat() {

    }
}
