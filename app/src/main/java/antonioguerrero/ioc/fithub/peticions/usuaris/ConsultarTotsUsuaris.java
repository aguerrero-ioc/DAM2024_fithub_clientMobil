package antonioguerrero.ioc.fithub.peticions.usuaris;

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
 * Classe per obtenir tots els usuaris.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir tots els usuaris.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotsUsuaris extends ConnexioServidor {
    private final Context context;
    private static final String ETIQUETA = "ConsultarUsuaris";
    String sessioID;

    /**
     * Constructor de la classe.
     * <p>
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     * @param sessioID Identificador de la sessió.
     */
    public ConsultarTotsUsuaris(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;

        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície per obtenir la resposta del servidor.
     */
    public interface ConsultarTotsUsuarisListener {
        void onUsuarisObtinguts(List<HashMap<String, String>> usuaris);
    }

    /**
     * Mètode per obtenir tots els usuaris del servidor.
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarTotsUsuaris() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "usuari", null, sessioID);
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
    private void processarResposta(Object resposta) {
        // Verificar que la resposta no sigui nula i sigui un array d'objectes
        if (resposta instanceof Object[] respostaArray) {
            // Verificar que el array tingui almenys dos elements i que el primer element sigui un String
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String estat) {
                // Verificar si el primer element és "usuariLlista"
                if ("usuariLlista".equals(estat)) {
                    // Verificar si el segon element és una llista
                    if (respostaArray[1] instanceof List) {
                        // Convertir el segon element a una llista de HashMaps
                        List<HashMap<String, String>> usuaris = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarTotsUsuarisListener) {
                            ((ConsultarTotsUsuarisListener) listener).onUsuarisObtinguts(usuaris);
                        }
                        Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                        guardarDadesUsuaris(usuaris);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta d'usuaris");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
    }

    /**
     * Mètode per executar la petició.
     */
    public void execute() throws ConnectException {
        consultarTotsUsuaris();
    }

    /**
     * Guarda les propietats de l'objecte Usuari a SharedPreferences.
     * <p>
     * @param usuaris La llista d'objectes Usuari que es guardarà a SharedPreferences.
     */
    private void guardarDadesUsuaris(List<HashMap<String, String>> usuaris) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Guardar les propietats de cada objecte Usuari a SharedPreferences
        for (int i = 0; i < usuaris.size(); i++) {
            HashMap<String, String> mapaUsuari = usuaris.get(i);
            editor.putInt("IDusuari" + i, Integer.parseInt(mapaUsuari.get("IDusuari")));
            editor.putString("correuUsuari" + i, mapaUsuari.get("correuUsuari"));
            // Add any other properties you want to save here...
        }

        // Guardar la quantitat total d'usuaris
        editor.putInt("numUsuaris", usuaris.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}
