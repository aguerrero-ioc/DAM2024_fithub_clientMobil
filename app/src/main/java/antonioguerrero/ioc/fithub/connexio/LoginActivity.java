package antonioguerrero.ioc.fithub.connexio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.PeticioLogin;
import antonioguerrero.ioc.fithub.usuari.RegistreActivity;

/**
 * Classe que representa l'activitat de login a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als usuaris iniciar sessió, recuperar contrasenyes i registrar-se.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements BasePeticions.OnServerResponseListener {

    private EditText etNomUsuari, etContrasenya;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialitzar el context
        context = this;

        // Referenciar els elements de la interfície d'usuari
        etNomUsuari = findViewById(R.id.et_nomusuari);
        etContrasenya = findViewById(R.id.et_contrasenya);
        Button btnLogin = findViewById(R.id.btn_login);
        CheckBox checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        TextView tvRecuperarContrasenya = findViewById(R.id.tv_oblidat_contrasenya);
        TextView tvRegistrar = findViewById(R.id.tv_registre);

        // Configurar el clic del botó d'inici de sessió
        btnLogin.setOnClickListener(v -> {
            String nomUsuari = etNomUsuari.getText().toString();
            String contrasenya = etContrasenya.getText().toString();
            // Verificar si s'han introduït els dos camps
            if (nomUsuari.isEmpty() && contrasenya.isEmpty()) {
                // Mostrar un missatge d'advertència per a tots dos camps buits
                Toast.makeText(context, "Introdueix el nom d'usuari i la contrasenya", Toast.LENGTH_SHORT).show();
            } else if (nomUsuari.isEmpty()) {
                // Mostrar un missatge d'advertència si falta el nom d'usuari
                Toast.makeText(context, "Introdueix el nom d'usuari", Toast.LENGTH_SHORT).show();
            } else if (contrasenya.isEmpty()) {
                // Mostrar un missatge d'advertència si falta la contrasenya
                Toast.makeText(context, "Introdueix la contrasenya", Toast.LENGTH_SHORT).show();
            } else {
                // Si s'han introduït tots dos camps, enviar la sol·licitud al servidor
                enviarPeticioLogin(nomUsuari, contrasenya);
            }
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

        // Configurar el clic del TextView para la recuperación de contraseña
        tvRecuperarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialegRecuperacioContrasenya();
            }
        });

        // Configurar el clic del TextView per al registre d'usuaris
        tvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dirigir al usuario a la actividad de registro
                Intent intent = new Intent(LoginActivity.this, RegistreActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Mètode per enviar una petició de login al servidor.
     *
     * @param nomUsuari Nom d'usuari per a l'inici de sessió
     * @param contrasenya Contrasenya de l'usuari per a l'inici de sessió
     */
    private void enviarPeticioLogin(String nomUsuari, String contrasenya) {
        // Crear una nova instància de la petició de login
        PeticioLogin peticioLogin = new PeticioLogin(context, nomUsuari, contrasenya);

        // Executar la petició
        peticioLogin.execute();
    }



    /**
     * Mètode per mostrar el diàleg de recuperació de contrasenya.
     */
    public void mostrarDialegRecuperacioContrasenya() {
        // Inflar el disseny del diàleg
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialeg_recuperar_contrasenya, null);

        // Obtindre referència del EditText del correu electrònic
        EditText etCorreu = dialogView.findViewById(R.id.et_correu_recuperacio);

        // Crear el diàleg d'alerta
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setTitle("Recuperació de Contrasenya")
                .setPositiveButton("Enviar", null) // No establim un OnClickListener aquí, el definirem més tard
                .setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Tancar el diàleg en cas de cancel·lar
                    }
                });

        // Mostrar el diàleg
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Afegir el listener al botó d'enviar
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correu = etCorreu.getText().toString();

                // Comprovar si el camp de correu electrònic està buit
                if (correu.isEmpty()) {
                    Toast.makeText(context, "El correu electrònic és obligatori", Toast.LENGTH_SHORT).show();
                    return; // Sortir del mètode sense tancar el diàleg
                }

                // Comprovar si el correu electrònic té un format vàlid
                if (!esEmailValid(correu)) {
                    Toast.makeText(context, "El format del correu electrònic és incorrecte", Toast.LENGTH_SHORT).show();
                    return; // Sortir del mètode sense tancar el diàleg
                }

                // Aquí pots implementar la lògica per enviar el correu de recuperació
                Toast.makeText(context, "Enviar correu a: " + correu, Toast.LENGTH_SHORT).show();

                // Tancar el diàleg ja que el correu és vàlid
                alertDialog.dismiss();
            }
        });
    }


    /**
     * Mètode per validar el format d'un correu electrònic utilitzant una expressió regular.
     *
     * @param email El correu electrònic a validar.
     * @return Cert si el correu electrònic té el format vàlid, fals altrament.
     */
    private boolean esEmailValid(String email) {
        // Patró per validar el format d'un correu electrònic
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // Comprova si el correu electrònic coincideix amb el patró
        return email.matches(emailPattern);
    }

    @Override
    public void onServerResponse(Object resposta) {

    }
}
