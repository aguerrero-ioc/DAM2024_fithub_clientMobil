package antonioguerrero.ioc.fithub.objectes;


/**
 * Classe que representa una reserva d'una classe.
 * <p>
 * Cada reserva té un identificador únic, un identificador d'usuari, un identificador d'instal·lació, un identificador de classe, una data, una hora, una durada i un estat.
 *
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class Reserva {

    // Dades obligatòries de la reserva
    private int IDReserva;
    private int IDUsuari;


    /**
     * Constructor de la classe Reserva reduït.
     *
     * @param IDReserva L'identificador de la reserva.
     * @param IDUsuari  L'identificador de l'usuari.
     */
    public Reserva(int IDReserva, int IDUsuari) {
        this.IDReserva = IDReserva;
        this.IDUsuari = IDUsuari;
    }


    // Getters y setters

    /**
     * Obté l'identificador de la reserva.
     *
     * @return L'identificador de la reserva.
     */

    public int getIDReserva() {
        return IDReserva;
    }

    /**
     * Estableix l'identificador de la reserva.
     *
     * @param IDReserva L'identificador de la reserva.
     */

    public void setIDReserva(int IDReserva) {
        this.IDReserva = IDReserva;
    }

    /**
     * Obté l'identificador de l'usuari.
     *
     * @return L'identificador de l'usuari.
     */
    public int getIDUsuari() {
        return IDUsuari;
    }

    /**
     * Estableix l'identificador de l'usuari.
     *
     * @param IDUsuari L'identificador de l'usuari.
     */
    public void setIDUsuari(int IDUsuari) {
        this.IDUsuari = IDUsuari;
    }

}