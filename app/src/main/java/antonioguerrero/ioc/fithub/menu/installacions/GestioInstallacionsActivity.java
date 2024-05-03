package antonioguerrero.ioc.fithub.menu.installacions;

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
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsAdapter;

import antonioguerrero.ioc.fithub.peticions.installacions.ConsultarTotesInstallacions;

public class GestioInstallacionsActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesInstallacions.ConsultarTotesInstallacionsListener, GestioInstallacionsAdapter.OnInstallacioDeletedListener {

private RecyclerView recyclerView;
private GestioInstallacionsAdapter adapter;

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestio_installacions);

        recyclerView = findViewById(R.id.rvInstallacions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
        handleNavigationItemSelected(menuItem);
        return true;
        });

        // Inflar el layout de la cabecera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Botó "Crear nova instal·lació"
        Button btnCrearNovaInstallacio = findViewById(R.id.btnCrearNovaInstallacio);
        btnCrearNovaInstallacio.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        // Obrir l'activitat CrearInstallacioActivity
        Intent intent = new Intent(GestioInstallacionsActivity.this, CrearInstallacioActivity.class);
        startActivity(intent);
        }
        });

        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
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
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

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

@Override
public void onInstallacionsObtingudes(List<HashMap<String, String>> installacions) {
        if (installacions != null && !installacions.isEmpty()) {
        adapter = new GestioInstallacionsAdapter(this, installacions);
        recyclerView.setAdapter(adapter);
        } else {
        Utils.mostrarToast(GestioInstallacionsActivity.this, "No hi ha instal·lacions disponibles");
        }
        }

@Override
public void respostaServidor(Object resposta) throws ConnectException {
        }

@Override
public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
        }

@Override
public void onInstallacioDeleted() {
        Toast.makeText(this, "Instal·lació eliminada correctament", Toast.LENGTH_SHORT).show();
        consultarTotesInstallacions(null);
        }
        }
