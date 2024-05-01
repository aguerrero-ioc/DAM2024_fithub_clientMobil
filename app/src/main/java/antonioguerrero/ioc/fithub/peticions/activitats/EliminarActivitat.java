package antonioguerrero.ioc.fithub.peticions.activitats;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.activitats.GestioActivitatsActivity;

/**
 * Classe per eliminar una activitat.
 * Aquesta classe s'encarrega d'enviar la petició al servidor per eliminar una activitat.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class EliminarActivitat extends ConnexioServidor {

    private String nomActivitat;
    private Context context;
    private static final String ETIQUETA = "EliminarActivitat";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la classe EliminarActivitat.
     *
     * @param listener      L'objecte que escoltarà les respostes del servidor.
     * @param context       Contexte de l'aplicació.
     * @param nomActivitat  Nom de l'activitat a eliminar.
     */
    public EliminarActivitat(ConnexioServidor.respostaServidorListener listener, Context context, String nomActivitat) {
        super(listener);
        this.context = context;
        this.nomActivitat = nomActivitat;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode per eliminar una activitat.
     */
    @SuppressLint("StaticFieldLeak")
    public void eliminarActivitat() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("delete", "activitat", nomActivitat, sessioID);
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
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
     * Mètode per executar la petició.
     */
    @Override
    public void execute() throws ConnectException {
        eliminarActivitat();
    }

    /**
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Activitat eliminada correctament");
            Utils.mostrarToast(context, "Activitat eliminada correctament");
            // Redirigeix a l'usuari a la pantalla de gestió d'activitats
            Intent intent = new Intent(context, GestioActivitatsActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en eliminar l'activitat: " + missatgeError);
            Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut eliminar l'activitat");
        }
        return null;
    }
}
