package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ModificarReserva extends BasePeticions {
    private Reserva reserva;
    private Context context;

    SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
    String sessioID = preferencies.getString("sessioID", "");

    public ModificarReserva(BasePeticions.respostaServidorListener listener) {
        super(listener);
    }

    /**
     * Mètode per modificar les dades d'una reserva.
     *
     * @param reserva L'objecte Reserva amb les dades modificades.
     */
    public void modificarReserva(Reserva reserva) {
        // Crear el Object[] para la petición
        Object[] peticio = new Object[4];
        peticio[0] = "update";
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
    // Aquí es crida al mètode per modificar la reserva
    modificarReserva(reserva);
}

@Override
public void respostaServidor(Object resposta) {
    // Aquí es processa la resposta del servidor
    if (resposta instanceof HashMap) {
        HashMap<String, String> mapaResposta = (HashMap<String, String>) resposta;
        String estat = mapaResposta.get("estat");
        if (estat.equals("true")) {
            // Si l'estat és 'true', la reserva s'ha modificat correctament
            System.out.println("La reserva s'ha modificat correctament");
        } else {
            // Si l'estat no és 'true', hi ha hagut un error en modificar la reserva
            System.out.println("Error en modificar la reserva");
        }
    } else {
        // Si la resposta no és un HashMap, hi ha hagut un error en la resposta del servidor
        System.out.println("Error en la resposta del servidor");
    }
}
}
