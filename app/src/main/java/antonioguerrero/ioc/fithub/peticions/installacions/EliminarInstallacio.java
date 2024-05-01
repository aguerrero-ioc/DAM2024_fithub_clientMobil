package antonioguerrero.ioc.fithub.peticions.installacions;

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
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsActivity;

/**
 * Clase para eliminar una instalación.
 * Esta clase se encarga de enviar la petición al servidor para eliminar una instalación.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class EliminarInstallacio extends ConnexioServidor {

    private String nomInstallacio;
    private Context context;
    private static final String ETIQUETA = "EliminarInstallacio";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la clase EliminarInstallacio.
     *
     * @param listener         El objeto que escuchará las respuestas del servidor.
     * @param context          Contexto de la aplicación.
     * @param nomInstallacio   Nombre de la instalación a eliminar.
     */
    public EliminarInstallacio(ConnexioServidor.respostaServidorListener listener, Context context, String nomInstallacio) {
        super(listener);
        this.context = context;
        this.nomInstallacio = nomInstallacio;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Método para eliminar una instalación.
     */
    @SuppressLint("StaticFieldLeak")
    public void eliminarInstallacio() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("delete", "installacio", nomInstallacio, sessioID);
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
     * Método para obtener el tipo del objeto.
     *
     * @return La clase del objeto.
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Método para ejecutar la petición.
     */
    @Override
    public void execute() throws ConnectException {
        eliminarInstallacio();
    }

    /**
     * Método para gestionar la respuesta del servidor.
     *
     * @param resposta La respuesta del servidor.
     * @return
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Instal·lació eliminada correctament");
            Utils.mostrarToast(context, "Instal·lació eliminada correctament");
            // Redirigeix a l'usuari a la pantalla de gestió d'instal·lacions
            Intent intent = new Intent(context, GestioInstallacionsActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en eliminar la instal·lació: " + missatgeError);
            Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut eliminar la instal·lació");
        }
        return null;
    }
}
