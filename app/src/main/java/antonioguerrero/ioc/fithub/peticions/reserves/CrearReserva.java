package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class CrearReserva extends BasePeticions {

    private Reserva reserva;
    private Context context;

    public CrearReserva(BasePeticions.respostaServidorListener listener, Reserva reserva) {
        super(listener);
        this.reserva = reserva;
    }

    public void crearReserva() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("type", "insert");
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
        crearReserva();
    }

    @Override
    public void respostaServidor(Object resposta) {
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("true")) {
                Utils.mostrarToast(context, "Reserva creada correctament");
            } else {
                Utils.mostrarToast(context, "Error en crear la reserva");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }
}