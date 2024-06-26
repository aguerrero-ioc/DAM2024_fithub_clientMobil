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

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogin;

/**
 * Activitat per iniciar sessió a l'aplicació.
 * <p>
 * Aquesta activitat permet als usuaris iniciar sessió amb el seu correu electrònic i contrasenya.
 * Si l'usuari no té un compte, pot registrar-se fent clic a l'enllaç de registre.
 * Si l'usuari ha oblidat la contrasenya, pot recuperar-la fent clic a l'enllaç de recuperació de contrasenya.
 * <p>
 * Aquesta activitat és l'activitat principal de l'aplicació.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity  {
    private EditText etCorreuUsuari, etContrasenya;
    private Context context;

    /**
     * Mètode que s'executa en crear l'activitat.
     * <p>
     * Aquest mètode s'executa en crear l'activitat i s'encarrega de configurar els elements de la interfície d'usuari.
     * Configura els clics dels botons i els enllaços de la interfície d'usuari.
     *
     * @param savedInstanceState Estat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialitzar el context
        context = this;

        // Referenciar els elements de la interfície d'usuari
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etContrasenya = findViewById(R.id.et_contrasenya);
        Button btnLogin = findViewById(R.id.btn_login);
        CheckBox checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        TextView tvRecuperarContrasenya = findViewById(R.id.tv_oblidat_contrasenya);
        TextView tvRegistrar = findViewById(R.id.tv_registre);

        // Configurar el clic del botó d'inici de sessió
        btnLogin.setOnClickListener(v -> {
            String correuUsuari = etCorreuUsuari.getText().toString();
            String contrasenya = etContrasenya.getText().toString();
            // Verificar si s'han introduït tots dos camps
            if (correuUsuari.isEmpty() && contrasenya.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix el nom d'usuari i la contrasenya");
            } else if (correuUsuari.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix el nom d'usuari");
            } else if (contrasenya.isEmpty()) {
                Utils.mostrarToast(getApplicationContext(), "Introdueix la contrasenya");
            } else if (!Utils.esEmailValid(correuUsuari)) {
                Utils.mostrarToast(getApplicationContext(), "El format del correu electrònic és incorrecte");
            } else {
                enviarLogin(correuUsuari, contrasenya);
            }
        });

        // CheckBox per mostrar/ocultar la contrasenya
        checkMostrarContrasenya.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            } else {
                etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });

        // Configurar el clic del TextView per a la recuperació de contrasenya
        tvRecuperarContrasenya.setOnClickListener(v -> mostrarDialegRecuperacioContrasenya());

        // Configurar el clic del TextView per al registre d'usuaris
        tvRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistreActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Mètode per enviar les dades d'inici de sessió al servidor.
     * <p>
     * Aquest mètode envia les dades d'inici de sessió al servidor per comprovar si l'usuari existeix i les dades són correctes.
     * Si les dades són correctes, l'usuari serà redirigit a l'activitat corresponent al seu tipus d'usuari.
     * Si les dades són incorrectes, es mostrarà un missatge d'error.
     * <p>
     * @param correuUsuari Correu electrònic de l'usuari.
     * @param passUsuari Contrasenya de l'usuari.
     */
    private void enviarLogin(String correuUsuari, String passUsuari) {
        PeticioLogin peticioLogin = new PeticioLogin(context, correuUsuari, passUsuari) {
        };
        peticioLogin.execute();
    }

    /**
     * Mètode per mostrar el diàleg de recuperació de contrasenya.
     * <p>
     * Aquest mètode mostra un diàleg perquè l'usuari pugui recuperar la contrasenya si l'ha oblidat.
     * L'usuari haurà d'introduir el seu correu electrònic per rebre un correu amb les instruccions per recuperar la contrasenya.
     * Si l'usuari no introdueix cap correu electrònic o el format del correu electrònic és incorrecte, es mostrarà un missatge d'error.
     * Si el correu electrònic és correcte, es mostrarà un missatge d'èxit.
     */
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
            if (!Utils.esEmailValid(correu)) {
                Utils.mostrarToast(getApplicationContext(), "El format del correu electrònic és incorrecte");
                return;
            }
            Utils.mostrarToast(getApplicationContext(), "Enviar correu a: " + correu);
            dialegAlerta.dismiss();
        });
    }
}