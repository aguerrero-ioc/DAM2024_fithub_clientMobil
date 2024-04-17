package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe per consultar les dades d'una reserva.
 * <p>
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir les dades d'una reserva.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarReserva extends ConnexioServidor {
    private Context context;
    private static final String ETIQUETA = "ConsultarReserva";
    private int IDReserva;

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe ConsultarReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     * @param context  Context de l'aplicació.
     * @param idReserva  ID de la reserva a consultar.
     */
    public ConsultarReserva(respostaServidorListener listener, Context context, int idReserva) {
        super(listener);
        this.context = context;
        this.IDReserva = IDReserva;
    }

    /**
     * Mètode per consultar les dades d'una reserva.
     *
     * @param idReserva L'ID de la reserva a consultar.
     */
    public void consultarReserva(int idReserva) throws ConnectException {
        String idReservaString = Integer.toString(idReserva);
        enviarPeticioString("select", "reserva", idReservaString, this.sessioID);
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
     * Mètode per gestionar la resposta del servidor.
     *
     * @return La resposta del servidor.
     */
    @Override
    public void execute() {
        // PENDENT
    }

    /**
     * Mètode per obtenir la resposta del servidor.
     *
     * @param response La resposta del servidor.
     * @return La resposta del servidor.
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object response) {
        // PENDENT
        return null;
    }
}
