package antonioguerrero.ioc.fithub.peticions;

import android.os.AsyncTask;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe per gestionar les peticions relacionades amb els usuaris.
 * Hereta de la classe BasePeticions.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioUsuari extends BasePeticions {

    /**
     * Constructor de la classe PeticioUsuari.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public PeticioUsuari(OnServerResponseListener listener) {
        super(listener);
    }

    /**
     * Mètode per crear un nou usuari.
     *
     * @param usuari L'objecte Usuari que representa el nou usuari.
     */
    public static void crearUsuari(Usuari usuari) {
        String peticio = "insert,usuari," +
                usuari.getNom() + "," +
                (usuari.getCognoms() != null ? usuari.getCognoms() : "") + "," +
                (usuari.getDataNaixement() != null ? usuari.getDataNaixement() : "") + "," +
                (usuari.getAdreca() != null ? usuari.getAdreca() : "") + "," +
                (usuari.getTelefon() != null ? usuari.getTelefon() : "") + "," +
                (usuari.getCorreu() != null ? usuari.getCorreu() : "") + "," +
                (usuari.getContrasenya() != null ? usuari.getContrasenya() : "");

        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per modificar les dades d'un usuari.
     *
     * @param usuari L'objecte Usuari amb les dades modificades.
     */
    public static void modificarUsuari(Usuari usuari) {
        String peticio = "update,usuari," +
                usuari.getNom() + "," +
                (usuari.getCognoms() != null ? usuari.getCognoms() : "") + "," +
                (usuari.getDataNaixement() != null ? usuari.getDataNaixement() : "") + "," +
                (usuari.getAdreca() != null ? usuari.getAdreca() : "") + "," +
                (usuari.getTelefon() != null ? usuari.getTelefon() : "") + "," +
                (usuari.getCorreu() != null ? usuari.getCorreu() : "") + "," +
                (usuari.getContrasenya() != null ? usuari.getContrasenya() : "") + "," +
                (usuari.getDataInscripcio() != null ? usuari.getDataInscripcio() : "");

        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);

    }

    /**
     * Mètode per consultar les dades d'un usuari.
     *
     * @param correu El correu electrònic de l'usuari.
     */
    public static void consultarUsuari(String correu) {
        String peticio = "select,usuari," + correu;
        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per canviar la contrasenya d'un usuari.
     *
     * @param usuari L'objecte Usuari amb la nova contrasenya.
     */
    public static void canviarContrasenya(Usuari usuari) {
        String peticio = "update,usuari," +
                usuari.getCorreu() + "," +
                usuari.getContrasenya();

        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    @Override
    protected void execute() {
    }


}
