package antonioguerrero.ioc.fithub.peticions.reserves;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.Arrays;
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

    private String IDclasseDirigida;
    private String IDusuari;
    private SharedPreferences preferencies;

    private String sessioID;

    public CrearReserva(respostaServidorListener listener, Context context, String IDusuari, String IDclasseDirigida, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.IDusuari = IDusuari;
        this.IDclasseDirigida = IDclasseDirigida;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }



    @SuppressLint("StaticFieldLeak")
    public void crearReserva(Class<?> activityClass) {

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Reserva reserva = new Reserva(IDclasseDirigida, IDusuari);
                    HashMap<String, String> mapaReserva = reserva.reserva_a_hashmap(reserva);
                    return enviarPeticioHashMap("insert", "reserva", mapaReserva,sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta, activityClass);
            }
        }.execute();
    }


    public List<HashMap<String, String>> processarResposta(Object resposta, Class<?> activityClass) {
         Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Reserva confirmada");
            Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Reserva confirmada");

            // Redirigeix a l'usuari a la pantalla anterior
            Intent intent = new Intent(context, activityClass);
            if (context instanceof Activity) {
                ((Activity) context).startActivity(intent);
                ((Activity) context).finish();
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear la reserva: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), missatgeError);
        }
        return null;
    }

    /**
     * Mètode que permet obtenir el tipus d'objecte
     * @return Tipus d'objecte
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }



}
