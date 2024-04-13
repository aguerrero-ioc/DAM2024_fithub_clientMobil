package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class CanviarContrasenya extends BasePeticions {

    private static final String ETIQUETA = "CanviarContrasenya";
    private Usuari usuari;
    private Context context;

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    public CanviarContrasenya(respostaServidorListener listener, Usuari usuari, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        super(listener, objectOut, objectIn);
        this.usuari = usuari;
    }

    public void canviarContrasenya() throws ConnectException {
        // Convertir el objecte Usuari a un HashMap
        HashMap<String, String> usuariMap = Utils.ObjecteAHashMap(usuari);

        /* Comprovar si funciona amb ObjecteAHashMap
        HashMap<String, String> usuariMap = new HashMap<>();
        usuariMap.put("nom", usuari.getNomUsuari());
        usuariMap.put("cognoms", usuari.getCognomsUsuari());
        if (usuari.getDataNaixement() != null) {
            usuariMap.put("dataNaixement", new SimpleDateFormat("dd-MM-yyyy").format(usuari.getDataNaixement()));
        }
        usuariMap.put("adreca", usuari.getAdreca());
        usuariMap.put("telefon", usuari.getTelefon());
        usuariMap.put("correu", usuari.getCorreuUsuari());
        usuariMap.put("contrasenya", usuari.getPassUsuari());*/

        enviarPeticioHashMap("update", "pass", usuariMap, this.sessioID);

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
                Usuari usuari = (Usuari) Utils.HashMapAObjecte(mapaUsuari, Usuari.class);
                Utils.mostrarToast(context, "Contrasenya modificada correctament");

                /* Comprovar si funciona amb HashMapAObjecte
                Usuari usuari = new Usuari();
                usuari.setNomUsuari(mapaUsuari.get("nomUsuari"));
                usuari.setCorreuUsuari(mapaUsuari.get("correuUsuari"));
                usuari.setPassUsuari(mapaUsuari.get("passUsuari"));
                usuari.setCognomsUsuari(mapaUsuari.get("cognomsUsuari"));
                usuari.setTelefon(mapaUsuari.get("telefon"));
                usuari.setAdreca(mapaUsuari.get("Adreca"));
                try {
                    SimpleDateFormat formatData = new SimpleDateFormat("dd-MM-yyyy");
                    usuari.setDataNaixement(formatData.parse(mapaUsuari.get("DataNaixement")));
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/

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
    public void execute() throws ConnectException {
        canviarContrasenya();
    }


    protected abstract Object doInBackground(Void... voids);
}