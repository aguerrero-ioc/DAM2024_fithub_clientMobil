package antonioguerrero.ioc.fithub;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Classe que gestiona la connexió amb el servidor utilitzant la biblioteca Volley.
 * Aquesta classe proporciona funcionalitats per enviar peticions al servidor i gestionar les respostes.
 * Autor: Antonio Guerrero
 */
public class ConnexioServidor {
    private static final String IP = "192.168.0.47";
    private static final int PORT = 8080;

    private static ConnexioServidor instancia;
    private RequestQueue cuaPeticions;
    private static Context context;

    // Constructor privat per a evitar instàncies externes
    private ConnexioServidor(Context context) {
        this.context = context;
        cuaPeticions = obtenirCuaPeticions();
    }

    /**
     * Mètode per obtenir la instància única de ConnexioServidor.
     * @param context Context de l'aplicació.
     * @return La instància única de ConnexioServidor.
     */
    public static synchronized ConnexioServidor obtenirInstancia(Context context) {
        if (instancia == null) {
            instancia = new ConnexioServidor(context);
        }
        return instancia;
    }

    // Mètode per a obtenir la cua de peticions de Volley
    private RequestQueue obtenirCuaPeticions() {
        if (cuaPeticions == null) {
            cuaPeticions = Volley.newRequestQueue(context.getApplicationContext());
        }
        return cuaPeticions;
    }

    /**
     * Mètode per afegir una petició a la cua de peticions de Volley.
     * @param peticio La petició a afegir a la cua.
     * @param <T> El tipus de la petició.
     */
    public <T> void afegirAPeticions(Request<T> peticio) {
        obtenirCuaPeticions().add(peticio);
    }

    /**
     * Mètode per enviar una petició al servidor.
     * @param peticio La petició a enviar.
     */
    public void enviarPeticio(StringRequest peticio) {
        afegirAPeticions(peticio);
    }

    /**
     * Mètode per construir la URL a partir de l'endpoint proporcionat.
     * @param endpoint L'endpoint de la URL.
     * @return La URL completa.
     */
    public String construirUrl(String endpoint) {
        return "http://" + IP + ":" + PORT + "/" + endpoint;
    }

    /**
     * Interfície per gestionar les respostes de les peticions a Volley.
     */
    public interface VolleyCallback {
        /**
         * Mètode cridat quan la petició s'ha completat amb èxit.
         * @param response La resposta del servidor.
         */
        void onSuccess(JSONObject response);

        /**
         * Mètode cridat quan hi ha hagut un error durant la petició.
         * @param error El missatge d'error.
         */
        void onError(VolleyError error);
    }

    /**
     * Mètode per tancar la sessió de l'usuari al servidor.
     * @param userId L'identificador de l'usuari.
     * @param logoutUrl La URL de tancament de sessió.
     * @param callback El callback per gestionar la resposta del servidor.
     */
    public void logout(String userId, String logoutUrl, final VolleyCallback callback) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Fer una petició POST al servidor per a informar del tancament de sessió
        JsonObjectRequest logoutRequest = new JsonObjectRequest(Request.Method.POST, logoutUrl, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error);
            }
        });

        // Afegir a petició a la cua de peticions
        afegirAPeticions(logoutRequest);
    }
}
