package antonioguerrero.ioc.fithub.peticions.reserves;

import android.annotation.SuppressLint;
import android.content.Context;
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
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe per eliminar una reserva.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per eliminar una reserva.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class EliminarReserva extends ConnexioServidor {

    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "EliminarReserva";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la classe EliminarReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     * @param context  Context de l'aplicació.
     */
    public EliminarReserva(respostaServidorListener listener, Context context) {
        super(listener);
        this.context = context;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    /**
     * Mètode per eliminar una reserva.
     */
    @SuppressLint("StaticFieldLeak")
    public void eliminarReserva() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaReserva = reserva.reserva_a_hashmap(reserva);
                    return enviarPeticioHashMap("delete", "reserva", mapaReserva, sessioID);
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
        eliminarReserva();
    }

    /**
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta);

        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            boolean exit = respostaArray.length > 0 && "True".equalsIgnoreCase((String) respostaArray[0]);
            if (exit) {
                Log.d(ETIQUETA, "Reserva confirmada");
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                Utils.mostrarToast(context, "Reserva cancelada");
            } else {
                String missatgeError = respostaArray.length > 1 ? (String) respostaArray[1] : "Error desconegut";
                Log.d(ETIQUETA, "Error en cancelar la reserva: " + missatgeError);
                Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut cancelar la reserva: " + missatgeError);
            }
        } else {
            Log.d(ETIQUETA, "Resposta del servidor inesperada o nula");
            Utils.mostrarToast(context.getApplicationContext(), "Resposta del servidor inesperada o nula");
        }

        return null;
    }
}