package antonioguerrero.ioc.fithub.peticions.activitats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;


/**
 * Classe per obtenir totes les activitats.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les activitats.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarTotesActivitats extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultaActivitats";

    public ConsultarTotesActivitats(respostaServidorListener listener, Context context) {
        super(listener);
        this.context = context;
    }

    /**
     * Mètode per obtenir les dades de totes les activitats.
     */
    public void obtenirActivitats() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("type", "selectAll");
        requestMap.put("objectType", "Activitat");
        requestMap.put("data", null);
        Log.d(ETIQUETA, "Enviant petició: " + requestMap.toString());
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestMap);
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
     */
    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("activitatLlista")) {
                List<HashMap<String, String>> activitatsList = (List<HashMap<String, String>>) respostaArray[1];
                guardarDadesActivitats(activitatsList);
            } else {
                Utils.mostrarToast(context, "No s'han pogut obtenir les activitats");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }

    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() {
        obtenirActivitats();
    }

    /**
     * Guarda les propietats de l'objecte Activitat a SharedPreferences.
     *
     * @param activitatsList La llista d'activitats.
     */
    private void guardarDadesActivitats(List<HashMap<String, String>> activitatsList) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte activitat a SharedPreferences
        for (int i = 0; i < activitatsList.size(); i++) {
            HashMap<String, String> activitatMap = activitatsList.get(i);
            editor.putString("activitatNom" + i, activitatMap.get("nomActivitat"));
            editor.putString("activitatDescripcio" + i, activitatMap.get("descripcioActivitat"));
            editor.putString("activitatTipus" + i, activitatMap.get("tipusActivitat"));
            editor.putString("activitatAforament" + i, activitatMap.get("aforamentActivitat"));
        }

        // Guardar la quantitat total d'activitats
        editor.putInt("numActivitats", activitatsList.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}