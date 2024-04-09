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
        // Crear el Object[] per la petició
        Object[] peticio = new Object[3];
        peticio[0] = "login";
        peticio[1] = this.correuUsuari;
        peticio[2] = this.contrasenya;

        Log.d(ETIQUETA, "Enviant petició: " + peticio.toString());
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            if (respostaArray[0] instanceof String) {
                String estat = (String) respostaArray[0];
                if (estat.equals("usuariActiu")) {
                    if (respostaArray[1] instanceof HashMap) {
                        HashMap<String, String> usuariMap = (HashMap<String, String>) respostaArray[1];
                        Usuari usuari = (Usuari) Utils.HashMapAObjecte(usuariMap, Usuari.class);
                        if (usuari != null) {
                            // Guardar l'ID de sessió a SharedPreferences
                            SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferencies.edit();
                            editor.putString("sessioID", usuariMap.get("sessioID"));
                            editor.apply();
                            // Guardar l'objecte d'usuari a SharedPreferences
                            guardarDadesUsuari(usuari);
                            // Extreure el tipus d'usuari
                            String tipusUsuari = usuari.getTipus();
                            Log.d("PeticioLogin", "Tipus d'usuari: " + tipusUsuari);
                            // Obrir l'activitat de l'usuari corresponent
                            obrirActivitat(tipusUsuari);
                        } else {
                            Log.d("PeticioLogin", "Error al transformar el HashMap en Usuari");
                        }
                    } else {
                        Log.d("PeticioLogin", "Tipus d'objecte no vàlid en la resposta");
                    }
                } else if (estat.equals("false")) {
                    Utils.mostrarToast(context, "Credencials incorrectes");
                }
            } else {
                Utils.mostrarToast(context, "Error de connexió");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }


    /**
     * Guarda les propietats de l'objecte Usuari a SharedPreferences.
     *
     * @param usuari L'objecte Usuari que es guardarà a SharedPreferences.
     */
    private void guardarDadesUsuari(Usuari usuari) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        // Guardar les propietats de l'objecte usuari a SharedPreferences
        editor.putString("nomUsuari", usuari.getNom());
        editor.putString("idUsuari", String.valueOf(usuari.getUsuariID()));
        editor.putString("tipusClient", usuari.getTipus());
        editor.putString("correu", usuari.getCorreuUsuari());
        editor.putString("contrasenya", usuari.getContrasenya());
        editor.putString("dataInscripcio", format.format(usuari.getDataInscripcio()));
        editor.putString("cognoms", usuari.getCognoms());
        editor.putString("dataNaixement", format.format(usuari.getDataNaixement()));
        editor.putString("adreca", usuari.getAdreca());
        editor.putString("telefon", usuari.getTelefon());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }

    /**
     * Mètode que obre l'activitat corresponent segons el tipus d'usuari autenticat.
     *
     * @param tipusUsuari Tipus d'usuari autenticat (client o administrador).
     */
    private void obrirActivitat(String tipusUsuari) {
        // Obrir l'activitat corresponent segons el tipus d'usuari
        Intent intent;
        if (tipusUsuari.equals("client")) {
            // Usuari tipus client
            intent = new Intent(context, ClientActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Utils.mostrarToast(context, "Benvingut, client");
        } else if (tipusUsuari.equals("admin")) {
            // Usuari tipus admin
            intent = new Intent(context, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Utils.mostrarToast(context, "Benvingut, administrador");
        } else {
            // Tipus d'usuari desconegut
            Utils.mostrarToast(context, "No s'ha pogut iniciar sessió. Tipus d'usuari desconegut.");
        }
    }

}
