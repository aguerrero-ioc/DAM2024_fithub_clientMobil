package antonioguerrero.ioc.fithub.menu.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogin;

public class LoginActivity extends AppCompatActivity implements BasePeticions.respostaServidorListener {

    private EditText etCorreuUsuari, etContrasenya;
    private Context context;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicializar el contexto
        context = this;

        // Referenciar los elementos de la interfaz de usuario
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etContrasenya = findViewById(R.id.et_contrasenya);
        Button btnLogin = findViewById(R.id.btn_login);
        CheckBox checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        TextView tvRecuperarContrasenya = findViewById(R.id.tv_oblidat_contrasenya);
        TextView tvRegistrar = findViewById(R.id.tv_registre);

        // Configurar el clic del botón de inicio de sesión
        btnLogin.setOnClickListener(v -> {
            String nomUsuari = etCorreuUsuari.getText().toString();
            String contrasenya = etContrasenya.getText().toString();
            // Verificar si se han introducido ambos campos
            if (nomUsuari.isEmpty() && contrasenya.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix el nom d'usuari i la contrasenya");
            } else if (nomUsuari.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix el nom d'usuari");
            } else if (contrasenya.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix la contrasenya");
            } else {
                enviarLogin(nomUsuari, contrasenya);
            }
        });

        // Configurar el CheckBox para mostrar/ocultar la contraseña
        checkMostrarContrasenya.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        // Configurar el clic del TextView para la recuperación de contraseña
        tvRecuperarContrasenya.setOnClickListener(v -> mostrarDialegRecuperacioContrasenya());

        // Configurar el clic del TextView para el registro de usuarios
        tvRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistreActivity.class);
            startActivity(intent);
        });
    }

    private void enviarLogin(String correuUsuari, String passUsuari) {

        PeticioLogin peticioLogin = new PeticioLogin(context, correuUsuari, passUsuari, objectOut, objectIn) {
            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }
        };
        peticioLogin.execute();
    }

    public void mostrarDialegRecuperacioContrasenya() {
        View vistaDialeg = LayoutInflater.from(context).inflate(R.layout.dialeg_recuperar_contrasenya, null);
        EditText etCorreu = vistaDialeg.findViewById(R.id.et_correu_recuperacio);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(vistaDialeg)
                .setTitle("Recuperació de Contrasenya")
                .setPositiveButton("Enviar", null)
                .setNegativeButton("Cancel·lar", (dialog, which) -> dialog.dismiss());

        final AlertDialog dialegAlerta = builder.create();
        dialegAlerta.show();

        dialegAlerta.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String correu = etCorreu.getText().toString();
            if (correu.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "El correu electrònic és obligatori");
                return;
            }
            if (!esEmailValid(correu)) {
                Utils.mostrarToast(getApplicationContext(), "El format del correu electrònic és incorrecte");
                return;
            }
            Utils.mostrarToast(getApplicationContext(), "Enviar correu a: " + correu);
            dialegAlerta.dismiss();
        });
    }

    private boolean esEmailValid(String email) {
        String patroEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(patroEmail);
    }

    @Override
    public void respostaServidor(Object resposta) {
        if (resposta != null) {
            String tipusUsuari = Utils.obtenirTipusUsuari(resposta.toString());
            if (!tipusUsuari.isEmpty()) {
                obrirActivitat(tipusUsuari);
            } else {
                Utils.mostrarToast(getApplicationContext(), "Credencials incorrectes");
            }
        } else {
            Utils.mostrarToast(getApplicationContext(), "Error de connexió");
        }
    }


    private void obrirActivitat(String tipusUsuari) {
        Intent intent;
        if (tipusUsuari.equals("client")) {
            intent = new Intent(LoginActivity.this, ClientActivity.class);
            startActivity(intent);
            Utils.mostrarToast(getApplicationContext(), "Benvingut, client");
        } else if (tipusUsuari.equals("admin")) {
            intent = new Intent(LoginActivity.this, AdminActivity.class);
            startActivity(intent);
            Utils.mostrarToast(getApplicationContext(), "Benvingut, administrador");
        } else {
            Utils.mostrarToast(getApplicationContext(), "No s'ha pogut iniciar sessió. Tipus d'usuari desconegut.");
        }
    }
}
