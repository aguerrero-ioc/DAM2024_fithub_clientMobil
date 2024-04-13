package antonioguerrero.ioc.fithub.peticions;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe abstracta base per a les peticions al servidor.
 * Conté mètodes per establir la connexió amb el servidor i processar les respostes.
 */
public abstract class BasePeticions {

    protected respostaServidorListener listener;
    protected Socket socket;
    protected ObjectOutputStream objectOut;

    /**
     * Constructor de la classe BasePeticions.
     *
     * @param listener  L'objecte que escoltarà les respostes del servidor.
     * @param objectOut
     * @param objectIn
     */
    public BasePeticions(respostaServidorListener listener, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        this.listener = listener;
    }

    public void conectar(String direccionServidor, int puertoServidor) throws IOException {
        this.socket = new Socket(direccionServidor, puertoServidor);
        this.objectOut = new ObjectOutputStream(socket.getOutputStream());
    }



    protected void enviarPeticioHashmap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket socket = new Socket("192.168.0.252", 8080);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                    Object[] peticio = new Object[4];
                    peticio[0] = operacio;
                    peticio[1] = nomObjecte;
                    peticio[2] = objecteMapa;
                    peticio[3] = idSessio;

                    objectOut.writeObject(peticio);
                    objectOut.flush();


                    String peticioString = Arrays.toString(peticio);
                    Log.d("PeticioInfo:", "Petición enviada: " + peticioString);
                } catch (IOException e) {
                    Log.e("PeticioError:", "Error al enviar la petición", e);
                }
                return null;
            }
        }.execute();
    }

    protected void enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) {
    new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Socket socket = new Socket("192.168.0.252", 8080);
                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                Object[] peticio = new Object[4];
                peticio[0] = operacio;
                peticio[1] = dada1;
                peticio[2] = dada2;
                peticio[3] = idSessio;

                objectOut.writeObject(peticio);
                objectOut.flush();


                String peticioString = Arrays.toString(peticio);
                Log.d("PeticioInfo:", "Petición enviada: " + peticioString);
            } catch (IOException e) {
                Log.e("PeticioError:", "Error al enviar la petición", e);
            }
            return null;
        }
    }.execute();
}

    /**
     * Mètode abstracte per a executar la petició.
     */
    public abstract void execute();

    /**
     * Mètode abstracte per a obtenir el tipus de l'objecte.
     * @return La classe de l'objecte.
     */
    public abstract Class<?> obtenirTipusObjecte();


    /**
     * Mètode abstracte per a gestionar la resposta del servidor.
     * @param resposta La resposta rebuda del servidor.
     */
    public abstract void respostaServidor(Object resposta);

    /**
     * Interfície per a escoltar les respostes del servidor.
     */
    public interface respostaServidorListener {
        /**
         * Mètode cridat quan es rep una resposta del servidor.
         * @param resposta La resposta rebuda del servidor.
         */
        void respostaServidor(Object resposta);
    }


    /*
    public void enviarPeticioString(String operacio, String dada1, String dada2, String sessioID) {
        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = dada1;
        peticio[2] = dada2;
        peticio[3] = sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }


    public void enviarPeticio(String operacio, String entitat, Object dades, String sessioID) {
        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = entitat;
        peticio[2] = dades;
        peticio[3] = sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }*/
}
