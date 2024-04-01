package antonioguerrero.ioc.fithub.peticions;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;

/**
 * Classe per gestionar les peticions relacionades amb les reserves.
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
        StringBuilder missatgeBuilder = new StringBuilder("insert,reserva,");
        missatgeBuilder.append(reserva.getId()).append(",");
        missatgeBuilder.append(reserva.getUsuari().getUsuariID()).append(",");
        missatgeBuilder.append(reserva.getInstallacio().getId()).append(",");
        missatgeBuilder.append(reserva.getData()).append(",");
        missatgeBuilder.append(reserva.getHora()).append(",");
        missatgeBuilder.append(reserva.getDurada()).append(",");
        missatgeBuilder.append(reserva.getNombrePersones()).append(",");
        missatgeBuilder.append(reserva.getPreu()).append(",");
        missatgeBuilder.append(reserva.getEstat());

        new ConnexioServidor.ConnectToServerTask().execute(missatgeBuilder.toString());
    }

    /**
     * Mètode per modificar les dades d'una reserva.
     *
     * @param reserva L'objecte Reserva amb les dades modificades.
     */
    public void modificarReserva(Reserva reserva) {
        StringBuilder missatgeBuilder = new StringBuilder("update,reserva,");
        missatgeBuilder.append(reserva.getId()).append(",");
        missatgeBuilder.append(reserva.getUsuari().getUsuariID()).append(",");
        missatgeBuilder.append(reserva.getInstallacio().getId()).append(",");
        missatgeBuilder.append(reserva.getData()).append(",");
        missatgeBuilder.append(reserva.getHora()).append(",");
        missatgeBuilder.append(reserva.getDurada()).append(",");
        missatgeBuilder.append(reserva.getNombrePersones()).append(",");
        missatgeBuilder.append(reserva.getPreu()).append(",");
        missatgeBuilder.append(reserva.getEstat());

        new ConnexioServidor.ConnectToServerTask().execute(missatgeBuilder.toString());
    }

    /**
     * Mètode per consultar les dades d'una reserva.
     *
     * @param idReserva L'ID de la reserva a consultar.
     */
    public void consultarReserva(int idReserva) {
        String missatge = "(select,reserva," + idReserva + ")";
        new ConnexioServidor.ConnectToServerTask().execute(missatge);
    }

    /**
     * Mètode per eliminar una reserva.
     *
     * @param idReserva L'ID de la reserva a eliminar.
     */
    public void eliminarReserva(int idReserva) {
        String missatge = "delete,reserva," + idReserva;
        new ConnexioServidor.ConnectToServerTask().execute(missatge);
    }

    @Override
    protected void execute() {
        // Aquest mètode no s'utilitza en aquesta classe ja que cada operació específica té el seu propi mètode.
    }
}
