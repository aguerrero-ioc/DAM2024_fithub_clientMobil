package antonioguerrero.ioc.fithub.objectes;

import android.content.Context;

import java.io.Serializable;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;

/**
 * Classe Usuari que representa un usuari en l'aplicació FitHub.
 * <p>
 * Conté informació com el nom, cognoms, data de naixement, adreça, correu electrònic,
 * telèfon, contrasenya i data d'inscripció de l'usuari.
 *
 * @author Antonio Guerrero
 * @version 1.1
 */
public class Usuari implements Serializable {

    //Dades Obligatories
    private int IDUsuari;
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
        this.IDUsuari = -1;

        this.tipusUsuari = Integer.parseInt(Utils.VALOR_DEFAULT);
        this.nomUsuari = Utils.VALOR_DEFAULT;
        this.cognomsUsuari = Utils.VALOR_DEFAULT;
        this.dataNaixement = Utils.VALOR_DEFAULT;
        this.adreca = Utils.VALOR_DEFAULT;
        this.telefon = Utils.VALOR_DEFAULT;
        this.dataInscripcio = Utils.VALOR_DEFAULT;
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
        mapaUsuari.put("objectType", "usuari");
        mapaUsuari.put("IDUsuari", String.valueOf(usuari.getIDUsuari()));
        mapaUsuari.put("correuUsuari", usuari.getCorreuUsuari());
        mapaUsuari.put("nomUsuari", usuari.getNomUsuari());
        mapaUsuari.put("cognomsUsuari", usuari.getCognomsUsuari());
        mapaUsuari.put("dataNaixement", usuari.getDataNaixement());
        mapaUsuari.put("adreca", usuari.getAdreca());
        mapaUsuari.put("telefon", usuari.getTelefon());
        mapaUsuari.put("tipusUsuari", String.valueOf(usuari.getTipusUsuari()));
        return mapaUsuari;
    }

    /** Metode per convertir un HashMap a un objecte Usuari
     * @param map HashMap amb les dades de l'usuari
     * @return Objecte Usuari amb les dades del HashMap
     */
    public Usuari hashmap_a_usuari(HashMap<String, String> map) {
        Usuari usuari = new Usuari();
        usuari.setIDUsuari(Integer.parseInt(map.get("IDUsuari")));
        usuari.setCorreuUsuari(map.get("correuUsuari"));
        usuari.setNomUsuari(map.get("nomUsuari"));
        usuari.setCognomsUsuari(map.get("cognomsUsuari"));
        usuari.setDataNaixement(map.get("dataNaixement"));
        usuari.setAdreca(map.get("adreca"));
        usuari.setTelefon(map.get("telefon"));
        usuari.setTipusUsuari(Integer.parseInt(map.get("tipusUsuari")));
        usuari.setDataInscripcio(map.get("dataInscripcio"));
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
    public int getIDUsuari() {
        return IDUsuari;
    }

    /**
     * Estableix l'ID de l'usuari.
     *
     * @param IDUsuari L'ID de l'usuari.
     */
    public void setIDUsuari(int IDUsuari) {
        this.IDUsuari = IDUsuari;
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
        Utils.guardarDadesObjecte(context, usuari, Usuari.class);

                /* Comparar amb aquesta implementacio

        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        // Guardar les propietats de l'objecte usuari a SharedPreferences
        editor.putString("nomUsuari", usuari.getNomUsuari());
        editor.putString("idUsuari", String.valueOf(usuari.getUsuariID()));
        editor.putString("tipusUsuari", usuari.getTipusUsuari());
        editor.putString("correuUsuari", usuari.getCorreuUsuari());
        editor.putString("passUsuari", usuari.getPassUsuari());
        editor.putString("dataInscripcio", format.format(usuari.getDataInscripcio()));
        editor.putString("cognomsUsuari", usuari.getCognomsUsuari());
        editor.putString("DataNaixement", format.format(usuari.getDataNaixement()));
        editor.putString("adreca", usuari.getAdreca());
        editor.putString("telefon", usuari.getTelefon());

        // Aplicar els canvis a SharedPreferences
        editor.apply();*/
    }
}
