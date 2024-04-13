package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;

/**
 * Classe per eliminar una reserva.
 * Hereta de la classe BasePeticions.
 *
 * Aquesta classe és la que s'encarrega de fer la petició al servidor per eliminar una reserva.
 *
 * @Author Antonio Guerrero
 * @Version 1.0
 */
public class EliminarReserva extends BasePeticions {

    private int IDReserva;
    private Context context;

    private static final String ETIQUETA = "EliminarReserva";


    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe EliminarReserva.
     *
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public EliminarReserva(BasePeticions.respostaServidorListener listener, Context context, int IDReserva) {
        super(listener);
        this.context = context;
        this.IDReserva = IDReserva;
    }

    /**
     * Mètode per eliminar una reserva.
     */
    public void eliminarReserva() {
        enviarPeticio("delete", "reserva", IDReserva, this.sessioID);
    }

    /**
     * Mètode per obtenir el tipus de l'objecte.
     *
     * @return La classe de l'objecte.
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode per executar la petició.
     */
    @Override
    public void execute() {
        eliminarReserva();
    }

    /**
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    @Override
    public void respostaServidor(Object resposta) {
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("true")) {
                Utils.mostrarToast(context, "Reserva eliminada correctament");
            } else {
                Utils.mostrarToast(context, "Error en eliminar la reserva");
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }
}