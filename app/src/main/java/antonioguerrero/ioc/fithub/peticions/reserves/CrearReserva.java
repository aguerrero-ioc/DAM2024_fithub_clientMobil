package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
//import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;

/**
 * Classe que s'encarrega de crear una reserva a la base de dades
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CrearReserva extends ConnexioServidor {
    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "CrearReserva";
    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe
     * @param listener Listener que s'encarrega de gestionar la resposta del servidor
     * @param reserva Objecte Reserva que es vol crear
     */
    public CrearReserva(ConnexioServidor.respostaServidorListener listener, Reserva reserva) {
        super(listener);
        this.reserva = reserva;
    }

    /**
     * Mètode que s'encarrega de crear la reserva a la base de dades
     * @throws ConnectException
     */
    public void crearReserva() throws ConnectException {
        // Convertir el objecte Reserva a un HashMap
        HashMap<String, String> mapaReserva = Utils.ObjecteAHashMap(reserva);
        mapaReserva.put("IDReserva", String.valueOf(reserva.getIDReserva()));
        mapaReserva.put("IDUsuari", String.valueOf(reserva.getIDusuari()));

        enviarPeticioHashMap("insert", "reserva", mapaReserva, this.sessioID);
    }

    /**
     * Mètode que s'encarrega de crear la reserva a la base de dades
     * @throws ConnectException
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que s'encarrega de crear la reserva a la base de dades
     * @throws ConnectException
     */
    @Override
    public void execute() throws ConnectException {
        crearReserva();
    }

    /**
     * Mètode que s'encarrega de crear la reserva a la base de dades
     * @throws ConnectException
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("True");
        if (exit) {
            Log.d(ETIQUETA, "Reserva creada con éxito");
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Reserva confirmada");
            // Redirigeix a l'usuari a la pantalla de Reserves
        /*Intent intent = new Intent(context, ReservesActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();*/
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear la reserva: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), "No s'ha pogut realitzar la reserva: " + missatgeError);
        }
        return null;
    }
}