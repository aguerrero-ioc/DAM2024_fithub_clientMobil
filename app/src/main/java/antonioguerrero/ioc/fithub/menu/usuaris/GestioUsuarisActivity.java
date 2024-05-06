package antonioguerrero.ioc.fithub.menu.usuaris;

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
import antonioguerrero.ioc.fithub.menu.usuaris.GestioUsuarisAdapter;

import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarTotsUsuaris;

public class GestioUsuarisActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotsUsuaris.ConsultarTotsUsuarisListener, GestioUsuarisAdapter.OnUsuariEliminatListener {

    private RecyclerView recyclerView;
    private GestioUsuarisAdapter adapter;

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
        String nomUsuari = preferences.getString(Constants.NOM_USUARI, "Nom d'Usuari");
        String correuElectronic = preferences.getString(Constants.CORREU_USUARI, "correu@fithub.es");

        // Actualitzar el text de les vistes amb les dades de l'usuari
        tvNomUsuari.setText(nomUsuari);
        tvCorreuElectronic.setText(correuElectronic);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Botó "Crear nou usuari"
        Button btnCrearNouUsuari = findViewById(R.id.btnCrearNouUsuari);
        btnCrearNouUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir l'activitat CrearUsuariActivity
                Intent intent = new Intent(GestioUsuarisActivity.this, CrearUsuariActivity.class);
                startActivity(intent);
            }
        });

        // Obtenir sessioID de l'usuari
        preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotsUsuaris consulta = new ConsultarTotsUsuaris(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotsUsuaris();
    }

    public void consultarTotsUsuaris(View view) {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

        ConsultarTotsUsuaris consulta = new ConsultarTotsUsuaris(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotsUsuaris();
    }

    @Override
    public void onUsuarisObtinguts(List<HashMap<String, String>> usuaris) {
        if (usuaris != null && !usuaris.isEmpty()) {
            adapter = new GestioUsuarisAdapter(this, usuaris);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(GestioUsuarisActivity.this, "No hi ha usuaris disponibles");
        }
    }
    /**
     * Mètode que gestiona la resposta del servidor.
     * @param resposta La resposta del servidor.
     */
    @Override
    public void respostaServidor(Object resposta) throws ConnectException {
    }


    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }


    @Override
    public void onUsuariEliminat() {

    }
}