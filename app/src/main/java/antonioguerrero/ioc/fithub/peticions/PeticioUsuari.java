package antonioguerrero.ioc.fithub.peticions;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe per gestionar les peticions relacionades amb els usuaris.
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
        StringBuilder missatgeBuilder = new StringBuilder("insert,usuari,");
        missatgeBuilder.append(usuari.getNom()).append(",");
        missatgeBuilder.append(usuari.getCognoms() != null ? usuari.getCognoms() : "").append(",");
        missatgeBuilder.append(usuari.getDataNaixement() != null ? usuari.getDataNaixement() : "").append(",");
        missatgeBuilder.append(usuari.getAdreca() != null ? usuari.getAdreca() : "").append(",");
        missatgeBuilder.append(usuari.getTelefon() != null ? usuari.getTelefon() : "").append(",");
        missatgeBuilder.append(usuari.getCorreu() != null ? usuari.getCorreu() : "").append(",");
        missatgeBuilder.append(usuari.getContrasenya() != null ? usuari.getContrasenya() : "");

        new ConnexioServidor.ConnectToServerTask().execute(missatgeBuilder.toString());
    }

    /**
     * Mètode per modificar les dades d'un usuari.
     *
     * @param usuari L'objecte Usuari amb les dades modificades.
     */
    public static void modificarUsuari(Usuari usuari) {
        // Construir el mensaje para la petición al servidor
        StringBuilder missatgeBuilder = new StringBuilder("update,usuari,");
        missatgeBuilder.append(usuari.getNom()).append(",");
        missatgeBuilder.append(usuari.getCognoms() != null ? usuari.getCognoms() : "").append(",");
        missatgeBuilder.append(usuari.getDataNaixement() != null ? usuari.getDataNaixement() : "").append(",");
        missatgeBuilder.append(usuari.getAdreca() != null ? usuari.getAdreca() : "").append(",");
        missatgeBuilder.append(usuari.getTelefon() != null ? usuari.getTelefon() : "").append(",");
        missatgeBuilder.append(usuari.getCorreu() != null ? usuari.getCorreu() : "").append(",");
        missatgeBuilder.append(usuari.getContrasenya() != null ? usuari.getContrasenya() : "").append(",");
        missatgeBuilder.append(usuari.getDataInscripcio() != null ? usuari.getDataInscripcio() : "");

        // Ejecutar la tarea asíncrona para enviar la petición al servidor
        new ConnexioServidor.ConnectToServerTask().execute(missatgeBuilder.toString());

    }

    /**
     * Mètode per consultar les dades d'un usuari.
     *
     * @param nomUsuari El nom d'usuari de l'usuari a consultar.
     */
    public static void consultarUsuari(String nomUsuari) {
        String missatge = "(select,usuari," + nomUsuari + ")";
        new ConnexioServidor.ConnectToServerTask().execute(missatge);
    }

    /**
     * Mètode per canviar la contrasenya d'un usuari.
     *
     * @param usuari L'objecte Usuari amb la nova contrasenya.
     */
    public static void canviarContrasenya(Usuari usuari) {
        StringBuilder missatgeBuilder = new StringBuilder("update,usuari,");
        missatgeBuilder.append(usuari.getNom()).append(",");
        missatgeBuilder.append(usuari.getCognoms() != null ? usuari.getCognoms() : "").append(",");
        missatgeBuilder.append(usuari.getTelefon() != null ? usuari.getTelefon() : "").append(",");
        missatgeBuilder.append(usuari.getCorreu() != null ? usuari.getCorreu() : "").append(",");
        missatgeBuilder.append(usuari.getContrasenya() != null ? usuari.getContrasenya() : "");

        new ConnexioServidor.ConnectToServerTask().execute(missatgeBuilder.toString());
    }

    @Override
    protected void execute() {
        // Aquest mètode no s'utilitza en aquesta classe ja que cada operació específica té el seu propi mètode.
    }
}
