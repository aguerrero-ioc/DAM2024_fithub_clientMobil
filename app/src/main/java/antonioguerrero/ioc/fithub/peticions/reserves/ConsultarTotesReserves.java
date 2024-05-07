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

/**
 * Classe que realitza la petició al servidor per obtenir totes les reserves d'un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesReserves extends ConnexioServidor {
    private final Context context;
    private static final String ETIQUETA = "ConsultarTotesReserves";
    private final String correuUsuari;
    private final String sessioID;

    /**
     * Constructor de la classe
     * <p>
     * @param listener Listener per a la resposta del servidor
     * @param context Context de l'aplicació
     * @param correuUsuari Correu de l'usuari
     */
    public ConsultarTotesReserves(respostaServidorListener listener, Context context, String correuUsuari, String sessioID) {
        super(listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.sessioID = sessioID;
    }

    /**
     * Interfície per obtenir la resposta del servidor.
     */
    public interface ConsultarTotesReservesListener {
        void onReservesObtingudes(List<HashMap<String, String>> reserves);
    }

    /**
     * Mètode que realitza la petició al servidor per obtenir totes les reserves d'un usuari
     */
    @SuppressLint("StaticFieldLeak")
    public void consultarTotesReserves() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "reserva", correuUsuari, sessioID);
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
     * <p>
     * @param resposta Resposta del servidor
     */
    private void processarResposta(Object resposta) {
        if (resposta != null && resposta instanceof Object[] respostaArray) {
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String estat) {
                if ("reservaLlista".equals(estat)) {
                    if (respostaArray[1] instanceof List) {
                        List<HashMap<String, String>> reserves = (List<HashMap<String, String>>) respostaArray[1];
                        for (HashMap<String, String> reserva : reserves) {
                            Log.d("Reserva", "Datos de reserva:");
                            for (String clave : reserva.keySet()) {
                                String valor = reserva.get(clave);
                                Log.d("Reserva", clave + ": " + valor);
                            }
                        }
                        if (listener instanceof ConsultarTotesReservesListener) {
                            ((ConsultarTotesReservesListener) listener).onReservesObtingudes(reserves);
                        }
                        Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
                        guardarDadesReserves(reserves);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de reserves");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
    }

    /**
     * Mètode per guardar les dades de les reserves a les preferències de l'aplicació.
     * <p>
     * @param llistaReserves Llista de reserves
     */
    private void guardarDadesReserves(List<HashMap<String, String>> llistaReserves) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        for (int i = 0; i < llistaReserves.size(); i++) {
            HashMap<String, String> mapaReserva = llistaReserves.get(i);
            editor.putString(Constants.CLASSE_ID + i, mapaReserva.get(Constants.CLASSE_ID));
            editor.putString(Constants.ID_USUARI + i, mapaReserva.get(Constants.ID_USUARI));
            editor.putString(Constants.RESERVA_ID + i, mapaReserva.get(Constants.RESERVA_ID));
            editor.putString(Constants.CLASSE_DATA + i, mapaReserva.get(Constants.CLASSE_DATA));
            editor.putString(Constants.CLASSE_HORA + i, mapaReserva.get(Constants.CLASSE_HORA));
            editor.putString(Constants.CLASSE_DURACIO + i, mapaReserva.get(Constants.CLASSE_DURACIO));
            editor.putString(Constants.CLASSE_OCUPACIO + i, mapaReserva.get(Constants.CLASSE_OCUPACIO));
            editor.putString(Constants.CLASSE_ESTAT + i, mapaReserva.get(Constants.CLASSE_ESTAT));
            editor.putString(Constants.ACT_NOM + i, mapaReserva.get(Constants.ACT_NOM));
            editor.putString(Constants.INS_NOM + i, mapaReserva.get(Constants.INS_NOM));

        }
        editor.putInt("numReserves", llistaReserves.size());
        editor.apply();
    }

    /**
     * Mètode per executar la petició.
     */
    public void execute() throws ConnectException {
        consultarTotesReserves();
    }
}