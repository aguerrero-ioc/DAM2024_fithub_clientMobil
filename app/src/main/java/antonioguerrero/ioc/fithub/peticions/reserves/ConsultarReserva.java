package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class ConsultarReserva extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarReserva";
    private int IDReserva;

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarReserva(respostaServidorListener listener, Context context, int idReserva, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
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

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() {
        // PENDENT
    }

    @Override
    public List<HashMap<String, String>> respostaServidor(Object response) {
        // PENDENT
        return null;
    }
}
