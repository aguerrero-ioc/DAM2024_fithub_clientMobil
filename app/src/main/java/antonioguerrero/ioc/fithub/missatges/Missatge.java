package antonioguerrero.ioc.fithub.missatges;

public class Missatge {
    private String remitent;
    private String contingut;
    private String data;

    public Missatge(String remitent, String contingut, String data) {
        this.remitent = remitent;
        this.contingut = contingut;
        this.data = data;
    }

    public String getRemitent() {
        return remitent;
    }

    public String getContingut() {
        return contingut;
    }

    public String getData() {
        return data;
    }
}
