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
    private String IDclasseDirigida;
    private String IDusuari;


    /**
     * Constructor de la classe Reserva reduït.
     */
    public Reserva(String IDclasseDirigida, String IDusuari) {
        this.IDclasseDirigida = IDclasseDirigida;
        this.IDusuari = IDusuari;
    }

    // Getters y setters
    public String getIDclasseDirigida() {
        return IDclasseDirigida;
    }

    public void setIDclasseDirigida(String IDclasseDirigida) {
        this.IDclasseDirigida = IDclasseDirigida;
    }

    public String getIDusuari() {
        return IDusuari;
    }

    public void setIDusuari(String IDusuari) {
        this.IDusuari = IDusuari;
    }


    /**
     * Mètode per convertir una reserva a un HashMap.
     *
     * @param reserva La reserva que es vol convertir.
     * @return HashMap amb les dades de la reserva.
     */
    public HashMap<String, String> reserva_a_hashmap(Reserva reserva) {
        HashMap<String, String> mapaReserva = new HashMap<>();
        mapaReserva.put(Constants.CLASSE_ID, IDclasseDirigida);
        mapaReserva.put(Constants.ID_USUARI, IDusuari);
        return mapaReserva;
    }

    /**
     * Mètode per convertir un HashMap a una reserva.
     *
     * @param mapaReserva El HashMap que es vol convertir.
     * @return La reserva amb les dades del HashMap.
     */
    public Reserva hashmap_a_reserva(HashMap<String, String> mapaReserva) {
        String IDclasseDirigida = mapaReserva.get(Constants.CLASSE_ID);
        String IDusuari = mapaReserva.get(Constants.ID_USUARI);
        return new Reserva(IDclasseDirigida, IDusuari);
    }

}