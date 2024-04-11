package antonioguerrero.ioc.fithub.peticions;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Arrays;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Classe abstracta base per a les peticions al servidor.
 * Conté mètodes per establir la connexió amb el servidor i processar les respostes.
 */
public abstract class BasePeticions {

    protected respostaServidorListener listener;

    /**
     * Constructor de la classe BasePeticions.
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public BasePeticions(respostaServidorListener listener) {
        this.listener = listener;
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
     * @param entitat L'entitat a la que es realitza l'operació.
     * @param dades Les dades a enviar.
     * @param sessioID L'identificador de la sessió.
     * @param etiqueta L'etiqueta per a identificar la petició.
     */
    public void enviarPeticio(String operacio, String entitat, Object dades, String sessioID, String etiqueta) {
        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = entitat;
        peticio[2] = dades;
        peticio[3] = sessioID;

        // Registrar la petición en el log de depuración
        Log.d(etiqueta, "Petición enviada: " + Arrays.toString(peticio));

        new ConnexioServidor.ConnectToServerTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }
}
