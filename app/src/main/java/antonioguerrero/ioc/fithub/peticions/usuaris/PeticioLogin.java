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
        HashMap<String, String> mapaPeticio = new HashMap<>();
        mapaPeticio.put("type", "select");
        mapaPeticio.put("objectType", "usuari");
        mapaPeticio.put("correuUsuari", correuUsuari);
        mapaPeticio.put("contrasenya", contrasenya);
        Log.d(ETIQUETA, "Enviant petició: " + mapaPeticio.toString());
        new ConnexioServidor.ConnectToServerTask(this).execute(mapaPeticio);
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
        if (resposta instanceof HashMap) {
            HashMap<String, String> respostaMap = (HashMap<String, String>) resposta;
            String estat = respostaMap.get("exit");
            if (estat != null && estat.equals("usuariActiu")) {
                String tipusObjecte = respostaMap.get("tipusObjecte");
                if (tipusObjecte != null && tipusObjecte.equals("usuari")) {
                    Usuari usuari = (Usuari) Utils.HashMapAObjecte(respostaMap, Usuari.class);
                    if (usuari != null) {
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
            } else {
                Utils.mostrarToast(context, "Credencials incorrectes");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
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
        editor.putString("correu", usuari.getCorreu());
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
