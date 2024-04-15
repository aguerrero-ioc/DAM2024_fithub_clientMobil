package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public abstract class CrearUsuari extends BasePeticions {
    private static final String ETIQUETA = "CrearUsuari";
    private String nomUsuari;
    private String cognomsUsuari;
    private String telefon;
    private String correuUsuari;
    private String passUsuari;
    private final Context context;

    public CrearUsuari(respostaServidorListener listener, Usuari usuari, Context context) {
        super(listener);
        this.context = context;
        this.correuUsuari = usuari.getCorreuUsuari();
        this.passUsuari = usuari.getPassUsuari();
        this.nomUsuari = usuari.getNomUsuari();
        this.cognomsUsuari = usuari.getCognomsUsuari();
        this.telefon = usuari.getTelefon();

    }

    @SuppressLint("StaticFieldLeak")
    public void crearUsuari() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Usuari usuari = new Usuari(correuUsuari, passUsuari, nomUsuari, cognomsUsuari, telefon);
                    HashMap<String, String> mapaUsuari = usuari.usuari_a_hashmap(usuari);
                    return enviarPeticioHashMap("insert", "usuari", mapaUsuari, null);
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
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
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Usuari creat amb èxit");
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Usuari creat amb èxit. Si us plau, inicia sessió.");
            // Redirigeix a l'usuari a la pantalla d'inici de sessió
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear l'usuari: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), "Error en crear l'usuari: " + missatgeError);
        }
        return null;
    }

    @Override
    public void execute() throws ConnectException {
        crearUsuari();
    }

    protected abstract Object doInBackground(Void... voids);
}