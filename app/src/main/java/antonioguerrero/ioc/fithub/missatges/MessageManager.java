package antonioguerrero.ioc.fithub.missatges;

import android.util.Log;

/**
 * Classe encarregada de gestionar l'enviament i recepció de missatges amb el servidor.
 */
public class MessageManager {

    /**
     * Mètode per enviar un missatge al servidor.
     *
     * @param remitente  El remitent del missatge.
     * @param contenido  El contingut del missatge.
     * @param fecha      La data del missatge.
     * @param hora       L'hora del missatge.
     * @param listener   El listener per gestionar la resposta del servidor.
     */
    public static void enviarMissatge(String remitente, String contenido, String fecha, String hora, ServerResponseListener listener) {
        // Aquí va la lògica per enviar el missatge al servidor
        // Simplement per exemple, loguejem el missatge enviat
        String message = "Remitente: " + remitente + ", Contenido: " + contenido + ", Fecha: " + fecha + ", Hora: " + hora;
        Log.d("MessageManager", "Mensaje enviado al servidor: " + message);

        // Simulem una resposta del servidor (en aquest cas, una resposta aleatòria)
        String response = "Respuesta del servidor: OK";

        // Notifiquem al listener la resposta del servidor
        listener.onServerResponse(response);
    }

    /**
     * Interface per gestionar les respostes del servidor.
     */
    public interface ServerResponseListener {
        /**
         * Mètode cridat quan s'ha rebut una resposta del servidor.
         *
         * @param response La resposta rebuda del servidor.
         */
        void onServerResponse(String response);
    }

    /**
     * Mètode per rebre un missatge del servidor.
     *
     * @param listener El listener per gestionar la resposta del servidor.
     */
    public static void receiveMessage(ServerResponseListener listener) {
        // Aquí va la lògica per rebre un missatge del servidor
        // En aquest exemple, simplement generem una resposta aleatòria del servidor
        String response = "Missatge del servidor: Hola des del servidor!";

        // Notifiquem al listener la resposta del servidor
        listener.onServerResponse(response);
    }
}
