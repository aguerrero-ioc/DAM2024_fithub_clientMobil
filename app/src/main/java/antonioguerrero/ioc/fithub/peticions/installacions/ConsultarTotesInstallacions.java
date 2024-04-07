package antonioguerrero.ioc.fithub.peticions.installacions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per obtenir totes les instal·lacions.
 * Hereta de la classe BasePeticions.
 *
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per obtenir totes les instal·lacions.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConsultarTotesInstallacions extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarTotesInstallacions";

    /**
     * Constructor de la classe ConsultarTotesInstallacions.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public ConsultarTotesInstallacions(respostaServidorListener listener, Context context) {
        super(listener);
        this.context = context;
    }

    /**
     * Mètode per obtenir les dades de totes les instal·lacions.
     */
    public void obtenirInstallacions() {
        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("type", "selectAll");
        requestMap.put("objectType", "Installacio");
        requestMap.put("data", null);
        Log.d(ETIQUETA, "Enviant petició: " + requestMap.toString());
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestMap);
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
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    @Override
    public void respostaServidor(Object resposta) {
        Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("installacioLlista")) {
                List<HashMap<String, String>> installacionsList = (List<HashMap<String, String>>) respostaArray[1];
                for (HashMap<String, String> installacioMap : installacionsList) {
                    Installacio installacio = new Installacio(
                            Integer.parseInt(installacioMap.get("id")),
                            installacioMap.get("nomInstallacio"),
                            installacioMap.get("descripcioInstallacio"),
                            Integer.parseInt(installacioMap.get("tipusInstallacio"))
                    );
                    guardarDadesInstallacions(installacionsList);
                }
            } else {
                Utils.mostrarToast(context, "Error en la consulta d'instal·lacions");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }

    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() {
        obtenirInstallacions();
    }

    /**
     * Guarda les propietats de l'objecte Installacio a SharedPreferences.
     *
     * @param installacionsList La llista d'objectes Installacio que es guardarà a SharedPreferences.
     */
    private void guardarDadesInstallacions(List<HashMap<String, String>> installacionsList) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de cada objecte installacio a SharedPreferences
        for (int i = 0; i < installacionsList.size(); i++) {
            HashMap<String, String> installacioMap = installacionsList.get(i);
            editor.putString("installacioNom" + i, installacioMap.get("nomInstallacio"));
            editor.putString("installacioDescripcio" + i, installacioMap.get("descripcioInstallacio"));
            editor.putString("installacioTipus" + i, installacioMap.get("tipusInstallacio"));
        }

        // Guardar la quantitat total d'instal·lacions
        editor.putInt("numInstallacions", installacionsList.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }
}