package antonioguerrero.ioc.fithub.peticions.reserves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;

public abstract class CrearReserva extends ConnexioServidor {
    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "CrearReserva";

    private String IDclasseDirigida;
    private String IDusuari;
    private SharedPreferences preferencies;

    private String sessioID;

    public CrearReserva(respostaServidorListener listener, Context context, String IDusuari, String IDclasseDirigida, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.IDusuari = IDusuari;
        this.IDclasseDirigida = IDclasseDirigida;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }



    public interface CrearReservaListener {
        void onReservaCreada(Reserva reserva);
    }

    @SuppressLint("StaticFieldLeak")
    public void crearReserva() {

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Reserva reserva = new Reserva(IDclasseDirigida, IDusuari);
                    HashMap<String, String> mapaReserva = reserva.reserva_a_hashmap(reserva);
                    return enviarPeticioHashMap("insert", "reserva", mapaReserva,sessioID);
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


    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta);

        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            boolean exit = respostaArray.length > 0 && "True".equals(respostaArray[0]);
            if (exit) {
                Log.d(ETIQUETA, "Reserva confirmada");
                Utils.mostrarToast(context, "Reserva confirmada");
            } else {
                String missatgeError = respostaArray.length > 1 ? (String) respostaArray[1] : "Error desconegut";
                Log.d(ETIQUETA, "Error en crear la reserva: " + missatgeError);
                Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut realitzar la reserva: " + missatgeError);
            }
        } else {
            Log.d(ETIQUETA, "Resposta del servidor inesperada o nula");
            Utils.mostrarToast(context.getApplicationContext(), "Resposta del servidor inesperada o nula");
        }

        return null;
    }

    /**
     * Mètode que permet obtenir el tipus d'objecte
     * @return Tipus d'objecte
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que executa la petició
     * @throws ConnectException Excepció de connexió
     */
    @Override
    public void execute() throws ConnectException {
        crearReserva();
    }

}
