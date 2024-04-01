package antonioguerrero.ioc.fithub.peticions;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe que representa una petició de login al servidor.
 * Hereta de la classe BasePeticions.
 *
 * Aquesta classe s'utilitza per iniciar sessió a través del servidor mitjançant les credencials d'usuari.
 * El missatge de la petició de login s'envia amb el nom d'usuari i la contrasenya.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioLogin extends BasePeticions {

    private String nomUsuari;
    private String contrasenya;

    /**
     * Constructor de la classe PeticioLogin.
     *
     * @param nomUsuari Nom d'usuari per a l'inici de sessió
     * @param contrasenya Contrasenya de l'usuari per a l'inici de sessió
     * @param listener Listener per a les respostes del servidor
     */
    public PeticioLogin(String nomUsuari, String contrasenya, OnServerResponseListener listener) {
        super(listener);
        this.nomUsuari = nomUsuari;
        this.contrasenya = contrasenya;
    }

    /**
     * Executa la petició de login.
     */
    public void execute() {
        // Construeix el missatge de la petició de login amb el nom d'usuari i la contrasenya
        String missatge = "login," + nomUsuari + "," + contrasenya;
        // Executa la tasca asíncrona per connectar-se al servidor i enviar la petició de login
        new ConnexioServidor.ConnectToServerTask().execute(missatge);
    }


}
