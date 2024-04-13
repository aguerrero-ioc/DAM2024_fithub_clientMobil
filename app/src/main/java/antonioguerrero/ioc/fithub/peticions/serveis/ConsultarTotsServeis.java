package antonioguerrero.ioc.fithub.peticions.serveis;

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
import antonioguerrero.ioc.fithub.menu.serveis.ServeisActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir tots els serveis.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir tots els serveis.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarTotsServeis extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarTotsServeis";
    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe ConsultarTotsServeis.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarTotsServeis(respostaServidorListener listener, Context context) {
        super(listener);
        this.context = context;
    }

    /**
     * Mètode per obtenir les dades de tots els serveis.
     */
    public void obtenirTotsServeis() {
        enviarPeticioString("select", "serveis", null, this.sessioID);
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
                // Obtenir la llista de serveis
                List<HashMap<String, String>> llistaServeis = (List<HashMap<String, String>>) respostaArray[1];
                /*// Iniciar l'activitat ServeisActivity
                Utils.iniciarActivitatLlista(context, ServeisActivity.class, llistaServeis, "llistaServeis");*/
                // Guardar les dades dels serveis a SharedPreferences
                guardarDadesServeis(llistaServeis);
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
     * @param llistaServeis La llista de serveis.
     */
    private void guardarDadesServeis(List<HashMap<String, String>> llistaServeis) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte servei a SharedPreferences
        for (int i = 0; i < llistaServeis.size(); i++) {
            HashMap<String, String> serveiMap = llistaServeis.get(i);
            editor.putString("IDServei" + i, serveiMap.get("IDServei"));
            editor.putString("nomServei" + i, serveiMap.get("nomServei"));
            editor.putString("descripcioServei" + i, serveiMap.get("descripcioServei"));
            editor.putString("aforamentServei", serveiMap.get("aforamentServei"));
            editor.putString("tipusInstallacio", serveiMap.get("tipusInstallacio"));
            editor.putString("personalServei" + i, serveiMap.get("personalServei"));
            editor.putString("preuServei" + i, serveiMap.get("preuServei"));
        }

        // Guardar el número de serveis
        editor.putInt("numServeis", llistaServeis.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}