package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

public abstract class CanviarContrasenya extends ConnexioServidor {

    private static final String ETIQUETA = "CanviarContrasenya";
    private Usuari usuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;

    public CanviarContrasenya(ModificarUsuari.ModificarUsuariListener listener, Context context, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.sessioID = sessioID;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }


    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }


    @SuppressLint("StaticFieldLeak")
    public void canviarContrasenya() {

        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    return enviarPeticioHashMap("update", "pass", mapaUsuari, sessioID);
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
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("usuari")) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                Usuari usuari = Usuari.hashmap_a_usuari(mapaUsuari);

                ((ModificarUsuari.ModificarUsuariListener) listener).onUsuariModificat(usuari);
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));

                Utils.mostrarToast(context, "Contrasenya modificada correctament");
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de la contrasenya");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta;
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
        return null;
    }

    @Override
    public void execute() throws ConnectException {
        canviarContrasenya();
    }


    protected abstract Object doInBackground(Void... voids);
}