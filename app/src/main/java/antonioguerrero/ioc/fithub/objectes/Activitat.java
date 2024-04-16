package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe que representa una activitat que es realitza en una instal·lació.
 * <p>
 * Cada activitat té una identificació única, un nom, una descripció, un aforament i una instal·lació on es realitza.
 * Aquesta classe també conté mètodes per convertir una activitat a un HashMap i viceversa.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Activitat implements Serializable {

    // Atributs de la classe

    private int IDActivitat;
    private String nomActivitat;
    private String descripcioActivitat;
    private int aforamentActivitat;

    private int tipusInstallacio;

    // Altres atributs que podrien ser necessaris
    private int dia;
    private int horaInici;

    /**
     * Constructor de la classe Activitat.
     */

    public Activitat() {
        this.IDActivitat = IDActivitat;
        this.nomActivitat = nomActivitat;
        this.descripcioActivitat = descripcioActivitat;
        this.aforamentActivitat = aforamentActivitat;
        this.tipusInstallacio = tipusInstallacio;
    }


    // Getters i setters

    /**
     * Obté l'identificador de l'activitat.
     *
     * @return Identificador de l'activitat
     */
    public int getIDActivitat() {
        return IDActivitat;
    }

    /**
     * Estableix l'identificador de l'activitat.
     *
     * @param IDActivitat Identificador de l'activitat
     */
    public void setIDActivitat(int IDActivitat) {
        this.IDActivitat = IDActivitat;
    }

    /**
     * Obté el nom de l'activitat.
     *
     * @return El nom de l'activitat
     */
    public String getNomActivitat() {
        return nomActivitat;
    }

    /**
     * Estableix el nom de l'activitat.
     *
     * @param nomActivitat El nou nom de l'activitat
     */
    public void setNomActivitat(String nomActivitat) {
        this.nomActivitat = nomActivitat;
    }

    /**
     * Obté la descripció de l'activitat.
     *
     * @return La descripció de l'activitat
     */
    public String getDescripcioActivitat() {
        return descripcioActivitat;
    }

    /**
     * Estableix la descripció de l'activitat.
     *
     * @param descripcioActivitat La nova descripció de l'activitat
     */
    public void setDescripcioActivitat(String descripcioActivitat) {
        this.descripcioActivitat = descripcioActivitat;
    }

    /**
     * Obté l'aforament de l'activitat.
     *
     * @return L'aforament de l'activitat
     */
    public int getAforamentActivitat() {
        return aforamentActivitat;
    }

    /**
     * Estableix l'aforament de l'activitat.
     *
     * @param aforamentActivitat El nou aforament de l'activitat
     */
    public void setAforamentActivitat(int aforamentActivitat) {
        this.aforamentActivitat = aforamentActivitat;
    }

    /**
     * Obté el tipus d'instal·lació on es realitza l'activitat.
     *
     * @return El tipus d'instal·lació on es realitza l'activitat
     */
    public int getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus d'instal·lació on es realitza l'activitat.
     *
     * @param tipusInstallacio El nou tipus d'instal·lació on es realitza l'activitat
     */
    public void setTipusInstallacio(int tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;}



    /**
     * Converteix una activitat en un HashMap.
     *
     * @param activitat L'activitat a convertir
     * @return Un HashMap amb les dades de l'activitat
     */
    public HashMap<String, String> activitat_a_hashmap(Activitat activitat) {
        HashMap<String, String> mapaActivitat = new HashMap<>();
        mapaActivitat.put("objectType","activitat");
        mapaActivitat.put("IDactivitat",Integer.toString(activitat.getIDActivitat()));
        mapaActivitat.put("nomActivitat",activitat.getNomActivitat());
        mapaActivitat.put("descripcioActivitat", activitat.getDescripcioActivitat());
        mapaActivitat.put("aforamentActivitat",Integer.toString(activitat.getAforamentActivitat()));
        mapaActivitat.put("tipusActivitat",Integer.toString(activitat.getTipusInstallacio()));
        return mapaActivitat;
    }

    /**
     * Converteix un HashMap en una activitat.
     *
     * @param mapaActivitat El HashMap a convertir
     * @return L'activitat amb les dades del HashMap
     */
    public static Activitat hashmap_a_activitat(HashMap<String, String> mapaActivitat) {
        Activitat activitat = new Activitat();
        activitat.setIDActivitat(Integer.parseInt(mapaActivitat.get("IDactivitat")));
        activitat.setNomActivitat(mapaActivitat.get("nomActivitat"));
        activitat.setDescripcioActivitat(mapaActivitat.get("descripcioActivitat"));;
        activitat.setAforamentActivitat(Integer.parseInt(mapaActivitat.get("aforamentActivitat")));
        activitat.setTipusInstallacio(Integer.parseInt(mapaActivitat.get("tipusActivitat")));
        return activitat;
    }
}
