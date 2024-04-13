package antonioguerrero.ioc.fithub.peticions.usuaris;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
    private String nomUsuari;
    private String cognomsUsuari;
    private String telefon;
    private String correuUsuari;
    private String passUsuari;
    private final Context context;

    public CrearUsuari(respostaServidorListener listener, Usuari usuari, Context context){
        super(listener);
        this.context = context;
        this.correuUsuari = usuari.getCorreuUsuari();
        this.passUsuari = usuari.getPassUsuari();
        this.nomUsuari = usuari.getNomUsuari();
        this.cognomsUsuari = usuari.getCognomsUsuari();
        this.telefon = usuari.getTelefon();

    }

    /*public void crearUsuari() {
        // Convertir el objecte Usuari a un HashMap
        HashMap<String, String> mapaUsuari = Utils.ObjecteAHashMap(usuari);

        /* Comprovar si funciona amb ObjecteAHashMap
        HashMap<String, String> usuariMap = new HashMap<>();
        usuariMap.put("nom", usuari.getNomUsuari());
        usuariMap.put("cognoms", usuari.getCognomsUsuari());
        if (usuari.getDataNaixement() != null) {
            usuariMap.put("dataNaixement", new SimpleDateFormat("dd-MM-yyyy").format(usuari.getDataNaixement()));
        }
        usuariMap.put("adreca", usuari.getAdreca());
        usuariMap.put("telefon", usuari.getTelefon());
        usuariMap.put("correu", usuari.getCorreuUsuari());
        usuariMap.put("contrasenya", usuari.getPassUsuari());
        */
        // Crear el Object[] per la petició (sense sessioID)
        /*enviarPeticio("insert", "usuari", mapaUsuari, null);

    }*/

    /*public void crearUsuari() {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        Socket socket = new Socket("192.168.0.252", 8080);
                        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                        // Crear un nuevo objeto Usuari con los datos proporcionados
                        Usuari usuari = new Usuari(correuUsuari, passUsuari, nomUsuari, cognomsUsuari, telefon);

                        // Convertir el objeto Usuari a un HashMap
                        HashMap<String, String> mapaUsuari = usuari.usuari_a_mapa(usuari);

                        Object[] peticio = new Object[4];
                        peticio[0] = "insert";
                        peticio[1] = "usuari";
                        peticio[2] = mapaUsuari; // Enviar el HashMap del usuario como tercer elemento del array
                        peticio[3] = null; // No se envía el ID de sesión

                        objectOut.writeObject(peticio);
                        objectOut.flush();

                        // Registrar la petición en el log de depuración
                        Log.d(ETIQUETA, "Petición enviada: " + Arrays.toString(peticio));
                    } catch (IOException e) {
                        Log.e(ETIQUETA, "Error al enviar la petición de creación de usuario", e);
                    }
                    return null;
                }
            }.execute();}*/

    public void crearUsuari() {
        Usuari usuari = new Usuari(correuUsuari, passUsuari, nomUsuari, cognomsUsuari, telefon);
        HashMap<String, String> mapaUsuari = usuari.usuari_a_mapa(usuari);
        enviarPeticioHashmap("insert", "usuari", mapaUsuari, null);
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