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

    private ClasseDirigida classeDirigida;

    private Usuari usuari;


    /**
     * Constructor de la classe Reserva reduït.
     */
    public Reserva() {
    }


    // Getters y setters

    public ClasseDirigida getClasseDirigida() {
        return classeDirigida;
    }

    public void setClasseDirigida(ClasseDirigida classeDirigida) {
        this.classeDirigida = classeDirigida;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Mètode per convertir una reserva a un HashMap.
     *
     * @param reserva La reserva que es vol convertir.
     * @return HashMap amb les dades de la reserva.
     */
    public HashMap<String, String> reserva_a_hashmap(Reserva reserva) {
        HashMap<String, String> mapaReserva = new HashMap<>();

        // Obtener los datos de la clase dirigida y el usuario directamente de las instancias
        mapaReserva.put(Constants.CLASSE_ID, String.valueOf(reserva.getClasseDirigida().getIDclasseDirigida()));
        mapaReserva.put(Constants.ID_USUARI, String.valueOf(reserva.getUsuari().getIDusuari()));

        return mapaReserva;
    }

    /**
     * Mètode per convertir un HashMap a una reserva.
     *
     * @param mapaReserva El HashMap que es vol convertir.
     * @return La reserva amb les dades del HashMap.
     */
    public Reserva hashmap_a_reserva(HashMap<String, String> mapaReserva) {
        Reserva reserva = new Reserva();

        // Crear una nueva instancia de ClasseDirigida y asignarle los datos del mapa
        ClasseDirigida classeDirigida = new ClasseDirigida();
        classeDirigida.setIDclasseDirigida(Integer.parseInt(mapaReserva.get(Constants.CLASSE_ID)));

        // Crear una nueva instancia de Usuari y asignarle los datos del mapa
        Usuari usuari = new Usuari();
        usuari.setIDusuari(Integer.parseInt(mapaReserva.get(Constants.ID_USUARI)));

        // Asignar las instancias de ClasseDirigida y Usuari a la Reserva
        reserva.setClasseDirigida(classeDirigida);
        reserva.setUsuari(usuari);

        return reserva;
    }

}