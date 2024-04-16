package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
//import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;


public abstract class ConsultarClassesDirigidesDia extends ConnexioServidor {

    private String dia;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;
    private static final String ETIQUETA = "ConsultarClasseDirigidaDia";


    public ConsultarClassesDirigidesDia(respostaServidorListener listener, Context context, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener);
        this.context = context;
        this.dia = dia;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    public void obtenirClassesDirigides() throws ConnectException {
        enviarPeticioString("selectAll", "classe", dia, this.sessioID);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("classeDirigida")) {
                ArrayList<HashMap<String, String>> dadesClassesDirigides = (ArrayList<HashMap<String, String>>) respostaArray[1];

                // Guardar les dades de les instal·lacions a SharedPreferences
                guardarDadesClassesDirigides(dadesClassesDirigides);
            } else {
                Utils.mostrarToast(context, "Error en la consulta de clases dirigidas");
            }
        } else {
            Utils.mostrarToast(context, "Error de conexión");
        }
        return null;
    }

    /**
     * Guarda les dades de les classes dirigides a SharedPreferences.
     *
     * @param dadesClassesDirigides La llista de dades de les classes dirigides.
     */
    private void guardarDadesClassesDirigides(List<HashMap<String, String>> dadesClassesDirigides) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les dades de les classes dirigides a SharedPreferences
        for (int i = 0; i < dadesClassesDirigides.size(); i++) {
            HashMap<String, String> mapaClassesDirigides = dadesClassesDirigides.get(i);
            editor.putInt("IDActivitat" + i, Integer.parseInt(mapaClassesDirigides.get("IDActivitat")));
            editor.putInt("IDInstallacio" + i, Integer.parseInt(mapaClassesDirigides.get("IDInstallacio")));
            editor.putString("dia" + i, mapaClassesDirigides.get("dia"));
            editor.putString("horaInici" + i, mapaClassesDirigides.get("horaInici"));
            editor.putInt("duracio" + i, Integer.parseInt(mapaClassesDirigides.get("duracio")));

        }

        // Guardar la quantitat de classes dirigides a SharedPreferences
        editor.putInt("numClassesDirigides", dadesClassesDirigides.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }



    @Override
    public void execute() throws ConnectException {
        obtenirClassesDirigides();
    }
}