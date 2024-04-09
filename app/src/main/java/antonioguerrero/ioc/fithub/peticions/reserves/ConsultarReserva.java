package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConsultarReserva extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarReserva";
    private int idReserva;

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public ConsultarReserva(respostaServidorListener listener, Context context, int idReserva) {
        super(listener);
        this.context = context;
        this.idReserva = idReserva;
    }

    /**
     * Mètode per consultar les dades d'una reserva.
     *
     * @param idReserva L'ID de la reserva a consultar.
     */
    public void consultarReserva(int idReserva) {
        // Crear el Object[] per la petició
        Object[] peticio = new Object[4];
        peticio[0] = "select";
        peticio[1] = "reserva";
        peticio[2] = idReserva;
        peticio[3] = this.sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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
    public void respostaServidor(Object response) {
        // PENDENT
    }
}
