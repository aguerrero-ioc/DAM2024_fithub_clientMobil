package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa un missatge en l'aplicació FitHub.
 * <p>
 *  Cada missatge té una data, una hora, un remitent i un contingut.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Missatge {
    private String data;
    private String hora;

    private String remitent;
    private String contingut;

    /**
     * Constructor de la classe Missatge.
     * <p>
     * @param data La data del missatge.
     * @param hora L'hora del missatge.
     * @param remitent El remitent del missatge.
     * @param contingut El contingut del missatge.
     */
    public Missatge(String data, String hora, String remitent, String contingut) {
        this.data = data;
        this.hora = hora;
        this.remitent = remitent;
        this.contingut = contingut;
    }

    /**
     * Obté la data del missatge.
     * <p>
     * @return La data del missatge.
     */
    public String getData() {
        return data;
    }

    /**
     * Estableix la data del missatge.
     * <p>
     * @param data La data del missatge.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obté l'hora del missatge.
     * <p>
     * @return L'hora del missatge.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Estableix l'hora del missatge.
     * <p>
     * @param hora L'hora del missatge.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Obté el remitent del missatge.
     * <p>
     * @return El remitent del missatge.
     */
    public String getRemitent() {
        return remitent;
    }

    /**
     * Estableix el remitent del missatge.
     * <p>
     * @param remitent El remitent del missatge.
     */
    public void setRemitent(String remitent) {
        this.remitent = remitent;
    }

    /**
     * Obté el contingut del missatge.
     * <p>
     * @return El contingut del missatge.
     */
    public String getContingut() {
        return contingut;
    }

    /**
     * Estableix el contingut del missatge.
     * <p>
     * @param contingut El contingut del missatge.
     */
    public void setContingut(String contingut) {
        this.contingut = contingut;
    }
}
