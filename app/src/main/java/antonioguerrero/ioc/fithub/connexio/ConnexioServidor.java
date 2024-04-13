package antonioguerrero.ioc.fithub.connexio;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

import antonioguerrero.ioc.fithub.peticions.BasePeticions;

public class ConnexioServidor {
    public static final String SERVIDOR_IP = "192.168.0.252";
    public static final int SERVIDOR_PORT = 8080;

    public static class ConnectToServerTask extends AsyncTask<Object[], Void, Object[]> {
        private BasePeticions.respostaServidorListener listener;

        public ConnectToServerTask(BasePeticions.respostaServidorListener listener) {
            this.listener = listener;
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
                try {
                    listener.respostaServidor(resposta);
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
