package antonioguerrero.ioc.fithub.peticions.serveis;

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


public abstract class ConsultarTotsServeis extends ConnexioServidor {
    private final Context context;
    private final String sessioID;

    public ConsultarTotsServeis(respostaServidorListener listener, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;
    }

    public interface ConsultarTotsServeisListener {
        void onServeisObtinguts(List<HashMap<String, String>> serveis);
    }

    @SuppressLint("StaticFieldLeak")
    public void consultarTotsServeis() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("selectAll", "servei", null, sessioID);
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
                if ("serveiLlista".equals(estat)) {
                    if (respostaArray[1] instanceof List) {
                        List<HashMap<String, String>> serveis = (List<HashMap<String, String>>) respostaArray[1];
                        if (listener instanceof ConsultarTotsServeisListener) {
                            ((ConsultarTotsServeisListener) listener).onServeisObtinguts(serveis);
                        }
                        guardarDadesServeis(serveis);
                        return;
                    }
                } else {
                    Utils.mostrarToast(context, "Error en la consulta de servicios");
                }
            }
        }
        Utils.mostrarToast(context, "Error en la respuesta del servidor");
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void execute() throws ConnectException {
        consultarTotsServeis();
    }

    private void guardarDadesServeis(List<HashMap<String, String>> llistaServeis) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        for (int i = 0; i < llistaServeis.size(); i++) {
            HashMap<String, String> mapaServei = llistaServeis.get(i);
            editor.putInt("IDservei" + i, Integer.parseInt(mapaServei.get("IDservei")));
            editor.putString("nomServei" + i, mapaServei.get("nomServei"));
            editor.putString("descripcioServei" + i, mapaServei.get("descripcioServei"));
            editor.putString("tipusInstallacio" + i, mapaServei.get("tipusInstallacio"));
        }

        editor.putInt("numServeis", llistaServeis.size());
        editor.apply();
    }
}
