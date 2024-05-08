package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;

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
    private int IDactivitat;
    private String nomActivitat;
    private String descripcioActivitat;
    private int aforamentActivitat;
    private int tipusInstallacio;

    /**
     * Constructor de la classe Activitat buit.
     */
    public Activitat() {
    }

    /**
     * Constructor de la classe Activitat amb paràmetres.
     * @param nomActivitat El nom de l'activitat
     * @param descripcioActivitat La descripció de l'activitat
     * @param aforamentActivitat L'aforament de l'activitat
     * @param tipusInstallacio El tipus d'instal·lació on es realitza l'activitat
     */
    public Activitat(String nomActivitat, String descripcioActivitat, int aforamentActivitat, int tipusInstallacio) {
        this.nomActivitat = nomActivitat;
        this.descripcioActivitat = descripcioActivitat;
        this.aforamentActivitat = aforamentActivitat;
        this.tipusInstallacio = tipusInstallacio;
    }

    // Getters i setters

    /**
     * Obté l'identificador de l'activitat.
     * <p>
     * @return Identificador de l'activitat
     */
    public int getIDactivitat() {
        return IDactivitat;
    }

    /**
     * Estableix l'identificador de l'activitat.
     * <p>
     * @param IDactivitat Identificador de l'activitat
     */
    public void setIDactivitat(int IDactivitat) {
        this.IDactivitat = IDactivitat;
    }

    /**
     * Obté el nom de l'activitat.
     * <p>
     * @return El nom de l'activitat
     */
    public String getNomActivitat() {
        return nomActivitat;
    }

    /**
     * Estableix el nom de l'activitat.
     * <p>
     * @param nomActivitat El nou nom de l'activitat
     */
    public void setNomActivitat(String nomActivitat) {
        this.nomActivitat = nomActivitat;
    }

    /**
     * Obté la descripció de l'activitat.
     * <p>
     * @return La descripció de l'activitat
     */
    public String getDescripcioActivitat() {
        return descripcioActivitat;
    }

    /**
     * Estableix la descripció de l'activitat.
     * <p>
     * @param descripcioActivitat La nova descripció de l'activitat
     */
    public void setDescripcioActivitat(String descripcioActivitat) {
        this.descripcioActivitat = descripcioActivitat;
    }

    /**
     * Obté l'aforament de l'activitat.
     * <p>
     * @return L'aforament de l'activitat
     */
    public int getAforamentActivitat() {
        return aforamentActivitat;
    }

    /**
     * Estableix l'aforament de l'activitat.
     * <p>
     * @param aforamentActivitat El nou aforament de l'activitat
     */
    public void setAforamentActivitat(int aforamentActivitat) {
        this.aforamentActivitat = aforamentActivitat;
    }

    /**
     * Obté el tipus d'instal·lació on es realitza l'activitat.
     * <p>
     * @return El tipus d'instal·lació on es realitza l'activitat
     */
    public int getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus d'instal·lació on es realitza l'activitat.
     * <p>
     * @param tipusInstallacio El nou tipus d'instal·lació on es realitza l'activitat
     */
    public void setTipusInstallacio(int tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;}

    /**
     * Mètode per convertir una activitat en un HashMap.
     * <p>
     * @param activitat L'activitat a convertir
     * @return Un HashMap amb les dades de l'activitat
     */
    public HashMap<String, String> activitat_a_hashmap(Activitat activitat) {
        HashMap<String, String> mapaActivitat = new HashMap<>();
        mapaActivitat.put(Constants.OBJTYPE,Constants.OBJ_ACT);
        mapaActivitat.put(Constants.ACT_ID,Integer.toString(activitat.getIDactivitat()));
        mapaActivitat.put(Constants.ACT_NOM,activitat.getNomActivitat());
        mapaActivitat.put(Constants.ACT_DESC, activitat.getDescripcioActivitat());
        mapaActivitat.put(Constants.ACT_TIPUS,Integer.toString(activitat.getTipusInstallacio()));
        mapaActivitat.put(Constants.ACT_AFORAMENT,Integer.toString(activitat.getAforamentActivitat()));
        return mapaActivitat;
    }

    /**
     * Mètode per convertir un HashMap en una activitat.
     * <p>
     * @param mapaActivitat El HashMap a convertir
     * @return L'activitat amb les dades del HashMap
     */
    public static Activitat hashmap_a_activitat(HashMap<String, String> mapaActivitat) {
        Activitat activitat = new Activitat();
        activitat.setIDactivitat(Integer.parseInt(mapaActivitat.get(Constants.ACT_ID)));
        activitat.setNomActivitat(mapaActivitat.get(Constants.ACT_NOM));
        activitat.setDescripcioActivitat(mapaActivitat.get(Constants.ACT_DESC));
        activitat.setTipusInstallacio(Integer.parseInt(mapaActivitat.get(Constants.ACT_TIPUS)));
        activitat.setAforamentActivitat(Integer.parseInt(mapaActivitat.get(Constants.ACT_AFORAMENT)));
        return activitat;
    }
}