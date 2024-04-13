package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

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

    Utils.LogWrapper logWrapper = new Utils.LogWrapper();


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
    /*public void peticioLogin() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket socket = new Socket("192.168.0.252", 8080);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                    Object[] peticio = new Object[4];
                    peticio[0] = "login";
                    peticio[1] = correuUsuari;
                    peticio[2] = passUsuari;
                    peticio[3] = null;

                    objectOut.writeObject(peticio);
                    objectOut.flush();

                    // Registrar la petición en el log de depuración
                    Log.d(ETIQUETA, "Petición enviada: " + Arrays.toString(peticio));
                } catch (IOException e) {
                    Log.e(ETIQUETA, "Error al enviar la petición de inicio de sesión", e);
                }
                return null;
            }
        }.execute();}*/


    public void peticioLogin() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                enviarPeticioString("login", correuUsuari, passUsuari, null);
                return null;
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
    @Override
    public void respostaServidor(Object resposta) {
    Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
    if (resposta instanceof Object[]) {
        Object[] respostaArray = (Object[]) resposta;
        if (respostaArray[0] instanceof String && respostaArray[1] instanceof HashMap) {
            // Inicio de sesión exitoso
            String sessioID = (String) respostaArray[0];
            HashMap<String, String> usuariMap = (HashMap<String, String>) respostaArray[1];
            Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);
            if (usuari != null) {
                guardarSessioID(sessioID);
                Usuari.guardarDadesUsuari(usuari);
                obrirActivitat(usuari.getTipusUsuari());
            } else {
                Log.d(ETIQUETA, "Error en transformar el HashMap en Usuari");
            }
        } else if (respostaArray[0] instanceof Boolean && (Boolean) respostaArray[0] == false) {
            // Inicio de sesión no exitoso
            Utils.mostrarToast(context, "Credencials incorrectes");
        } else {
            Log.d(ETIQUETA, "Tipus d'objecte no vàlid en la resposta");
        }
    } else {
        Utils.mostrarToast(context, Utils.ERROR_CONNEXIO);
    }
}

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
}
