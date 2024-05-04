package antonioguerrero.ioc.fithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.net.ParseException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Constants {

    //USUARI
    public static final String OBJ_USUARI = "usuari";
    public static final String ID_USUARI = "IDusuari";
    public static final String NOM_USUARI = "nomUsuari";
    public static final String PASS_USUARI = "passUsuari";
    public static final String TIPUS_USUARI = "tipusUsuari";
    public static final String CORREU_USUARI = "correuUsuari";
    public static final String COGNOMS_USUARI = "cognomsUsuari";
    public static final String TELEFON = "telefon";
    public static final String ADRECA = "adreca";
    public static final String DATA_NAIXEMENT = "dataNaixement";
    public static final String DATA_INSCRIPCIO = "dataInscripcio";


    //ACTIVITAT

    public static final String OBJ_ACT = "activitat";
    public static final String ACT_ID = "IDactivitat";
    public static final String ACT_NOM = "nomActivitat";
    public static final String ACT_DESC = "descripcioActivitat";
    public static final String ACT_TIPUS = "tipusActivitat";
    public static final String ACT_AFORAMENT = "aforamentActivitat";

    //INSTALLACIO
    public static final String OBJ_INS = "installacio";
    public static final String INS_ID = "IDinstallacio";
    public static final String INS_NOM = "nomInstallacio";
    public static final String INS_DESC = "descripcioInstallacio";
    public static final String INS_TIPUS = "tipusInstallacio";

    // SERVEI

    public static final String OBJ_SERVEI = "servei";
    public static final String SERVEI_ID = "IDservei";
    public static final String SERVEI_NOM = "nomServei";
    public static final String SERVEI_DESC = "descripcioServei";
    public static final String SERVEI_PREU = "preu";

    // RESERVA
    public static final String OBJ_RESERVA = "reserva";
    public static final String RESERVA_ID = "IDreserva";



   // CLASSE DIRIGIDA
   public static final String OBJ_CLASSE = "classeDirigida";
    public static final String CLASSE_ID = "IDclasse";
    public static final String CLASSE_DATA = "dataClasseDirigida";
    public static final String CLASSE_HORA = "horaInici";
    public static final String CLASSE_DURACIO = "duracio";
    public static final String CLASSE_OCUPACIO = "ocupacio";
    public static final String CLASSE_ESTAT = "estatClasse";


    // RESTA
    public static final String OBJTYPE = "objectType";

    public static final String PREFERENCIES = "Preferències";
    public static final String SESSIO_ID = "sessioID";
    public static final String VALOR_DEFAULT = "";
    public static final String ERROR_CONNEXIO = "Error de connexió";
    public static final String PENDENT_IMPLEMENTAR = "Pendent d'implementar. Aviat disponible!";
    public static final String NO_ADMIN = "No disponible per a administradors";
    public static final String FORMAT_DATA = "dd-MM-yyyy";

}