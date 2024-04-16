package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

public abstract class ConsultarTotesReserves extends ConnexioServidor {

    private Context context;
    private Usuari usuari;
    private static final String ETIQUETA = "ConsultarTotesReserves";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarTotesReserves(respostaServidorListener listener, Context context, Usuari usuari, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener);
        this.context = context;
        this.usuari = usuari;
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    public void consultarTotesReserves() throws ConnectException {
        String IDUsuariString = Integer.toString(usuari.getIDusuari());

        enviarPeticioString("selectAll", "reserva", IDUsuariString, this.sessioID);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() throws ConnectException {
        consultarTotesReserves();
    }

    @Override
    public List<HashMap<String, String>> respostaServidor(Object response) {
        // PENDENT
        return null;
    }
}