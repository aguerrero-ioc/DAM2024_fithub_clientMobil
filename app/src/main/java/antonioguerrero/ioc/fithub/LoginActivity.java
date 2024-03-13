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

public class LoginActivity extends AppCompatActivity {
    private static final String IP = "127.0.0.1";
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
                // Implementar la lògica per recuperar la contrasenya
                Toast.makeText(context, "Implementar recuperació de contrasenya", Toast.LENGTH_SHORT).show();
            }
        });

        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implementar la lògica per registrar nous usuaris
                Toast.makeText(context, "Implementar registre d'usuaris", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Mètode per iniciar la sessió
    public void login(final String nomUsuari, final String contrasenya) {
        // Construir la sol·licitud en el nou format
        String peticio = "login," + nomUsuari + "," + contrasenya;
        // Enviar la sol·licitud al servidor
        enviarSolicitudAlServidor(peticio);
    }


    // Mètode per enviar la sol·licitud al servidor
    public void enviarSolicitudAlServidor(final String peticio) {
        // URL del servidor
        String url = "http://" + IP + ":" + PORT;

        // Crear una sol·licitud HTTP POST
        StringRequest solicitudString = new StringRequest(Request.Method.POST, url,
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
                        // Gestionar els errors de la sol·licitud
                        Log.e(TAG, "Error en la sol·licitud: " + error.getMessage());
                        Toast.makeText(context, "Error en la sol·licitud", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Col·locar els paràmetres en un mapa perquè Volley els enviï en el cos de la sol·licitud
                Map<String, String> params = new HashMap<>();
                params.put("peticio", peticio);
                return params;
            }
        };

        // Enviar la sol·licitud utilitzant la classe ConnexioServidor existent
        ConnexioServidor.obtenirInstancia(context).enviarSolicitud(solicitudString);
    }


    // Mètode per gestionar la resposta de l'inici de sessió
    private void gestionarRespostaLogin(String resposta) {
        if (resposta.equals("usuari") || resposta.equals("administrador")) {
            String idUsuari = new UserActivity().obtenirIdUsuari(context);
            if (resposta.equals("usuari")) {
                iniciaActivitatUsuari(idUsuari);
            } else {
                iniciaActivitatAdmin(idUsuari);
            }
        } else {
            Toast.makeText(context, "Usuari o contrasenya incorrectes", Toast.LENGTH_SHORT).show();
        }
    }



    // Mètode per iniciar l'activitat d'usuari
    private void iniciaActivitatUsuari(String idUsuari) {
        Intent intent = new Intent(context, UserActivity.class);
        context.startActivity(intent);
        finish();
    }

    // Mètode per iniciar l'activitat d'administrador
    private void iniciaActivitatAdmin(String idUsuari) {
        Intent intent = new Intent(context, AdminActivity.class);
        context.startActivity(intent);
        finish();
    }
}
