package antonioguerrero.ioc.fithub.connexio;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

import antonioguerrero.ioc.fithub.peticions.BasePeticions;


/**
 * Classe que gestiona la connexió amb el servidor.
 * <p>
 * Aquesta classe facilita la connexió amb el servidor per enviar peticions de login i escoltar les respostes.
 * El servidor al qual es connecta està configurat amb l'adreça IP i el port especificats com a constants.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConnexioServidor {

    private static final String SERVER_IP = "192.168.0.216";
    private static final int SERVER_PORT = 8080;

    private static respostaServidorListener listener;

    /**
     * Constructor de la classe ConnexioServidor.
     *
     * @param listener Listener per a les respostes del servidor
     */
    public ConnexioServidor(respostaServidorListener listener) {
        this.listener = listener;
    }


    /**
     * Tasca asíncrona per connectar-se al servidor i enviar la petició de login.
     */
    public static class ConnectToServerTask extends AsyncTask<HashMap<String, String>, Void, Object[]> {
        private BasePeticions peticio;

        public ConnectToServerTask(BasePeticions peticio) {
            this.peticio = peticio;
        }
        @Override
        protected Object[] doInBackground(HashMap<String, String>... params) {
            HashMap<String, String> peticioMap = params[0];
            try {
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                // Llegir la resposta inicial del servidor
                String respostaHandshake = in.readLine();
                Log.d("ConnexioServidor", "Resposta del handshake: " + respostaHandshake);

                // Enviar la petició al servidor com un array d'objectes
                out.writeObject(new Object[] {peticioMap});

                // Llegir la resposta del servidor com un array d'objectes
                Object[] resposta = (Object[]) in.readObject();

                // Crear un nou array del tipus correcte
                Class<?>[] tipusObjecte = new Class[]{peticio.obtenirTipusObjecte()};
                Object[] respostaTipusCorrecte = (Object[]) java.lang.reflect.Array.newInstance(tipusObjecte[0], resposta.length);

                // Copiar els objectes de la resposta en el nou array
                System.arraycopy(resposta, 0, respostaTipusCorrecte, 0, resposta.length);

                out.close();
                in.close();
                socket.close();

                return resposta;
            } catch (IOException | ClassNotFoundException e) {
                Log.e("ConnexioServidor", "Error de connexió: " + e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object[] resposta) {
            if (listener != null) {
                String respostaString = Arrays.toString(resposta);
                listener.respostaServidor(respostaString);
            }
        }
    }

    /**
     * Interfície per a escoltar les respostes del servidor.
     */
    public interface respostaServidorListener {
        void respostaServidor(String resposta);
    }
}