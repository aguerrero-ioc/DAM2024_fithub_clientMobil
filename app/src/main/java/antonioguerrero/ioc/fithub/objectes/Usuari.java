package antonioguerrero.ioc.fithub.objectes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.Serializable;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.Utils;

/**
 * Classe Usuari que representa un usuari en l'aplicació FitHub.
 * <p>
 * Conté informació com el nom, cognoms, data de naixement, adreça, correu electrònic,
 * telèfon, contrasenya i data d'inscripció de l'usuari.
 * Aquesta classe també conté mètodes per convertir un usuari a un HashMap i viceversa.
 * <p>
 * @author Antonio Guerrero
 * @version 1.1
 */
public class Usuari implements Serializable {

    //Dades Obligatories
    private int IDusuari;
    private String correuUsuari;
    private String passUsuari;
    private int tipusUsuari;

    //Altres dades de l'usuari
    private String nomUsuari;
    private String cognomsUsuari;
    private String dataNaixement;
    private String adreca;
    private String telefon;
    private String dataInscripcio;
    private static Context context;

    /**
     * Constructor de la classe Usuari reduit.
     *
     * @param correuUsuari         El correu electrònic de l'usuari.
     * @param passUsuari    La contrasenya de l'usuari.
     */
    public Usuari(String correuUsuari, String passUsuari) {
        this.correuUsuari = correuUsuari;
        this.passUsuari = passUsuari;
        this.IDusuari = -1;

        this.tipusUsuari = Integer.parseInt(Constants.VALOR_DEFAULT);
        this.nomUsuari = Constants.VALOR_DEFAULT;
        this.cognomsUsuari = Constants.VALOR_DEFAULT;
        this.dataNaixement = Constants.VALOR_DEFAULT;
        this.adreca = Constants.VALOR_DEFAULT;
        this.telefon = Constants.VALOR_DEFAULT;
        this.dataInscripcio = Constants.VALOR_DEFAULT;
    }

    /**
     * Constructor de la classe Usuari per el registre.
     *
     * @param correuUsuari         El correu electrònic de l'usuari.
     * @param passUsuari    La contrasenya de l'usuari.
     * @param nomUsuari            El nom de l'usuari.
     * @param cognomsUsuari        Els cognoms de l'usuari.
     * @param telefon        El número de telèfon de l'usuari.
     */

    public Usuari(String correuUsuari, String passUsuari, String nomUsuari, String cognomsUsuari, String telefon) {
        this.correuUsuari = correuUsuari;
        this.passUsuari = passUsuari;
        this.nomUsuari = nomUsuari;
        this.cognomsUsuari = cognomsUsuari;
        this.telefon = telefon;
    }



    /** Metode per convertir un objecte Usuari a un HashMap
     * @param usuari Objecte Usuari a convertir
     * @return HashMap amb les dades de l'usuari
     */
    public HashMap<String, String> usuari_a_hashmap(Usuari usuari) {
        HashMap<String, String> mapaUsuari = new HashMap<>();
        mapaUsuari.put(Constants.OBJTYPE, Constants.OBJ_USUARI);
        mapaUsuari.put(Constants.ID_USUARI, String.valueOf(usuari.getIDusuari()));
        mapaUsuari.put(Constants.CORREU_USUARI, usuari.getCorreuUsuari());
        mapaUsuari.put(Constants.PASS_USUARI, usuari.getPassUsuari());
        mapaUsuari.put(Constants.NOM_USUARI, usuari.getNomUsuari());
        mapaUsuari.put(Constants.COGNOMS_USUARI, usuari.getCognomsUsuari());
        mapaUsuari.put(Constants.DATA_NAIXEMENT, usuari.getDataNaixement());
        mapaUsuari.put(Constants.DATA_INSCRIPCIO, usuari.getDataNaixement());
        mapaUsuari.put(Constants.ADRECA, usuari.getAdreca());
        mapaUsuari.put(Constants.TELEFON, usuari.getTelefon());
        mapaUsuari.put(Constants.TIPUS_USUARI, String.valueOf(usuari.getTipusUsuari()));
        return mapaUsuari;
    }

    /** Metode per convertir un HashMap a un objecte Usuari
     * @param map HashMap amb les dades de l'usuari
     * @return Objecte Usuari amb les dades del HashMap
     */
    public static Usuari hashmap_a_usuari(HashMap<String, String> map) {
        Usuari usuari = new Usuari();
        usuari.setIDusuari(Integer.parseInt(map.get(Constants.ID_USUARI)));
        usuari.setCorreuUsuari(map.get(Constants.CORREU_USUARI));
        usuari.setPassUsuari(map.get(Constants.PASS_USUARI));
        usuari.setNomUsuari(map.get(Constants.NOM_USUARI));
        usuari.setCognomsUsuari(map.get(Constants.COGNOMS_USUARI));
        usuari.setDataNaixement(map.get(Constants.DATA_NAIXEMENT));
        usuari.setAdreca(map.get(Constants.ADRECA));
        usuari.setTelefon(map.get(Constants.TELEFON));
        usuari.setTipusUsuari(Integer.parseInt(map.get(Constants.TIPUS_USUARI)));
        usuari.setDataInscripcio(map.get(Constants.DATA_INSCRIPCIO));
        return usuari;
    }


        /**
         * Constructor buit de la classe Usuari.
         */
    public Usuari() {

    }

    // Getters i setters

    /**
     * Obté l'ID de l'usuari.
     *
     * @return L'ID de l'usuari.
     */
    public int getIDusuari() {
        return IDusuari;
    }

    /**
     * Estableix l'ID de l'usuari.
     *
     * @param IDusuari L'ID de l'usuari.
     */
    public void setIDusuari(int IDusuari) {
        this.IDusuari = IDusuari;
    }

