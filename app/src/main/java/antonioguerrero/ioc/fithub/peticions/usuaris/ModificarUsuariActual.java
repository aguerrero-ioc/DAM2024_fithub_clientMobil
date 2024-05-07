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
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que s'encarrega de fer la petició al servidor per modificar un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarUsuariActual extends ConnexioServidor {
    private static final String ETIQUETA = "ModificarUsuari";
    private Usuari usuari;
    private final Context context;
    private String sessioID;

    /**
     * Constructor de la classe
     * <p>
     * @param listener Listener per a la resposta del servidor
     * @param context Context de l'aplicació
     * @param sessioID Sessió de l'usuari
     */
    public ModificarUsuariActual(ModificarUsuariListener listener, Context context, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.sessioID = sessioID;
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que conté el mètode onUsuariModificat
     */
    public interface ModificarUsuariListener {
        void onUsuariModificat(Usuari usuari);
    }

    /**
     * Mètode que retorna l'usuari
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Mètode que modifica un usuari
     */
    @SuppressLint("StaticFieldLeak")
    public void modificarUsuari() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    return enviarPeticioHashMap("update", "usuari", mapaUsuari, sessioID);
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
     * Mètode que retorna la resposta del servidor
     * <p>
     * @param resposta Resposta del servidor
     * @return Llista de HashMaps
     */
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[] arrayResposta) {
            String estat = (String) arrayResposta[0];
            if (estat.equals("usuari")) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                Usuari usuari = Usuari.hashmap_a_usuari(mapaUsuari);

                ((ModificarUsuariListener) listener).onUsuariModificat(usuari);
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                Utils.mostrarToast(context, "S'han desat els canvis correctament");
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de l'usuari");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta;
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
        return null;
    }

    /**
     * Mètode que executa la petició
     */
    public void execute() throws ConnectException {
        modificarUsuari();
    }
}