package antonioguerrero.ioc.fithub.objectes;


import java.util.HashMap;

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
    private int IDusuari;


    /**
     * Constructor de la classe Reserva reduït.
     */
    public Reserva() {
        this.IDReserva = IDReserva;
        this.IDusuari = IDusuari;
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
    public int getIDusuari() {
        return IDusuari;
    }

    /**
     * Estableix l'identificador de l'usuari.
     *
     * @param IDusuari L'identificador de l'usuari.
     */
    public void setIDusuari(int IDusuari) {
        this.IDusuari = IDusuari;
    }

    public HashMap<String, String> reserva_a_hashmap(Reserva reserva) {
        HashMap<String, String> mapaReserva = new HashMap<>();
        mapaReserva.put("objectType","reserva");
        mapaReserva.put("id",Integer.toString(reserva.getIDReserva()));
        mapaReserva.put("idUsuari",Integer.toString(reserva.getIDusuari()));
        return mapaReserva;
    }

    public Reserva hashmap_a_reserva(HashMap<String, String> mapaReserva) {
        Reserva reserva = new Reserva();
        reserva.setIDReserva(Integer.parseInt(mapaReserva.get("id")));
        reserva.setIDusuari(Integer.parseInt(mapaReserva.get("idUsuari")));
        return reserva;
    }

}