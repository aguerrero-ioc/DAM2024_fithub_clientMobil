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
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que s'encarrega de fer la petició al servidor per crear un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class RegistrarUsuari extends ConnexioServidor {
    private static final String ETIQUETA = "CrearUsuari";
    private final String nomUsuari;
    private final String cognomsUsuari;
    private final String telefon;
    private final String correuUsuari;
    private final String passUsuari;
    private final Context context;

    /**
     * Constructor de la classe
     * <p>
     * @param listener Listener per a la resposta del servidor
     * @param usuari Usuari a crear
     * @param context Context de l'aplicació
     */
    public RegistrarUsuari(respostaServidorListener listener, Usuari usuari, Context context) {
        super(listener);
        this.context = context;
        this.correuUsuari = usuari.getCorreuUsuari();
        this.passUsuari = usuari.getPassUsuari();
        this.nomUsuari = usuari.getNomUsuari();
        this.cognomsUsuari = usuari.getCognomsUsuari();
        this.telefon = usuari.getTelefon();
    }

    /**
     * Mètode que crea un usuari
     */
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

    /**
     * Mètode que retorna la resposta del servidor
     * <p>
     * @param resposta Resposta del servidor
     * @return null
     */
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

    /**
     * Mètode que executa la petició
     */
    public void execute() throws ConnectException {
        crearUsuari();
    }
}