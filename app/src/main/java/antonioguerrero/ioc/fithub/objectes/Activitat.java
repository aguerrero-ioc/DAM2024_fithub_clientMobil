package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe que representa una activitat que es realitza en una instal·lació.
 * <p>
 * Cada activitat té una identificació única, un nom, una descripció, un aforament i una instal·lació on es realitza.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Activitat implements Serializable {

    private int id;
    private String nom;
    private String descripcio;
    private int aforament;

    private String tipusInstallacio;

    // Altres atributs que podrien ser necessaris

    private int dia;
    private int horaInici;

    /**
     * Constructor de la classe Activitat.
     *
     * @param id Identificador de l'activitat
     * @param nom Nom de l'activitat
     * @param descripcio Descripció de l'activitat
     * @param aforament Aforament de l'activitat
     * @param tipusInstallacio Tipus de la instal·lació on es realitza l'activitat
     */

    public Activitat(int id, String nom, String descripcio, int aforament, String tipusInstallacio) {
        this.id = id;
        this.nom = nom;
        this.descripcio = descripcio;
        this.aforament = aforament;
        this.tipusInstallacio = tipusInstallacio;
    }

    public static Activitat deHashMap(HashMap<String, String> activitatMap) {
        return new Activitat(
                Integer.parseInt(activitatMap.get("id")),
                activitatMap.get("nom"),
                activitatMap.get("descripcio"),
                Integer.parseInt(activitatMap.get("aforament")),
                activitatMap.get("tipusInstallacio")
        );
    }

    // Getters i setters

    /**
     * Obté l'identificador de l'activitat.
     *
     * @return Identificador de l'activitat
     */
    public int getId() {
        return id;
    }

    /**
     * Estableix l'identificador de l'activitat.
     *
     * @param id Identificador de l'activitat
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obté el nom de l'activitat.
     *
     * @return El nom de l'activitat
     */
    public String getNom() {
        return nom;
    }

    /**
     * Estableix el nom de l'activitat.
     *
     * @param nom El nou nom de l'activitat
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obté la descripció de l'activitat.
     *
     * @return La descripció de l'activitat
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Estableix la descripció de l'activitat.
     *
     * @param descripcio La nova descripció de l'activitat
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Obté l'aforament de l'activitat.
     *
     * @return L'aforament de l'activitat
     */
    public int getAforament() {
        return aforament;
    }

    /**
     * Estableix l'aforament de l'activitat.
     *
     * @param aforament El nou aforament de l'activitat
     */
    public void setAforament(int aforament) {
        this.aforament = aforament;
    }

    /**
     * Obté el tipus d'instal·lació on es realitza l'activitat.
     *
     * @return El tipus d'instal·lació on es realitza l'activitat
     */
    public String getTipusInstallacio() {
        return tipusInstallacio;
    }

    /**
     * Estableix el tipus d'instal·lació on es realitza l'activitat.
     *
     * @param tipusInstallacio El nou tipus d'instal·lació on es realitza l'activitat
     */
    public void setTipusInstallacio(String tipusInstallacio) {
        this.tipusInstallacio = tipusInstallacio;}

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getHoraInici() {
        return horaInici;
    }

    public void setHora(int hora) {
        this.horaInici = hora;
    }

}
