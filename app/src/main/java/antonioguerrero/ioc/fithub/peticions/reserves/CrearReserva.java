package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class CrearReserva extends BasePeticions {

    private Reserva reserva;
    private Context context;

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public CrearReserva(BasePeticions.respostaServidorListener listener, Reserva reserva) {
        super(listener);
        this.reserva = reserva;
    }

    public void crearReserva() {
        // Crear el Object[] para la petición
        Object[] peticio = new Object[4];
        peticio[0] = "insert";
        peticio[1] = "reserva";
        peticio[2] = reserva;
        peticio[3] = this.sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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