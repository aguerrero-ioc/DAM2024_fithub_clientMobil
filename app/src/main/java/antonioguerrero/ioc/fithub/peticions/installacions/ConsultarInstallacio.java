package antonioguerrero.ioc.fithub.peticions.installacions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir una instal·lació.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir una instal·lació.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */

public class ConsultarInstallacio extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarInstallacio";
    private final String nomInstallacio;

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe ConsultarInstallacio.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarInstallacio(respostaServidorListener listener, Context context, String nomInstallacio) {
        super(listener);
        this.context = context;
        this.nomInstallacio = nomInstallacio;
    }

    /**
     * Mètode per obtenir les dades d'una instal·lació.
     */
    public void obtenirInstallacio() {
        enviarPeticio("select", "installacio", this.nomInstallacio, this.sessioID, ETIQUETA);
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
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("installacio")) {
                // Guardar les dades de la instal·lació a SharedPreferences
                HashMap<String, String> installacioMap = (HashMap<String, String>) respostaArray[1];
                // Convertir el HashMap a un objecte Installacio
                Installacio installacio = (Installacio) Utils.HashMapAObjecte(installacioMap, Installacio.class);
                // Guardar les dades de la instal·lació a SharedPreferences
                guardarDadesInstallacio(installacio);
            } else {
                Utils.mostrarToast(context, "Error en la consulta de la instal·lació");
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
        obtenirInstallacio();
    }

    /**
     * Guarda les propietats de l'objecte Installacio a SharedPreferences.
     *
     * @param installacio L'objecte Installacio que es guardarà a SharedPreferences.
     */
    private void guardarDadesInstallacio(Installacio installacio) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte installacio a SharedPreferences
        editor.putInt("idInstallacio", installacio.getIDInstallacio());
        editor.putString("nomInstallacio", installacio.getNomInstallacio());
        editor.putString("descripcioInstallacio", installacio.getDescripcioInstallacio());
        editor.putInt("tipusInstallacio", installacio.getTipusInstallacio());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}