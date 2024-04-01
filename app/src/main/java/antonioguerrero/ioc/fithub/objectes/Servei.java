package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa un servei reservat en una instal·lació.
 * Hereta de la classe Reserva.
 *
 * Cada servei té un nom, una descripció, personal assignat i hereta els altres atributs de la reserva.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Servei extends Reserva {

    private String nom;
    private String descripcio;
    private String personal;

    /**
     * Constructor de la classe Servei.
     *
     * @param id Identificador del servei
     * @param usuari Usuari que realitza la reserva del servei
     * @param data Data de la reserva del servei
     * @param hora Hora de la reserva del servei
     * @param durada Durada de la reserva del servei
     * @param nombrePersones Nombre de persones per a la reserva del servei
     * @param preu Preu de la reserva del servei
     * @param estat Estat de la reserva del servei
     * @param nom Nom del servei
     * @param descripcio Descripció del servei
     * @param personal Personal assignat al servei
     */
    public Servei(int id, Usuari usuari, Installacio installacio, String data, String hora, int durada, int nombrePersones, float preu, String estat, String nom, String descripcio, String tipus, String personal) {
        super(id, usuari, installacio, data, hora, durada, nombrePersones, preu, estat);
        this.nom = nom;
        this.descripcio = descripcio;
        this.personal = personal;
    }

    // Getters i setters

    /**
     * Obté el nom del servei.
     * @return El nom del servei.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom del servei.
     * @param nom El nou nom del servei.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció del servei.
     * @return La descripció del servei.
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció del servei.
     * @param descripcio La nova descripció del servei.
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté el personal assignat al servei.
     * @return El personal assignat al servei.
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * Estableix el personal assignat al servei.
     * @param personal El nou personal assignat al servei.
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

}
