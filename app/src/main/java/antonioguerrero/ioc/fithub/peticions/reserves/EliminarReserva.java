package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe per eliminar una reserva.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per eliminar una reserva.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class EliminarReserva extends ConnexioServidor {

    private String IDreserva;
    private Context context;
    private static final String ETIQUETA = "EliminarReserva";
    SharedPreferences preferencies;
    String sessioID;

    /**
     * Constructor de la classe EliminarReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     * @param context  Context de l'aplicació.
     * @param IDreserva  ID de la reserva a eliminar.
     */
    public EliminarReserva(ConnexioServidor.respostaServidorListener listener, Context context, String IDreserva) {
        super(listener);
        this.context = context;
        this.IDreserva = IDreserva;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode per eliminar una reserva.
     */
    public void eliminarReserva() throws ConnectException {
        String IDReservaString = IDreserva;

        enviarPeticioString("delete", "reserva", IDReservaString, this.sessioID);
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
                Utils.mostrarToast(context, "Reserva confirmada");
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