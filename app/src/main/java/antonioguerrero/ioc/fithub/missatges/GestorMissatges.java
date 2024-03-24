package antonioguerrero.ioc.fithub.missatges;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe encarregada de gestionar l'enviament i recepció de missatges amb el servidor.
 */
public class GestorMissatges {

    private static final String URL_SERVIDOR = "http://192.168.0.252:8080";

    /**
     * Mètode per enviar un missatge al servidor.
     *
     * @param remitent  El remitent del missatge.
     * @param contingut El contingut del missatge.
     * @param data      La data del missatge.
     * @param hora      L'hora del missatge.
     * @param listener  El listener per gestionar la resposta del servidor.
     */
    public static void enviarMissatge(String remitent, String contingut, String data, String hora, GestorRespostaServidor listener) {
        new TascaEnviarMissatge(remitent, contingut, data, hora, listener).execute();
    }

    /**
     * Mètode per rebre un missatge del servidor.
     *
     * @param listener El listener per gestionar la resposta del servidor.
     */
    public static void rebreMissatge(GestorRespostaServidor listener) {
        new TascaRebreMissatge(listener).execute();
    }

    /**
     * Interface per gestionar les respostes del servidor.
     */
    public interface GestorRespostaServidor  {
        /**
         * Mètode cridat quan s'ha rebut una resposta del servidor.
         *
         * @param resposta La resposta rebuda del servidor.
         */
        void onServerResponse(String resposta);
    }

    private static class TascaEnviarMissatge extends AsyncTask<Void, Void, String> {
        private String remitent;
        private String contingut;
        private String data;
        private String hora;
        private GestorRespostaServidor listener;

        public TascaEnviarMissatge(String remitent, String contingut, String data, String hora, GestorRespostaServidor listener) {
            this.remitent = remitent;
            this.contingut = contingut;
            this.data = data;
            this.hora = hora;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection connexio = null;
            BufferedReader lector = null;
            String resposta = null;

            try {
                URL url = new URL(URL_SERVIDOR);
                connexio = (HttpURLConnection) url.openConnection();
                connexio.setRequestMethod("POST");
                connexio.setDoOutput(true);

                // Construir la petició
                String peticio = "remitent=" + remitent + "&contingut=" + contingut + "&data=" + data + "&hora=" + hora;

                // Escriure dades en la connexió
                OutputStream outputStream = connexio.getOutputStream();
                outputStream.write(peticio.getBytes());
                outputStream.close();

                // Llegir la resposta del servidor
                lector = new BufferedReader(new InputStreamReader(connexio.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String linia;
                while ((linia = lector.readLine()) != null) {
                    stringBuilder.append(linia).append("\n");
                }
                resposta = stringBuilder.toString();
            } catch (IOException e) {
                Log.e("GestorMissatges", "Error en la connexió: " + e.getMessage());
            } finally {
                if (connexio != null) {
                    connexio.disconnect();
                }
                if (lector != null) {
                    try {
                        lector.close();
                    } catch (IOException e) {
                        Log.e("GestorMissatges", "Error tancant el lector: " + e.getMessage());
                    }
                }
            }

            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta) {
            if (listener != null) {
                listener.onServerResponse(resposta);
            }
        }
    }

    private static class TascaRebreMissatge extends AsyncTask<Void, Void, String> {
        private GestorRespostaServidor listener;

        public TascaRebreMissatge(GestorRespostaServidor listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // Aquí es podria implementar la lògica per rebre un missatge del servidor
            // En aquest exemple, simplement generem una resposta aleatòria del servidor
            return "Missatge del servidor: Hola des del servidor!";
        }

        @Override
        protected void onPostExecute(String resposta) {
            if (listener != null) {
                listener.onServerResponse(resposta);
            }
        }
    }
}
