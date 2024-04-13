package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class PeticioLogout extends BasePeticions {
    private static final String ETIQUETA = "PeticioLogout";
    private String idUsuariLogout;
    private String idSessioLogout;

    private Context context;
    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public PeticioLogout(respostaServidorListener listener, String idUsuariLogout, String idSessioLogout, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
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