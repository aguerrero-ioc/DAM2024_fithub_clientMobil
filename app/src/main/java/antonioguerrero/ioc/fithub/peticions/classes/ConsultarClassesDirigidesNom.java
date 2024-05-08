package antonioguerrero.ioc.fithub.peticions.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe per obtenir les classes dirigides disponibles per un nom d'activitat.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les classes dirigides disponibles per un nom d'activitat.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarClassesDirigidesNom extends ConnexioServidor {
    private static final String ETIQUETA = "ConsultarClasseDirigida";
    private final String nomClasseDirigida;
    private final Context context;
    private final String sessioID;

    /**
     * Constructor de la classe.
     * <p>
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     * @param nomClasseDirigida Nom de l'activitat de les classes dirigides a obtenir.
     */
    public ConsultarClassesDirigidesNom(respostaServidorListener listener, Context context, String nomClasseDirigida, String sessioID) {
        super(listener);
        this.context = context;
        this.nomClasseDirigida = nomClasseDirigida;
        this.sessioID = sessioID;
    }

    /**
     * Interfície per a les respostes del servidor.
     */
    public interface ConsultarClassesDirigidesNomListener {
        void onClassesDirigidesNomObtingudes(List<HashMap<String, String>> classesDirigides);
    }

    /**
     * Mètode per obtenir les classes dirigides per un nom d'activitat.
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarClasseDirigidaNom() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("select", "classeDirigida", nomClasseDirigida, sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta);
            }
        }.execute();
    }

    /**
     * Mètode per processar la resposta del servidor.
     * <p>
     * @param resposta Resposta del servidor.
     */
    public void processarResposta(Object resposta) {
        // Verificar que la resposta no sigui nula i sigui un array d'objectes
        if (resposta != null && resposta instanceof Object[] respostaArray) {
            // Verificar que el array tingui almenys dos elements i que el primer element sigui un String
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String estat) {
                // Verificar si el primer element és "classeDirigidaLlista"
                if ("classeDirigidaLlista".equals(estat)) {
                    // Verificar si el segon element és una llista
                    if (respostaArray[1] instanceof List) {
                        // Convertir el segon element a una llista de HashMaps
                        List<HashMap<String, String>> classesDirigides = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarClassesDirigidesNomListener) {
                            ((ConsultarClassesDirigidesNomListener) listener).onClassesDirigidesNomObtingudes(classesDirigides);
                        }
                        Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                        guardarDadesClassesDirigides(classesDirigides);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de classes dirigides");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
    }

    /**
     * Mètode per guardar les dades de les classes dirigides a SharedPreferences.
     * <p>
     * @param dadesClassesDirigides Llista de dades de les classes dirigides.
     */
    private void guardarDadesClassesDirigides(List<HashMap<String, String>> dadesClassesDirigides) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les dades de les classes dirigides a SharedPreferences
        for (int i = 0; i < dadesClassesDirigides.size(); i++) {
            HashMap<String, String> mapaClassesDirigides = dadesClassesDirigides.get(i);
            editor.putString(Constants.ACT_NOM + i, mapaClassesDirigides.get(Constants.ACT_NOM));
            editor.putString(Constants.INS_NOM + i, mapaClassesDirigides.get(Constants.INS_NOM));
            editor.putString(Constants.CLASSE_ID + i, mapaClassesDirigides.get(Constants.CLASSE_ID));
            editor.putString(Constants.CLASSE_DATA + i, mapaClassesDirigides.get(Constants.CLASSE_DATA));
            editor.putString(Constants.CLASSE_HORA + i, mapaClassesDirigides.get(Constants.CLASSE_HORA));
            editor.putString(Constants.CLASSE_DURACIO + i, mapaClassesDirigides.get(Constants.CLASSE_DURACIO));
        }

        // Guardar la quantitat de classes dirigides a SharedPreferences
        editor.putInt("numClassesDirigides", dadesClassesDirigides.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}