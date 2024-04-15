package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe que representa un servei reservat en una instal·lació.
 * Hereta de la classe Reserva.
 * <p>
 * Cada servei té un nom, una descripció, personal assignat i hereta els altres atributs de la reserva.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Servei implements Serializable {

    // Atributs de la classe
    private int IDServei;
    private String nomServei;
    private String descripcioServei;
    private int aforamentServei;

    private String tipusInstallacio;

    // Altres atributs que podrien ser necessaris
    private String personalServei;
    private int preuServei;


    /**
     * Constructor de la classe Servei.
     *
     * @param IDServei Identificador del servei
     * @param nomServei Nom del servei
     * @param descripcioServei Descripció del servei
     * @param aforamentServei Aforament del servei
     * @param tipusInstallacio Tipus de la instal·lació on es realitza el servei
     */

    public Servei(int IDServei, String nomServei, String descripcioServei, int aforamentServei, String tipusInstallacio) {
        this.IDServei = IDServei;
        this.nomServei = nomServei;
        this.descripcioServei = descripcioServei;
        this.aforamentServei = aforamentServei;
        this.tipusInstallacio = tipusInstallacio;
    }

    public Servei() {

    }


    // Getters i setters

    /**
     * Obté l'identificador del servei.
     *
     * @return Identificador del servei
     */

    public int getIDServei() {
        return IDServei;
    }

    /**
     * Estableix l'identificador del servei
     *
     * @param IDServei Identificador del servei
     */

    public void setIDServei(int IDServei) {
        this.IDServei = IDServei;
    }


    /**
     * Obté el nom del servei.
     *
     * @return El nom del servei
     */

    public String getNomServei() {
        return nomServei;
    }

    /**
     * Estableix el nom del servei
     *
     * @param nomServei El nom del servei
     */

    public void setNomServei(String nomServei) {
        this.nomServei = nomServei;
    }

    /**
     * Obté la descripció del servei.
     *
     * @return La descripció del servei
     */

    public String getDescripcioServei() {
        return descripcioServei;
    }

    /**
     * Estableix la descripció del servei
     *
     * @param descripcioServei La descripció del servei
     */

    public void setDescripcioServei(String descripcioServei) {
        this.descripcioServei = descripcioServei;
    }

/**
     * Obté l'aforament del servei.
     *
     * @return L'aforament del servei
     */

    public int getAforamentServei() {
        return aforamentServei;
    }

    /**
     * Estableix l'aforament del servei
     *
     * @param aforamentServei L'aforament del servei
     */

    public void setAforamentServei(int aforamentServei) {
        this.aforamentServei = aforamentServei;
    }

    /**
     * Obté el tipus de la instal·lació on es realitza el servei.
     *
     * @return El tipus de la instal·lació
     */

    public String getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus de la instal·lació on es realitza el servei
     *
     * @param tipusInstallacio El tipus de la instal·lació
     */

    public void setTipusInstallacio(String tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;
    }

    /**
     * Obté el personal assignat al servei.
     *
     * @return El personal assignat al servei
     */

    public String getPersonalServei() {
        return personalServei;
    }

    /**
     * Estableix el personal assignat al servei
     *
     * @param personalServei El personal assignat al servei
     */

    public void setPersonalServei(String personalServei) {
        this.personalServei = personalServei;
    }

    /**
     * Obté el preu del servei.
     *
     * @return El preu del servei
     */

    public int getPreuServei() {
        return preuServei;
    }

    /**
     * Estableix el preu del servei
     *
     * @param preuServei El preu del servei
     */

    public void setPreuServei(int preuServei) {
        this.preuServei = preuServei;
    }

    public HashMap<String, String> servei_a_hashmap(Servei servei) {
        HashMap<String, String> mapaServei = new HashMap<>();
        mapaServei.put("objectType","servei");
        mapaServei.put("id",Integer.toString(servei.getIDServei()));
        mapaServei.put("nomServei",servei.getNomServei());
        mapaServei.put("descripcioServei", servei.getDescripcioServei());
        mapaServei.put("aforamentServei",Integer.toString(servei.getAforamentServei()));
        mapaServei.put("tipusInstallacio",servei.getTipusInstallacio());
        mapaServei.put("personalServei",servei.getPersonalServei());
        mapaServei.put("preuServei",Integer.toString(servei.getPreuServei()));
        return mapaServei;
    }

    public Servei hashmap_a_servei(HashMap<String, String> mapaServei) {
        Servei servei = new Servei();
        servei.setIDServei(Integer.parseInt(mapaServei.get("id")));
        servei.setNomServei(mapaServei.get("nomServei"));
        servei.setDescripcioServei(mapaServei.get("descripcioServei"));
        servei.setAforamentServei(Integer.parseInt(mapaServei.get("aforamentServei")));
        servei.setTipusInstallacio(mapaServei.get("tipusInstallacio"));
        servei.setPersonalServei(mapaServei.get("personalServei"));
        servei.setPreuServei(Integer.parseInt(mapaServei.get("preuServei")));
        return servei;
    }

}