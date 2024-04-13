package antonioguerrero.ioc.fithub.peticions;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

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
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public BasePeticions(respostaServidorListener listener) {
        this.listener = listener;
    }

    public void conectar(String direccionServidor, int puertoServidor) throws IOException {
        this.socket = new Socket(direccionServidor, puertoServidor);
        this.objectOut = new ObjectOutputStream(socket.getOutputStream());
    }

    protected void enviarPeticioAsync(Object[] peticio) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    objectOut.writeObject(peticio);
                    objectOut.flush();
                } catch (IOException e) {
                    Log.e("BasePeticions", "Error al enviar la petición", e);
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

    /**
     * Mètode per enviar una petició al servidor.
     * @param operacio L'operació a realitzar.
     * @param dada1 La primera dada a enviar.
     * @param dada2 La segona dada a enviar.
     * @param sessioID L'identificador de la sessió.
     */
    public void enviarPeticioString(String operacio, String dada1, String dada2, String sessioID) {
        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = dada1;
        peticio[2] = dada2;
        peticio[3] = sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per enviar una petició al servidor.
     * @param operacio L'operació a realitzar.
     * @param entitat L'entitat a la que es realitza l'operació.
     * @param dades Les dades a enviar.
     * @param sessioID L'identificador de la sessió.
     */
    public void enviarPeticio(String operacio, String entitat, Object dades, String sessioID) {
        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = entitat;
        peticio[2] = dades;
        peticio[3] = sessioID;

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }
}
