package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;


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
     *
     * @param IDClasseDirigida ID de la classe dirigida
     * @param activitat Activitat de la classe dirigida
     * @param installacio Installació on es realitza la classe dirigida
     * @param dia Dia de la setmana en què es realitza la classe dirigida
     * @param horaInici Hora d'inici de la classe dirigida
     * @param duracio Duració de la classe dirigida
     * @param reservesActuals Reserves actuals de la classe dirigida
     */
    public ClasseDirigida(String IDClasseDirigida, Activitat activitat, Installacio installacio, String dia, String horaInici, int duracio, int reservesActuals) {
        this.IDClasseDirigida = IDClasseDirigida;
        this.activitat = activitat;
        this.installacio = installacio;
        this.dia = dia;
        this.horaInici = horaInici;
        this.duracio = duracio;
        this.reservesActuals = reservesActuals;
        this.estaReservat = false; // Inicialmente, la clase no está reservada
    }

    // Getters y setters


    public String getIDClasseDirigida() {
        return IDClasseDirigida;
    }

    public void setIDClasseDirigida(String IDClasseDirigida) {
        this.IDClasseDirigida = IDClasseDirigida;
    }


    public Activitat getActivitat() {
        return activitat;
    }

    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }

    public String getNomActivitat() {
        return activitat.getNomActivitat();
    }


    public void setNomActivitat(String nomActivitat) {
    }




    public Installacio getInstallacio() {
        return installacio;
    }

    public void setInstallacio(Installacio installacio) {
        this.installacio = installacio;
    }

    public String getNomInstallacio() {
        return installacio.getNomInstallacio();
    }

    public void setNomInstallacio(String nomInstallacio) {
    }



    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(String horaInici) {
        this.horaInici = horaInici;
    }

    public int getDuracio() {
        return duracio;
    }

    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }


    public boolean estaReservat() {
        return estaReservat;
    }

    public void setEstaReservat(boolean estaReservat) {
        this.estaReservat = estaReservat;
    }
}