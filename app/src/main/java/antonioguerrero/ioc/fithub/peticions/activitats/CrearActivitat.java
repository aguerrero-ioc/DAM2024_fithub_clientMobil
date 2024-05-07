package antonioguerrero.ioc.fithub.peticions.activitats;

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
import antonioguerrero.ioc.fithub.menu.activitats.GestioActivitatsActivity;
import antonioguerrero.ioc.fithub.objectes.Activitat;

/**
 * Classe que s'encarrega de fer la petició al servidor per crear una activitat
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class CrearActivitat extends ConnexioServidor {
    private static final String ETIQUETA = "CrearActivitat";
    private final String nomActivitat;
    private final String descripcioActivitat;
    private final int aforamentActivitat;
    private final int tipusInstallacio;
    private final Context context;
    private final String sessioID;

    /**
     * Constructor de la classe
     * <p>
     * @param listener Listener per a la resposta del servidor
     * @param activitat Activitat a crear
     * @param context Context de l'aplicació
     * @param sessioID Sessió de l'usuari
     */
    public CrearActivitat(respostaServidorListener listener, Activitat activitat, Context context, String sessioID) {
        super(listener);
        this.context = context;
        this.nomActivitat = activitat.getNomActivitat();
        this.descripcioActivitat = activitat.getDescripcioActivitat();
        this.aforamentActivitat = activitat.getAforamentActivitat();
        this.tipusInstallacio = activitat.getTipusInstallacio();
        this.sessioID = sessioID;

    }

    /**
     * Mètode que crea una activitat
     */
    @SuppressLint("StaticFieldLeak")
    public void crearActivitat() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    Activitat activitat = new Activitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio);
                    HashMap<String, String> mapaActivitat = activitat.activitat_a_hashmap(activitat);
                    return enviarPeticioHashMap("insert", "activitat", mapaActivitat, sessioID);
                } catch (ConnectException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            protected void onPostExecute(Object resposta) {
                processarResposta(resposta);
            }
        }.execute();
    }

    /**
     * Mètode que retorna la resposta del servidor
     * @param resposta Resposta del servidor
     * @return null
     */
     public List<HashMap<String, String>> processarResposta(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        Object[] respostaArray = (Object[]) resposta;
        boolean exit = respostaArray[0].equals("true");
        if (exit) {
            Log.d(ETIQUETA, "Activitat creada amb èxit");
            // Mostra un missatge de confirmació a l'usuari
            Utils.mostrarToast(context, "Activitat creada amb èxit.");
            // Redirigeix a l'usuari a la pantalla de gestió d'activitats
            Intent intent = new Intent(context, GestioActivitatsActivity.class);
            context.startActivity(intent);
            ((Activity) context).finish();
        } else {
            String missatgeError = (String) respostaArray[1];
            Log.d(ETIQUETA, "Error en crear l'activitat: " + missatgeError);
            // Mostra el missatge d'error a l'usuari
            Utils.mostrarToast(context.getApplicationContext(), "Error en crear l'activitat: " + missatgeError);
        }
        return null;
    }

    /**
     * Mètode que executa la petició
     */
    public void execute() throws ConnectException {
        crearActivitat();
    }
}