package antonioguerrero.ioc.fithub.peticions.installacions;

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
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.objectes.Installacio;

/**
 * Classe que s'encarrega de fer la petició al servidor per crear una instal·lació
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CrearInstallacio extends ConnexioServidor {
    private static final String ETIQUETA = "CrearInstallacio";
    private String nomInstallacio;
    private String descripcioInstallacio;
    private int tipusInstallacio;
    private final Context context;
    private String sessioID;


    /**
     * Constructor de la classe
     *
     * @param listener    Listener per a la resposta del servidor
     * @param installacio Installacio a crear
     * @param context     Context de l'aplicació
     * @param sessioID
     */
    public CrearInstallacio(respostaServidorListener listener, Installacio installacio, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.nomInstallacio = installacio.getNomInstallacio();
        this.descripcioInstallacio = installacio.getDescripcioInstallacio();
        this.tipusInstallacio = installacio.getTipusInstallacio();
        this.sessioID = sessioID;

    }

    /**
     * Mètode que crea una instal·lació
     */
    @SuppressLint("StaticFieldLeak")
    public void crearInstallacio() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Installacio installacio = new Installacio(0, nomInstallacio, descripcioInstallacio, tipusInstallacio);
                    HashMap<String, String> mapaInstallacio = installacio.installacio_a_hashmap(installacio);
                    return enviarPeticioHashMap("insert", "installacio", mapaInstallacio, sessioID);
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
            Log.d(ETIQUETA, "Instal·lació creada amb èxit");
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Instal·lació creada amb èxit.");
            // Redirigeix a l'usuari a la pantalla d'inici de sessió
            Intent intent = new Intent(context, GestioInstallacionsActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear la instal·lació: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), "Error en crear la instal·lació: " + missatgeError);
        }
        return null;
    }

    /**
     * Mètode que executa la petició
     * @throws ConnectException
     */
    @Override
    public void execute() throws ConnectException {
        crearInstallacio();
    }

    /**
     * Mètode que s'executa en segon pla
     * @param voids
     * @return null
     */
    protected abstract Object doInBackground(Void... voids);
}
