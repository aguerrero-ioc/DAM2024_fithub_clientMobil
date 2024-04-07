package antonioguerrero.ioc.fithub.peticions.reserves;

import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ModificarReserva extends BasePeticions {

    public ModificarReserva(BasePeticions.respostaServidorListener listener) {
        super(listener);
    }

    /**
     * Mètode per modificar les dades d'una reserva.
     *
     * @param reserva L'objecte Reserva amb les dades modificades.
     */
    public void modificarReserva(Reserva reserva) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("objectType", "reserva");
        requestMap.put("id", String.valueOf(reserva.getId()));
        requestMap.put("usuariID", String.valueOf(reserva.getUsuari().getUsuariID()));
        requestMap.put("installacioID", String.valueOf(reserva.getInstallacio().getId()));
        requestMap.put("data", reserva.getData());
        requestMap.put("hora", reserva.getHora());
        requestMap.put("durada", String.valueOf(reserva.getDurada()));
        requestMap.put("nombrePersones", String.valueOf(reserva.getNombrePersones()));
        requestMap.put("estat", reserva.getEstat());

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
