package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class CanviarContrasenya extends BasePeticions {

    private static final String ETIQUETA = "CanviarContrasenya";
    private Usuari usuari;
    private Context context;

    public CanviarContrasenya(respostaServidorListener listener) {
        super(listener);
    }

    public void canviarContrasenya(Usuari usuari) {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("type", "update");
        requestMap.put("objectType", "usuari");
        requestMap.put("correu", usuari.getCorreu());
        requestMap.put("contrasenya", usuari.getContrasenya());

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestMap);
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
                usuari.setNom(mapaUsuari.get("nomUsuari"));
                usuari.setCorreu(mapaUsuari.get("correuUsuari"));
                usuari.setContrasenya(mapaUsuari.get("passUsuari"));
                usuari.setCognoms(mapaUsuari.get("cognomsUsuari"));
                usuari.setTelefon(mapaUsuari.get("telefon"));
                usuari.setAdreca(mapaUsuari.get("Adreca"));
                try {
                    SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
                    usuari.setDataNaixement(formatData.parse(mapaUsuari.get("DataNaixement")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Error en la modificació de la contrasenya");
            }
        } else {
            String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta.toString();
            Log.e(ETIQUETA, missatgeError);
            Utils.mostrarToast(context, "Error en la resposta del servidor");
        }
    }

    @Override
    public void execute() {
        canviarContrasenya(usuari);
    }


}