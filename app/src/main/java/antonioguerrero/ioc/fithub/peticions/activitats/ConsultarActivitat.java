package antonioguerrero.ioc.fithub.peticions.activitats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe per obtenir una activitat.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir una activitat.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarActivitat extends ConnexioServidor {
    private Context context;
    private static final String ETIQUETA = "ConsultaActivitat";
    private String nomActivitat;

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    /**
     * Constructor de la classe ConsultaActivitat.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */


            public ConsultarActivitat(ConsultarActivitat.ConsultarActivitatListener listener, Context context, String nomActivitat, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.nomActivitat = nomActivitat;
                this.sessioID = sessioID;

                SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    /**
     * Mètode per obtenir les dades d'una activitat.
     */
    @SuppressLint("StaticFieldLeak")
    public void obtenirActivitat() {
        final String nomActivitat = this.nomActivitat;
        final String sessioID = this.sessioID;

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("select", "activitat", nomActivitat, sessioID);
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
    public List<HashMap<String, String>> respostaServidor(Object resposta){
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("activitat")) {
                // Obtenir les dades de l'activitat
                HashMap<String, String> mapaActivitat = (HashMap<String, String>) respostaArray[1];
                Activitat activitat = Activitat.hashmap_a_activitat(mapaActivitat);
              ((ConsultarActivitat.ConsultarActivitatListener) listener).onActivitatObtinguda(activitat);
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));


                // Guardar les dades de l'activitat a SharedPreferences
                guardarDadesActivitat(activitat);
            } else {
                Utils.mostrarToast(context, "Activitat no trobada");
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
        obtenirActivitat();
    }

    /**
     * Guarda las propiedades del objeto Activitat en SharedPreferences.
     *
     * @param activitat El objeto Activitat que se guardará en SharedPreferences.
     */
    private void guardarDadesActivitat(Activitat activitat) {

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar las propiedades del objeto activitat en SharedPreferences
        editor.putString("nomActivitat", activitat.getNomActivitat());
        editor.putString("descripcioActivitat", activitat.getDescripcioActivitat());
        editor.putString("tipusInstallacio", String.valueOf(activitat.getTipusInstallacio()));
        editor.putInt("aforamentActivitat", activitat.getAforamentActivitat());
        editor.putInt("idActivitat", activitat.getIDActivitat());

        // Aplicar los cambios a SharedPreferences
        editor.apply();
    }

    public interface ConsultarActivitatListener {
        void onActivitatObtinguda(Activitat activitat);
    }
}