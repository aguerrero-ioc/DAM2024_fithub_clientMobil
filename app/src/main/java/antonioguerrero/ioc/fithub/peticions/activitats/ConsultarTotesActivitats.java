package antonioguerrero.ioc.fithub.peticions.activitats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import java.util.ArrayList;
import antonioguerrero.ioc.fithub.objectes.Activitat; // Asegúrate de que esta sea la ruta correcta a la clase Activitat


/**
 * Classe per obtenir totes les activitats.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les activitats.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesActivitats extends ConnexioServidor {
    private Context context;
    private static final String ETIQUETA = "ConsultaActivitats";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarTotesActivitats(respostaServidorListener listener, String nomActivitat) {
        super(listener);
        this.context = context;
    }

    /**
     * Mètode per obtenir les dades de totes les activitats.
     */
    @SuppressLint("StaticFieldLeak")
    public void obtenirTotesActivitats() {
        final String sessioID = this.sessioID;

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "activitat","null", sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Object resposta) {
                respostaServidor(resposta);
            }
        }.execute();
    }

    /**
     * Mètode per obtenir el tipus de l'objecte.
     *
     * @return La classe de l'objecte.
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode per obtenir la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("activitatLlista")) {
                // Obtenir la llista d'activitats
                List<HashMap<String, String>> llistaActivitats = (List<HashMap<String, String>>) respostaArray[1];

                // Convertir cada HashMap en un objeto Activitat
                List<Activitat> activitats = new ArrayList<>();
                for (HashMap<String, String> mapaActivitat : llistaActivitats) {
                    Activitat activitat = Activitat.hashmap_a_activitat(mapaActivitat);
                    activitats.add(activitat);
                }
                // Guardar les dades de les activitats a SharedPreferences
                guardarDadesActivitats(llistaActivitats);
                // Devolver la lista de activitats en lugar de iniciar la actividad
                return llistaActivitats;
            } else {
                Utils.mostrarToast(context, "No s'han pogut obtenir les activitats");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
        return null;
    }

    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() throws ConnectException {
        obtenirTotesActivitats();
    }

    /**
     * Guarda les propietats de l'objecte Activitat a SharedPreferences.
     *
     * @param llistaActivitats La llista d'activitats.
     */
    private void guardarDadesActivitats(List<HashMap<String, String>> llistaActivitats) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte activitat a SharedPreferences
        for (int i = 0; i < llistaActivitats.size(); i++) {
            HashMap<String, String> mapaActivitat = llistaActivitats.get(i);
            editor.putString("IDactivitat" + i, mapaActivitat.get("idActivitat"));
            editor.putString("nomActivitat" + i, mapaActivitat.get("nomActivitat"));
            editor.putString("descripcioActivitat" + i, mapaActivitat.get("descripcioActivitat"));
            editor.putString("tipusActivitat" + i, mapaActivitat.get("tipusActivitat"));
            editor.putString("aforamentActivitat" + i, mapaActivitat.get("aforamentActivitat"));
        }

        // Guardar la quantitat total d'activitats
        editor.putInt("numActivitats", llistaActivitats.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}