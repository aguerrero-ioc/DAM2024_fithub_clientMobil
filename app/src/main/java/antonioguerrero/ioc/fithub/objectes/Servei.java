package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;

/**
 * Classe que representa un servei reservat en una instal·lació.
 * <p>
 * Cada servei té un nom, una descripció, personal assignat i hereta els altres atributs de la reserva.
 * Aquesta classe també conté mètodes per convertir un servei a un HashMap i viceversa.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Servei implements Serializable {

    // Atributs de la classe
    private int IDServei;
    private String nomServei;
    private String descripcioServei;
    private String preuServei;


    // Altres atributs que podrien ser necessaris
    private String personalServei;

    /**
     * Constructor de la classe Servei.
     *
     * @param IDServei Identificador del servei
     * @param nomServei Nom del servei
     * @param descripcioServei Descripció del servei
     * @param preuServei Preu del servei
     */

    public Servei(int IDServei, String nomServei, String descripcioServei, String preuServei) {
        this.IDServei = IDServei;
        this.nomServei = nomServei;
        this.descripcioServei = descripcioServei;
        this.preuServei = preuServei;
    }


    /**
     * Constructor de la classe Servei amb paràmetres.
     *
     * @param nomServei Nom del servei
     * @param descripcioServei Descripció del servei
     * @param preuServei Preu del servei
     */
    public Servei(String nomServei, String descripcioServei, String preuServei) {
        this.nomServei = nomServei;
        this.descripcioServei = descripcioServei;
        this.preuServei = preuServei;
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
     * Obté el preu del servei.
     *
     * @return El preu del servei
     */

    public String getPreuServei() {
        return preuServei;
    }

    /**
     * Estableix el preu del servei
     *
     * @param preuServei El preu del servei
     */

    public void setPreuServei(String preuServei) {
        this.preuServei = preuServei;
    }

    /**
     * Converteix un servei a un HashMap.
     *
     * @param servei El servei a convertir
     * @return El HashMap resultant
     */
    public HashMap<String, String> servei_a_hashmap(Servei servei) {
        HashMap<String, String> mapaServei = new HashMap<>();
        mapaServei.put(Constants.OBJTYPE,"servei");
        mapaServei.put("IDservei",Integer.toString(servei.getIDServei()));
        mapaServei.put("nomServei",servei.getNomServei());
        mapaServei.put("descripcioServei", servei.getDescripcioServei());
        mapaServei.put("preuServei",servei.getPreuServei());
        return mapaServei;
    }

    /**
     * Converteix un HashMap a un servei.
     *
     * @param mapaServei El HashMap a convertir
     * @return El servei resultant
     */
    public Servei hashmap_a_servei(HashMap<String, String> mapaServei) {
        Servei servei = new Servei(nomServei, descripcioServei, preuServei);
        servei.setIDServei(Integer.parseInt(mapaServei.get("IDservei")));
        servei.setNomServei(mapaServei.get("nomServei"));
        servei.setDescripcioServei(mapaServei.get("descripcioServei"));
        servei.setPreuServei(mapaServei.get("preuServei"));
        return servei;
    }

}