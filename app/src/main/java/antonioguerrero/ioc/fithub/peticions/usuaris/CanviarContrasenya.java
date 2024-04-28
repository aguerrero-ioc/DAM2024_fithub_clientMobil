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
 * Classe que permet canviar la contrasenya d'un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CanviarContrasenya extends ConnexioServidor {

    private static final String ETIQUETA = "CanviarContrasenya";
    private Usuari usuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;

    /**
     * Constructor de la classe
     * @param listener Listener de la classe
     * @param context Context de l'aplicació
     * @param sessioID Sessió de l'usuari
     */
    public CanviarContrasenya(ModificarUsuari.ModificarUsuariListener listener, Context context, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.sessioID = sessioID;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode que permet obtenir l'usuari
     * @return Usuari
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Mètode que permet canviar la contrasenya de l'usuari
     */
    @SuppressLint("StaticFieldLeak")
    public void canviarContrasenya() {

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    return enviarPeticioHashMap("update", "pass", mapaUsuari, sessioID);
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
     * Mètode que permet obtenir el tipus d'objecte
     * @return Tipus d'objecte
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que permet obtenir la resposta del servidor
     * @param resposta Resposta del servidor
     * @return Resposta del servidor
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("usuari")) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                Usuari usuari = Usuari.hashmap_a_usuari(mapaUsuari);

                ((ModificarUsuari.ModificarUsuariListener) listener).onUsuariModificat(usuari);
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));

                Utils.mostrarToast(context, "Contrasenya modificada correctament");
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de la contrasenya");
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
     * @throws ConnectException Excepció de connexió
     */
    @Override
    public void execute() throws ConnectException {
        canviarContrasenya();
    }

    /**
     * Mètode que s'executa en segon pla
     * @param voids Paràmetres de tipus void
     * @return Objecte
     */
    protected abstract Object doInBackground(Void... voids);
}