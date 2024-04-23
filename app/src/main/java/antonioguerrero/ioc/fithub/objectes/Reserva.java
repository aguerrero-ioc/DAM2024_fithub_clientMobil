package antonioguerrero.ioc.fithub.objectes;


import java.util.HashMap;

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
    private int IDclasseDirigida;
    private int IDusuari;


    /**
     * Constructor de la classe Reserva reduït.
     */
    public Reserva() {
    }


    // Getters y setters

    /**
     * Obté l'identificador de la reserva.
     *
     * @return L'identificador de la reserva.
     */

    public int getIDclasseDirigida() {
        return IDclasseDirigida;
    }

    /**
     * Estableix l'identificador de la reserva.
     *
     * @param IDclasseDirigida L'identificador de la reserva.
     */

    public void setIDclasseDirigida(int IDclasseDirigida) {
        this.IDclasseDirigida = IDclasseDirigida;
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

    /**
     * Converteix una reserva a un HashMap.
     *
     * @param reserva La reserva a convertir.
     * @return El HashMap resultant.
     */
    public HashMap<String, String> reserva_a_hashmap(Reserva reserva) {
        HashMap<String, String> mapaReserva = new HashMap<>();
        mapaReserva.put("objectType","reserva");
        mapaReserva.put("IDreserva",Integer.toString(reserva.getIDclasseDirigida()));
        mapaReserva.put("IDusuari",Integer.toString(reserva.getIDusuari()));
        return mapaReserva;
    }

    /**
     * Converteix un HashMap a una reserva.
     *
     * @param mapaReserva El HashMap a convertir.
     * @return La reserva resultant.
     */
    public Reserva hashmap_a_reserva(HashMap<String, String> mapaReserva) {
        Reserva reserva = new Reserva();
        reserva.setIDclasseDirigida(Integer.parseInt(mapaReserva.get("IDclasseDirigida")));
        reserva.setIDusuari(Integer.parseInt(mapaReserva.get("IDusuari")));
        return reserva;
    }

}