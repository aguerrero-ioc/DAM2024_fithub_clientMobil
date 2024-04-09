package antonioguerrero.ioc.fithub.peticions.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir tots els serveis.
 * Hereta de la classe BasePeticions.
 *
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir tots els serveis.
 *
 * @Author Antonio Guerrero
 * @Version 1.0
 */
public class ConsultarTotsServeis extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarTotsServeis";
    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    /**
     * Constructor de la classe ConsultarTotsServeis.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarTotsServeis(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;
    }

    /**
     * Mètode per obtenir les dades de tots els serveis.
     */
    public void obtenirTotsServeis() {
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "serveis";
        peticio[2] = null;
        peticio[3] = this.sessioID;
        Log.d(ETIQUETA, "Enviant sol·licitud: " + Arrays.toString(peticio));
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("serveiLlista")) {
                List<HashMap<String, String>> serveisList = (List<HashMap<String, String>>) respostaArray[1];
                for (HashMap<String, String> serveiMap : serveisList) {
                    Servei servei = new Servei(
                            serveiMap.get("nom"),
                            serveiMap.get("descripcio"),
                            serveiMap.get("personal"),
                            Integer.parseInt(serveiMap.get("preu"))
                    );
                    guardarDadesServeis(serveisList);                }
            } else {
                Utils.mostrarToast(context, "Error en la consulta de serveis");
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
        obtenirTotsServeis();
    }

    /**
     * Mètode per guardar les dades dels serveis a SharedPreferences.
     *
     * @param serveisList La llista de serveis.
     */
    private void guardarDadesServeis(List<HashMap<String, String>> serveisList) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte servei a SharedPreferences
        for (int i = 0; i < serveisList.size(); i++) {
            HashMap<String, String> serveiMap = serveisList.get(i);
            editor.putString("serveiNom" + i, serveiMap.get("nom"));
            editor.putString("serveiDescripcio" + i, serveiMap.get("descripcio"));
            editor.putString("serveiPersonal" + i, serveiMap.get("personal"));
            editor.putInt("serveiPreu" + i, Integer.parseInt(serveiMap.get("preu")));
        }

        // Guardar el número de serveis
        editor.putInt("numServeis", serveisList.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}