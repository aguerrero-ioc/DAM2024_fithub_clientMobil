package antonioguerrero.ioc.fithub.menu.classesdirigides;

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
public class ClassesPerNomActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarClassesDirigidesNom.ConsultarClassesDirigidesNomListener, ConsultarTotesActivitats.ConsultarTotesActivitatsListener {

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
        setContentView(R.layout.activity_classes_nom);

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

        recyclerView = findViewById(R.id.rvClassesDirigides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvTitol = findViewById(R.id.tvTitol);
        tvSeleccionar = findViewById(R.id.tvSeleccionar);
        spinnerActivitats = findViewById(R.id.spinnerActivitats);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        tvTitol.setText("Classes disponibles per activitat:");
        tvSeleccionar.setText("Activitat seleccionada:");

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

                // Verificar si se ha seleccionado la opción por defecto
                if (nomActivitatSeleccionada.equals("Seleccionar activitat")) {
                    // No se seleccionó ninguna actividad
                } else {
                    // Se seleccionó una actividad, proceder con la consulta de clases dirigidas
                    consultarClassesDirigides(nomActivitatSeleccionada);
                }
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
            nomsActivitats.add(0, "Seleccionar activitat");

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
        if (classesDirigides != null && !classesDirigides.isEmpty()) {
            ClassesPerNomAdapter adapter = new ClassesPerNomAdapter(this, classesDirigides);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(ClassesPerNomActivity.this, "No hi ha classes dirigides disponibles");
        }
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
        String sessioID = obtenirSessioID();

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
            }
        };
        consulta.consultarClasseDirigidaNom();
    }

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}
