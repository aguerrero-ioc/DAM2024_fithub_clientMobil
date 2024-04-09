package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConsultarTotesReserves extends BasePeticions {

    private Context context;
    private Usuari usuari;
    private static final String ETIQUETA = "ConsultarTotesReserves";

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public ConsultarTotesReserves(respostaServidorListener listener, Context context, Usuari usuari) {
        super(listener);
        this.context = context;
        this.usuari = usuari;
    }

    public void consultarTotesReserves() {
        // Crear el Object[] per la petició
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "reservaall";
        peticio[2] = usuari.getCorreuUsuari();
        peticio[3] = this.sessioID;

        Log.d(ETIQUETA, "Enviant petició: " + Arrays.toString(peticio));
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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