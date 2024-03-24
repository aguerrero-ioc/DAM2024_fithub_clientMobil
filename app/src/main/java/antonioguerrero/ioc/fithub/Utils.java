package antonioguerrero.ioc.fithub;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe d'utilitats amb mètodes útils per a diverses funcionalitats.
 */
public class Utils {

    /**
     * Obté la data actual en format de cadena.
     *
     * @return Data actual en format "yyyy-MM-dd"
     */
    public static String obtenirDataActual() {
        // Obté la data actual
        Date dataActual = Calendar.getInstance().getTime();

        // Formateja la data en el format desitjat
        SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd");
        return formatData.format(dataActual);
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
        SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm:ss");
        return formatHora.format(horaActual);
    }

    /**
     * Obté el nom del client actual a partir de la resposta del servidor.
     *
     * @param resposta Resposta del servidor
     * @return Nom del client actual
     */
    public static String obtenirNomClientActual(String resposta) {
        // Verifica si la resposta del servidor no és nul·la i té els paràmetres esperats
        if (resposta != null) {
            String[] parts = resposta.split(",");
            // Comprova si la resposta conté els paràmetres esperats (només hi hauria 3 parts si ho fa)
            if (parts.length == 3) {
                // Retorna el nom del client que es troba a la primera posició
                return parts[0];
            }
        }
        // En cas contrari, retorna un valor predeterminat o buit
        return ""; // Opcional: pots retornar un valor predeterminat o llançar una excepció segons la teva lògica
    }

    /**
     * Obté el tipus d'usuari actual a partir de la resposta del servidor.
     *
     * @param resposta Resposta del servidor
     * @return Tipus d'usuari actual
     */
    public static String obtenirTipusUsuariActual(String resposta) {
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

}
