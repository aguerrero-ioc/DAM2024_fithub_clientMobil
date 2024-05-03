package antonioguerrero.ioc.fithub.peticions.activitats;

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
import antonioguerrero.ioc.fithub.menu.activitats.GestioActivitatsActivity;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que representa una petició al servidor per a modificar una activitat.
 * <p>
 * Aquesta classe realitza una petició al servidor per a modificar una activitat.
 * La classe implementa la interfície ConnexioServidor per a realitzar la petició.
 * <p>
 * La classe també conté un listener per a les respostes del servidor.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ModificarActivitat extends ConnexioServidor {

    private static final String ETIQUETA = "ModificarActivitat";
    private Activitat activitat;
    private Context context;
    private SharedPreferences preferences;
    private String sessioID;

    /**
     * Constructor de la classe ModificarActivitat.
     *
     * @param listener Listener per a les respostes del servidor.
     * @param context  Context de l'aplicació.
     */
    public ModificarActivitat(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Interfície que defineix el listener per a la modificació d'una activitat.
     */
    public interface ModificarActivitatListener {
        void onActivitatModificada(Activitat activitat);
    }

    /**
     * Obte l'activitat que es vol modificar.
     *
     * @return L'activitat que es vol modificar.
     */
    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }

    /**
     * Modifica una activitat a la base de dades.
     */
    @SuppressLint("StaticFieldLeak")
    public void modificarActivitat() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaActivitat = activitat.activitat_a_hashmap(activitat);
                    return enviarPeticioHashMap("update", "activitat", mapaActivitat, sessioID);
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
                if ("activitat".equals(estat)) {
                    HashMap<String, String> mapaActivitat = (HashMap<String, String>) arrayResposta[1];
                    Activitat activitat = Activitat.hashmap_a_activitat(mapaActivitat);
                    if (listener instanceof ModificarActivitatListener) {

                        ((ModificarActivitatListener) listener).onActivitatModificada(activitat);
                    }
                    guardarDadesActivitat(activitat);
                    Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                    Utils.mostrarToast(context, "S'han desat els canvis correctament");
                    // Redirigeix a l'usuari a la pantalla de gestió d'activitats
                    Intent intent = new Intent(context, GestioActivitatsActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else {
                    Utils.mostrarToast(context, "Error en la modificació de l'activitat");
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
        modificarActivitat();
    }

    /**
     * Guarda las propiedades del objeto Activitat en SharedPreferences.
     *
     * @param activitat El objeto Activitat que se guardará en SharedPreferences.
     */
    private void guardarDadesActivitat(Activitat activitat) {

        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar las propiedades del objeto activitat en SharedPreferences
        editor.putString(Constants.ACT_NOM, activitat.getNomActivitat());
        editor.putString(Constants.ACT_DESC, activitat.getDescripcioActivitat());
        editor.putString(Constants.ACT_TIPUS, String.valueOf(activitat.getTipusInstallacio()));
        editor.putInt(Constants.ACT_AFORAMENT, activitat.getAforamentActivitat());
        editor.putInt(Constants.ACT_ID, activitat.getIDactivitat());

        // Aplicar los cambios a SharedPreferences
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
