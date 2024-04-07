package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe Usuari que representa un usuari en l'aplicació FitHub.
 * <p>
 * Conté informació com el nom, cognoms, data de naixement, adreça, correu electrònic,
 * telèfon, contrasenya i data d'inscripció de l'usuari.
 *
 * @author Antonio Guerrero, Xavi Cano
 * @version 1.1
 */
public class Usuari implements Serializable {
    private String DEFAULT_VALUE = "";

    //Dades Obligatories
    private int usuariID;
    private String correu;
    private String contrasenya;
    private String tipus;

    //Altres dades de l'usuari
    private String nom;
    private String cognoms;
    private Date dataNaixement;
    private String adreca;
    private String telefon;
    private Date dataInscripcio;

    /**
     * Constructor de la classe Usuari reduit.
     *
     * @param correu         El correu electrònic de l'usuari.
     * @param contrasenya    La contrasenya de l'usuari.
     */
    public Usuari(String correu, String contrasenya) {
        this.correu = correu;
        this.contrasenya = contrasenya;
        this.usuariID = -1;

        this.tipus = DEFAULT_VALUE;
        this.nom = DEFAULT_VALUE;
        this.cognoms = DEFAULT_VALUE;
        this.dataNaixement = null;
        this.adreca = DEFAULT_VALUE;
        this.telefon = DEFAULT_VALUE;
        this.dataInscripcio = null;
    }

    /**
     * Constructor de la classe Usuari.
     *
     * @param correu         El correu electrònic de l'usuari.
     * @param contrasenya    La contrasenya de l'usuari.
     * @param usuariID       L'ID de l'usuari.
     * @param tipus          El tipus d'usuari.
     * @param dataInscripcio La data d'inscripció de l'usuari.
     * @param nom            El nom de l'usuari.
     * @param cognoms        Els cognoms de l'usuari.
     * @param dataNaixement  La data de naixement de l'usuari.
     * @param adreca         L'adreça de l'usuari.
     * @param telefon        El número de telèfon de l'usuari.
     */

    public Usuari(String correu, String contrasenya, Integer usuariID, String tipus, Date dataInscripcio, String nom, String cognoms, Date dataNaixement, String adreca, String telefon) {
        this.correu = correu;
        this.contrasenya = contrasenya;
        this.usuariID = -1;
        this.tipus = DEFAULT_VALUE;
        this.dataInscripcio = dataInscripcio;
        this.nom = nom;
        this.cognoms = cognoms;
        this.dataNaixement = dataNaixement;
        this.adreca = adreca;
        this.telefon = telefon;
    }

    /**
     * Constructor buit de la classe Usuari.
     */
    public Usuari() {

    }


    /**
     * Obté el nom de l'usuari.
     *
     * @return El nom de l'usuari.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de l'usuari.
     *
     * @param nom El nom de l'usuari.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté els cognoms de l'usuari.
     *
     * @return Els cognoms de l'usuari.
     */
    public String getCognoms() {
        return cognoms;
    }

    /**
     * Estableix els cognoms de l'usuari.
     *
     * @param cognoms Els cognoms de l'usuari.
     */
    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    /**
     * Obté la data de naixement de l'usuari.
     *
     * @return La data de naixement de l'usuari.
     */
    public Date getDataNaixement() {
        return dataNaixement;
    }

    /**
     * Estableix la data de naixement de l'usuari.
     *
     * @param dataNaixement La data de naixement de l'usuari.
     */
    public void setDataNaixement(Date dataNaixement) {
        this.dataNaixement = dataNaixement;
    }

    /**
     * Obté l'adreça de l'usuari.
     *
     * @return L'adreça de l'usuari.
     */
    public String getAdreca() {
        return adreca;
    }

    /**
     * Estableix l'adreça de l'usuari.
     *
     * @param adreca L'adreça de l'usuari.
     */
    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    /**
     * Obté el número de telèfon de l'usuari.
     *
     * @return El número de telèfon de l'usuari.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Estableix el número de telèfon de l'usuari.
     *
     * @param telefon El número de telèfon de l'usuari.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Obté el correu electrònic de l'usuari.
     *
     * @return El correu electrònic de l'usuari.
     */
    public String getCorreu() {
        return correu;
    }

    /**
     * Estableix el correu electrònic de l'usuari.
     *
     * @param correu El correu electrònic de l'usuari.
     */
    public void setCorreu(String correu) {
        this.correu = correu;
    }

    /**
     * Obté la contrasenya de l'usuari.
     *
     * @return La contrasenya de l'usuari.
     */
    public String getContrasenya() {
        return contrasenya;
    }

    /**
     * Estableix la contrasenya de l'usuari.
     *
     * @param contrasenya La contrasenya de l'usuari.
     */
    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    /**
     * Obté la data d'inscripció de l'usuari.
     *
     * @return La data d'inscripció de l'usuari.
     */
    public Date getDataInscripcio() {
        return dataInscripcio;
    }

    /**
     * Estableix la data d'inscripció de l'usuari.
     *
     * @param dataInscripcio La data d'inscripció de l'usuari.
     */
    public void setDataInscripcio(Date dataInscripcio) {
        this.dataInscripcio = dataInscripcio;
    }

    /**
     * Obté l'ID de l'usuari.
     *
     * @return L'ID de l'usuari.
     */
    public int getUsuariID() {
        return usuariID;
    }

    /**
     * Estableix l'ID de l'usuari.
     *
     * @param usuariID L'ID de l'usuari.
     */
    public void setUsuariID(int usuariID) {
        this.usuariID = usuariID;
    }

    /**
     * Obté el tipus de l'usuari.
     *
     * @return El tipus de l'usuari.
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Estableix el tipus de l'usuari.
     *
     * @param tipus El tipus de l'usuari.
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}
