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
 * Classe per obtenir un usuari.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir un usuari.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarUsuari extends ConnexioServidor {
    private Context context;
    private static final String ETIQUETA = "ConsultarUsuari";
    private String correuUsuari;
    private String sessioID;

    /**
     * Constructor de la classe.
     *
     * @param listener     Listener per obtenir la resposta del servidor.
     * @param context      Context de l'aplicació.
     * @param correuUsuari Correu de l'usuari.
     * @param sessioID     Sessió de l'usuari.
     */
    public ConsultarUsuari(ConsultarUsuariListener listener, Context context, String correuUsuari, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.sessioID = sessioID;

        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície per obtenir la resposta del servidor.
     */
    public interface ConsultarUsuariListener {
        void onUsuariObtingut(Usuari usuari);
    }

    /**
     * Mètode per obtenir un usuari.
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarUsuari() {
        final String correuUsuari = this.correuUsuari;
        final String sessioID = this.sessioID;
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("select", "usuari", correuUsuari, sessioID);
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
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return
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

                ((ConsultarUsuariListener) listener).onUsuariObtingut(usuari);

                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));

                guardarDadesUsuari(usuari);
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la consulta de l'usuari");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta.toString();
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
        return null;
    }

    /**
     * Mètode per guardar les dades de l'usuari.
     *
     * @param usuari Usuari a guardar.
     */
    private void guardarDadesUsuari(Usuari usuari) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        editor.putInt(Constants.ID_USUARI, usuari.getIDusuari());
        editor.putString(Constants.NOM_USUARI, usuari.getNomUsuari());
        editor.putString(Constants.PASS_USUARI, usuari.getPassUsuari());
        editor.putInt(Constants.TIPUS_USUARI, usuari.getTipusUsuari());
        editor.putString(Constants.CORREU_USUARI, usuari.getCorreuUsuari());
        editor.putString(Constants.COGNOMS_USUARI, usuari.getCognomsUsuari());
        editor.putString(Constants.TELEFON, usuari.getTelefon());
        editor.putString(Constants.ADRECA, usuari.getAdreca());
        editor.putString(Constants.DATA_NAIXEMENT, usuari.getDataNaixement());
        editor.putString(Constants.DATA_INSCRIPCIO, usuari.getDataInscripcio());

        editor.apply();
    }


    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() throws ConnectException {
        consultarUsuari();
    }

    /**
     * Mètode per obtenir la resposta del servidor.
     *
     * @param resposta Resposta del servidor.
     */
    public abstract void respostaServidor(Object[] resposta);

    /**
     * Mètode que s'executa en segon pla.
     *
     * @param voids Paràmetres de tipus void.
     * @return
     */
    protected Object doInBackground(Void... voids) {
        return null;
    }
}