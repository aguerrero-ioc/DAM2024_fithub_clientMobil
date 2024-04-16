package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Classe que representa una classe dirigida que es realitza en una instal·lació.
 * <p>
 * Cada classe dirigida té una identificació única, una activitat, una instal·lació on es realitza,
 * un dia, una hora d'inici, una duració, unes reserves actuals i un estat de reserva.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClasseDirigida implements Serializable {

    // Atributs de la classe
    private String IDClasseDirigida; // Nuevo atributo
    private Activitat activitat;
    private Installacio installacio;
    private String dia;
    private String horaInici;
    private int duracio;
    private int reservesActuals;
    private boolean estaReservat;

    /**
     * Constructor de la classe ClasseDirigida.
     */
    public ClasseDirigida() {
        this.IDClasseDirigida = IDClasseDirigida;
        this.activitat = activitat;
        this.installacio = installacio;
        this.dia = dia;
        this.horaInici = horaInici;
        this.duracio = duracio;
        this.reservesActuals = reservesActuals;
        this.estaReservat = false; // Inicialment, no reservada
    }

    // Getters y setters

    /**
     * Obté l'identificador de la classe dirigida.
     *
     * @return Identificador de la classe dirigida
     */
    public String getIDClasseDirigida() {
        return IDClasseDirigida;
    }

    /**
     * Estableix l'identificador de la classe dirigida.
     *
     * @param IDClasseDirigida Identificador de la classe dirigida
     */
    public void setIDClasseDirigida(String IDClasseDirigida) {
        this.IDClasseDirigida = IDClasseDirigida;
    }

    /**
     * Obté l'activitat de la classe dirigida.
     *
     * @return Activitat de la classe dirigida
     */
    public Activitat getActivitat() {
        return activitat;
    }
    /**
     * Estableix l'activitat de la classe dirigida.
     *
     * @param activitat Activitat de la classe dirigida
     */
    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }
    /**
     * Obté el nom de l'activitat de la classe dirigida.
     *
     * @return Nom de l'activitat de la classe dirigida
     */
    public String getNomActivitat() {
        return activitat.getNomActivitat();
    }

    /**
     * Estableix el nom de l'activitat de la classe dirigida.
     *
     * @param nomActivitat Nom de l'activitat de la classe dirigida
     */
    public void setNomActivitat(String nomActivitat) {
    }

    /**
     * Obté la instal·lació on es realitza la classe dirigida.
     *
     * @return Instal·lació on es realitza la classe dirigida
     */
    public Installacio getInstallacio() {
        return installacio;
    }

    /**
     * Estableix la instal·lació on es realitza la classe dirigida.
     *
     * @param installacio Instal·lació on es realitza la classe dirigida
     */
    public void setInstallacio(Installacio installacio) {
        this.installacio = installacio;
    }

    /**
     * Obté el nom de la instal·lació on es realitza la classe dirigida.
     *
     * @return Nom de la instal·lació on es realitza la classe dirigida
     */
    public String getNomInstallacio() {
        return installacio.getNomInstallacio();
    }

    /**
     * Estableix el nom de la instal·lació on es realitza la classe dirigida.
     *
     * @param nomInstallacio Nom de la instal·lació on es realitza la classe dirigida
     */
    public void setNomInstallacio(String nomInstallacio) {
    }

    /**
     * Obté el dia de la setmana en què es realitza la classe dirigida.
     *
     * @return Dia de la setmana en què es realitza la classe dirigida
     */
    public String getDia() {
        return dia;
    }

    /**
     * Estableix el dia de la setmana en què es realitza la classe dirigida.
     *
     * @param dia Dia de la setmana en què es realitza la classe dirigida
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * Obté l'hora d'inici de la classe dirigida.
     *
     * @return Hora d'inici de la classe dirigida
     */
    public String getHoraInici() {
        return horaInici;
    }

    /**
     * Estableix l'hora d'inici de la classe dirigida.
     *
     * @param horaInici Hora d'inici de la classe dirigida
     */
    public void setHoraInici(String horaInici) {
        this.horaInici = horaInici;
    }

    /**
     * Obté la duració de la classe dirigida.
     *
     * @return Duració de la classe dirigida
     */
    public int getDuracio() {
        return duracio;
    }

    /**
     * Estableix la duració de la classe dirigida.
     *
     * @param duracio Duració de la classe dirigida
     */
    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }

    /**
     * Obté les reserves actuals de la classe dirigida.
     *
     * @return Reserves actuals de la classe dirigida
     */
    private int getReservesActuals() {
        return reservesActuals;
    }

    /**
     * Estableix les reserves actuals de la classe dirigida.
     *
     * @param reservesActuals Reserves actuals de la classe dirigida
     */
    private void setReservesActuals(int reservesActuals) {
    }

    /**
     * Obté si la classe dirigida està reservada.
     *
     * @return Indica si la classe dirigida està reservada
     */
    public boolean estaReservat() {
        return estaReservat;
    }

    /**
     * Estableix si la classe dirigida està reservada.
     *
     * @param estaReservat Indica si la classe dirigida està reservada
     */
    public void setEstaReservat(boolean estaReservat) {
        this.estaReservat = estaReservat;
    }

    /**
     * Mètode per convertir una classe dirigida a un HashMap.
     *
     * @param classeDirigida La classe dirigida que es vol convertir.
     * @return HashMap amb les dades de la classe dirigida.
     */
    public HashMap<String, String> classeDirigida_a_hashmap(ClasseDirigida classeDirigida) {
        HashMap<String, String> mapaClasseDirigida = new HashMap<>();
        mapaClasseDirigida.put("objectType","classeDirigida");
        mapaClasseDirigida.put("IDclasseDirigida",classeDirigida.getIDClasseDirigida());
        mapaClasseDirigida.put("nomActivitat",classeDirigida.getNomActivitat());
        mapaClasseDirigida.put("nomInstallacio",classeDirigida.getNomInstallacio());
        mapaClasseDirigida.put("dia",classeDirigida.getDia());
        mapaClasseDirigida.put("horaInici",classeDirigida.getHoraInici());
        mapaClasseDirigida.put("duracio",Integer.toString(classeDirigida.getDuracio()));
        mapaClasseDirigida.put("reservesActuals",Integer.toString(classeDirigida.getReservesActuals()));
        return mapaClasseDirigida;
    }

    /**
     * Mètode per convertir un HashMap a una classe dirigida.
     *
     * @param mapaClasseDirigida El HashMap que es vol convertir.
     * @return La classe dirigida amb les dades del HashMap.
     */
    public ClasseDirigida hashmap_a_classeDirigida(HashMap<String, String> mapaClasseDirigida) {
        ClasseDirigida classeDirigida = new ClasseDirigida();
        classeDirigida.setIDClasseDirigida(mapaClasseDirigida.get("IDclasseDirigida"));
        classeDirigida.setNomActivitat(mapaClasseDirigida.get("nomActivitat"));
        classeDirigida.setNomInstallacio(mapaClasseDirigida.get("nomInstallacio"));
        classeDirigida.setDia(mapaClasseDirigida.get("dia"));
        classeDirigida.setHoraInici(mapaClasseDirigida.get("horaInici"));
        classeDirigida.setDuracio(Integer.parseInt(mapaClasseDirigida.get("duracio")));
        classeDirigida.setReservesActuals(Integer.parseInt(mapaClasseDirigida.get("reservesActuals")));
        return classeDirigida;
    }
}