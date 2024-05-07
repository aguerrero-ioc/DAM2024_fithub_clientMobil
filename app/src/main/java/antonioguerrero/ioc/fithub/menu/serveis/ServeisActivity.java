package antonioguerrero.ioc.fithub.menu.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

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
import antonioguerrero.ioc.fithub.peticions.serveis.ConsultarTotsServeis;

/**
 * Activitat per mostrar els serveis disponibles al centre esportiu.
 * <p>
 * Aquesta activitat mostra una llista de tots els serveis disponibles al centre esportiu.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ServeisActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotsServeis.ConsultarTotsServeisListener {

    private RecyclerView recyclerView;

    /**
     * Mètode que s'executa quan s'ha creat l'activitat.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveis);

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

        recyclerView = findViewById(R.id.rvServeis);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Obtenir sessioID de l'usuari
        preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotsServeis consulta = new ConsultarTotsServeis(this, this, sessioID) {

        };

        consulta.consultarTotsServeis();
    }

    /**
     * Mètode que s'executa quan s'han obtingut els serveis.
     * <p>
     * @param serveis Llista dels serveis obtinguts.
     */
    @Override
    public void onServeisObtinguts(List<HashMap<String, String>> serveis) {
        if (serveis != null && !serveis.isEmpty()) {
            ServeisAdapter adapter = new ServeisAdapter(this, serveis);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(ServeisActivity.this, "No hi ha serveis disponibles");
        }
    }
}