package antonioguerrero.ioc.fithub.missatges;

/**
 * Classe que representa un missatge en l'aplicació FitHub.
 */
public class Missatge {
    private String remitent;
    private String contingut;
    private String data;
    private String hora;

    /**
     * Constructor de la classe Missatge.
     *
     * @param remitent  El remitent del missatge.
     * @param contingut El contingut del missatge.
     * @param data      La data del missatge.
     * @param hora      L'hora del missatge.
     */
    public Missatge(String remitent, String contingut, String data, String hora) {
        this.remitent = remitent;
        this.contingut = contingut;
        this.data = data;
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
     * Obté el contingut del missatge.
     *
     * @return El contingut del missatge.
     */
    public String getContingut() {
        return contingut;
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
     * Obté l'hora del missatge.
     *
     * @return L'hora del missatge.
     */
    public String getHora() {
        return hora;
    }
}
