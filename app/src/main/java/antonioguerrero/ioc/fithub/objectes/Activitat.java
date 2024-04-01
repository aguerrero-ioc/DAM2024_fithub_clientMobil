package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa una activitat reservada en una instal·lació.
 * Hereta de la classe Reserva.
 *
 * Cada activitat té un nom, una descripció, un aforament i hereta els altres atributs de la reserva.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Activitat extends Reserva {

    private String nom;
    private String descripcio;
    private int aforament;

    /**
     * Constructor de la classe Activitat.
     *
     * @param id Identificador de l'activitat
     * @param usuari Usuari que realitza la reserva de l'activitat
     * @param data Data de la reserva de l'activitat
     * @param hora Hora de la reserva de l'activitat
     * @param durada Durada de la reserva de l'activitat
     * @param nombrePersones Nombre de persones per a la reserva de l'activitat
     * @param preu Preu de la reserva de l'activitat
     * @param estat Estat de la reserva de l'activitat
     * @param nom Nom de l'activitat
     * @param descripcio Descripció de l'activitat
     * @param aforament Aforament de l'activitat
     */
    public Activitat(int id, Usuari usuari, Installacio installacio, String data, String hora, int durada, int nombrePersones, float preu, String estat, String nom, String descripcio, int aforament) {
        super(id, usuari, installacio, data, hora, durada, nombrePersones, preu, estat);
        this.nom = nom;
        this.descripcio = descripcio;
        this.aforament = aforament;
    }

    // Getters i setters

    /**
     * Obté el nom de l'activitat.
     *
     * @return El nom de l'activitat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de l'activitat.
     *
     * @param nom El nou nom de l'activitat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció de l'activitat.
     *
     * @return La descripció de l'activitat
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció de l'activitat.
     *
     * @param descripcio La nova descripció de l'activitat
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté l'aforament de l'activitat.
     *
     * @return L'aforament de l'activitat
     */
    public int getAforament() {
        return aforament;
    }

    /**
     * Estableix l'aforament de l'activitat.
     *
     * @param aforament El nou aforament de l'activitat
     */
    public void setAforament(int aforament) {
        this.aforament = aforament;
    }
}
