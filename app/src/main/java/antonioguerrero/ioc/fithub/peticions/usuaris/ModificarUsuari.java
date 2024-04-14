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
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class ModificarUsuari extends BasePeticions {

    private static final String ETIQUETA = "ModificarUsuari";
    private Usuari usuari;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;
    private String correuUsuari;



    public ModificarUsuari(ModificarUsuariListener listener, Context context, String correuUsuari, String sessioID) {
        super((respostaServidorListener) listener);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.sessioID = sessioID;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    public interface ModificarUsuariListener {
        void onUsuariModificat(Usuari usuari);
    }


    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    @SuppressLint("StaticFieldLeak")
    public void modificarUsuari() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    enviarPeticioHashMap("update", "usuari", mapaUsuari, sessioID);
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
                return null;
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
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] arrayResposta = (Object[]) resposta;
            String estat = (String) arrayResposta[0];
            if (estat.equals("usuari")) {
                HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];

                Usuari usuari = new Usuari();
                usuari.setIDusuari(Integer.parseInt(mapaUsuari.get("IDusuari")));
                usuari.setNomUsuari(mapaUsuari.get("nomUsuari"));
                usuari.setPassUsuari(mapaUsuari.get("passUsuari"));
                usuari.setTipusUsuari(Integer.parseInt(mapaUsuari.get("tipusUsuari")));
                usuari.setCorreuUsuari(mapaUsuari.get("correuUsuari"));
                usuari.setCognomsUsuari(mapaUsuari.get("cognomsUsuari"));
                usuari.setTelefon(mapaUsuari.get("telefon"));
                usuari.setAdreca(mapaUsuari.get("adreca"));
                usuari.setDataNaixement(mapaUsuari.get("dataNaixement"));
                usuari.setDataInscripcio(mapaUsuari.get("dataInscripcio"));

                ((ModificarUsuariListener) listener).onUsuariModificat(usuari);
                Log.d(ETIQUETA, "Dades rebudes: " + Arrays.toString((Object[]) resposta));

                Utils.mostrarToast(context, "S'han desat els canvis correctament");
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de l'usuari");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta.toString();
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
    }

    @Override
    public void execute() throws ConnectException {
        modificarUsuari();
    }

    public abstract List<HashMap<String, String>> respostaServidorHashmap(Object resposta);

    protected abstract Object doInBackground(Void... voids);
}