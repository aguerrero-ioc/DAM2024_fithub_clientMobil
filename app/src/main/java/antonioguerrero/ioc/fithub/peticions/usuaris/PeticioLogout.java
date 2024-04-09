package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class PeticioLogout extends BasePeticions {
    private static final String ETIQUETA = "PeticioLogout";
    private String idUsuariLogout;
    private String idSessioLogout;

    private Context context;
    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public PeticioLogout(respostaServidorListener listener, String idUsuariLogout, String idSessioLogout) {
        super(listener);
        this.idUsuariLogout = idUsuariLogout;
        this.idSessioLogout = idSessioLogout;
    }

    public void ferLogout() {
        // Crear el Object[] per la petició
        Object[] peticio = new Object[4];
        peticio[0] = "logout";
        peticio[1] = this.idUsuariLogout;
        peticio[2] = this.idSessioLogout;
        peticio[3] = this.sessioID;


        Log.d(ETIQUETA, "Enviant petició: " + peticio.toString());
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        // PENDENT DE FER
    }

    @Override
    public void execute() {
        ferLogout();
    }
}