package antonioguerrero.ioc.fithub.peticions.serveis;

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
import antonioguerrero.ioc.fithub.menu.installacions.GestioInstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.serveis.GestioServeisActivity;
import antonioguerrero.ioc.fithub.objectes.Servei;

/**
 * Classe que s'encarrega de fer la petició al servidor per crear un servei
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CrearServei extends ConnexioServidor {
    private static final String ETIQUETA = "CrearServei";
    private String nomServei;
    private String descripcioServei;
    private String preuServei;
    private final Context context;
    private String sessioID;


    /**
     * Constructor de la classe
     * @param listener Listener per a la resposta del servidor
     * @param servei Servei a crear
     * @param context Context de l'aplicació
     */
    public CrearServei(respostaServidorListener listener, Servei servei, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.nomServei = servei.getNomServei();
        this.descripcioServei = servei.getDescripcioServei();
        this.preuServei = servei.getPreuServei();
        this.sessioID = sessioID;
    }

    /**
     * Mètode que crea un servei
     */
    @SuppressLint("StaticFieldLeak")
    public void crearServei() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Servei servei = new Servei(nomServei, descripcioServei, preuServei);
                    HashMap<String, String> mapaServei = servei.servei_a_hashmap(servei);
                    return enviarPeticioHashMap("insert", "servei", mapaServei, sessioID);
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
     * Mètode que retorna el tipus d'objecte
     * @return Object[].class
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que retorna la resposta del servidor
     * @param resposta Resposta del servidor
     * @return null
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Servei creat amb èxit");
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Servei creat amb èxit.");
            // Redirigeix a l'usuari a la pantalla d'inici de sessió
            Intent intent = new Intent(context, GestioServeisActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear el servei: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), "Error en crear el servei: " + missatgeError);
        }
        return null;
    }

    /**
     * Mètode que executa la petició
     * @throws ConnectException
     */
    @Override
    public void execute() throws ConnectException {
        crearServei();
    }

    /**
     * Mètode que s'executa en segon pla
     * @param voids
     * @return null
     */
    protected abstract Object doInBackground(Void... voids);
}
