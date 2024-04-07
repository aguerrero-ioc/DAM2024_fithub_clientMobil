package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe abstracta que representa una reserva en una instal·lació per a un servei, activitat o classe.
 *
 * Cada reserva té una identificació única, un usuari associat, una instal·lació on es realitza la reserva,
 * una data i hora, durada, nombre de persones, preu i estat.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Reserva {
    private int id;
    private Usuari usuari;
    private Installacio installacio;
    private String data;
    private String hora;
    private int durada;
    private int nombrePersones;
    private String estat;

    /**
     * Constructor de la classe Reserva.
     *
     * @param id Identificador de la reserva
     * @param usuari Usuari que realitza la reserva
     * @param installacio Instal·lació on es realitza la reserva
     * @param data Data de la reserva
     * @param hora Hora de la reserva
     * @param durada Durada de la reserva
     * @param nombrePersones Nombre de persones per a la reserva
     * @param estat Estat de la reserva
     */
    public Reserva(int id, Usuari usuari, Installacio installacio, String data, String hora, int durada, int nombrePersones, float preu, String estat) {
        this.id = id;
        this.usuari = usuari;
        this.installacio = installacio;
        this.data = data;
        this.hora = hora;
        this.durada = durada;
        this.nombrePersones = nombrePersones;
        this.estat = estat;
    }

    // Getters i setters

    /**
     * Obté l'identificador de la reserva.
     *
     * @return Identificador de la reserva
     */
    public int getId() {
        return id;
    }

    /**
     * Estableix l'identificador de la reserva.
     *
     * @param id Identificador de la reserva
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obté l'usuari que realitza la reserva.
     *
     * @return Usuari de la reserva
     */
    public Usuari getUsuari() {
        return usuari;
    }

    /**
     * Estableix l'usuari que realitza la reserva.
     *
     * @param usuari Usuari de la reserva
     */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    /**
     * Obté la instal·lació on es realitza la reserva.
     *
     * @return Instal·lació de la reserva
     */
    public Installacio getInstallacio() {
        return installacio;
    }

    /**
     * Estableix la instal·lació on es realitza la reserva.
     *
     * @param installacio Instal·lació de la reserva
     */
    public void setInstallacio(Installacio installacio) {
        this.installacio = installacio;
    }

    /**
     * Obté la data de la reserva.
     *
     * @return Data de la reserva
     */
    public String getData() {
        return data;
    }

    /**
     * Estableix la data de la reserva.
     *
     * @param data Data de la reserva
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obté l'hora de la reserva.
     *
     * @return Hora de la reserva
     */
    public String getHora() {
        return hora;
    }

    /**
     * Estableix l'hora de la reserva.
     *
     * @param hora Hora de la reserva
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Obté la durada de la reserva.
     *
     * @return Durada de la reserva
     */
    public int getDurada() {
        return durada;
    }

    /**
     * Estableix la durada de la reserva.
     *
     * @param durada Durada de la reserva
     */
    public void setDurada(int durada) {
        this.durada = durada;
    }

    /**
     * Obté el nombre de persones per a la reserva.
     *
     * @return Nombre de persones per a la reserva
     */
    public int getNombrePersones() {
        return nombrePersones;
    }

    /**
     * Estableix el nombre de persones per a la reserva.
     *
     * @param nombrePersones Nombre de persones per a la reserva
     */
    public void setNombrePersones(int nombrePersones) {
        this.nombrePersones = nombrePersones;
    }


    /**
     * Obté l'estat de la reserva.
     *
     * @return Estat de la reserva
     */
    public String getEstat() {
        return estat;
    }

    /**
     * Estableix l'estat de la reserva.
     *
     * @param estat Estat de la reserva
     */
    public void setEstat(String estat) {
        this.estat = estat;
    }

}
