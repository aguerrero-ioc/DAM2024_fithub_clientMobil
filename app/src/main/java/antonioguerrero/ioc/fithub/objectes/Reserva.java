package antonioguerrero.ioc.fithub.objectes;


import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;

/**
 * Classe que representa una reserva d'una classe.
 * <p>
 * Cada reserva té un identificador únic, un identificador d'usuari, un identificador d'instal·lació, un identificador de classe, una data, una hora, una durada i un estat.
 * Aquesta classe també conté mètodes per convertir una reserva a un HashMap i viceversa.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Reserva {

    // Dades obligatòries de la reserva
    private String IDreserva;
    private String IDclasseDirigida;
    private String IDusuari;
    private String estatReserva;


    /**
     * Constructor de la classe Reserva reduït.
     */
    public Reserva(String IDreserva, String IDclasseDirigida, String IDusuari) {
        this.IDreserva = IDreserva;
        this.IDclasseDirigida = IDclasseDirigida;
        this.IDusuari = IDusuari;
    }

    /**
     * Constructor de la classe Reserva amb paràmetres.
     *
     * @param IDreserva        Identificador de la reserva
     * @param IDclasseDirigida Identificador de la classe dirigida
     * @param IDusuari         Identificador de l'usuari
     * @param estatReserva     Estat de la reserva
     */
    public Reserva(int IDreserva, String IDclasseDirigida, String IDusuari, String estatReserva) {
        this.IDreserva = String.valueOf(IDreserva);
        this.IDclasseDirigida = IDclasseDirigida;
        this.IDusuari = IDusuari;
        this.estatReserva = estatReserva;
    }

    /**
     * Constructor de la classe Reserva amb paràmetres.
     *
     * @param IDclasseDirigida Identificador de la classe dirigida
     * @param IDusuari         Identificador de l'usuari
     */
    public Reserva(String IDclasseDirigida, String IDusuari) {
        this.IDclasseDirigida = IDclasseDirigida;
        this.IDusuari = IDusuari;
    }

    // Getters y setters

    /**
     * Mètode per obtenir l'identificador de la reserva.
     *
     * @return L'identificador de la reserva.
     */
    public String getIDreserva() {
        return IDreserva;
    }

    /**
     * Mètode per establir l'identificador de la reserva.
     *
     * @param IDreserva L'identificador de la reserva.
     */

    public void setIDreserva(String IDreserva) {
        this.IDreserva = IDreserva;
    }


    /**
     * Mètode per obtenir l'identificador de la classe dirigida.
     *
     * @return L'identificador de la classe dirigida.
     */
    public String getIDclasseDirigida() {
        return IDclasseDirigida;
    }

    /**
     * Mètode per establir l'identificador de la classe dirigida.
     *
     * @param IDclasseDirigida L'identificador de la classe dirigida.
     */
    public void setIDclasseDirigida(String IDclasseDirigida) {
        this.IDclasseDirigida = IDclasseDirigida;
    }

    /**
     * Mètode per obtenir l'identificador de l'usuari.
     *
     * @return L'identificador de l'usuari.
     */
    public String getIDusuari() {
        return IDusuari;
    }

    /**
     * Mètode per establir l'identificador de l'usuari.
     *
     * @param IDusuari L'identificador de l'usuari.
     */
    public void setIDusuari(String IDusuari) {
        this.IDusuari = IDusuari;
    }

    /**
     * Mètode per obtenir l'estat de la reserva.
     *
     * @return L'estat de la reserva.
     */
    public String getEstatReserva() {
        return estatReserva;
    }

    /**
     * Mètode per establir l'estat de la reserva.
     *
     * @param estatReserva L'estat de la reserva.
     */
    public void setEstatReserva(String estatReserva) {
        this.estatReserva = estatReserva;
    }


    /**
     * Mètode per convertir una reserva a un HashMap.
     *
     * @param reserva La reserva que es vol convertir.
     * @return HashMap amb les dades de la reserva.
     */
    public HashMap<String, String> reserva_a_hashmap(Reserva reserva) {
        HashMap<String, String> mapaReserva = new HashMap<>();
        mapaReserva.put(Constants.OBJTYPE,Constants.OBJ_RESERVA);
        mapaReserva.put(Constants.RESERVA_ID, String.valueOf(reserva.getIDreserva()));
        mapaReserva.put(Constants.CLASSE_ID, reserva.getIDclasseDirigida());
        mapaReserva.put(Constants.ID_USUARI, reserva.getIDusuari());
        mapaReserva.put(Constants.RESERVA_ESTAT, reserva.getEstatReserva());
        return mapaReserva;
    }

    /**
     * Mètode per convertir un HashMap a una reserva.
     *
     * @param mapaReserva El HashMap que es vol convertir.
     * @return La reserva amb les dades del HashMap.
     */
    public Reserva hashmap_a_reserva(HashMap<String, String> mapaReserva) {
        int IDreserva = Integer.parseInt(mapaReserva.get(Constants.RESERVA_ID));
        String IDclasseDirigida = mapaReserva.get(Constants.CLASSE_ID);
        String IDusuari = mapaReserva.get(Constants.ID_USUARI);
        String estatReserva = mapaReserva.get(Constants.RESERVA_ESTAT);
        return new Reserva(IDreserva, IDclasseDirigida, IDusuari, estatReserva);
    }

}