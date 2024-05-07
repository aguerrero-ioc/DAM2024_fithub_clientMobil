package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;

/**
 * Classe per gestionar la petició de tancament de sessió.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioLogout extends ConnexioServidor {
    private static final String ETIQUETA = "PeticioLogout";
    private final String IDusuari;
    private final Context context;
    private String sessioID;

    /**
     * Constructor de la classe PeticioLogout.
     * <p>
     * @param listener  L'objecte que es notificarà quan la petició estigui completa.
     * @param context   El context de l'aplicació.
     * @param IDusuari  L'ID de l'usuari que vol tancar la sessió.
     * @param sessioID  L'ID de la sessió que es vol tancar.
     */
    public PeticioLogout(respostaServidorListener listener, Context context, String IDusuari, String sessioID) {
        super(listener);
        this.context = context;
        this.IDusuari = IDusuari;
        this.sessioID = sessioID;
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    /**
     * Mètode per enviar la petició de tancament de sessió al servidor.
     */
    @SuppressLint("StaticFieldLeak")
    public void peticioLogout() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    // Envía la petición de logout con el ID de usuario como una cadena
                    return enviarPeticioString("logout", IDusuari, "null", sessioID);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            protected void onPostExecute(Object resposta) {
                respostaServidor(resposta);
            }
        }.execute();
    }


    /**
     * Mètode per gestionar la resposta del servidor a la petició de tancament de sessió.
     * <p>
     * @param resposta La resposta del servidor.
     */
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("true")) {

                Log.d(ETIQUETA, "Tancament de sessió exitós");
                Toast.makeText(context, "Tancament de sessió exitós", Toast.LENGTH_SHORT).show();

                // Torna a LoginActivity
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish(); // Tanca l'activitat actual
            } else {
                Log.e(ETIQUETA, "Error en el tancament de sessió");
            }
        }
        return null;
    }

    /**
     * Mètode per executar la petició de tancament de sessió.
     */
    public void execute() {
        peticioLogout();
    }

    /**
     * Mètode per obtenir l'etiqueta de la classe.
     * <p>
     * @return L'etiqueta de la classe.
     */
    public String getEtiqueta() {
        return ETIQUETA;
    }
}