    /**
     * Obté el tipus d'usuari.
     *
     * @return El tipus d'usuari.
     */
    public int getTipusUsuari() {
        return tipusUsuari;
    }

    /**
     * Estableix el tipus d'usuari.
     *
     * @param tipusUsuari El tipus d'usuari.
     */
    public void setTipusUsuari(int tipusUsuari) {
        this.tipusUsuari = tipusUsuari;
    }

    public void setTipusUsuariString(String tipusUsuari) {
        if (tipusUsuari.equals("Administrador")) {
            this.tipusUsuari = 1;
        } else if (tipusUsuari.equals("Client")) {
            this.tipusUsuari = 2;
        } else {
            this.tipusUsuari = 0;
        }
    }

    /**
     * Obté el nom de l'usuari.
     *
     * @return El nom de l'usuari.
     */
    public String getNomUsuari() {
        return nomUsuari;
    }

    /**
     * Estableix el nom de l'usuari.
     *
     * @param nomUsuari El nom de l'usuari.
     */
    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    /**
     * Obté els cognoms de l'usuari.
     *
     * @return Els cognoms de l'usuari.
     */
    public String getCognomsUsuari() {
        return cognomsUsuari;
    }

    /**
     * Estableix els cognoms de l'usuari.
     *
     * @param cognomsUsuari Els cognoms de l'usuari.
     */
    public void setCognomsUsuari(String cognomsUsuari) {
        this.cognomsUsuari = cognomsUsuari;
    }

    /**
     * Obté la data de naixement de l'usuari.
     *
     * @return La data de naixement de l'usuari.
     */
    public String getDataNaixement() {
        return dataNaixement;
    }

    /**
     * Estableix la data de naixement de l'usuari.
     *
     * @param DataNaixement La data de naixement de l'usuari.
     */
    public void setDataNaixement(String DataNaixement) {
        this.dataNaixement = DataNaixement;
    }

    /**
     * Obté l'adreça de l'usuari.
     *
     * @return L'adreça de l'usuari.
     */
    public String getAdreca() {
        return adreca;
    }

    /**
     * Estableix l'adreça de l'usuari.
     *
     * @param adreca L'adreça de l'usuari.
     */
    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    /**
     * Obté el número de telèfon de l'usuari.
     *
     * @return El número de telèfon de l'usuari.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Estableix el número de telèfon de l'usuari.
     *
     * @param telefon El número de telèfon de l'usuari.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Obté el correu electrònic de l'usuari.
     *
     * @return El correu electrònic de l'usuari.
     */
    public String getCorreuUsuari() {
        return correuUsuari;
    }

    /**
     * Estableix el correu electrònic de l'usuari.
     *
     * @param correuUsuari El correu electrònic de l'usuari.
     */
    public void setCorreuUsuari(String correuUsuari) {
        this.correuUsuari = correuUsuari;
    }

    /**
     * Obté la contrasenya de l'usuari.
     *
     * @return La contrasenya de l'usuari.
     */
    public String getPassUsuari() {
        return passUsuari;
    }

    /**
     * Estableix la contrasenya de l'usuari.
     *
     * @param passUsuari La contrasenya de l'usuari.
     */
    public void setPassUsuari(String passUsuari) {
        this.passUsuari = passUsuari;
    }

    /**
     * Obté la data d'inscripció de l'usuari.
     *
     * @return La data d'inscripció de l'usuari.
     */
    public String getDataInscripcio() {
        return dataInscripcio;
    }

    /**
     * Estableix la data d'inscripció de l'usuari.
     *
     * @param dataInscripcio La data d'inscripció de l'usuari.
     */
    public void setDataInscripcio(String dataInscripcio) {
        this.dataInscripcio = dataInscripcio;
    }

    public static void setContext(Context context) {
        Usuari.context = context;
    }



    /**
     * Guarda les propietats de l'objecte Usuari a SharedPreferences.
     *
     * @param usuari L'objecte Usuari que es guardarà a SharedPreferences.
     */
    public static void guardarDadesUsuari(Usuari usuari) {

        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les propietats de l'objecte usuari a SharedPreferences
        editor.putString(Constants.NOM_USUARI, usuari.getNomUsuari());
        editor.putString(Constants.ID_USUARI, String.valueOf(usuari.getIDusuari()));
        editor.putString(Constants.TIPUS_USUARI, String.valueOf(usuari.getTipusUsuari()));
        editor.putString(Constants.CORREU_USUARI, usuari.getCorreuUsuari());
        editor.putString(Constants.PASS_USUARI, usuari.getPassUsuari());
        editor.putString(Constants.DATA_INSCRIPCIO, usuari.getDataInscripcio());
        editor.putString(Constants.COGNOMS_USUARI, usuari.getCognomsUsuari());
        editor.putString(Constants.DATA_NAIXEMENT, usuari.getDataNaixement());
        editor.putString(Constants.ADRECA, usuari.getAdreca());
        editor.putString(Constants.TELEFON, usuari.getTelefon());

        // Aplicar els canvis a SharedPreferences
        editor.apply();

        // Log para ver los datos guardados
        Log.d("Usuari Data", "nomUsuari: " + usuari.getNomUsuari() +
                ", IDusuari: " + usuari.getIDusuari() +
                ", tipusUsuari: " + usuari.getTipusUsuari() +
                ", correuUsuari: " + usuari.getCorreuUsuari() +
                ", passUsuari: " + usuari.getPassUsuari() +
                ", dataInscripcio: " + usuari.getDataInscripcio() +
                ", cognomsUsuari: " + usuari.getCognomsUsuari() +
                ", DataNaixement: " + usuari.getDataNaixement() +
                ", adreca: " + usuari.getAdreca() +
                ", telefon: " + usuari.getTelefon());
    }
}
