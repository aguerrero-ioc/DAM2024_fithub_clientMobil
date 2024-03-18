package antonioguerrero.ioc.fithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa l'activitat de login a l'aplicació FitHub.
 * Aquesta classe permet als usuaris iniciar sessió, recuperar contrasenyes i registrar-se.
 * Autor: Antonio Guerrero
 */
public class LoginActivity extends AppCompatActivity {
    private static final String IP = "192.168.0.47";
    private static final int PORT = 8080;

    private static final String TAG = LoginActivity.class.getSimpleName();

    private EditText etNomUsuari, etContrasenya;
    private CheckBox checkMostrarContrasenya;
    private TextView tvRecuperarContrasenya, tvRegistrar;
    private Button btnIniciarSessio;

    private SharedPreferences preferencies;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        context = this;
        preferencies = getSharedPreferences("Preferencies", Context.MODE_PRIVATE);

        etNomUsuari = findViewById(R.id.et_nomusuari);
        etContrasenya = findViewById(R.id.et_contrasenya);
        checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        tvRecuperarContrasenya = findViewById(R.id.tv_oblidat_contrasenya);
        tvRegistrar = findViewById(R.id.tv_registre);
        btnIniciarSessio = findViewById(R.id.btn_login);

        btnIniciarSessio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomUsuari = etNomUsuari.getText().toString().trim();
                String contrasenya = etContrasenya.getText().toString().trim();
                login(nomUsuari, contrasenya);
            }
        });

        checkMostrarContrasenya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Si el CheckBox está marcado, mostrar la contraseña
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    // Si el CheckBox no está marcado, ocultar la contraseña
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        tvRecuperarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Informar pendent implementar
                Toast.makeText(context, "Implementar recuperació de contrasenya", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Informar pendent implementar
                Toast.makeText(context, "Implementar registre d'usuaris", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Mètode per iniciar sessió.
     * @param nomUsuari El nom d'usuari.
     * @param contrasenya La contrasenya.
     */
    public void login(final String nomUsuari, final String contrasenya) {
        // Construir la petició en el nou format
        String peticio = "login," + nomUsuari + "," + contrasenya;
        // Enviar la petició al servidor
        enviarPeticioAlServidor(peticio);
    }


    /**
     * Mètode per enviar la petició d'inici de sessió al servidor.
     * @param peticio La petició d'inici de sessió.
     */
    public void enviarPeticioAlServidor(final String peticio) {
        // URL del servidor
        String url = "http://" + IP + ":" + PORT;

        // Crear una petició HTTP POST
        StringRequest peticioString = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resposta) {
                        // Gestionar la resposta del servidor
                        gestionarRespostaLogin(resposta);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestionar els errors de la petició
                        Log.e(TAG, "Error en la petició: " + error.getMessage());
                        Toast.makeText(context, "Error en la petició", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Col·locar els paràmetres en un mapa perquè Volley els enviï en el cos de la petició
                Map<String, String> params = new HashMap<>();
                params.put("peticio", peticio);
                return params;
            }
        };

        // Enviar la petició utilitzant la classe ConnexioServidor existent
        ConnexioServidor.obtenirInstancia(context).enviarPeticio(peticioString);
    }


    /**
     * Mètode per gestionar la resposta del servidor després de l'inici de sessió.
     * @param resposta La resposta del servidor.
     */
    private void gestionarRespostaLogin(String resposta) {
        if (resposta.equals("client") || resposta.equals("administrador")) {
            String idUsuari = new ClientActivity().obtenirIdUsuari(context);
            if (resposta.equals("client")) {
                iniciaActivitatUsuari(idUsuari);
            } else {
                iniciaActivitatAdmin(idUsuari);
            }
        } else {
            Toast.makeText(context, "Usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Mètode per iniciar l'activitat de client.
     * @param idUsuari L'ID de l'usuari.
     */
    private void iniciaActivitatUsuari(String idUsuari) {
        Intent intent = new Intent(context, ClientActivity.class);
        context.startActivity(intent);
        finish();
    }


    /**
     * Mètode per iniciar l'activitat d'administrador.
     * @param idUsuari L'ID de l'usuari.
     */
    private void iniciaActivitatAdmin(String idUsuari) {
        Intent intent = new Intent(context, AdminActivity.class);
        context.startActivity(intent);
        finish();
    }
}
