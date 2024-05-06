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
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;
import antonioguerrero.ioc.fithub.menu.usuaris.GestioUsuarisActivity;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.serveis.ModificarServei;

/**
 * Classe que s'encarrega de fer la petició al servidor per modificar un usuari
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarUsuari extends ConnexioServidor {

    private static final String ETIQUETA = "ModificarUsuari";
    private Usuari usuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;

    /**
     * Constructor de la classe
     * @param listener Listener per a la resposta del servidor
     * @param context Context de l'aplicació
     */
    public ModificarUsuari(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que conté el mètode onUsuariModificat
     */
    public interface ModificarUsuariListener {
        void onUsuariModificat(Usuari usuari);
    }

    /**
     * Mètode que retorna l'usuari
     * @return Usuari a modificar
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Mètode que modifica un usuari
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
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que retorna la resposta del servidor
     * @param resposta Resposta del servidor
     * @return Llista de HashMaps
     */
    private void processarResposta(Object resposta) {
        if (resposta != null && resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            if (arrayResposta.length >= 2 && arrayResposta[0] instanceof String) {
                String objecte = (String) arrayResposta[0];
                if ("usuari".equals(objecte)) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                Usuari usuari = Usuari.hashmap_a_usuari(mapaUsuari);
                    if (listener instanceof ModificarUsuariListener) {
                        ((ModificarUsuariListener) listener).onUsuariModificat(usuari);
                    }
                    Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                    Utils.mostrarToast(context, "S'han desat els canvis correctament");
                    // Redirigeix a l'usuari a la pantalla de gestió de serveis
                    Intent intent = new Intent(context, GestioUsuarisActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
            } else if (objecte.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de l'usuari");
                }
            }
        } else {
            Utils.mostrarToast(context, "Error: La resposta del servidor no és vàlida");
        }
    }

    private void guardarDadesUsuari(Usuari usuari) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        editor.putString(Constants.ID_USUARI, String.valueOf(usuari.getIDusuari()));
        editor.putString(Constants.TIPUS_USUARI, String.valueOf(usuari.getTipusUsuari()));
        editor.putString(Constants.CORREU_USUARI, usuari.getCorreuUsuari());
        editor.putString(Constants.PASS_USUARI, usuari.getPassUsuari());
        editor.putString(Constants.NOM_USUARI, usuari.getNomUsuari());
        editor.putString(Constants.COGNOMS_USUARI, usuari.getCognomsUsuari());
        editor.putString(Constants.ADRECA, usuari.getAdreca());
        editor.putString(Constants.TELEFON, usuari.getTelefon());
        editor.putString(Constants.DATA_NAIXEMENT, usuari.getDataNaixement());
        editor.putString(Constants.DATA_INSCRIPCIO, usuari.getDataInscripcio());

        editor.apply();
    }

    /**
     * Mètode que executa la petició
     * @throws ConnectException Excepció de connexió
     */
    @Override
    public void execute() throws ConnectException {
        modificarUsuari();
    }

    /**
     * Mètode que retorna la resposta del servidor
     * @param resposta Resposta del servidor
     */
    public abstract List<HashMap<String, String>> respostaServidorHashmap(Object resposta);

    /**
     * Mètode que retorna la resposta del servidor
     * @param resposta Resposta del servidor
     */
    public abstract void respostaServidor(Object[] resposta);

    /**
     * Mètode que s'executa en segon pla
     */
    protected abstract Object doInBackground(Void... voids);
}