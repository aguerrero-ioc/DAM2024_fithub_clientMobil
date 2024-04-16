package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

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

    private int IDReserva;
    private Context context;
    private static final String ETIQUETA = "EliminarReserva";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe EliminarReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public EliminarReserva(ConnexioServidor.respostaServidorListener listener, Context context, int IDReserva) {
        super(listener);
        this.context = context;
        this.IDReserva = IDReserva;
    }

    /**
     * Mètode per eliminar una reserva.
     */
    public void eliminarReserva() throws ConnectException {
        String IDReservaString = Integer.toString(IDReserva);

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
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("true")) {
                Utils.mostrarToast(context, "Reserva eliminada correctament");
            } else {
                Utils.mostrarToast(context, "Error en eliminar la reserva");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
        return null;
    }
}