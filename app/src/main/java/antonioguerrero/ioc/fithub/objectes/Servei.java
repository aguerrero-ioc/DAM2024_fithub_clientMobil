package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;

/**
 * Classe que representa un servei reservat en una instal·lació.
 * Hereta de la classe Reserva.
 *
 * Cada servei té un nom, una descripció, personal assignat i hereta els altres atributs de la reserva.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Servei implements Serializable {

    private String nom;
    private String descripcio;
    private String personal;
    private int preu;


    /**
     * Constructor de la classe Servei.
     *
     * @param nom       Nom del servei
     * @param descripcio Descripció del servei
     * @param personal   Personal assignat al servei
     * @param preu       Preu del servei
     */
    public Servei(String nom, String descripcio, String personal, int preu) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.personal = personal;
        this.preu = preu;

    }

    // Getters i setters

    /**
     * Obté el nom del servei.
     *
     * @return El nom del servei
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom del servei
     *
     * @param nom El nom del servei
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció del servei.
     *
     * @return La descripció del servei
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció del servei
     *
     * @param descripcio La descripció del servei
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté el personal assignat al servei.
     *
     * @return El personal assignat al servei
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * Estableix el personal assignat al servei
     *
     * @param personal El personal assignat al servei
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     * Obté el preu del servei.
     *
     * @return El preu del servei
     */
    public int getPreu() {
        return preu;
    }

    /**
     * Estableix el preu del servei
     *
     * @param preu El preu del servei
     */
    public void setPreu(int preu) {
        this.preu = preu;
    }
}

