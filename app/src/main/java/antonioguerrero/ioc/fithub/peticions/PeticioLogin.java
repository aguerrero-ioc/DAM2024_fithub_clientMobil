package antonioguerrero.ioc.fithub.peticions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menuInici.AdminActivity;
import antonioguerrero.ioc.fithub.menuInici.ClientActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;

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
     * Executa la petició de login.
     */
    @Override
    public void execute() {
        // Construeix el missatge de la petició de login amb el nom d'usuari i la contrasenya
        String missatge = "login," + correuUsuari + "," + contrasenya;
        // Executa la tasca asíncrona per connectar-se al servidor i enviar la petició de login
        new ConnexioServidor.ConnectToServerTask().execute(missatge);
    }

    /**
     * Mètode que gestiona la resposta del servidor després de l'intent d'inici de sessió.
     * Aquest mètode serà cridat pel servidor per informar sobre l'estat de l'autenticació.
     *
     * @param resposta Resposta del servidor, que pot ser l'èxit de l'autenticació o un error.
     */
    @Override
    public void onServerResponse(Object resposta){
        Log.d("PeticioLogin", "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] responseArray = (Object[]) resposta;
            boolean exit = (boolean) responseArray[0];
            if (exit) {
                Usuari usuari = (Usuari) responseArray[1];
                // Guardar l'objecte d'usuari a SharedPreferences
                guardarDadesUsuari(usuari);

                // Extreure el tipus d'usuari
                String tipusUsuari = usuari.getTipus();

                Log.d("PeticioLogin", "Tipus d'usuari: " + tipusUsuari);
                // Obrir l'activitat de l'usuari corresponent
                obrirActivitat(tipusUsuari);
            } else {
                // Resposta incorrecta del servidor
                Toast.makeText(context, "Credencials incorrectes", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Error de connexió
            Toast.makeText(context, "Error de connexió", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Guarda les dades de l'usuari a SharedPreferences.
     *
     * @param usuari L'objecte Usuari que es guardarà a SharedPreferences.
     */
    private void guardarDadesUsuari(Usuari usuari) {
        SharedPreferences preferences = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        // Guardar les propietats de l'objecte usuari a SharedPreferences
        editor.putString("nomUsuari", usuari.getNom());
        editor.putString("idUsuari", String.valueOf(usuari.getUsuariID()));
        editor.putString("tipusClient", usuari.getTipus());
        editor.putString("correu", usuari.getCorreu());
        editor.putString("contrasenya", usuari.getContrasenya());
        editor.putString("dataInscripcio", usuari.getDataInscripcio());
        editor.putString("cognoms", usuari.getCognoms());
        editor.putString("dataNaixement", usuari.getDataNaixement());
        editor.putString("adreca", usuari.getAdreca());
        editor.putString("telefon", usuari.getTelefon());
        // Aplica els canvis a SharedPreferences
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
            Toast.makeText(context, "Benvingut, client", Toast.LENGTH_SHORT).show();
        } else if (tipusUsuari.equals("admin")) {
            // Usuari tipus admin
            intent = new Intent(context, AdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Toast.makeText(context, "Benvingut, administrador", Toast.LENGTH_SHORT).show();
        } else {
            // Tipus d'usuari desconegut
            Toast.makeText(context, "No s'ha pogut iniciar sessió. Tipus d'usuari desconegut.", Toast.LENGTH_SHORT).show();
        }
    }

}
