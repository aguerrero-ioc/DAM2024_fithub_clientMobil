package antonioguerrero.ioc.fithub.peticions.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;


/**
 * Classe per obtenir un servei.
 * Hereta de la classe BasePeticions.
 *
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir un servei.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarServei extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarServei";
    private String nomServei;
    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    /**
     * Constructor de la classe ConsultarServei.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarServei(respostaServidorListener listener, Context context, String nomServei, String sessioID) {
        super(listener);
        this.context = context;
        this.nomServei = nomServei;
        this.sessioID = sessioID;
    }

    /**
     * Mètode per obtenir les dades d'un servei.
     */
    public void obtenirServei() {
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "servei";
        peticio[2] = this.nomServei;
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
    public void respostaServidor(Object resposta){
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("servei")) {
                HashMap<String, String> serveiMap = (HashMap<String, String>) respostaArray[1];
                Servei servei = new Servei(
                        serveiMap.get("nom"),
                        serveiMap.get("descripcio"),
                        serveiMap.get("personal"),
                        Integer.parseInt(serveiMap.get("preu"))
                );
                guardarDadesServei(servei);
            } else {
                Utils.mostrarToast(context, "Error en la consulta del servei");
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
        obtenirServei();
    }

    /**
     * Mètode per guardar les dades d'un servei.
     *
     * @param servei El servei a guardar.
     */
    private void guardarDadesServei(Servei servei) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte servei a SharedPreferences
        editor.putString("serveiNom", servei.getNom());
        editor.putString("serveiDescripcio", servei.getDescripcio());
        editor.putString("serveiPersonal", servei.getPersonal());
        editor.putInt("serveiPreu", servei.getPreu());

        // Aplicar els canvis
        editor.apply();
    }
}