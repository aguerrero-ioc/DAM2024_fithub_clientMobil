package antonioguerrero.ioc.fithub.peticions.activitats;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe que permet consultar totes les activitats disponibles al servidor
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesActivitats extends ConnexioServidor {
    private final Context context;
    private final String sessioID;

    /**
     * Constructor de la classe
     * <p>
     * @param listener Listener per a les respostes del servidor
     * @param context Context de l'aplicació
     * @param sessioID ID de la sessió de l'usuari
     */
    public ConsultarTotesActivitats(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;
    }

    /**
     * Interfície per a les respostes del servidor
     */
    public interface ConsultarTotesActivitatsListener {
        void onActivitatsObtingudes(List<HashMap<String, String>> activitats);
    }

    /**
     * Mètode que envia la petició al servidor per a consultar totes les activitats
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarTotesActivitats() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "activitat", null, sessioID);
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
     * Mètode que processa la resposta del servidor
     * @param resposta Resposta del servidor
     */
    private void processarResposta(Object resposta) {
        if (resposta != null && resposta instanceof Object[] respostaArray) {
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String estat) {
                if ("activitatLlista".equals(estat)) {
                    if (respostaArray[1] instanceof List) {
                        List<HashMap<String, String>> activitats = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarTotesActivitatsListener) {
                            ((ConsultarTotesActivitatsListener) listener).onActivitatsObtingudes(activitats);
                        }
                        guardarDadesActivitats(activitats);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de activitats");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
    }

    /**
     * Mètode que executa la petició al servidor
     */
    public void execute() throws ConnectException {
        consultarTotesActivitats();
    }

    /**
     * Mètode que guarda les dades de les activitats a les preferències de l'aplicació
     * @param llistaActivitats Llista d'activitats
     */
    private void guardarDadesActivitats(List<HashMap<String, String>> llistaActivitats) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        for (int i = 0; i < llistaActivitats.size(); i++) {
            HashMap<String, String> mapaActivitat = llistaActivitats.get(i);
            editor.putInt("IDactivitat" + i, Integer.parseInt(mapaActivitat.get("IDactivitat")));
            editor.putString("nomActivitat" + i, mapaActivitat.get("nomActivitat"));
            editor.putString("descripcioActivitat" + i, mapaActivitat.get("descripcioActivitat"));
            editor.putString("tipusActivitat" + i, mapaActivitat.get("tipusActivitat"));
        }
        editor.putInt("numActivitats", llistaActivitats.size());
        editor.apply();
    }
}