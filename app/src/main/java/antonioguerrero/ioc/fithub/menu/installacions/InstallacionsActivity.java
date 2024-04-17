package antonioguerrero.ioc.fithub.menu.installacions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.installacions.ConsultarTotesInstallacions;

/**
 * Activitat per mostrar les instal·lacions disponibles al centre esportiu.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
  */
public class InstallacionsActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesInstallacions.ConsultarTotesInstallacionsListener {

    private RecyclerView recyclerView;
    private InstallacionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installacions);

        recyclerView = findViewById(R.id.rvInstallacions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Utils.PENDENT_IMPLEMENTAR));

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
        ConsultarTotesInstallacions consulta = new ConsultarTotesInstallacions(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotesInstallacions();
    }

    public void consultarTotesInstallacions(View view) {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

        ConsultarTotesInstallacions consulta = new ConsultarTotesInstallacions(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        consulta.consultarTotesInstallacions();
    }

    /**
     * Mètode qye s'executa quan s'han obtingut les instal·lacions.
     */
    @Override
    public void onInstallacionsObtingudes(List<HashMap<String, String>> installacions) {
        if (installacions != null && !installacions.isEmpty()) {
            adapter = new InstallacionsAdapter(this, installacions);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(InstallacionsActivity.this, "No hi ha instal·lacions disponibles");
        }
    }

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {
    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}