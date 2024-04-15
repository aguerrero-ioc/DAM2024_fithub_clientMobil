package antonioguerrero.ioc.fithub.objectes;

import java.io.Serializable;
import java.util.HashMap;


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
    private int getReservesActuals() {
        return reservesActuals;
    }

    private void setReservesActuals(int reservesActuals) {
    }

    public boolean estaReservat() {
        return estaReservat;
    }

    public void setEstaReservat(boolean estaReservat) {
        this.estaReservat = estaReservat;
    }



    public HashMap<String, String> classeDirigida_a_hashmap(ClasseDirigida classeDirigida) {
        HashMap<String, String> mapaClasseDirigida = new HashMap<>();
        mapaClasseDirigida.put("objectType","classeDirigida");
        mapaClasseDirigida.put("id",classeDirigida.getIDClasseDirigida());
        mapaClasseDirigida.put("nomActivitat",classeDirigida.getNomActivitat()); // Se utiliza el método getNomActivitat
        mapaClasseDirigida.put("nomInstallacio",classeDirigida.getNomInstallacio()); // Se utiliza el método getNomInstallacio
        mapaClasseDirigida.put("dia",classeDirigida.getDia());
        mapaClasseDirigida.put("horaInici",classeDirigida.getHoraInici());
        mapaClasseDirigida.put("duracio",Integer.toString(classeDirigida.getDuracio()));
        mapaClasseDirigida.put("reservesActuals",Integer.toString(classeDirigida.getReservesActuals()));
        return mapaClasseDirigida;
    }



    public ClasseDirigida hashmap_a_classeDirigida(HashMap<String, String> mapaClasseDirigida) {
        ClasseDirigida classeDirigida = new ClasseDirigida();
        classeDirigida.setIDClasseDirigida(mapaClasseDirigida.get("id"));
        classeDirigida.setNomActivitat(mapaClasseDirigida.get("nomActivitat"));
        classeDirigida.setNomInstallacio(mapaClasseDirigida.get("nomInstallacio"));
        classeDirigida.setDia(mapaClasseDirigida.get("dia"));
        classeDirigida.setHoraInici(mapaClasseDirigida.get("horaInici"));
        classeDirigida.setDuracio(Integer.parseInt(mapaClasseDirigida.get("duracio")));
        classeDirigida.setReservesActuals(Integer.parseInt(mapaClasseDirigida.get("reservesActuals")));
        return classeDirigida;
    }


}