package antonioguerrero.ioc.fithub.menu.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.peticions.activitats.ConsultarTotesActivitats;
import antonioguerrero.ioc.fithub.peticions.classes.ConsultarClassesDirigidesNom;

/**
 * Activitat que mostra les reserves de classes dirigides per activitat.
 * <p>
 * Aquesta activitat permet consultar les classes dirigides disponibles per a una activitat concreta.
 * L'usuari pot seleccionar una activitat i consultar les classes dirigides disponibles per a aquesta activitat.
 * Les classes dirigides es mostren en una llista, on per a cada classe dirigida es mostra el nom, l'hora d'inici i un botó per a més detalls.
 * Quan es fa clic al botó "Més detalls", es mostra un diàleg amb la informació de la classe dirigida.
 * Aquest diàleg mostra el nom de la classe, l'hora d'inici i la durada de la classe.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ReservesPerNomActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarClassesDirigidesNom.ConsultarClassesDirigidesNomListener, ConsultarTotesActivitats.ConsultarTotesActivitatsListener {

    private RecyclerView recyclerView;
    private TextView tvTitol;
    private TextView tvSeleccionar;
    private Spinner spinnerActivitats;



    /**
     * Mètode que s'executa quan es crea l'activitat.
     *
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves_nom);

        recyclerView = findViewById(R.id.rvClassesDirigides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTitol = findViewById(R.id.tvTitol);
        tvSeleccionar = findViewById(R.id.tvSeleccionar);
        spinnerActivitats = findViewById(R.id.spinnerActivitats);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        tvTitol.setText("Classes disponibles per activitat:");
        tvSeleccionar.setText("Seleccionar activitat:");

        // Consultar todas las actividades disponibles
        ConsultarTotesActivitats consultarTotesActivitats = new ConsultarTotesActivitats(this, this, obtenirSessioID()) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };
        consultarTotesActivitats.consultarTotesActivitats();

        // Configurar listener para el spinner de actividades
        spinnerActivitats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomActivitatSeleccionada = parent.getItemAtPosition(position).toString();
                consultarClassesDirigides(nomActivitatSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se seleccionó ninguna actividad
            }
        });
    }

    private String obtenirSessioID() {
        SharedPreferences preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        return preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    @Override
    public void onActivitatsObtingudes(List<HashMap<String, String>> activitats) {
        if (activitats != null && !activitats.isEmpty()) {
            List<String> nomsActivitats = obtenirNomActivitats(activitats);

            // Configurar el adaptador del Spinner con los nombres de actividades obtenidos del servidor
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomsActivitats);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerActivitats.setAdapter(adapter);
        } else {
            Utils.mostrarToast(this, "No hi ha activitats disponibles");
        }
    }

    @Override
    public void onClassesDirigidesNomObtingudes(List<HashMap<String, String>> classesDirigides) {

    }

    private List<String> obtenirNomActivitats(List<HashMap<String, String>> activitats) {
        List<String> nomsActivitats = new ArrayList<>();
        for (HashMap<String, String> activitat : activitats) {
            String nomActivitat = activitat.get("nomActivitat");
            nomsActivitats.add(nomActivitat);
        }
        return nomsActivitats;
    }



    // Método para consultar las clases dirigidas por el nombre de la actividad seleccionada
    private void consultarClassesDirigides(String nomActivitat) {
        // Obtener sesión ID del usuario
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

        // Realizar la consulta de clases dirigidas por el nombre de la actividad seleccionada
        ConsultarClassesDirigidesNom consulta = new ConsultarClassesDirigidesNom(this, this, nomActivitat, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public Class<?> obtenirTipusObjecte() {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            @Override
            public void execute() throws ConnectException {

            }

            @Override
            public void onClassesDirigidesNomObtingudes(List<HashMap<String, String>> classesDirigides) {
                // Aquí puedes manejar la respuesta de la consulta de clases dirigidas
                // Por ejemplo, actualizar el RecyclerView con las clases dirigidas obtenidas
                if (classesDirigides != null && !classesDirigides.isEmpty()) {
                    ReservesPerDiaAdapter adapter = new ReservesPerDiaAdapter(ReservesPerNomActivity.this, classesDirigides);
                    recyclerView.setAdapter(adapter);
                } else {
                    Utils.mostrarToast(ReservesPerNomActivity.this, "No hi ha classes dirigides disponibles per a aquesta activitat");
                }
            }
        };
        consulta.consultarClasseDirigida();
    }

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}
