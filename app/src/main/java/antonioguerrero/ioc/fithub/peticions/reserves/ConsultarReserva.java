package antonioguerrero.ioc.fithub.peticions.reserves;

import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConsultarReserva extends BasePeticions {

    public ConsultarReserva(BasePeticions.respostaServidorListener listener) {
        super(listener);
    }

    /**
     * Mètode per consultar les dades d'una reserva.
     *
     * @param idReserva L'ID de la reserva a consultar.
     */
    public void consultarReserva(int idReserva) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("objectType", "reserva");
        requestMap.put("id", String.valueOf(idReserva));

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestMap);
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
