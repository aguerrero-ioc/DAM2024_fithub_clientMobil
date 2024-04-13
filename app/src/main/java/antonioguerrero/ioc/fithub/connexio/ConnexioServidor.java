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

    public static final String SERVIDOR_IP = "192.168.0.252";
    public static final int SERVIDOR_PORT = 8080;

    protected Socket socket;
    protected ObjectOutputStream objectOut;

    private static respostaServidorListener listener;

    /**
     * Constructor de la classe ConnexioServidor.
     *
     * @param listener Listener per a les respostes del servidor
     */
    public ConnexioServidor(respostaServidorListener listener) {
        this.listener = listener;
    }

    public void Conectar(String direccionServidor, int puertoServidor) throws IOException {
        this.socket = new Socket(direccionServidor, puertoServidor);
        this.objectOut = new ObjectOutputStream(socket.getOutputStream());
    }


    /**
     * Tasca asíncrona per connectar-se al servidor i enviar la petició de login.
     */
    public static class ConnectToServerTask extends AsyncTask<Object[], Void, Object[]> {
        private BasePeticions peticio;

        public ConnectToServerTask(BasePeticions peticio) {
            this.peticio = peticio;
        }
        @Override
        protected Object[] doInBackground(Object[]... params) {
            Object[] peticioArray = params[0];
            try {
                Socket socket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);

                ObjectOutputStream sortida = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());

                // Llegir la resposta inicial del servidor
                String respostaHandshake = (String) entrada.readObject();
                Log.d("ConnexioServidor", "Resposta del handshake: " + respostaHandshake);

                // Enviar la petició al servidor com un array d'objectes
                sortida.writeObject(peticioArray);

                // Llegir la resposta del servidor com un array d'objectes
                Object[] resposta = (Object[]) entrada.readObject();

                // Crear un nou array del tipus correcte
                Class<?>[] tipusObjecte = new Class[]{peticio.obtenirTipusObjecte()};
                Object[] respostaTipusCorrecte = (Object[]) java.lang.reflect.Array.newInstance(tipusObjecte[0], resposta.length);

                // Copiar els objectes de la resposta en el nou array
                System.arraycopy(resposta, 0, respostaTipusCorrecte, 0, resposta.length);

                sortida.close();
                entrada.close();
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