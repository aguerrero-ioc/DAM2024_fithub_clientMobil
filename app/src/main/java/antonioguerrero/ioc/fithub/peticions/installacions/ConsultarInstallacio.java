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
    private String nomInstallacio;

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

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
        // Crear el Object[] per la petició
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "installacio";
        peticio[2] = this.nomInstallacio;
        peticio[3] = this.sessioID;


        Log.d(ETIQUETA, "Enviant petició: " + peticio);
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
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("installacio")) {
                HashMap<String, String> installacioMap = (HashMap<String, String>) respostaArray[1];
                Installacio installacio = new Installacio(
                        Integer.parseInt(installacioMap.get("id")),
                        installacioMap.get("nomInstallacio"),
                        installacioMap.get("descripcioInstallacio"),
                        Integer.parseInt(installacioMap.get("tipusInstallacio"))
                );
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
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte installacio a SharedPreferences
        editor.putString("installacioNom", installacio.getNom());
        editor.putString("installacioDescripcio", installacio.getDescripcio());
        editor.putInt("installacioTipus", installacio.getTipus());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}