package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;

/**
 * Classe que representa una classe dirigida que es realitza en una instal·lació.
 * <p>
 * Cada classe dirigida té una identificació única, una activitat, una instal·lació on es realitza,
 * un dia, una hora d'inici i una duració.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClasseDirigida implements Serializable {

    // Atributs de la classe
    private int IDclasseDirigida;
    private Activitat activitat;
    private Installacio installacio;
    private String data;
    private String horaInici;
    private String duracio;

    /**
     * Constructor de la classe ClasseDirigida.
     */
    public ClasseDirigida() {
    }

    // Getters y setters

    /**
     * Obté l'identificador de la classe dirigida.
     * <p>
     * @return Identificador de la classe dirigida
     */
    public int getIDclasseDirigida() {
        return IDclasseDirigida;
    }

    /**
     * Estableix l'identificador de la classe dirigida.
     * <p>
     * @param IDclasseDirigida Identificador de la classe dirigida
     */
    public void setIDclasseDirigida(int IDclasseDirigida) {
        this.IDclasseDirigida = IDclasseDirigida;
    }

    /**
     * Obté l'activitat de la classe dirigida.
     * <p>
     * @return Activitat de la classe dirigida
     */
    public Activitat getActivitat() {
        return activitat;
    }
    
    /**
     * Estableix l'activitat de la classe dirigida.
     * <p>
     * @param activitat Activitat de la classe dirigida
     */
    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }
    
    /**
     * Obté el nom de l'activitat de la classe dirigida.
     * <p>
     * @return Nom de l'activitat de la classe dirigida
     */
    public String getNomActivitat() {
        return activitat.getNomActivitat();
    }

    /**
     * Estableix el nom de l'activitat de la classe dirigida.
     * <p>
     * @param nomActivitat Nom de l'activitat de la classe dirigida
     */
    public void setNomActivitat(String nomActivitat) {
    }

    /**
     * Obté la instal·lació on es realitza la classe dirigida.
     * <p>
     * @return Instal·lació on es realitza la classe dirigida
     */
    public Installacio getInstallacio() {
        return installacio;
    }

    /**
     * Estableix la instal·lació on es realitza la classe dirigida.
     * <p>
     * @param installacio Instal·lació on es realitza la classe dirigida
     */
    public void setInstallacio(Installacio installacio) {
        this.installacio = installacio;
    }

    /**
     * Obté el nom de la instal·lació on es realitza la classe dirigida.
     * <p>
     * @return Nom de la instal·lació on es realitza la classe dirigida
     */
    public String getNomInstallacio() {
        return installacio.getNomInstallacio();
    }

    /**
     * Estableix el nom de la instal·lació on es realitza la classe dirigida.
     * <p>
     * @param nomInstallacio Nom de la instal·lació on es realitza la classe dirigida
     */
    public void setNomInstallacio(String nomInstallacio) {
    }

     /**
     * Obté el dia de la classe dirigida.
     * <p>
     * @return Dia de la classe dirigida
     */
    public String getData() {
        return data;
    }

    /**
     * Estableix el dia de la classe dirigida.
     * <p>
     * @param data Dia de la classe dirigida
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obté l'hora d'inici de la classe dirigida.
     * <p>
     * @return Hora d'inici de la classe dirigida
     */
    public String getHoraInici() {
        return horaInici;
    }

    /**
     * Estableix l'hora d'inici de la classe dirigida.
     * <p>
     * @param horaInici Hora d'inici de la classe dirigida
     */
    public void setHoraInici(String horaInici) {
        this.horaInici = horaInici;
    }

    /**
     * Obté la duració de la classe dirigida.
     * <p>
     * @return Duració de la classe dirigida
     */
    public String getDuracio() {
        return duracio;
    }

    /**
     * Estableix la duració de la classe dirigida.
     * <p>
     * @param duracio Duració de la classe dirigida
     */
    public void setDuracio(String duracio) {
        this.duracio = duracio;
    }
}