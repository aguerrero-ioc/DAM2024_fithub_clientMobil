package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
        // Convertir el objecte Usuari a un HashMap
        HashMap<String, String> usuariMap = new HashMap<>();
        usuariMap.put("nom", usuari.getNom());
        usuariMap.put("cognoms", usuari.getCognoms());
        if (usuari.getDataNaixement() != null) {
            usuariMap.put("dataNaixement", new SimpleDateFormat("dd-MM-yyyy").format(usuari.getDataNaixement()));
        }
        usuariMap.put("adreca", usuari.getAdreca());
        usuariMap.put("telefon", usuari.getTelefon());
        usuariMap.put("correu", usuari.getCorreuUsuari());
        usuariMap.put("contrasenya", usuari.getContrasenya());

        // Crear el Object[] per la petició
        Object[] peticio = new Object[3];
        peticio[0] = "insert";
        peticio[1] = "usuari";
        peticio[2] = usuariMap;

        Log.d(ETIQUETA, "Enviant petició: " + Arrays.toString(peticio));
        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
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