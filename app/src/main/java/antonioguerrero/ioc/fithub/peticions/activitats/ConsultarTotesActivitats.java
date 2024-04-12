package antonioguerrero.ioc.fithub.peticions.activitats;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;


/**
 * Classe per obtenir totes les activitats.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les activitats.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarTotesActivitats extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultaActivitats";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarTotesActivitats(respostaServidorListener listener, Context context) {
        super(listener);
        this.context = context;
    }

    /**
     * Mètode per obtenir les dades de totes les activitats.
     */
    public void obtenirActivitats() {
        enviarPeticio("selectAll", "activitat", null, this.sessioID, ETIQUETA);
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
     * Mètode per obtenir la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("activitatLlista")) {
                // Obtenir la llista d'activitats
                List<HashMap<String, String>> llistaActivitats = (List<HashMap<String, String>>) respostaArray[1];
                // Iniciar l'activitat de les activitats
                Utils.iniciarActivitatLlista(context, ActivitatsActivity.class, llistaActivitats, "llistaActivitats");
                // Guardar les dades de les activitats a SharedPreferences
                guardarDadesActivitats(llistaActivitats);
            } else {
                Utils.mostrarToast(context, "No s'han pogut obtenir les activitats");
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
        obtenirActivitats();
    }

    /**
     * Guarda les propietats de l'objecte Activitat a SharedPreferences.
     *
     * @param llistaActivitats La llista d'activitats.
     */
    private void guardarDadesActivitats(List<HashMap<String, String>> llistaActivitats) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte activitat a SharedPreferences
        for (int i = 0; i < llistaActivitats.size(); i++) {
            HashMap<String, String> mapaActivitat = llistaActivitats.get(i);
            editor.putString("idActivitat" + i, mapaActivitat.get("idActivitat"));
            editor.putString("nomActivitat" + i, mapaActivitat.get("nomActivitat"));
            editor.putString("descripcioActivitat" + i, mapaActivitat.get("descripcioActivitat"));
            editor.putString("tipusActivitat" + i, mapaActivitat.get("tipusActivitat"));
            editor.putString("aforamentActivitat" + i, mapaActivitat.get("aforamentActivitat"));
        }

        // Guardar la quantitat total d'activitats
        editor.putInt("numActivitats", llistaActivitats.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}