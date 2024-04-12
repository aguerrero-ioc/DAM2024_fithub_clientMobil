package antonioguerrero.ioc.fithub.peticions.reserves;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
//import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;

public class CrearReserva extends BasePeticions {

    private Reserva reserva;
    private Context context;
    private static final String ETIQUETA = "CrearReserva";


    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public CrearReserva(BasePeticions.respostaServidorListener listener, Reserva reserva) {
        super(listener);
        this.reserva = reserva;
    }

    public void crearReserva() {
        // Convertir el objecte Reserva a un HashMap
        HashMap<String, String> mapaReserva = Utils.ObjecteAHashMap(reserva);
        mapaReserva.put("IDReserva", String.valueOf(reserva.getIDReserva()));
        mapaReserva.put("IDUsuari", String.valueOf(reserva.getIDUsuari()));

        enviarPeticio("insert", "reserva", mapaReserva, this.sessioID, ETIQUETA);
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
}
}