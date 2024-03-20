package antonioguerrero.ioc.fithub;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe que gestiona la connexió amb el servidor.
 *
 * Aquesta classe facilita la connexió amb el servidor per enviar peticions de login i escoltar les respostes.
 * El servidor al qual es connecta està configurat amb l'adreça IP i el port especificats com a constants.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConnexioServidor {

    private static final String SERVER_IP = "192.168.0.252";
    private static final int SERVER_PORT = 8080;

    private OnServerResponseListener listener;

    /**
     * Constructor de la classe ConnexioServidor.
     *
     * @param listener Listener per a les respostes del servidor
     */
    public ConnexioServidor(OnServerResponseListener listener) {
        this.listener = listener;
    }

    /**
     * Envia una petició de login al servidor.
     *
     * @param nomUsuari   Nom d'usuari
     * @param contrasenya Contrasenya de l'usuari
     */
    public void sendLoginRequest(String nomUsuari, String contrasenya) {
        String missatge = "login," + nomUsuari + "," + contrasenya;
        new ConnectToServerTask().execute(missatge);
    }

    /**
     * Tasca asíncrona per connectar-se al servidor i enviar la petició de login.
     */
    private class ConnectToServerTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String missatge = params[0];
            try {
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Llegir el handshake
                String handshakeResponse = in.readLine();
                Log.d("ConnexioServidor", "Resposta del handshake: " + handshakeResponse);

                // Enviar la petició
                out.println(missatge);
                Log.d("ConnexioServidor", "Enviant petició de login: " + missatge);

                // Llegir la resposta del servidor
                String resposta = in.readLine();

                out.close();
                in.close();
                socket.close();

                return resposta;
            } catch (IOException e) {
                Log.e("ConnexioServidor", "Error de connexió: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String resposta) {
            if (listener != null) {
                listener.onServerResponse(resposta);
            }
        }
    }

    /**
     * Interfície per a escoltar les respostes del servidor.
     */
    public interface OnServerResponseListener {
        /**
         * Métode cridat quan s'ha rebut una resposta del servidor.
         *
         * @param resposta Resposta rebuda del servidor
         */
        void onServerResponse(String resposta);
    }
}
