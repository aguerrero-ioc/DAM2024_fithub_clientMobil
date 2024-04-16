package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;

/**
 * Classe que realitza la petició al servidor per obtenir totes les reserves d'un usuari
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class ConsultarTotesReserves extends ConnexioServidor {

    private Context context;
    private Usuari usuari;
    private static final String ETIQUETA = "ConsultarTotesReserves";

    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    /**
     * Constructor de la classe
     * @param listener Listener que escolta la resposta del servidor
     * @param context Context de l'aplicació
     * @param usuari Usuari que vol consultar les seves reserves
     */
    public ConsultarTotesReserves(respostaServidorListener listener, Context context, Usuari usuari) {
        super(listener);
        this.context = context;
        this.usuari = usuari;
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    /**
     * Mètode que envia la petició al servidor per obtenir totes les reserves d'un usuari
     * @throws ConnectException
     */
    public void consultarTotesReserves() throws ConnectException {
        String IDUsuariString = Integer.toString(usuari.getIDusuari());

        enviarPeticioString("selectAll", "reserva", IDUsuariString, this.sessioID);
    }

    /**
     * Mètode que rep la resposta del servidor
     * @return Objecte amb la resposta del servidor
     * @throws Exception
     */
    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    /**
     * Mètode que processa la resposta del servidor
     * @throws Exception
     */
    @Override
    public void execute() throws ConnectException {
        consultarTotesReserves();
    }

    /**
     * Mètode que processa la resposta del servidor
     * @param response Resposta del servidor
     * @return Llista amb les reserves de l'usuari
     */
    @Override
    public List<HashMap<String, String>> respostaServidor(Object response) {
        // PENDENT
        return null;
    }
}