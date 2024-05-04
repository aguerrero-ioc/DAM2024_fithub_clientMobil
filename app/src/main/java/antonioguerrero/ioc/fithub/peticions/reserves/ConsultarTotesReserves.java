package antonioguerrero.ioc.fithub.peticions.reserves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.activitats.ConsultarTotesActivitats;

/**
 * Classe que realitza la petició al servidor per obtenir totes les reserves d'un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesReserves extends ConnexioServidor {

    private Context context;

    private static final String ETIQUETA = "ConsultarTotesReserves";

    private String correuUsuari;
    private String sessioID;



    public ConsultarTotesReserves(respostaServidorListener listener, Context context, String correuUsuari, String sessioID) {
        super(listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.sessioID = sessioID;
    }

    public interface ConsultarTotesReservesListener {
        void onReservesObtingudes(List<HashMap<String, String>> reserves);
    }



    @SuppressLint("StaticFieldLeak")
    public void consultarTotesReserves() {
        final String correuUsuari = this.correuUsuari;
        final String sessioID = this.sessioID;
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "reserva", correuUsuari, sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta);
            }
        }.execute();
    }


    private void processarResposta(Object resposta) {
        if (resposta != null && resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            if (respostaArray.length >= 2 && respostaArray[0] instanceof String) {
                String estat = (String) respostaArray[0];
                if ("reservaLlista".equals(estat)) {
                    if (respostaArray[1] instanceof List) {
                        List<HashMap<String, String>> reserves = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarTotesActivitats.ConsultarTotesActivitatsListener) {
                            ((ConsultarTotesReserves.ConsultarTotesReservesListener) listener).onReservesObtingudes(reserves);
                        }
                        guardarDadesReserves(reserves);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de reserves");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la resposta del servidor");
    }

    private void guardarDadesReserves(List<HashMap<String, String>> llistaReserves) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        for (int i = 0; i < llistaReserves.size(); i++) {
            HashMap<String, String> mapaReserva = llistaReserves.get(i);
            editor.putInt(Constants.CLASSE_ID + i, Integer.parseInt(mapaReserva.get(Constants.CLASSE_ID)));
            editor.putString(Constants.ID_USUARI + i, mapaReserva.get(Constants.ID_USUARI));
        }

        editor.putInt("numReserves", llistaReserves.size());
        editor.apply();
    }

    /**
     * Mètode que rep la resposta del servidor
     * @return Objecte amb la resposta del servidor
     * @throws Exception
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que processa la resposta del servidor
     * @throws Exception
     */
    @Override
    public void execute() throws ConnectException {
        consultarTotesReserves();
    }

}