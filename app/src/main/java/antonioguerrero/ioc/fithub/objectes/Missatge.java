package antonioguerrero.ioc.fithub.objectes;

/**
 * Classe que representa un missatge en l'aplicació FitHub.
 *
 *  Cada missatge té una data, una hora, un remitent i un contingut.
 */
public class Missatge {
    private String data;
    private String hora;

    private String remitent;
    private String contingut;

    /**
     * Constructor de la classe Missatge.
     *
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
     *
     * @return La data del missatge.
     */
    public String getData() {
        return data;
    }

    /**
     * Estableix la data del missatge.
     *
     * @param data La data del missatge.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obté l'hora del missatge.
     *
     * @return L'hora del missatge.
     */
    public String getHora() {
        return hora;
    }

    /**
     * Estableix l'hora del missatge.
     *
     * @param hora L'hora del missatge.
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * Obté el remitent del missatge.
     *
     * @return El remitent del missatge.
     */
    public String getRemitent() {
        return remitent;
    }

    /**
     * Estableix el remitent del missatge.
     *
     * @param remitent El remitent del missatge.
     */
    public void setRemitent(String remitent) {
        this.remitent = remitent;
    }

    /**
     * Obté el contingut del missatge.
     *
     * @return El contingut del missatge.
     */
    public String getContingut() {
        return contingut;
    }

    /**
     * Estableix el contingut del missatge.
     *
     * @param contingut El contingut del missatge.
     */
    public void setContingut(String contingut) {
        this.contingut = contingut;
    }
}
