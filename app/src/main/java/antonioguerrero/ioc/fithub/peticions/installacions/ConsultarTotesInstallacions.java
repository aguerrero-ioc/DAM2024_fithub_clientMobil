package antonioguerrero.ioc.fithub.peticions.installacions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir totes les instal·lacions.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les instal·lacions.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesInstallacions extends BasePeticions {
    private final Context context;
    private static final String ETIQUETA = "ConsultarInstallacions";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la classe ConsultarTotesInstallacions.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarTotesInstallacions(respostaServidorListener listener, Context context, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
        this.context = context;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    /**
     * Mètode per obtenir les dades de totes les instal·lacions.
     */
    public void obtenirInstallacions() throws ConnectException {
        enviarPeticioString("selectAll", "installacio", null, this.sessioID);

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
            if (estat != null && estat.equals("llistaInstallacions")) {
                // Obtenir la llista d'instal·lacions
                List<HashMap<String, String>> llistaInstallacions = (List<HashMap<String, String>>) respostaArray[1];
                // Iniciar l'activitat InstallacionsActivity amb la llista d'instal·lacions
                Utils.iniciarActivitatLlista(context, InstallacionsActivity.class, llistaInstallacions, "llistaInstallacions");
                // Guardar les dades de les instal·lacions a SharedPreferences
                guardarDadesInstallacions(llistaInstallacions);

                /*List<HashMap<String, String>> installacionsList = (List<HashMap<String, String>>) respostaArray[1];
                for (HashMap<String, String> installacioMap : installacionsList) {
                    Installacio installacio = new Installacio(
                            Integer.parseInt(installacioMap.get("id")),
                            installacioMap.get("nomInstallacio"),
                            installacioMap.get("descripcioInstallacio"),
                            Integer.parseInt(installacioMap.get("tipusInstallacio"))
                    );
                }*/
            } else {
                Utils.mostrarToast(context, "Error en la consulta d'instal·lacions");
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
        obtenirInstallacions();
    }

    /**
     * Guarda les propietats de l'objecte Installacio a SharedPreferences.
     *
     * @param llistaInstallacions La llista d'objectes Installacio que es guardarà a SharedPreferences.
     */
    private void guardarDadesInstallacions(List<HashMap<String, String>> llistaInstallacions) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte installacio a SharedPreferences
        for (int i = 0; i < llistaInstallacions.size(); i++) {
            HashMap<String, String> mapaInstallacions = llistaInstallacions.get(i);
            editor.putInt("idInstallacio"+ i, Integer.parseInt(mapaInstallacions.get("idInstallacio")));
            editor.putString("nomInstallacio" + i, mapaInstallacions.get("nomInstallacio"));
            editor.putString("descripcioInstallacio" + i, mapaInstallacions.get("descripcioInstallacio"));
            editor.putString("tipusInstallacio" + i, mapaInstallacions.get("tipusInstallacio"));
        }

        // Guardar la quantitat total d'instal·lacions
        editor.putInt("numInstallacions", llistaInstallacions.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}