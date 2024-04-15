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

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe que representa una petició de login al servidor.
 * Hereta de la classe BasePeticions.
 * <p>
 * Aquesta classe s'utilitza per iniciar sessió a través del servidor mitjançant les credencials d'usuari.
 * El missatge de la petició de login s'envia amb el correu d'usuari i la contrasenya.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class PeticioLogin extends BasePeticions {

    private static String ETIQUETA = "PeticioLogin";
    private String correuUsuari;
    private String passUsuari;
    private Context context;

    /**
     * Constructor de la classe PeticioLogin.
     *
     * @param context      El context de l'aplicació.
     * @param correuUsuari El correu de l'usuari.
     * @param passUsuari   La contrasenya de l'usuari.
     */
    public PeticioLogin(Context context, String correuUsuari, String passUsuari) {
        super(context, correuUsuari, passUsuari);
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


    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Usuari.setContext(context);

        if (context != null) {
            SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);

            Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
            if (resposta instanceof Object[]) {
                Object[] respostaArray = (Object[]) resposta;
                if (respostaArray.length == 2 && respostaArray[0] instanceof String && respostaArray[1] instanceof HashMap) {
                    // Inicio de sesión exitoso con el identificador de sesión y los datos del usuario
                    String sessioID = (String) respostaArray[0];
                    HashMap<String, String> usuariMap = (HashMap<String, String>) respostaArray[1];
                    Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);
                    if (usuari != null) {
                        obrirActivitat(usuari.getTipusUsuari());
                        Usuari.guardarDadesUsuari(usuari);
                        guardarSessioID(sessioID);
                    } else {
                        Log.d(ETIQUETA, "Error en transformar el HashMap en Usuari");
                    }
                } else if (respostaArray[0] instanceof Boolean && (Boolean) respostaArray[0] == false) {
                    // Inici de sesión no exitoso
                    Utils.mostrarToast(context, "Credencials incorrectes");
                } else {
                    Log.d(ETIQUETA, "Tipus d'objecte no vàlid en la resposta");
                }
            } else {
                Utils.mostrarToast(context, Utils.ERROR_CONNEXIO);
            }
        } else {
            Log.e("PeticioLogin", "El context es nul");
        }
        return null;
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
     * Executa la petició de login.
     */
    @Override
    public void execute() {
        peticioLogin();
    }

    /**
     * Mètode que gestiona la resposta del servidor després de l'intent d'inici de sessió.
     * Aquest mètode serà cridat pel servidor per informar sobre l'estat de l'autenticació.
     *
     * @param resposta Resposta del servidor, que pot ser l'èxit de l'autenticació o un error.
     */


    /**
     * Guarda l'identificador de sessió a SharedPreferences.
     *
     * @param sessioID L'identificador de sessió que es guardarà a SharedPreferences.
     */
    private void guardarSessioID(String sessioID) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        editor.putString(Utils.SESSIO_ID, sessioID);
        editor.apply();
        Log.d("PeticioLogin", "sessioID guardat: " + sessioID);
    }


    /**
     * Mètode per obrir l'activitat corresponent segons el tipus d'usuari.
     *
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

    public abstract void respostaServidor(Object[] resposta);

    protected abstract Object doInBackground(Void... voids);

    public String getEtiqueta() {
        return ETIQUETA;
    }
}
