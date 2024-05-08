package antonioguerrero.ioc.fithub.peticions.serveis;

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
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;
import antonioguerrero.ioc.fithub.objectes.Servei;

/**
 * Classe que representa una petició al servidor per a modificar un servei.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarServei extends ConnexioServidor {
    private static final String ETIQUETA = "ModificarServei";
    private Servei servei;
    private final Context context;
    private final String sessioID;

    /**
     * Constructor de la classe ModificarServei.
     * <p>
     * @param listener Listener per a les respostes del servidor.
     * @param context  Context de l'aplicació.
     */
    public ModificarServei(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que defineix el listener per a la modificació d'un servei.
     */
    public interface ModificarServeiListener {
        void onServeiModificat(Servei servei);
    }

    /**
     * Obte el servei que es vol modificar.
     * <p>
     * @return El servei que es vol modificar.
     */
    public void setServei(Servei servei) {
        this.servei = servei;
    }

    /**
     * Modifica un servei a la base de dades.
     */
    @SuppressLint("StaticFieldLeak")
    public void modificarServei() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaServei = servei.servei_a_hashmap(servei);
                    return enviarPeticioHashMap("update", "servei", mapaServei, sessioID);
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
     * <p>
     * @param resposta La resposta del servidor.
     */
    private void processarResposta(Object resposta) {
        if (resposta instanceof Object[] arrayResposta) {
            if (arrayResposta.length >= 2 && arrayResposta[0] instanceof String objecte) {
                if ("servei".equals(objecte)) {
                    HashMap<String, String> mapaServei = (HashMap<String, String>) arrayResposta[1];
                    Servei servei = Servei.hashmap_a_servei(mapaServei);
                    if (listener instanceof ModificarServeiListener) {
                        ((ModificarServeiListener) listener).onServeiModificat(servei);
                    }
                    guardarDadesServei(servei);
                    Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                    Utils.mostrarToast(context, "S'han desat els canvis correctament");
                    // Redirigeix a l'usuari a la pantalla de gestió de serveis
                    Intent intent = new Intent(context, GestioServeisActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Utils.mostrarToast(context, "Error en la modificació del servei");
                }
            }
        } else {
            Utils.mostrarToast(context, "Error: La resposta del servidor no és vàlida");
        }
    }

    /**
     * Executa la petició al servidor.
     * <p>
     * @throws ConnectException Si no es pot connectar amb el servidor.
     */
    public void execute() throws ConnectException {
        modificarServei();
    }

    /**
     * Guarda les propietats de l'objecte Servei en SharedPreferences.
     * <p>
     * @param servei L'objecte Servei que es guardarà en SharedPreferences.
     */
    private void guardarDadesServei(Servei servei) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte servei en SharedPreferences
        editor.putString(Constants.SERVEI_NOM, servei.getNomServei());
        editor.putString(Constants.SERVEI_DESC, servei.getDescripcioServei());
        editor.putString(Constants.SERVEI_PREU, servei.getPreuServei());
        editor.putInt(Constants.SERVEI_ID, servei.getIDservei());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}