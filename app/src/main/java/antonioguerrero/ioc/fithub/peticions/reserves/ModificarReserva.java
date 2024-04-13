package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class ModificarReserva extends BasePeticions {
    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "ModificarReserva";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public ModificarReserva(BasePeticions.respostaServidorListener listener, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
    }

    /**
     * Mètode per modificar les dades d'una reserva.
     *
     * @param reserva L'objecte Reserva amb les dades modificades.
     */
    public void modificarReserva(Reserva reserva) {
        // Convertir el objecte Reserva a un HashMap
        HashMap<String, String> reservaMap = Utils.ObjecteAHashMap(reserva);

        enviarPeticioHashmap("update", "reserva", reservaMap, this.sessioID);
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
