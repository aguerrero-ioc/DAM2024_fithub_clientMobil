package antonioguerrero.ioc.fithub.peticions.usuaris;

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
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.usuaris.GestioUsuarisActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que representa una petició al servidor per a modificar un usuari.
 * <p>
 * Aquesta classe realitza una petició al servidor per a modificar un usuari.
 * La classe implementa la interfície ConnexioServidor per a realitzar la petició.
 * <p>
 * La classe també conté un listener per a les respostes del servidor.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarUsuari extends ConnexioServidor {

    private static final String ETIQUETA = "ModificarUsuari";
    private Usuari usuari;
    private Context context;
    private SharedPreferences preferences;
    private String sessioID;

    /**
     * Constructor de la classe ModificarUsuari.
     *
     * @param listener Listener per a les respostes del servidor.
     * @param context  Context de l'aplicació.
     */
    public ModificarUsuari(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que defineix el listener per a la modificació d'un usuari.
     */
    public interface ModificarUsuariListener {
        void onUsuariModificat(Usuari usuari);
    }

    /**
     * Estableix l'usuari que es vol modificar.
     *
     * @param usuari L'usuari que es vol modificar.
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Modifica un usuari a la base de dades.
     */
    @SuppressLint("StaticFieldLeak")
    public void modificarUsuari() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    return enviarPeticioHashMap("update", "usuari", mapaUsuari, sessioID);
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
     * Obte el tipus de l'objecte que es passarà com a paràmetre a la petició.
     *
     * @return El tipus de l'objecte que es passarà com a paràmetre a la petició.
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Processa la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    private void processarResposta(Object resposta) {
        if (resposta != null && resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            if (arrayResposta.length >= 2 && arrayResposta[0] instanceof String) {
                String estat = (String) arrayResposta[0];
                if ("usuari".equals(estat)) {
                    HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                    Usuari usuari = Usuari.hashmap_a_usuari(mapaUsuari);
                    if (listener instanceof ModificarUsuariListener) {
                        ((ModificarUsuariListener) listener).onUsuariModificat(usuari);
                    }
                    guardarDadesUsuari(usuari);
                    Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                    Utils.mostrarToast(context, "S'han desat els canvis correctament");
                    // Redirigeix a l'usuari a la pantalla de gestió d'usuaris
                    Intent intent = new Intent(context, GestioUsuarisActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Utils.mostrarToast(context, "Error en la modificació de l'usuari");
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
    @Override
    public void execute() throws ConnectException {
        modificarUsuari();
    }

    /**
     * Guarda les propietats de l'objecte Usuari en SharedPreferences.
     *
     * @param usuari L'objecte Usuari que es guardarà en SharedPreferences.
     */
    private void guardarDadesUsuari(Usuari usuari) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Guarda les propietats de l'objecte usuari en SharedPreferences
        editor.putString(Constants.ID_USUARI, String.valueOf(usuari.getIDusuari()));
        editor.putString(Constants.NOM_USUARI, usuari.getNomUsuari());
        editor.putString(Constants.COGNOMS_USUARI, usuari.getCognomsUsuari());
        editor.putString(Constants.PASS_USUARI, usuari.getPassUsuari());
        editor.putString(Constants.TIPUS_USUARI, String.valueOf(usuari.getTipusUsuari()));
        editor.putString(Constants.CORREU_USUARI, usuari.getCorreuUsuari());
        editor.putString(Constants.TELEFON, usuari.getTelefon());
        editor.putString(Constants.ADRECA, usuari.getAdreca());
        editor.putString(Constants.DATA_INSCRIPCIO, usuari.getDataInscripcio());
        editor.putString(Constants.DATA_NAIXEMENT, usuari.getDataNaixement());


        // Aplica els canvis a SharedPreferences
        editor.apply();
    }


    /**
     * Obte la resposta del servidor en forma de HashMap.
     *
     * @param resposta La resposta del servidor.
     * @return La resposta del servidor en forma de HashMap.
     */
    public abstract List<HashMap<String, String>> respostaServidorHashmap(Object resposta);

    /**
     * Obte la resposta del servidor en forma de Object[].
     *
     * @param resposta La resposta del servidor.
     * @return La resposta del servidor en forma de Object[].
     */
    public abstract void respostaServidor(Object[] resposta);

    /**
     * Executa la petició al servidor.
     *
     * @throws ConnectException Si no es pot connectar amb el servidor.
     */
    protected abstract Object doInBackground(Void... voids);
}
