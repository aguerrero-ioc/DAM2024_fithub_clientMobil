package antonioguerrero.ioc.fithub.peticions.activitats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir una activitat.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir una activitat.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarActivitat extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultaActivitat";
    private String nomActivitat;

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    /**
     * Constructor de la classe ConsultaActivitat.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */

    public ConsultarActivitat(respostaServidorListener listener, Context context, String nomActivitat) {
        super(listener);
        this.context = context;
        this.nomActivitat = nomActivitat;
    }

    /**
     * Mètode per obtenir les dades d'una activitat.
     */
    public void obtenirActivitat() {
        // Crear el Object[] per la petició
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "activitat";
        peticio[2] = this.nomActivitat;
        peticio[3] = this.sessioID;


        Log.d(ETIQUETA, "Enviant petició: " + Arrays.toString(peticio));
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
            if (estat != null && estat.equals("activitat")) {
                HashMap<String, String> activitatMap = (HashMap<String, String>) respostaArray[1];
                Activitat activitat = new Activitat(
                        Integer.parseInt(activitatMap.get("id")),
                        activitatMap.get("nomActivitat"),
                        activitatMap.get("descripcioActivitat"),
                        Integer.parseInt(activitatMap.get("aforamentActivitat")),
                        activitatMap.get("tipusActivitat")
                );
                guardarDadesActivitat(activitat);
            } else {
                Utils.mostrarToast(context, "Activitat no trobada");
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
        obtenirActivitat();
    }

    /**
     * Guarda las propiedades del objeto Activitat en SharedPreferences.
     *
     * @param activitat El objeto Activitat que se guardará en SharedPreferences.
     */
    private void guardarDadesActivitat(Activitat activitat) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar las propiedades del objeto activitat en SharedPreferences
        editor.putString("activitatNom", activitat.getNom());
        editor.putString("activitatDescripcio", activitat.getDescripcio());
        editor.putInt("activitatAforament", activitat.getAforament());
        editor.putString("activitatTipus", activitat.getTipusInstallacio());

        // Aplicar los cambios a SharedPreferences
        editor.apply();
    }
}