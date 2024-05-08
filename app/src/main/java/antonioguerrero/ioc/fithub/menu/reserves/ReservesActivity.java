package antonioguerrero.ioc.fithub.menu.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.reserves.ConsultarTotesReserves;

/**
 * Activitat que permet a l'usuari veure les reserves.
 * <p>
 * Aquesta activitat mostra una llista de totes les reserves de l'usuari.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ReservesActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesReserves.ConsultarTotesReservesListener {
    private RecyclerView recyclerView;

    /**
     * Mètode que s'executa en la creació de l'activitat.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);
        Usuari.setContext(this);

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

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.rvReserves);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtenir el correu de l'usuari
        String correuUsuari = Usuari.obtenirUsuari().getCorreuUsuari();

        // Realizar la consulta de todas las reservas del usuario
        consultarTotesReserves(correuUsuari);
    }

    /**
     * Mètode per consultar totes les reserves de l'usuari.
     * <p>
     * @param correuUsuari Correu de l'usuari.
     */
    private void consultarTotesReserves(String correuUsuari) {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotesReserves consulta = new ConsultarTotesReserves(this, this, correuUsuari, sessioID) {
        };
        consulta.consultarTotesReserves();
    }

    /**
     * Mètode per gestionar les reserves obtingudes.
     * <p>
     * @param reserves Llista de reserves.
     */
    @Override
    public void onReservesObtingudes(List<HashMap<String, String>> reserves) {
        if (reserves != null && !reserves.isEmpty()) {

            // Formata les dates i les hores de les classes dirigides
            for (HashMap<String, String> classeDirigida : reserves) {
                String hora = classeDirigida.get(Constants.CLASSE_HORA);
                String data = classeDirigida.get(Constants.CLASSE_DATA);
                String duracio = classeDirigida.get(Constants.CLASSE_DURACIO);
                String ocupacio = classeDirigida.get(Constants.CLASSE_OCUPACIO);
                classeDirigida.put(Constants.CLASSE_HORA, Utils.formatHora(hora));
                classeDirigida.put(Constants.CLASSE_DATA, Utils.formatData(data));
                classeDirigida.put(Constants.CLASSE_DURACIO, duracio);
                classeDirigida.put(Constants.CLASSE_OCUPACIO, ocupacio);
            }

            // Ordenar les classes dirigides filtrades per data
            Collections.sort(reserves, (classe1, classe2) -> {
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
                    Log.e("ReservesActivity", "Error en l'anàlisi de la data", e);
                    return 0;
                }
            });
            // Configura l'adaptador del RecyclerView amb les classes dirigides filtrades
            ReservesAdapter adapter = new ReservesAdapter(this, reserves);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(ReservesActivity.this, "No hi ha reserves disponibles");
        }
    }
}