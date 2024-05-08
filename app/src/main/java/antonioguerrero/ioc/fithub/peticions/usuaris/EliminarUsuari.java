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

/**
 * Classe per eliminar un usuari.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per eliminar un usuari.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class EliminarUsuari extends ConnexioServidor {
    private final String correuUsuari;
    private final Context context;
    private static final String ETIQUETA = "EliminarUsuari";
    SharedPreferences preferences;
    String sessioID;

    /**
     * Constructor de la classe EliminarUsuari.
     * <p>
     * @param listener     El objecte que escoltarà les respostes del servidor.
     * @param context      Context de l'aplicació.
     * @param correuUsuari Correu electrònic de l'usuari a eliminar.
     */
    public EliminarUsuari(ConnexioServidor.respostaServidorListener listener, Context context, String correuUsuari) {
        super(listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.preferences = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Métode per eliminar un usuari.
     */
    @SuppressLint("StaticFieldLeak")
    public void eliminarUsuari() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("delete", "usuari", correuUsuari, sessioID);
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
     * Métode per executar la petició.
     */
    public void execute() throws ConnectException {
        eliminarUsuari();
    }

    /**
     * Métode per gestionar la resposta del servidor.
     * <p>
     * @param resposta La resposta del servidor.
     */
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Usuari eliminat correctament");
            Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
            Utils.mostrarToast(context, "Usuari eliminat correctament");
            // Redirigeix a l'usuari a la pantalla de gestió d'usuaris
            Intent intent = new Intent(context, GestioUsuarisActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en eliminar l'usuari: " + missatgeError);
            Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut eliminar l'usuari");
        }
        return null;
    }
}
