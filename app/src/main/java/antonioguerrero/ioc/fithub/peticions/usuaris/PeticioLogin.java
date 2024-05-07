package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que representa una petició de login al servidor.
 * <p>
 * Aquesta classe s'utilitza per iniciar sessió a través del servidor mitjançant les credencials d'usuari.
 * El missatge de la petició de login s'envia amb el correu d'usuari i la contrasenya.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class PeticioLogin extends ConnexioServidor {
    private static final String ETIQUETA = "PeticioLogin";
    private final String correuUsuari;
    private final String passUsuari;
    private final Context context;

    /**
     * Constructor de la classe PeticioLogin.
     * <p>
     * @param context      El context de l'aplicació.
     * @param correuUsuari El correu de l'usuari.
     * @param passUsuari   La contrasenya de l'usuari.
     */
    public PeticioLogin(Context context, String correuUsuari, String passUsuari) {
        super();
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.passUsuari = passUsuari;
    }

    /**
     * Mètode per enviar la petició de login al servidor.
     */
    @SuppressLint("StaticFieldLeak")
    public void peticioLogin() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    return enviarPeticioString("login", correuUsuari, passUsuari, null);
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
     * Mètode per obtenir la resposta del servidor.
     * <p>
     * @param resposta La resposta del servidor.
     * @return La resposta del servidor.
     */
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Usuari.setContext(context);

        if (context != null) {
            Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
            if (resposta instanceof Object[] respostaArray) {
                if (respostaArray.length == 2 && respostaArray[0] instanceof String sessioID && respostaArray[1] instanceof HashMap) {
                    // Inicio de sesión exitoso con el identificador de sesión y los datos del usuario
                    HashMap<String, String> usuariMap = (HashMap<String, String>) respostaArray[1];
                    Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);
                    if (usuari != null) {
                        obrirActivitat(usuari.getTipusUsuari());
                        Usuari.guardarDadesUsuari(usuari);
                        guardarSessioID(sessioID);
                    } else {
                        Log.d(ETIQUETA, "Error en transformar el HashMap en Usuari");
                    }
                } else if (respostaArray[0] instanceof String && respostaArray[0].equals("false")) {
                    // Inici de sesión no exitoso
                    Utils.mostrarToast(context, "Credencials incorrectes");
                } else {
                    Log.d(ETIQUETA, "Tipus d'objecte no vàlid en la resposta");
                }
            } else {
                Utils.mostrarToast(context, Constants.ERROR_CONNEXIO);
            }
        } else {
            Log.e(ETIQUETA, "El context es nul");
        }
        return null;
    }

    /**
     * Executa la petició de login.
     */
    public void execute() {
        peticioLogin();
    }

    /**
     * Guarda l'identificador de sessió a SharedPreferences.
     * <p>
     * @param sessioID L'identificador de sessió que es guardarà a SharedPreferences.
     */
    public void guardarSessioID(String sessioID) {
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        editor.putString(Constants.SESSIO_ID, sessioID);
        editor.apply();
        Log.d(ETIQUETA, "sessioID guardat: " + sessioID);
    }

    /**
     * Mètode per obrir l'activitat corresponent segons el tipus d'usuari.
     * <p>
     * @param tipusUsuari El tipus d'usuari.
     */
    private void obrirActivitat(int tipusUsuari) {
        // Obrir l'activitat corresponent segons el tipus d'usuari
        if (tipusUsuari == 2) {
            // Usuari tipus client
            Utils.obrirActivitat(context, ClientActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.mostrarToast(context, "Benvingut, client");
        } else if (tipusUsuari == 1) {
            // Usuari tipus admin
            Utils.obrirActivitat(context, AdminActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.mostrarToast(context, "Benvingut, administrador");
        } else {
            // Tipus d'usuari desconegut
            Utils.mostrarToast(context, "No s'ha pogut iniciar sessió. Tipus d'usuari desconegut.");
        }
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
