package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa una classe reservada en una instal·lació.
 * Hereta de la classe Reserva.
 *
 * Cada classe té un nom, una descripció, un professor, aforament i hereta els altres atributs de la reserva.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Classe extends Reserva {

    private String nom;
    private String descripcio;
    private String professor;
    private int aforament;

    /**
     * Constructor de la classe Classe.
     *
     * @param id Identificador de la classe
     * @param usuari Usuari que realitza la reserva de la classe
     * @param data Data de la reserva de la classe
     * @param hora Hora de la reserva de la classe
     * @param durada Durada de la reserva de la classe
     * @param nombrePersones Nombre de persones per a la reserva de la classe
     * @param preu Preu de la reserva de la classe
     * @param estat Estat de la reserva de la classe
     * @param nom Nom de la classe
     * @param descripcio Descripció de la classe
     * @param professor Professor de la classe
     * @param aforament Aforament de la classe
     */
    public Classe(int id, Usuari usuari, Installacio installacio, String data, String hora, int durada, int nombrePersones, float preu, String estat, String nom, String descripcio, String professor, int aforament) {
        super(id, usuari, installacio, data, hora, durada, nombrePersones, preu, estat);
        this.nom = nom;
        this.descripcio = descripcio;
        this.professor = professor;
        this.aforament = aforament;
    }

    // Getters i setters

    /**
     * Obté el nom de la classe.
     *
     * @return El nom de la classe
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de la classe.
     *
     * @param nom El nou nom de la classe
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció de la classe.
     *
     * @return La descripció de la classe
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció de la classe.
     *
     * @param descripcio La nova descripció de la classe
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté el professor de la classe.
     *
     * @return El professor de la classe
     */
    public String getProfessor() {
        return professor;
    }

    /**
     * Estableix el professor de la classe.
     *
     * @param professor El nou professor de la classe
     */
    public void setProfessor(String professor) {
        this.professor = professor;
    }

    /**
     * Obté l'aforament de la classe.
     *
     * @return L'aforament de la classe
     */
    public int getAforament() {
        return aforament;
    }

    /**
     * Estableix l'aforament de la classe.
     *
     * @param aforament El nou aforament de la classe
     */
    public void setAforament(int aforament) {
        this.aforament = aforament;
    }
}