package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class PeticioLogout extends BasePeticions {
    private static final String ETIQUETA = "PeticioLogout";
    private String IDUsuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;

    public PeticioLogout(Context context, String IDUsuari) {
        super((respostaServidorListener) context);
        this.context = context;
        this.IDUsuari = IDUsuari;
        this.sessioID = sessioID;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    @SuppressLint("StaticFieldLeak")
    public void peticioLogout() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("logout",IDUsuari, null, sessioID);
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

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Respossta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("true")) {
                Log.d(ETIQUETA, "Logout exitoso");
            } else {
                Log.e(ETIQUETA, "Error en el logout");
            }
        } else {
            String missatgeError = "Error: " + resposta.toString();
            Log.e(ETIQUETA, missatgeError);
        }
    }

    @Override
    public void execute() {
        peticioLogout();
    }
}