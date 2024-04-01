package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa una instal·lació on es realitzen activitats, classes o serveis.
 *
 * Conté informació com el seu identificador, nom, capacitat, descripció, imatge, disponibilitat i tipus.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Installacio {
    private int id;
    private String nom;
    private int capacitat;
    private String descripcio;
    private String imatge;
    private boolean disponible;
    private String tipus;

    /**
     * Constructor de la classe Installacio.
     *
     * @param id Identificador de la instal·lació
     * @param nom Nom de la instal·lació
     * @param capacitat Capacitat de la instal·lació
     * @param descripcio Descripció de la instal·lació
     * @param imatge URL de la imatge de la instal·lació
     * @param disponible Disponibilitat de la instal·lació
     * @param tipus Tipus de la instal·lació
     */
    public Installacio(int id, String nom, int capacitat, String descripcio, String imatge, boolean disponible, String tipus) {
        this.id = id;
        this.nom = nom;
        this.capacitat = capacitat;
        this.descripcio = descripcio;
        this.imatge = imatge;
        this.disponible = disponible;
        this.tipus = tipus;
    }

    // Getters i setters

    /**
     * Obté l'identificador de la instal·lació.
     *
     * @return L'identificador de la instal·lació
     */
    public int getId() {
        return id;
    }

    /**
     * Estableix l'identificador de la instal·lació.
     *
     * @param id L'identificador de la instal·lació a establir
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obté el nom de la instal·lació.
     *
     * @return El nom de la instal·lació
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de la instal·lació.
     *
     * @param nom El nom de la instal·lació
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la capacitat de la instal·lació.
     *
     * @return La capacitat de la instal·lació
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * Estableix la capacitat de la instal·lació.
     *
     * @param capacitat La capacitat de la instal·lació
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }

    /**
     * Obté la descripció de la instal·lació.
     *
     * @return La descripció de la instal·lació
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció de la instal·lació.
     *
     * @param descripcio La descripció de la instal·lació
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté la URL de la imatge de la instal·lació.
     *
     * @return La URL de la imatge de la instal·lació
     */
    public String getImatge() {
        return imatge;
    }

    /**
     * Estableix la URL de la imatge de la instal·lació.
     *
     * @param imatge La URL de la imatge de la instal·lació
     */
    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    /**
     * Comprova si la instal·lació està disponible.
     *
     * @return Cert si la instal·lació està disponible, fals altrament
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Estableix la disponibilitat de la instal·lació.
     *
     * @param disponible La disponibilitat de la instal·lació
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Obté el tipus de la instal·lació.
     *
     * @return El tipus de la instal·lació
     */
    public String getTipus() {
        return tipus;
    }

    /**
     * Estableix el tipus de la instal·lació.
     *
     * @param tipus El tipus de la instal·lació
     */
    public void setTipus(String tipus) {
        this.tipus = tipus;
    }
}