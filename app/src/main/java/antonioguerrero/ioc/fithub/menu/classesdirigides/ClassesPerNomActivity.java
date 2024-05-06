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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
        TextView tvTitol = findViewById(R.id.tvTitol);
        TextView tvSeleccionar = findViewById(R.id.tvSeleccionar);
        spinnerActivitats = findViewById(R.id.spinnerActivitats);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        tvTitol.setText("Classes disponibles per activitat:");
        tvSeleccionar.setText("Activitat seleccionada:");

        // Consultar totes les activitats disponibles
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

        // Configurar listener per al spinner d'activitats
        spinnerActivitats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomActivitatSeleccionada = parent.getItemAtPosition(position).toString();

                // Verificar si s'ha seleccionat l'opció per defecte
                if (nomActivitatSeleccionada.equals("Seleccionar activitat")) {
                    // No s'ha seleccionat cap activitat, no es realitza cap acció
                } else {
                    // S'ha seleccionat una activitat, procedir amb la consulta de classes dirigides
                    consultarClassesDirigides(nomActivitatSeleccionada);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Mètode per obtenir l'ID de sessió.
     *
     * @return L'ID de sessió de l'usuari.
     */
    private String obtenirSessioID() {
        SharedPreferences preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        return preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode que s'executa quan s'obtenen les activitats disponibles.
     *
     * @param activitats La llista d'activitats disponibles.
     */
    @Override
    public void onActivitatsObtingudes(List<HashMap<String, String>> activitats) {
        if (activitats != null && !activitats.isEmpty()) {
            List<String> nomsActivitats = obtenirNomActivitats(activitats);
            nomsActivitats.add(0, "Seleccionar activitat");

            // Configurar l'adaptador del Spinner amb els noms d'activitats obtinguts del servidor
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nomsActivitats);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinnerActivitats.setAdapter(adapter);
        } else {
            Utils.mostrarToast(this, "No hi ha activitats disponibles");
        }
    }

    /**
     * Mètode que s'executa quan es reben les classes dirigides per nom.
     * Filtra les classes dirigides que són posteriors a la data actual,
     * les formata i les mostra en el RecyclerView.
     *
     * @param classesDirigides La llista de classes dirigides per nom.
     */
    @Override
    public void onClassesDirigidesNomObtingudes(List<HashMap<String, String>> classesDirigides) {
        if (classesDirigides != null && !classesDirigides.isEmpty()) {

            // Filtrar les classes dirigides que són posteriors a la data i hora actuals
            List<HashMap<String, String>> classesFiltrades = new ArrayList<>();
            for (HashMap<String, String> classeDirigida : classesDirigides) {
                String dataClasse = classeDirigida.get(Constants.CLASSE_DATA);
                if (!Utils.esDataAnterior(dataClasse) || !Utils.esHoraAnterior(classeDirigida.get(Constants.CLASSE_HORA))) {
                    classesFiltrades.add(classeDirigida);
                }
            }

            // Ordenar les classes dirigides filtrades per data
            Collections.sort(classesFiltrades, new Comparator<HashMap<String, String>>() {
                @Override
                public int compare(HashMap<String, String> classe1, HashMap<String, String> classe2) {
                    String data1 = classe1.get(Constants.CLASSE_DATA);
                    String data2 = classe2.get(Constants.CLASSE_DATA);

                    // Convertir les dates de String a Date amb el format adequat
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
                    try {
                        Date date1 = sdf.parse(data1);
                        Date date2 = sdf.parse(data2);

                        // Convertir les dates al format yyyyMMdd per a la comparació
                        SimpleDateFormat sdfSortable = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        String sortableDate1 = sdfSortable.format(date1);
                        String sortableDate2 = sdfSortable.format(date2);

                        return sortableDate1.compareTo(sortableDate2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });

            // Formatejar les dates i les hores de les classes dirigides
            for (HashMap<String, String> classeDirigida : classesFiltrades) {
                String hora = classeDirigida.get(Constants.CLASSE_HORA);
                String data = classeDirigida.get(Constants.CLASSE_DATA);
                String duracio = classeDirigida.get(Constants.CLASSE_DURACIO);
                String ocupacio = classeDirigida.get(Constants.CLASSE_OCUPACIO);

                // Formatejar la data al format original ddMMyyyy
                String dataFormateada = Utils.formatData(data);

                classeDirigida.put(Constants.CLASSE_HORA, Utils.formatHora(hora) + " hores");
                classeDirigida.put(Constants.CLASSE_DATA, dataFormateada);
                classeDirigida.put(Constants.CLASSE_DURACIO, duracio + " hora");
                classeDirigida.put(Constants.CLASSE_OCUPACIO, ocupacio + " clients");
            }

            // Configurar l'adaptador del RecyclerView amb les classes dirigides filtrades
            ClassesPerNomAdapter adaptador = new ClassesPerNomAdapter(this, classesFiltrades);
            recyclerView.setAdapter(adaptador);
        } else {
            Utils.mostrarToast(ClassesPerNomActivity.this, "No hi ha classes dirigides disponibles");
        }
    }

    /**
     * Mètode per obtenir els noms d'activitats a partir d'una llista de dades d'activitats.
     *
     * @param activitats La llista de dades d'activitats.
     * @return La llista de noms d'activitats.
     */
    private List<String> obtenirNomActivitats(List<HashMap<String, String>> activitats) {
        List<String> nomsActivitats = new ArrayList<>();
        for (HashMap<String, String> activitat : activitats) {
            String nomActivitat = activitat.get("nomActivitat");
            nomsActivitats.add(nomActivitat);
        }
        return nomsActivitats;
    }

    /**
     * Mètode per consultar les classes dirigides per nom d'activitat.
     *
     * @param nomActivitat El nom de l'activitat seleccionada.
     */
    private void consultarClassesDirigides(String nomActivitat) {

        // Obtindre l'ID de sessió de l'usuari
        String sessioID = obtenirSessioID();

        // Realitzar la consulta de classes dirigides pel nom de l'activitat seleccionada
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

    /**
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta Resposta del servidor.
     */

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    /**
     * Mètode per gestionar la resposta del servidor en format HashMap.
     *
     * @param resposta Resposta del servidor.
     * @return La llista de dades en format HashMap.
     */
    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}
