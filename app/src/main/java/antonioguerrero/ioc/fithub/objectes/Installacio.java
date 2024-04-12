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
    private int IDInstallacio;
    private String nomInstallacio;
    private String descripcioInstallacio;
    private int tipusInstallacio;

    // ALTRES (PENDENT)
    private String imatge;
    private int capacitat;
    private boolean disponible;


    /**
     * Constructor de la classe Installacio.
     *
     * @param IDInstallacio Identificador de la instal·lació
     * @param nomInstallacio Nom de la instal·lació
     * @param descripcioInstallacio Descripció de la instal·lació
     * @param tipusInstallacio Tipus de la instal·lació
     */
    public Installacio(int IDInstallacio, String nomInstallacio, String descripcioInstallacio, int tipusInstallacio) {
        this.IDInstallacio = IDInstallacio;
        this.nomInstallacio = nomInstallacio;
        this.descripcioInstallacio = descripcioInstallacio;
        this.tipusInstallacio = tipusInstallacio;
    }

    // Getters i setters

    /**
     * Obté l'identificador de la instal·lació.
     *
     * @return L'identificador de la instal·lació
     */
    public int getIDInstallacio() {
        return IDInstallacio;
    }

    /**
     * Estableix l'identificador de la instal·lació.
     *
     * @param IDInstallacio L'identificador de la instal·lació a establir
     */
    public void setIDInstallacio(int IDInstallacio) {
        this.IDInstallacio = IDInstallacio;
    }

    /**
     * Obté el nom de la instal·lació.
     *
     * @return El nom de la instal·lació
     */
    public String getNomInstallacio() {
        return nomInstallacio;
    }

    /**
     * Estableix el nom de la instal·lació.
     *
     * @param nomInstallacio El nom de la instal·lació
     */
    public void setNom(String nomInstallacio) {
        this.nomInstallacio = nomInstallacio;
    }


    /**
     * Obté la descripció de la instal·lació.
     *
     * @return La descripció de la instal·lació
     */
    public String getDescripcioInstallacio() {
        return descripcioInstallacio;
    }

    /**
     * Estableix la descripció de la instal·lació.
     *
     * @param descripcioInstallacio La descripció de la instal·lació
     */
    public void setDescripcioInstallacio(String descripcioInstallacio) {
        this.descripcioInstallacio = descripcioInstallacio;
    }


    /**
     * Obté el tipus de la instal·lació.
     *
     * @return El tipus de la instal·lació
     */
    public int getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus de la instal·lació.
     *
     * @param tipusInstallacio El tipus de la instal·lació
     */
    public void setTipus(int tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;
    }


    // ALTRES Getters i setters PENDENT

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

}