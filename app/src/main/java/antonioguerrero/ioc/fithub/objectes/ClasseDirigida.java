package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;

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
    private int IDclasseDirigida;
    private Activitat activitat;
    private Installacio installacio;
    private String data;
    private String horaInici;
    private int duracio;
    private int reservesActuals;
    private boolean estat;

    /**
     * Constructor de la classe ClasseDirigida.
     */
    public ClasseDirigida() {
        this.IDclasseDirigida = IDclasseDirigida;
        this.activitat = activitat;
        this.installacio = installacio;
        this.data = data;
        this.horaInici = horaInici;
        this.duracio = duracio;
        this.reservesActuals = reservesActuals;
        this.estat = false; // Inicialment, no reservada
    }

    // Getters y setters

    /**
     * Obté l'identificador de la classe dirigida.
     *
     * @return Identificador de la classe dirigida
     */
    public int getIDClasseDirigida() {
        return IDclasseDirigida;
    }

    /**
     * Estableix l'identificador de la classe dirigida.
     *
     * @param IDClasseDirigida Identificador de la classe dirigida
     */
    public void setIDClasseDirigida(int IDClasseDirigida) {
        this.IDclasseDirigida = IDClasseDirigida;
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

  
    public String getData() {
        return data;
    }


    public void setData(String data) {
        this.data = data;
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
    public boolean getEstat() {
        return estat;
    }

    /**
     * Estableix si la classe dirigida està reservada.
     *
     * @param estat Indica si la classe dirigida està reservada
     */
    public void setEstat(boolean estat) {
        this.estat = estat;
    }

    /**
     * Mètode per convertir una classe dirigida a un HashMap.
     *
     * @param classeDirigida La classe dirigida que es vol convertir.
     * @return HashMap amb les dades de la classe dirigida.
     */
    public HashMap<String, String> classeDirigida_a_hashmap(ClasseDirigida classeDirigida) {
        HashMap<String, String> mapaClasseDirigida = new HashMap<>();

        // Obtener los datos de Activitat y Installacio directamente de las instancias
        mapaClasseDirigida.put(Constants.ACT_NOM, classeDirigida.getNomActivitat());
        mapaClasseDirigida.put(Constants.INS_NOM, classeDirigida.getNomInstallacio());

        // Agregar otros atributos de la clase dirigida
        mapaClasseDirigida.put(Constants.OBJTYPE, Constants.OBJ_CLASSE);
        mapaClasseDirigida.put(Constants.CLASSE_ID, String.valueOf(classeDirigida.getIDClasseDirigida()));
        mapaClasseDirigida.put(Constants.CLASSE_HORA, classeDirigida.getHoraInici());
        mapaClasseDirigida.put(Constants.CLASSE_DURACIO, String.valueOf(classeDirigida.getDuracio()));
        mapaClasseDirigida.put(Constants.CLASSE_OCUPACIO, String.valueOf(classeDirigida.getReservesActuals()));

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
        classeDirigida.setIDClasseDirigida(Integer.parseInt(mapaClasseDirigida.get(Constants.CLASSE_ID)));

        // Crear una nueva instancia de Activitat y asignarle los datos del mapa
        Activitat activitat = new Activitat();
        activitat.setNomActivitat(mapaClasseDirigida.get(Constants.ACT_NOM));

        // Crear una nueva instancia de Installacio y asignarle los datos del mapa
        Installacio installacio = new Installacio();
        installacio.setNomInstallacio(mapaClasseDirigida.get(Constants.INS_NOM));

        // Asignar las instancias de Activitat e Installacio a la ClasseDirigida
        classeDirigida.setActivitat(activitat);
        classeDirigida.setInstallacio(installacio);

        // Asignar los otros atributos de ClasseDirigida
        classeDirigida.setData(mapaClasseDirigida.get("data"));
        classeDirigida.setHoraInici(mapaClasseDirigida.get(Constants.CLASSE_HORA));
        classeDirigida.setDuracio(Integer.parseInt(mapaClasseDirigida.get(Constants.CLASSE_DURACIO)));
        classeDirigida.setReservesActuals(Integer.parseInt(mapaClasseDirigida.get(Constants.CLASSE_OCUPACIO)));

        return classeDirigida;
    }

}