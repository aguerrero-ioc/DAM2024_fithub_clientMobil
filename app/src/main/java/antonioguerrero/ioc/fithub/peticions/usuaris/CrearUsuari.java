package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class CrearUsuari extends BasePeticions {
    private static final String ETIQUETA = "CrearUsuari";
    private Usuari usuari;

    private Context context;

    public CrearUsuari(respostaServidorListener listener, Usuari usuari, Context context)  {
        super(listener);
        this.usuari = usuari;
        this.context = context;
    }

    public void crearUsuari() {
        HashMap<String, String> mapaPeticio = new HashMap<>();
        mapaPeticio.put("objectType", "usuari");
        mapaPeticio.put("nom", usuari.getNom());
        mapaPeticio.put("cognoms", usuari.getCognoms());
        mapaPeticio.put("dataNaixement", new SimpleDateFormat("dd-MM-yyyy").format(usuari.getDataNaixement()));
        mapaPeticio.put("adreca", usuari.getAdreca());
        mapaPeticio.put("telefon", usuari.getTelefon());
        mapaPeticio.put("correu", usuari.getCorreu());
        mapaPeticio.put("contrasenya", usuari.getContrasenya());
        Log.d(ETIQUETA, "Enviant petició: " + mapaPeticio.toString());
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mapaPeticio);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }


    @Override
    public void respostaServidor(Object resposta) {
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
    }

    @Override
    public void execute() {
        crearUsuari();
    }
}