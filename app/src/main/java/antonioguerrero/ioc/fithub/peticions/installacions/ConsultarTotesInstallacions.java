package antonioguerrero.ioc.fithub.peticions.installacions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Installacio;

/**
 * Classe per obtenir totes les instal·lacions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les instal·lacions.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesInstallacions extends ConnexioServidor {
    protected static respostaServidorListener ConsultarTotesInstallacionsListener;
    private Context context;
    private static final String ETIQUETA = "ConsultarInstallacions";
    String sessioID;

    /**
     * Constructor de la classe.
     *
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     * @param sessioID Identificador de la sessió.
     */
    public ConsultarTotesInstallacions(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    /**
     * Interfície per obtenir la resposta del servidor.
     */
    public interface ConsultarTotesInstallacionsListener {
        void onInstallacionsObtingudes(List<HashMap<String, String>> installacions);
    }


    /**
     * Mètode per obtenir totes les instal·lacions del servidor.
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarTotesInstallacions() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "installacio", null, sessioID);
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
     * Mètode per processar la resposta del servidor.
     *
     * @param resposta Resposta del servidor.
     */
    private void processarResposta(Object resposta) {
        // Verificar que la resposta no sigui nula i sigui un array d'objectes
        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            // Verificar que el array tingui almenys dos elements i que el primer element sigui un String
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String) {
                String estat = (String) respostaArray[0];
                // Verificar si el primer element és "installacioLlista"
                if ("installacioLlista".equals(estat)) {
                    // Verificar si el segon element és una llista
                    if (respostaArray[1] instanceof List) {
                        // Convertir el segon element a una llista de HashMaps
                        List<HashMap<String, String>> installacions = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarTotesInstallacionsListener) {
                            ((ConsultarTotesInstallacionsListener) listener).onInstallacionsObtingudes(installacions);
                        }
                        guardarDadesInstallacions(installacions);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de instal·lacions");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
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
     * Mètode per executar la petició.
     */
    @Override
    public void execute() throws ConnectException {
        consultarTotesInstallacions();
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
            editor.putInt("IDinstallacio"+ i, Integer.parseInt(mapaInstallacions.get("IDinstallacio")));
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