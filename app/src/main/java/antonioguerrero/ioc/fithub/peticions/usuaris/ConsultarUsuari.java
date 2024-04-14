package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir un usuari.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir un usuari.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarUsuari extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarUsuari";
    private String correuUsuari;
    private String sessioID;

    /**
     * Constructor de la classe ConsultarUsuari.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarUsuari(respostaServidorListener listener, Context context, String correuUsuari, String sessioID) {
        super(listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.sessioID = sessioID;

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
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
     */
    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("usuari")) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                Usuari usuari = (Usuari) Utils.HashMapAObjecte(mapaUsuari, Usuari.class);

                /* Comprovar que funciona amb HashMapAObjecte
                Usuari usuari = new Usuari();
                usuari.setNomUsuari(mapaUsuari.get("nomUsuari"));
                usuari.setCorreuUsuari(mapaUsuari.get("correuUsuari"));
                usuari.setPassUsuari(mapaUsuari.get("passUsuari"));
                usuari.setCognomsUsuari(mapaUsuari.get("cognomsUsuari"));
                usuari.setTelefon(mapaUsuari.get("telefon"));
                usuari.setAdreca(mapaUsuari.get("Adreca"));
                try {
                    SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
                    usuari.setDataNaixement(formatData.parse(mapaUsuari.get("DataNaixement")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la consulta de l'usuari");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta.toString();
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
    }

    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() throws ConnectException {
        consultarUsuari();
    }

    protected Object doInBackground(Void... voids) {
        return null;
    }
}