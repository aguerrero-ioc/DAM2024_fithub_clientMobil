package antonioguerrero.ioc.fithub.menu.classesdirigides;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.peticions.classes.ConsultarClassesDirigidesDia;

/**
 * Activitat que mostra les reserves de classes dirigides.
 * <p>
 * Aquesta activitat permet consultar les classes dirigides disponibles per a una data concreta.
 * L'usuari pot seleccionar una data i consultar les classes dirigides disponibles per a aquesta data.
 * Les classes dirigides es mostren en una llista, on per a cada classe dirigida es mostra el nom, l'hora d'inici i un botó per a més detalls.
 * Quan es fa clic al botó "Més detalls", es mostra un diàleg amb la informació de la classe dirigida.
 * Aquest diàleg mostra el nom de la classe, l'hora d'inici i la durada de la classe i permet fer la reserva.
 * </p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClassesPerDiaActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarClassesDirigidesDia.ConsultarClassesDirigidesDiaListener {
    private RecyclerView recyclerView;
    private TextView tvData;
    private Calendar calendari;

    /**
     * Mètode que s'executa quan es crea l'activitat.
     *
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_dia);

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

        recyclerView = findViewById(R.id.rvClassesDirigides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView tvTitol = findViewById(R.id.tvTitol);
        TextView tvSeleccionar = findViewById(R.id.tvSeleccionar);
        tvData = findViewById(R.id.tvData);
        ImageView ivEdit = findViewById(R.id.ivEdit);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Obte la data actual
        String currentDate = Utils.obtenirDataActual();
        tvTitol.setText("Classes disponibles per dia:");
        tvSeleccionar.setText("Seleccionar dia:");

        tvData.setText(convertirData(currentDate));

        // Estableix la acció al fer clic en la data
        tvData.setOnClickListener(v -> mostrarDialegSeleccioData());

        // Estableix la acció al fer clic a l'ícona d'editar
        ivEdit.setOnClickListener(v -> mostrarDialegSeleccioData());

        consultarClassesDirigides(currentDate);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        calendari = Calendar.getInstance();
    }

    /**
     * Mètode per mostrar el diàleg de selecció de data.
     */
    private void mostrarDialegSeleccioData() {
        DatePickerDialog.OnDateSetListener listenerDataSeleccionada = (view, any, mes, dia) -> {
            calendari.set(Calendar.YEAR, any);
            calendari.set(Calendar.MONTH, mes);
            calendari.set(Calendar.DAY_OF_MONTH, dia);

            String dataSeleccionada = new SimpleDateFormat("ddMMyyyy", Locale.getDefault()).format(calendari.getTime());
            tvData.setText(convertirData(dataSeleccionada));
            consultarClassesDirigides(dataSeleccionada);
        };

        new DatePickerDialog(
                this,
                listenerDataSeleccionada,
                calendari.get(Calendar.YEAR),
                calendari.get(Calendar.MONTH),
                calendari.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    /**
     * Mètode per consultar les classes dirigides disponibles per a una data concreta.
     * <p>
     * @param dataSeleccionada Data seleccionada.
     */
    private void consultarClassesDirigides(String dataSeleccionada) {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

        ConsultarClassesDirigidesDia consulta = new ConsultarClassesDirigidesDia(this, this, dataSeleccionada, sessioID) {
         };
        consulta.consultarClassesDirigidesDia();
    }

    /**
     * Mètode per gestionar les classes dirigides obtingudes.
     *
     * @param classesDirigides Llista de classes dirigides.
     */
    @Override
    public void onClassesDirigidesDiaObtingudes(List<HashMap<String, String>> classesDirigides) {
        if (classesDirigides != null && !classesDirigides.isEmpty()) {
            // Formata les dates i les hores de les classes dirigides
            for (HashMap<String, String> classeDirigida : classesDirigides) {
                String hora = classeDirigida.get(Constants.CLASSE_HORA);
                String data = classeDirigida.get(Constants.CLASSE_DATA);
                String duracio = classeDirigida.get(Constants.CLASSE_DURACIO);
                String ocupacio = classeDirigida.get(Constants.CLASSE_OCUPACIO);
                classeDirigida.put(Constants.CLASSE_HORA, Utils.formatHora(hora) + " hores");
                classeDirigida.put(Constants.CLASSE_DATA, Utils.formatData(data));
                classeDirigida.put(Constants.CLASSE_DURACIO, duracio + " hora");
                classeDirigida.put(Constants.CLASSE_OCUPACIO, ocupacio + " clients");
            }
            ClassesPerDiaAdapter adapter = new ClassesPerDiaAdapter(this, classesDirigides);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(ClassesPerDiaActivity.this, "No hi ha classes dirigides disponibles per a aquest dia");
        }
    }

    /**
     * Converteix la data al format "Dia de la setmana, dd de mes de any".
     * <p>
     * @param data Data en format "ddMMyyyy".
     * @return Data formatada com a "Dia de la setmana, dd de mes de any".
     */
    private String convertirData(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
            Date dataConvertida = sdf.parse(data);
            if (dataConvertida != null) {
                sdf.applyPattern("EEEE, dd MMMM 'de' yyyy");
                String dataFormatejada = sdf.format(dataConvertida);
                return dataFormatejada.substring(0, 1).toUpperCase() + dataFormatejada.substring(1);
            } else {
                return "";
            }
        } catch (Exception excepcio) {
            Log.e("Convertir Data", "Error al convertir la data: " + excepcio.getMessage());
            return "";
        }
    }
}