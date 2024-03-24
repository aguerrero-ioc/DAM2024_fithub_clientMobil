package antonioguerrero.ioc.fithub.missatges;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnexioServidorMissatges {

    private static final String SERVER_URL = "https://tu_servidor.com/enviar_mensaje";

    public interface ServerResponseListener {
        void onServerResponse(String response);
    }

    public static void sendMessage(String remitente, String contenido, String fecha, String hora, ServerResponseListener listener) {
        new SendMessageTask(remitente, contenido, fecha, hora, listener).execute();
    }

    private static class SendMessageTask extends AsyncTask<Void, Void, String> {
        private String remitente;
        private String contenido;
        private String fecha;
        private String hora;
        private ServerResponseListener listener;

        public SendMessageTask(String remitente, String contenido, String fecha, String hora, ServerResponseListener listener) {
            this.remitente = remitente;
            this.contenido = contenido;
            this.fecha = fecha;
            this.hora = hora;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String response = null;

            try {
                URL url = new URL(SERVER_URL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                // Construir el cuerpo de la solicitud
                String requestBody = "remitente=" + remitente + "&contenido=" + contenido + "&fecha=" + fecha + "&hora=" + hora;

                // Escribir datos en la conexión
                OutputStream outputStream = urlConnection.getOutputStream();
                outputStream.write(requestBody.getBytes());
                outputStream.close();

                // Leer la respuesta del servidor
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                response = stringBuilder.toString();
            } catch (IOException e) {
                Log.e("ServerConnection", "Error en la conexión: " + e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("ServerConnection", "Error cerrando el reader: " + e.getMessage());
                    }
                }
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (listener != null) {
                listener.onServerResponse(response);
            }
        }
    }
}
