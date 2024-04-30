package antonioguerrero.ioc.fithub.peticions.classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe per obtenir les classes dirigides d'un dia.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir les classes dirigides d'un dia.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarClassesDirigidesDia extends ConnexioServidor {

    private String dia;
    private Context context;
    private String sessioID;

    /**
     * Constructor de la classe.
     *
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     * @param dia      Dia de les classes dirigides a obtenir.
     */
    public ConsultarClassesDirigidesDia(respostaServidorListener listener, Context context, String dia, String sessioID) {
        super(listener);
        this.context = context;
        this.dia = dia;
        this.sessioID = sessioID;
    }

    public abstract void onClassesDirigidesDiaObtingudes(List<HashMap<String, String>> classesDirigides);

    /**
     * Interfície per obtenir la resposta del servidor.
     */
    public interface ConsultarClassesDirigidesDiaListener {
        void onClassesDirigidesDiaObtingudes(List<HashMap<String, String>> classesDirigides);
    }

    /**
     * Mètode per obtenir les classes dirigides d'un dia.
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarClassesDirigidesDia() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "classeDirigida", dia, sessioID);
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
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return La llista de dades de les classes dirigides.
     */

    public void processarResposta(Object resposta) {
        // Verificar que la resposta no sigui nula i sigui un array d'objectes
        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            // Verificar que el array tingui almenys dos elements i que el primer element sigui un String
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String) {
                String estat = (String) respostaArray[0];
                // Verificar si el primer element és "classeDirigidaLlista"
                if ("classeDirigidaLlista".equals(estat)) {
                    // Verificar si el segon element és una llista
                    if (respostaArray[1] instanceof List) {
                        // Convertir el segon element a una llista de HashMaps
                        List<HashMap<String, String>> classesDirigides = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarClassesDirigidesDiaListener) {
                            ((ConsultarClassesDirigidesDiaListener) listener).onClassesDirigidesDiaObtingudes(classesDirigides);
                        }
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
     * Guarda les dades de les classes dirigides a SharedPreferences.
     *
     * @param dadesClassesDirigides La llista de dades de les classes dirigides.
     */
    private void guardarDadesClassesDirigides(List<HashMap<String, String>> dadesClassesDirigides) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les dades de les classes dirigides a SharedPreferences
        for (int i = 0; i < dadesClassesDirigides.size(); i++) {
            HashMap<String, String> mapaClassesDirigides = dadesClassesDirigides.get(i);
            editor.putString("nomActivitat" + i, mapaClassesDirigides.get("nomActivitat"));
            editor.putString("nomInstallacio" + i, mapaClassesDirigides.get("nomInstallacio"));
            editor.putString("dia" + i, mapaClassesDirigides.get("dia"));
            editor.putString("horaInici" + i, mapaClassesDirigides.get("horaInici"));
            editor.putString("duracio" + i, mapaClassesDirigides.get("duracio"));
        }

        // Guardar la quantitat de classes dirigides a SharedPreferences
        editor.putInt("numClassesDirigides", dadesClassesDirigides.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}