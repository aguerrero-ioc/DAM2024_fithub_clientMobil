package antonioguerrero.ioc.fithub.peticions.installacions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsActivity;
import antonioguerrero.ioc.fithub.objectes.Installacio;

/**
 * Classe que representa una petició al servidor per a modificar una instal·lació.
 * <p>
 * Aquesta classe realitza una petició al servidor per a modificar una instal·lació.
 * La classe implementa la interfície ConnexioServidor per a realitzar la petició.
 * <p>
 * La classe també conté un listener per a les respostes del servidor.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarInstallacio extends ConnexioServidor {

    private static final String ETIQUETA = "ModificarInstallacio";
    private Installacio installacio;
    private final Context context;
    private final String sessioID;

    /**
     * Constructor de la classe ModificarInstallacio.
     *
     * @param listener Listener per a les respostes del servidor.
     * @param context  Context de l'aplicació.
     */
    public ModificarInstallacio(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que defineix el listener per a la modificació d'una instal·lació.
     */
    public interface ModificarInstallacioListener {
        void onInstallacioModificada(Installacio installacio);
    }

    /**
     * Obte la instal·lació que es vol modificar.
     *
     */
    public void setInstallacio(Installacio installacio) {
        this.installacio = installacio;
    }

    /**
     * Modifica una instal·lació a la base de dades.
     */
    @SuppressLint("StaticFieldLeak")
    public void modificarInstallacio() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaInstallacio = installacio.installacio_a_hashmap(installacio);
                    return enviarPeticioHashMap("update", "installacio", mapaInstallacio, sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta);
            }
        }.execute();
    }

    /**
     * Processa la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    private void processarResposta(Object resposta) {
        if (resposta instanceof Object[] arrayResposta) {
            if (arrayResposta.length >= 2 && arrayResposta[0] instanceof String estat) {
                if ("installacio".equals(estat)) {
                    HashMap<String, String> mapaInstallacio = (HashMap<String, String>) arrayResposta[1];
                    Installacio installacio = Installacio.hashmap_a_installacio(mapaInstallacio);
                    if (listener instanceof ModificarInstallacioListener) {
                        ((ModificarInstallacioListener) listener).onInstallacioModificada(installacio);
                    }
                    guardarDadesInstallacio(installacio);
                    Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                    Utils.mostrarToast(context, "S'han desat els canvis correctament");
                    // Redirigeix a l'usuari a la pantalla de gestió d'instal·lacions
                    Intent intent = new Intent(context, GestioInstallacionsActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Utils.mostrarToast(context, "Error en la modificació de la instal·lació");
                }
            }
        } else {
            Utils.mostrarToast(context, "Error: La resposta del servidor no és vàlida");
        }
    }

    /**
     * Executa la petició al servidor.
     *
     * @throws ConnectException Si no es pot connectar amb el servidor.
     */
    public void execute() throws ConnectException {
        modificarInstallacio();
    }

    /**
     * Guarda les propietats de l'objecte Installacio en SharedPreferences.
     *
     * @param installacio L'objecte Installacio que es guardarà en SharedPreferences.
     */
    private void guardarDadesInstallacio(Installacio installacio) {

        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte installacio en SharedPreferences
        editor.putString(Constants.INS_NOM, installacio.getNomInstallacio());
        editor.putString(Constants.INS_DESC, installacio.getDescripcioInstallacio());
        editor.putInt(Constants.INS_TIPUS, installacio.getTipusInstallacio());
        editor.putInt(Constants.INS_ID, installacio.getIDinstallacio());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}