package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;

public abstract class CrearReserva extends ConnexioServidor {
    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "CrearReserva";
    SharedPreferences preferencies;
    String sessioID;

    public CrearReserva(respostaServidorListener listener, Reserva reserva, Context context) {
        super(listener);
        this.reserva = reserva;
        this.context = context;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    public void crearReserva() throws ConnectException {
        HashMap<String, String> mapaReserva = new HashMap<>();
        mapaReserva.put("IDclasseDirigida", String.valueOf(reserva.getClasseDirigida().getIDClasseDirigida()));
        mapaReserva.put("IDusuari", String.valueOf(reserva.getUsuari().getIDusuari()));

        enviarPeticioHashMap("insert", "reserva", mapaReserva, this.sessioID);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() throws ConnectException {
        crearReserva();
    }

    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta);

        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            boolean exit = respostaArray.length > 0 && "True".equals(respostaArray[0]);
            if (exit) {
                Log.d(ETIQUETA, "Reserva confirmada");
                Utils.mostrarToast(context, "Reserva confirmada");
            } else {
                String missatgeError = respostaArray.length > 1 ? (String) respostaArray[1] : "Error desconegut";
                Log.d(ETIQUETA, "Error en crear la reserva: " + missatgeError);
                Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut realitzar la reserva: " + missatgeError);
            }
        } else {
            Log.d(ETIQUETA, "Resposta del servidor inesperada o nula");
            Utils.mostrarToast(context.getApplicationContext(), "Resposta del servidor inesperada o nula");
        }

        return null;
    }
}
