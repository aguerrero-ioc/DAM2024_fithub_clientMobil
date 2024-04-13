package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class ConsultarTotesReserves extends BasePeticions {

    private Context context;
    private Usuari usuari;
    private static final String ETIQUETA = "ConsultarTotesReserves";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarTotesReserves(respostaServidorListener listener, Context context, Usuari usuari, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
        this.context = context;
        this.usuari = usuari;
    }

    public void consultarTotesReserves() {
        String IDUsuariString = Integer.toString(usuari.getIDUsuari());

        enviarPeticioString("selectAll", "reserva", IDUsuariString, this.sessioID);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() {
        consultarTotesReserves();
    }

    @Override
    public void respostaServidor(Object response) {
        // PENDENT
    }
}