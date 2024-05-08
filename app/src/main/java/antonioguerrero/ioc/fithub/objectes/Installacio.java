package antonioguerrero.ioc.fithub.objectes;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;

 /**
 * Classe que representa una instal·lació on es realitzen activitats, classes o serveis.
 * <p>
 * Conté informació com el seu identificador, nom, capacitat, descripció, imatge, disponibilitat i tipus.
 * Aquesta classe també conté mètodes per convertir una instal·lació a un HashMap i viceversa.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Installacio {
    private int IDinstallacio;
    private String nomInstallacio;
    private String descripcioInstallacio;
    private int tipusInstallacio;

    // ALTRES (PENDENT)
    private String imatge;
    private int capacitat;
    private boolean disponible;


    /**
     * Constructor de la classe Installacio.
     * <p>
     * @param IDinstallacio Identificador de la instal·lació
     * @param nomInstallacio Nom de la instal·lació
     * @param descripcioInstallacio Descripció de la instal·lació
     * @param tipusInstallacio Tipus de la instal·lació
     */
    public Installacio(int IDinstallacio, String nomInstallacio, String descripcioInstallacio, int tipusInstallacio) {
        this.IDinstallacio = IDinstallacio;
        this.nomInstallacio = nomInstallacio;
        this.descripcioInstallacio = descripcioInstallacio;
        this.tipusInstallacio = tipusInstallacio;
    }

    /**
     * Constructor de la classe Installacio.
     * <p>
     * @param nomInstallacio Nom de la instal·lació
     * @param descripcioInstallacio Descripció de la instal·lació
     * @param tipusInstallacio Tipus de la instal·lació
     */
    public Installacio(String nomInstallacio, String descripcioInstallacio, int tipusInstallacio) {
        this.nomInstallacio = nomInstallacio;
        this.descripcioInstallacio = descripcioInstallacio;
        this.tipusInstallacio = tipusInstallacio;
    }

    /**
     * Constructor de la classe Installacio buit.
     */
    public Installacio() {

    }

    // Getters i setters

    /**
     * Obté l'identificador de la instal·lació.
     * <p>
     * @return L'identificador de la instal·lació
     */
    public int getIDinstallacio() {
        return IDinstallacio;
    }

    /**
     * Estableix l'identificador de la instal·lació.
     * <p>
     * @param IDinstallacio L'identificador de la instal·lació a establir
     */
    public void setIDinstallacio(int IDinstallacio) {
        this.IDinstallacio = IDinstallacio;
    }

    /**
     * Obté el nom de la instal·lació.
     * <p>
     * @return El nom de la instal·lació
     */
    public String getNomInstallacio() {
        return nomInstallacio;
    }

    /**
     * Estableix el nom de la instal·lació.
     * <p>
     * @param nomInstallacio El nom de la instal·lació
     */
    public void setNomInstallacio(String nomInstallacio) {
        this.nomInstallacio = nomInstallacio;
    }


    /**
     * Obté la descripció de la instal·lació.
     * <p>
     * @return La descripció de la instal·lació
     */
    public String getDescripcioInstallacio() {
        return descripcioInstallacio;
    }

    /**
     * Estableix la descripció de la instal·lació.
     * <p>
     * @param descripcioInstallacio La descripció de la instal·lació
     */
    public void setDescripcioInstallacio(String descripcioInstallacio) {
        this.descripcioInstallacio = descripcioInstallacio;
    }


    /**
     * Obté el tipus de la instal·lació.
     * <p>
     * @return El tipus de la instal·lació
     */
    public int getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus de la instal·lació.
     * <p>
     * @param tipusInstallacio El tipus de la instal·lació
     */
    public void setTipus(int tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;
    }


    // ALTRES Getters i setters PENDENT

    /**
     * Obté la URL de la imatge de la instal·lació.
     * <p>
     * @return La URL de la imatge de la instal·lació
     */
    public String getImatge() {
        return imatge;
    }

    /**
     * Estableix la URL de la imatge de la instal·lació.
     * <p>
     * @param imatge La URL de la imatge de la instal·lació
     */
    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    /**
     * Comprova si la instal·lació està disponible.
     * <p>
     * @return Cert si la instal·lació està disponible, fals altrament
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Estableix la disponibilitat de la instal·lació.
     * <p>
     * @param disponible La disponibilitat de la instal·lació
     */
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Obté la capacitat de la instal·lació.
     * <p>
     * @return La capacitat de la instal·lació
     */
    public int getCapacitat() {
        return capacitat;
    }

    /**
     * Estableix la capacitat de la instal·lació.
     * <p>
     * @param capacitat La capacitat de la instal·lació
     */
    public void setCapacitat(int capacitat) {
        this.capacitat = capacitat;
    }


    /**
     * Mètode per convertir una instal·lació a un HashMap.
     * <p>
     * @param installacio La instal·lació a convertir
     * @return El HashMap amb les dades de la instal·lació
     */
    public static HashMap<String, String> installacio_a_hashmap(Installacio installacio) {
        HashMap<String, String> mapaInstallacio = new HashMap<>();
        mapaInstallacio.put(Constants.OBJTYPE, Constants.OBJ_INS);
        mapaInstallacio.put(Constants.INS_ID, Integer.toString(installacio.getIDinstallacio()));
        mapaInstallacio.put(Constants.INS_NOM, installacio.getNomInstallacio());
        mapaInstallacio.put(Constants.INS_DESC, installacio.getDescripcioInstallacio());
        mapaInstallacio.put(Constants.INS_TIPUS, Integer.toString(installacio.getTipusInstallacio()));
        return mapaInstallacio;
    }

    /**
     * Mètode per convertir un HashMap a una instal·lació.
     *
     * @param mapaInstallacio El HashMap amb les dades de la instal·lació
     * @return La instal·lació amb les dades del HashMap
     */
    public static Installacio hashmap_a_installacio(HashMap<String, String> mapaInstallacio) {
        Installacio installacio = new Installacio();
        installacio.setIDinstallacio(Integer.parseInt(mapaInstallacio.get(Constants.INS_ID)));
        installacio.setNomInstallacio(mapaInstallacio.get(Constants.INS_NOM));
        installacio.setDescripcioInstallacio(mapaInstallacio.get(Constants.INS_DESC));
        installacio.setTipus(Integer.parseInt(mapaInstallacio.get(Constants.INS_TIPUS)));
        return installacio;
    }
}