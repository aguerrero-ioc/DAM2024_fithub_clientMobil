package antonioguerrero.ioc.fithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Classe que representa l'activitat de login a l'aplicació FitHub.
 *
 * Aquesta classe permet als usuaris iniciar sessió, recuperar contrasenyes i registrar-se.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements ConnexioServidor.OnServerResponseListener {

    private EditText etNomUsuari, etContrasenya;
    private Button btnLogin;
    private CheckBox checkMostrarContrasenya;
    private TextView tvRecuperarContrasenya, tvRegistrar;

    private Context context;
    private ConnexioServidor serverConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialitzar el context
        context = this;

        // Referenciar els elements de la interfície d'usuari
        etNomUsuari = findViewById(R.id.et_nomusuari);
        etContrasenya = findViewById(R.id.et_contrasenya);
        btnLogin = findViewById(R.id.btn_login);
        checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        tvRecuperarContrasenya = findViewById(R.id.tv_oblidat_contrasenya);
        tvRegistrar = findViewById(R.id.tv_registre);

        // Inicialitzar la connexió amb el servidor
        serverConnection = new ConnexioServidor(this);

        // Configurar el clic del botó d'inici de sessió
        btnLogin.setOnClickListener(v -> {
            String nomUsuari = etNomUsuari.getText().toString();
            String contrasenya = etContrasenya.getText().toString();
            serverConnection.sendLoginRequest(nomUsuari, contrasenya);
        });

        // Configurar el CheckBox per mostrar/ocultar la contrasenya
        checkMostrarContrasenya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        // Configurar el clic del TextView per la recuperació de contrasenya
        tvRecuperarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Informar pendent implementar
                Toast.makeText(context, "Implementar recuperació de contrasenya", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el clic del TextView per al registre d'usuaris
        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Informar pendent implementar
                Toast.makeText(context, "Implementar registre d'usuaris", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Mètode que gestiona la resposta del servidor després de l'intent d'inici de sessió.
     * Aquest mètode serà cridat pel servidor per informar sobre l'estat de l'autenticació.
     *
     * @param resposta Resposta del servidor, que pot ser l'èxit de l'autenticació o un error.
     */
    @Override
    public void onServerResponse(String resposta){
        Log.d("LoginActivity", "Resposta del servidor: " + resposta);
        if (resposta != null) {
            String[] parts = resposta.split(",");
            if (parts.length == 3) {
                String tipoUsuari = parts[1]; 
                Log.d("LoginActivity", "Tipus d'usuari: " + tipoUsuari);
                obrirActivitat(tipoUsuari);
            } else {
                // Resposta del servidor incorrecta
                Toast.makeText(this, "Credencials incorrectes", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Error de connexió
            Toast.makeText(this, "Error de connexió", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Mètode que obre l'activitat corresponent segons el tipus d'usuari autenticat.
     *
     * @param tipusUsuari Tipus d'usuari autenticat (client o administrador).
     */
    private void obrirActivitat(String tipusUsuari) {
        // Obrir l'activitat corresponent segons el tipus d'usuari
        Intent intent;
        if (tipusUsuari.equals("client")) {
            // Usuari tipus client
            intent = new Intent(LoginActivity.this, ClientActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Benvingut, client", Toast.LENGTH_SHORT).show();
        } else if (tipusUsuari.equals("admin")) {
            // Usuari tipus admin
            intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "Benvingut, administrador", Toast.LENGTH_SHORT).show();
        } else {
            // Tipus d'usuari desconegut
            Toast.makeText(LoginActivity.this, "No s'ha pogut iniciar sessió. Tipus d'usuari desconegut.", Toast.LENGTH_SHORT).show();
        }
    }
}