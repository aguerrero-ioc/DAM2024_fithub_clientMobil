package antonioguerrero.ioc.fithub.peticions.activitats;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
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
public abstract class ConsultarActivitat extends BasePeticions {
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

    public ConsultarActivitat(BasePeticions.respostaServidorListener listener, ObjectOutputStream objectOut, ObjectInputStream objectIn, Context context, String nomActivitat) {
        super(listener, objectOut, objectIn);
        this.context = context;
        this.nomActivitat = nomActivitat;
    }

    /**
     * Mètode per obtenir les dades d'una activitat.
     */
    public void obtenirActivitat() {
        enviarPeticioString("select", "activitat", this.nomActivitat, this.sessioID);
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
                // Obtenir les dades de l'activitat
                HashMap<String, String> activitatMap = (HashMap<String, String>) respostaArray[1];
                // Convertir les dades de l'activitat a un objecte Activitat
                Activitat activitat = (Activitat) Utils.HashMapAObjecte(activitatMap, Activitat.class);
                // Guardar les dades de l'activitat a SharedPreferences
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

        Utils.guardarDadesObjecte(context, activitat, Activitat.class);

        /* Comparar amb aquesta implementacio
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar las propiedades del objeto activitat en SharedPreferences
        editor.putString("nomActivitat", activitat.getNomActivitat());
        editor.putString("descripcioActivitat", activitat.getDescripcioActivitat());
        editor.putString("tipusInstallacio", activitat.getTipusInstallacio());
        editor.putInt("aforamentActivitat", activitat.getAforamentActivitat());
        editor.putInt("idActivitat", activitat.getIdActivitat());

        // Aplicar los cambios a SharedPreferences
        editor.apply();*/
    }
}