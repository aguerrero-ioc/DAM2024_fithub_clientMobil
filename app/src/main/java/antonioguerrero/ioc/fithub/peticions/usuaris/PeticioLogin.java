package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
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
public class PeticioLogin extends BasePeticions {

    private static final String ETIQUETA = "PeticioLogin";
    private final String correuUsuari;
    private final String contrasenya;
    private final Context context;

    Utils.LogWrapper logWrapper = new Utils.LogWrapper();


    /**
     * Constructor de la classe PeticioLogin.
     *
     * @param context      Contexto de la aplicación
     * @param correuUsuari Correu electrònic de l'usuari per a l'inici de sessió
     * @param contrasenya  Contrasenya de l'usuari per a l'inici de sessió
     */
    public PeticioLogin(Context context, String correuUsuari, String contrasenya) {
        super(null);
        this.context = context;
        this.correuUsuari = correuUsuari;
        this.contrasenya = contrasenya;
    }

    /**
     * Mètode per enviar la petició de login al servidor.
     */
    public void peticioLogin() {
        enviarPeticio("login", this.correuUsuari, this.contrasenya, null, ETIQUETA);
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
    public void respostaServidor(Object resposta){
        logWrapper.d(ETIQUETA, "Resposta del servidor: " + resposta);

        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            gestionarRespostaArray((Object[]) resposta);
        } else {
            Utils.mostrarToast(context, Utils.ERROR_CONNEXIO);
        }
    }

    /**
     * Mètode per gestionar la resposta del servidor quan aquesta és un Object[].
     *
     * @param respostaArray La resposta del servidor.
     */
    private void gestionarRespostaArray(Object[] respostaArray) {
        if (respostaArray[0] instanceof String) {
            String estat = (String) respostaArray[0];
            if (estat.equals("usuariActiu")) {
                gestionarUsuariActiu(respostaArray);
            } else if (estat.equals("false")) {
                Utils.mostrarToast(context, "Credencials incorrectes");
            }
        } else {
            Utils.mostrarToast(context, Utils.ERROR_CONNEXIO);
        }
    }

    /**
     * Mètode per gestionar l'usuari actiu després de l'autenticació.
     *
     * @param respostaArray La resposta del servidor.
     */
    private void gestionarUsuariActiu(Object[] respostaArray) {
        if (respostaArray[1] instanceof HashMap) {
            HashMap<String, String> usuariMap = (HashMap<String, String>) respostaArray[1];
            Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);
            if (usuari != null) {
                guardarSessioID(usuariMap.get(Utils.SESSIO_ID));
                Usuari.guardarDadesUsuari(usuari);
                obrirActivitat(usuari.getTipusUsuari());
            } else {
                Log.d("PeticioLogin", "Error al transformar el HashMap en Usuari");
            }
        } else {
            Log.d("PeticioLogin", "Tipus d'objecte no vàlid en la resposta");
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

}
