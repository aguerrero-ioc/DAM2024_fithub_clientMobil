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

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;

/**
 * Classe per gestionar la petició de tancament de sessió.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class PeticioLogout extends ConnexioServidor {
    private static final String ETIQUETA = "PeticioLogout";
    private String IDusuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;

    /**
     * Constructor de la classe PeticioLogout.
     *
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
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
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
     * Mètode per obtenir el tipus de l'objecte.
     *
     * @return La classe de l'objecte.
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }


    /**
     * Mètode per gestionar la resposta del servidor a la petició de tancament de sessió.
     *
     * @param resposta La resposta del servidor.
     * @return
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("true")) {
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
            } else {
                String missatgeError = "Error: " + resposta.toString();
                Log.e(ETIQUETA, missatgeError);
                Toast.makeText(context, missatgeError, Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

    /**
     * Mètode per obtenir la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     * @return La resposta del servidor.
     */
    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
    /**
     * Mètode per executar la petició de tancament de sessió.
     */
    @Override
    public void execute() {
        peticioLogout();
    }

    /**
     * Mètode per obtenir l'etiqueta de la classe.
     *
     * @return L'etiqueta de la classe.
     */
    public String getEtiqueta() {
        return ETIQUETA;
    }
}