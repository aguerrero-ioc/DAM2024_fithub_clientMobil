package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConsultarTotesReserves extends BasePeticions {

    private Context context;
    private Usuari usuari;
    private static final String ETIQUETA = "ConsultarTotesReserves";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ConsultarTotesReserves(respostaServidorListener listener, Context context, Usuari usuari) {
        super(listener);
        this.context = context;
        this.usuari = usuari;
    }

    public void consultarTotesReserves() {
        enviarPeticio("selectAll", "reserva", usuari.getIDUsuari(), this.sessioID, ETIQUETA);
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