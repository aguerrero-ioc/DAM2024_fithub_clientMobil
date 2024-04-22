package antonioguerrero.ioc.fithub.menu.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.reserves.ConsultarClassesDirigidesDia;

/**
 * Activitat per mostrar les classes dirigides disponibles per a un dia concret.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ReservesActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarClassesDirigidesDia.ConsultarClassesDirigidesDiaListener {

    private RecyclerView recyclerView;
    private TextView tvData;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);

        recyclerView = findViewById(R.id.rvClassesDirigides);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvData = findViewById(R.id.tvData);
        datePicker = findViewById(R.id.datePicker);

        // Obtenir la data actual
        String dataActual = Utils.obtenirDataActual();
        tvData.setText(dataActual);

        consultarClassesDirigides(dataActual);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void consultarClassesDirigidesDia(View view) {
        // Obtenir la data seleccionada
        int dia = datePicker.getDayOfMonth();
        int mes = datePicker.getMonth() + 1; // Se agrega 1 ya que enero se considera como 0
        int any = datePicker.getYear();
        String dataSeleccionada = Utils.obtenirDataFormatejada(dia, mes, any);
        tvData.setText(dataSeleccionada);

        consultarClassesDirigides(dataSeleccionada);
    }

    /**
     * Consulta las clases dirigidas para una fecha específica.
     *
     * @param dataSeleccionada Fecha para la que se consultan las clases dirigidas.
     */
    private void consultarClassesDirigides(String dataSeleccionada) {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

        ConsultarClassesDirigidesDia consulta = new ConsultarClassesDirigidesDia(this, this, dataSeleccionada, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public void onClassesDirigidesDiaObtingudes(List<HashMap<String, String>> classesDirigides) {
                if (classesDirigides != null && !classesDirigides.isEmpty()) {
                    ClassesDirigidesAdapter adapter = new ClassesDirigidesAdapter(ReservesActivity.this, classesDirigides);
                    recyclerView.setAdapter(adapter);
                } else {
                    Utils.mostrarToast(ReservesActivity.this, "No hi ha classes dirigides disponibles");
                }
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
        };

        consulta.consultarClassesDirigidesDia();
    }

    @Override
    public void onClassesDirigidesDiaObtingudes(List<HashMap<String, String>> classesDirigides) {
        if (classesDirigides != null && !classesDirigides.isEmpty()) {
            ClassesDirigidesAdapter adapter = new ClassesDirigidesAdapter(this, classesDirigides);
            recyclerView.setAdapter(adapter);
        } else {
            Utils.mostrarToast(ReservesActivity.this, "No hi ha classes dirigides disponibles per a aquest dia");
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
