package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;

public class ClasseDirigida implements Serializable {

    // Atributs de la classe
    private int IDClasseDirigida;
    private String dia;
    private String hora;
    private int duracio;
    private Activitat activitat;
    private Installacio installacio;

    // Constructor
    public ClasseDirigida(int IDClasseDirigida, String dia, String hora, int duracio, Activitat activitat, Installacio installacio) {
        this.IDClasseDirigida = IDClasseDirigida;
        this.dia = dia;
        this.hora = hora;
        this.duracio = duracio;
        this.activitat = activitat;
        this.installacio = installacio;
    }

    // Getters y setters
    public int getIDClasseDirigida() {
        return IDClasseDirigida;
    }

    public void setIDClasseDirigida(int IDClasseDirigida) {
        this.IDClasseDirigida = IDClasseDirigida;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDuracio() {
        return duracio;
    }

    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }

    public int getIDactivitat() {
        return activitat.getIDActivitat();
    }

    public int getIDinstalacio() {
        return installacio.getIDInstallacio();
    }
}