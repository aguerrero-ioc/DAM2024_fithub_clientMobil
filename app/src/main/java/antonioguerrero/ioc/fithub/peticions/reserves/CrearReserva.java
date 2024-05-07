package antonioguerrero.ioc.fithub.peticions.reserves;

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
import antonioguerrero.ioc.fithub.objectes.Reserva;

/**
 * Classe per crear una reserva.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per crear una reserva.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CrearReserva extends ConnexioServidor {
    private Reserva reserva;
    private final Context context;
    private static final String ETIQUETA = "CrearReserva";
    private final String sessioID;

    /**
     * Constructor de la classe.
     * <p>
     * @param listener Listener de la classe.
     * @param context  Context de l'aplicació.
     */
    public CrearReserva(respostaServidorListener listener, Context context) {
        super((respostaServidorListener) listener);
        this.context = context;
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode per establir la reserva a eliminar.
     * <p>
     * @param reserva La reserva a eliminar.
     */
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    /**
     * Mètode per crear una reserva.
     */
    @SuppressLint("StaticFieldLeak")
    public void crearReserva(Class<?> activityClass) {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaReserva = reserva.reserva_a_hashmap(reserva);
                    return enviarPeticioHashMap("insert", "reserva", mapaReserva, sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta, activityClass);
            }
        }.execute();
    }

    /**
     * Mètode per processar la resposta del servidor.
     * <p>
     * @param resposta Resposta del servidor.
     */
    public List<HashMap<String, String>> processarResposta(Object resposta, Class<?> activityClass) {
         Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Reserva confirmada");
            Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Reserva confirmada");
            // Redirigeix a l'usuari a la pantalla anterior
            Intent intent = new Intent(context, activityClass);
            if (context instanceof Activity) {
                ((Activity) context).startActivity(intent);
                ((Activity) context).finish();
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear la reserva: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), missatgeError);
        }
        return null;
    }
}