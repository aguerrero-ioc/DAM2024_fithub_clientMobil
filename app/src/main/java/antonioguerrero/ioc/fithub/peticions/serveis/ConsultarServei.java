package antonioguerrero.ioc.fithub.peticions.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Activitat;
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
public abstract class ConsultarServei extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarServei";
    private String nomServei;
    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe ConsultarServei.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarServei(respostaServidorListener listener, Context context, String nomServei, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
        this.context = context;
        this.nomServei = nomServei;
    }

    /**
     * Mètode per obtenir les dades d'un servei.
     */
    public void obtenirServei() throws ConnectException {
        enviarPeticioString("select", "servei", this.nomServei, this.sessioID);
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
                Servei servei = (Servei) Utils.HashMapAObjecte(serveiMap, Servei.class);
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
    public void execute() throws ConnectException {
        obtenirServei();
    }

    /**
     * Mètode per guardar les dades d'un servei.
     *
     * @param servei El servei a guardar.
     */
    private void guardarDadesServei(Servei servei) {

        Utils.guardarDadesObjecte(context, servei, Servei.class);

        /* Comparar amb aquesta implementacio

        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte servei a SharedPreferences
        editor.putInt("IDServei", servei.getIDServei());
        editor.putString("nomServei", servei.getNomServei());
        editor.putString("descripcioServei", servei.getDescripcioServei());
        editor.putString("aforamentServei", servei.getDescripcioServei());
        editor.putString("tipusInstallacio", servei.getTipusInstallacio());
        editor.putString("personalServei", servei.getPersonalServei());
        editor.putInt("preuServei", servei.getPreuServei());


        // Aplicar els canvis
        editor.apply();

         */
    }
}