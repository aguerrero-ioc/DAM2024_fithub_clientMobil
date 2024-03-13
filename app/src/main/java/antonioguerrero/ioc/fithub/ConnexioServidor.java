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

public class ConnexioServidor {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 8080;

    private static ConnexioServidor instancia;
    private RequestQueue cuaPeticions;
    private static Context context;

    // Constructor privat per a evitar instàncies externes
    private ConnexioServidor(Context context) {
        this.context = context;
        cuaPeticions = obtenirCuaPeticions();
    }

    // Mètode per a obtenir la instància única de ConnexioServidor
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

    //Mètode per a afegir una petició a la cua de peticions
    public <T> void afegirAPeticions(Request<T> peticio) {
        obtenirCuaPeticions().add(peticio);
    }

    // Mètode per a enviar una sol·licitud al servidor
    public void enviarSolicitud(StringRequest solicitud) {
        afegirAPeticions(solicitud);
    }

    // Mètode per a construir la URL a partir del endpoint proporcionat
    public String construirUrl(String endpoint) {
        return "http://" + IP + ":" + PORT + "/" + endpoint;
    }

    public interface VolleyCallback {
        void onSuccess(JSONObject response);
        void onError(VolleyError error);
    }

    // Mètode per a tancar la sessió de l'usuari (en cas de ser necessari)
    public void logout(String userId, String logoutUrl, final VolleyCallback callback) {
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", userId);
         } catch (JSONException e) {
            e.printStackTrace();
        }

        // Fer una sol·licitud POST al servidor per a informar del tancament de sessió
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

        // Afegir a sol·licitud a la cua de sol·licituds
        afegirAPeticions(logoutRequest);
    }
}
