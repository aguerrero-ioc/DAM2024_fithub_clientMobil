package antonioguerrero.ioc.fithub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Classe d'utilitats amb mètodes útils per a diverses funcionalitats.
 * <p>
 * Aquesta classe conté mètodes per a la gestió de dades, la validació de correus electrònics,
 * la conversió d'objectes a HashMap i viceversa, la gestió de SharedPreferences, la creació de
 * Toasts, la conversió de dates a cadenes de text i altres funcionalitats útils per a l'aplicació.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class Utils {

    //USUARI
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

    // RESTA
    public static final String PREFERENCIES = "Preferències";
    public static final String SESSIO_ID = "sessioID";
    public static final String VALOR_DEFAULT = "";
    public static final String ERROR_CONNEXIO = "Error de connexió";
    public static final String PENDENT_IMPLEMENTAR = "Pendent d'implementar. Aviat disponible!";
    public static final String FORMAT_DATA = "dd-MM-yyyy";

    /**
     * Método para obtener la fecha actual formateada como "ddMMyyyy".
     *
     * @return Fecha actual formateada como "ddMMyyyy".
     */
    public static String obtenirDataActual() {
        Calendar calendar = Calendar.getInstance();
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH) + 1; // Se agrega 1 ya que enero se considera como 0
        int any = calendar.get(Calendar.YEAR);

        return obtenirDataFormatejada(dia, mes, any);
    }

    /**
     * Método para obtener la fecha formateada como "ddMMyyyy".
     *
     * @param dia Día.
     * @param mes Mes.
     * @param any Año.
     * @return Fecha formateada como "ddMMyyyy".
     */
    public static String obtenirDataFormatejada(int dia, int mes, int any) {
        return String.format(Locale.getDefault(), "%02d%02d%04d", dia, mes, any);
    }

    /**
     * Obté l'hora actual en format de cadena.
     *
     * @return Hora actual en format "HH:mm:ss"
     */
    public static String obtenirHoraActual() {
        // Obté l'hora actual
        Date horaActual = Calendar.getInstance().getTime();
        // Formateja l'hora en el format desitjat
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        return formatHora.format(horaActual);
    }

    /**
     * Obté el tipus d'usuari a partir de la resposta del servidor.
     *
     * @param resposta Resposta del servidor
     * @return Tipus d'usuari actual
     */
    public static String obtenirTipusUsuari(String resposta) {
        // Verifica si la resposta del servidor no és nul·la i té els paràmetres esperats
        if (resposta != null) {
            String[] parts = resposta.split(",");
            // Comprova si la resposta conté els paràmetres esperats (només hi hauria 3 parts si ho fa)
            if (parts.length == 3) {
                // Retorna el tipus d'usuari que es troba a la segona posició
                return parts[1];
            }
        }
        // En cas contrari, retorna un valor predeterminat o buit
        return ""; // Opcional: pots retornar un valor predeterminat o llançar una excepció segons la teva lògica
    }


    /**
     * Mètode per mostrar un Toast amb el missatge especificat.
     *
     * @param context  El context de l'aplicació.
     * @param missatge El missatge a mostrar en el Toast.
     */
    public static void mostrarToast(Context context, String missatge) {
        Toast.makeText(context, missatge, Toast.LENGTH_SHORT).show();
    }

    /**
     * Mètode per convertir un objecte a un HashMap.
     *
     * @param object L'objecte a convertir.
     * @return Un HashMap amb els valors de l'objecte.
     */
    public static HashMap<String, String> ObjecteAHashMap(Object object) {
        HashMap<String, String> map = new HashMap<>();

        if (object != null) {
            Field[] fields = object.getClass().getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value != null) {
                        map.put(field.getName(), value.toString());
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * Mètode per convertir un HashMap a un objecte.
     *
     * @param map   El HashMap a convertir.
     * @param clazz La classe de l'objecte.
     * @return Un objecte amb els valors del HashMap.
     */
    public static Object HashMapAObjecte(HashMap<String, String> map, Class<?> clazz) {
        Object object = null;
        try {
            object = clazz.newInstance(); // Crea una nueva instancia de la clase
            Field[] fields = clazz.getDeclaredFields(); // Obtiene todos los campos de la clase

            for (Field field : fields) {
                field.setAccessible(true); // Permite el acceso a campos privados
                String value = map.get(field.getName()); // Obtiene el valor del HashMap
                if (value != null) {
                    if (field.getType() == int.class) {
                        field.setInt(object, Integer.parseInt(value));
                    } else if (field.getType() == double.class) {
                        field.setDouble(object, Double.parseDouble(value));
                    } else if (field.getType() == boolean.class) {
                        field.setBoolean(object, Boolean.parseBoolean(value));
                    } else {
                        field.set(object, value); // Establece el valor del campo
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }

    /**
     * Mètode per guardar les dades d'un objecte a SharedPreferences.
     *
     * @param context Context de l'aplicació.
     * @param object  Objecte a guardar.
     * @param clazz   Classe de l'objecte.
     */
    public static void guardarDadesObjecte(Context context, Object object, Class<?> clazz) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        SimpleDateFormat format = new SimpleDateFormat(Utils.FORMAT_DATA);

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value != null) {
                    if (value instanceof Date) {
                        editor.putString(field.getName(), format.format((Date) value));
                    } else if (value instanceof Integer) {
                        editor.putInt(field.getName(), (Integer) value);
                    } else if (value instanceof Boolean) {
                        editor.putBoolean(field.getName(), (Boolean) value);
                    } else if (value instanceof Float) {
                        editor.putFloat(field.getName(), (Float) value);
                    } else if (value instanceof Long) {
                        editor.putLong(field.getName(), (Long) value);
                    } else if (value instanceof String) {
                        editor.putString(field.getName(), (String) value);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        editor.apply();
    }


    /**
     * Mètode per obrir una nova activitat.
     *
     * @param context      Context de l'aplicació.
     * @param activityClass Classe de l'activitat a obrir.
     * @param flags        Flags per a l'activitat.
     */
    public static void obrirActivitat(Context context, Class<?> activityClass, int flags) {
        Intent intent = new Intent(context, activityClass);
        intent.addFlags(flags);
        context.startActivity(intent);
    }

    /**
     * Mètode per iniciar una nova activitat amb llista.
     *
     * @param context      Context de l'aplicació.
     * @param activityClass Classe de l'activitat a obrir.
     */
    public static void iniciarActivitatLlista(Context context, Class<?> activityClass, List<? extends Serializable> llista, String clauLlista) {
        Intent intent = new Intent(context, activityClass);
        Bundle bundle = new Bundle();
        bundle.putSerializable(clauLlista, new ArrayList<>(llista));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * Mètode per validar el format d'un correu electrònic utilitzant una expressió regular.
     *
     * @param correu  El correu electrònic a validar.
     * @return Cert si el correu electrònic té el format vàlid, fals altrament.
     */
    public static boolean esEmailValid(String correu) {
        // Patró per validar el format d'un correu electrònic
        String patroCorreu = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // Comprova si el correu electrònic coincideix amb el patró
        return correu.matches(patroCorreu);
    }
}