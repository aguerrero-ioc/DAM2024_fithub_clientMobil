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
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.objectes.Installacio;

/**
 * Classe per obtenir totes les instal·lacions.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les instal·lacions.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesInstallacions extends ConnexioServidor {
    protected static respostaServidorListener ConsultarTotesInstallacionsListener;
    private Context context;
    private static final String ETIQUETA = "ConsultarInstallacions";
    String sessioID;

    /**
     * Constructor de la classe ConsultarTotesInstallacions.
     */
    public ConsultarTotesInstallacions(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }
    public interface ConsultarTotesInstallacionsListener {
        void onInstallacionsObtingudes(List<HashMap<String, String>> installacions);
    }


    /**
     * Mètode per obtenir totes les instal·lacions.
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
                respostaServidor(resposta);
            }
        }.execute();
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
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String && respostaArray[1] instanceof List) {
                String estat = (String) respostaArray[0];
                if ("installacioLlista".equals(estat)) {
                // Obtenir la llista d'instal·lacions
                    @SuppressWarnings("unchecked")
                    List<HashMap<String, String>> llistaInstallacions = (List<HashMap<String, String>>) respostaArray[1];
                    // Convertir cada HashMap en un objeto Installacio
                List<Installacio> installacions = new ArrayList<>();
                for (HashMap<String, String> mapaInstallacio : llistaInstallacions) {
                    Installacio installacio = Installacio.hashmap_a_installacio(mapaInstallacio);
                    installacions.add(installacio);
                }

                // Llamar al método onInstallacionsObtingudes con la lista de HashMaps
                ((ConsultarTotesInstallacionsListener) listener).onInstallacionsObtingudes(llistaInstallacions);

                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                // Guardar les dades de les instal·lacions a SharedPreferences
                guardarDadesInstallacions(llistaInstallacions);
                // Devolver la lista de instalaciones en lugar de iniciar la actividad
                    return llistaInstallacions;
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de instalaciones");
                }
            } else {
                Utils.mostrarToast(context, "Respuesta del servidor mal formada");
            }
        } else {
            Utils.mostrarToast(context, "Error de conexión");
        }
        return null;
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