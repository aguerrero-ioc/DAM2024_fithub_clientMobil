package antonioguerrero.ioc.fithub.peticions;

import android.os.AsyncTask;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;

/**
 * Classe per gestionar les peticions relacionades amb les reserves.
 * Hereta de la classe BasePeticions.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioReserva extends BasePeticions {

    /**
     * Constructor de la classe PeticioReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public PeticioReserva(OnServerResponseListener listener) {
        super(listener);
    }

    /**
     * Mètode per crear una nova reserva.
     *
     * @param reserva L'objecte Reserva que representa la nova reserva.
     */
    public void crearReserva(Reserva reserva) {
        String peticio = "insert,reserva," +
                reserva.getId() + "," +
                reserva.getUsuari().getUsuariID() + "," +
                reserva.getInstallacio().getId() + "," +
                reserva.getData() + "," +
                reserva.getHora() + "," +
                reserva.getDurada() + "," +
                reserva.getNombrePersones() + "," +
                reserva.getPreu() + "," +
                reserva.getEstat();

        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per modificar les dades d'una reserva.
     *
     * @param reserva L'objecte Reserva amb les dades modificades.
     */
    public void modificarReserva(Reserva reserva) {
        String peticio = "update,reserva," +
                reserva.getId() + "," +
                reserva.getUsuari().getUsuariID() + "," +
                reserva.getInstallacio().getId() + "," +
                reserva.getData() + "," +
                reserva.getHora() + "," +
                reserva.getDurada() + "," +
                reserva.getNombrePersones() + "," +
                reserva.getPreu() + "," +
                reserva.getEstat();

        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per consultar les dades d'una reserva.
     *
     * @param idReserva L'ID de la reserva a consultar.
     */
    public void consultarReserva(int idReserva) {
        String peticio = "(select,reserva," + idReserva;
        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);
    }

    /**
     * Mètode per eliminar una reserva.
     *
     * @param idReserva L'ID de la reserva a eliminar.
     */
    public void eliminarReserva(int idReserva) {
        String peticio = "delete,reserva," + idReserva;
        new ConnexioServidor.ConnectToServerTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, peticio);

        gestionarResposta(resposta);
    }

    @Override
    protected void execute() {
        // Aquest mètode no s'utilitza en aquesta classe ja que cada operació específica té el seu propi mètode.
    }

    @Override
    protected void gestionarResposta(String resposta) {
        if (resposta != null && resposta.equals("confirmacio")) {
            // Mostrar Toast de confirmación
            Utils.Toast.makeText(context, "Operació realitzada amb èxit", Toast.LENGTH_SHORT).show();
        } else {
            // Mostrar Toast de error
            Toast.makeText(context, "Error en l'operació", Toast.LENGTH_SHORT).show();
        }
    }
}
