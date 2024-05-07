package antonioguerrero.ioc.fithub.peticions.serveis;

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
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;

/**
 * Classe que s'encarrega de fer la petició al servidor per eliminar un servei
 * <p>
 * @version 1.0
 */
public abstract class EliminarServei extends ConnexioServidor {
    private final String nomServei;
    private final Context context;
    private static final String ETIQUETA = "EliminarServei";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la classe.
     * <p>
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     * @param nomServei Nom del servei a eliminar.
     */
    public EliminarServei(ConnexioServidor.respostaServidorListener listener, Context context, String nomServei) {
        super(listener);
        this.context = context;
        this.nomServei = nomServei;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode per eliminar un servei.
     */
    @SuppressLint("StaticFieldLeak")
    public void eliminarServei() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("delete", "servei", nomServei, sessioID);
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
     * Mètode per executar la petició al servidor.
     */
    public void execute() throws ConnectException {
        eliminarServei();
    }

    /**
     * Mètode per processar la resposta del servidor.
     * <p>
     * @param resposta La respuesta del servidor.
     */
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Servei eliminat correctament");
            Utils.mostrarToast(context, "Servei eliminat correctament");
            // Redirigeix a l'usuari a la pantalla de gestió de serveis
            Intent intent = new Intent(context, GestioServeisActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en eliminar el servei: " + missatgeError);
            Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut eliminar el servei");
        }
        return null;
    }
}