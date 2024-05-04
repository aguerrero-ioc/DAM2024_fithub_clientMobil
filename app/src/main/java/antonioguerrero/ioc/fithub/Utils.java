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

/**
 * Classe d'utilitats amb mètodes útils per a diverses funcionalitats.
 * <p>
 * Aquesta classe conté mètodes per obtenir la data i l'hora actuals, convertir una cadena de text
 * a una data o hora, comprovar si una data o hora és anterior a la data o hora actual, obtenir el
 * tipus d'usuari a partir de la resposta del servidor, mostrar un Toast, convertir un objecte a
 * un HashMap i viceversa, guardar les dades d'un objecte a SharedPreferences, obrir una nova
 * activitat, iniciar una nova activitat amb llista, validar el format d'un correu electrònic,
 * entre d'altres.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class Utils {


    /**
     * Mètode per obtenir la data actual en format "ddMMyyyy".
     *
     * @return Data actual en format "ddMMyyyy".
     */
    public static String obtenirDataActual() {
        return obtenirDataFormatejada(Calendar.getInstance());
    }

    /**
     * Mètode per obtenir la data formatejada com a "ddMMyyyy".
     *
     * @param calendar Instància de Calendar.
     * @return Data formatejada com a "ddMMyyyy".
     */
    public static String obtenirDataFormatejada(Calendar calendar) {
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        int mes = calendar.get(Calendar.MONTH) + 1; // S'afegeix 1 ja que gener es considera com a 0
        int any = calendar.get(Calendar.YEAR);
        return String.format(Locale.getDefault(), "%02d%02d%04d", dia, mes, any);
    }

    /**
     * Mètode per convertir una cadena de text a una data.
     *
     * @param cadenaData Cadena de text amb la data en format "ddMMyyyy".
     * @return Data convertida.
     */
    public static Date convertirStringAFecha(String cadenaData) {
        // Comprova si la cadena de data no és nul·la i té el format correcte
        if (cadenaData != null && cadenaData.matches("\\d{2}\\d{2}\\d{4}")) {
            try {
                // Crea un objecte SimpleDateFormat amb el format ddmmyyyy
                SimpleDateFormat formatData = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());

                // Analitza la cadena de data a un objecte Date
                Date data = formatData.parse(cadenaData);

                // Retorna la data convertida
                return data;
            } catch (ParseException | java.text.ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("El format de la data no és vàlid: " + cadenaData);
            return null;
        }
    }

    /**
     * Mètode per comprovar si una data és anterior a la data actual.
     *
     * @param stringData Data en format "ddMMyyyy".
     * @return Cert si la data és anterior a la data actual, fals altrament.
     */
    public static boolean esDataAnterior(String stringData) {
        // Creem un objecte SimpleDateFormat amb el format "ddMMyyyy"
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        try {
            // Convertim la cadena de text a un objecte Date
            Date data = format.parse(stringData);

            // Obtenim la data actual
            Date dataActual = new Date();

            // Comprovem si la data donada és anterior a la data actual
            if (data != null) {
                return data.before(dataActual);
            }
        } catch (ParseException | java.text.ParseException e) {
            // En cas d'error en la conversió de la cadena de text a Date, imprimeix l'error i retornem fals
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Mètode per obtenir l'hora actual en format "HHmm".
     *
     * @return Hora actual en format "HHmm".
     */
    public static String obtenirHoraActual() {
        return obtenirHoraFormatejada(Calendar.getInstance());
    }

    /**
     * Mètode per obtenir l'hora formatejada com a "HHmm".
     *
     * @param calendar Instància de Calendar.
     * @return Hora formatejada com a "HHmm".
     */
    public static String obtenirHoraFormatejada(Calendar calendar) {
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minut = calendar.get(Calendar.MINUTE);
        return String.format(Locale.getDefault(), "%02d%02d", hora, minut);
    }

    /**
     * Mètode per convertir una cadena de text a una hora.
     *
     * @param cadenaHora Cadena de text amb l'hora en format "HHmm".
     * @return Hora convertida.
     */
    public static Time convertirStringAHora(String cadenaHora) {
        // Comprova si la cadena d'hora no és nul·la i té el format correcte
        if (cadenaHora != null && cadenaHora.matches("\\d{2}\\d{2}")) {
            try {
                // Crea un objecte SimpleDateFormat amb el format "HHmm"
                SimpleDateFormat formatHora = new SimpleDateFormat("HHmm", Locale.getDefault());

                // Analitza la cadena d'hora a un objecte Time
                Time hora = new Time(formatHora.parse(cadenaHora).getTime());

                // Retorna l'hora convertida
                return hora;
            } catch (ParseException | java.text.ParseException e) {
                // En cas d'error en la conversió de la cadena de text a Time, imprimeix l'error i retorna null
                e.printStackTrace();
                return null;
            }
        } else {
            // Si el format de l'hora no és vàlid, imprimeix un missatge d'error i retorna null
            System.out.println("El format de l'hora no és vàlid: " + cadenaHora);
            return null;
        }
    }


    /**
     * Mètode per comprovar si una hora donada és anterior a l'hora actual.
     *
     * @param stringHora Hora en format "HHmm".
     * @return Cert si l'hora és anterior a l'hora actual, fals en cas contrari.
     */
    public static boolean esHoraAnterior(String stringHora) {
        // Creem un objecte SimpleDateFormat amb el format "HHmm"
        SimpleDateFormat format = new SimpleDateFormat("HHmm");
        try {
            // Convertim la cadena de text a un objecte Time
            Time hora = new Time(format.parse(stringHora).getTime());

            // Obtenim l'hora actual
            Time horaActual = new Time(System.currentTimeMillis());

            // Comprovem si l'hora donada és anterior a l'hora actual
            return hora.before(horaActual);
        } catch (ParseException | java.text.ParseException e) {
            // En cas d'error en la conversió de la cadena de text a Time, imprimim l'error i retornem fals
            e.printStackTrace();
            return false;
        }
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
        SharedPreferences preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();
        SimpleDateFormat format = new SimpleDateFormat(Constants.FORMAT_DATA);

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