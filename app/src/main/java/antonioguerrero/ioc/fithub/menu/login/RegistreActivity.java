package antonioguerrero.ioc.fithub.menu.login;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.RegistrarUsuari;

/**
 * Activitat per gestionar el registre d'un nou usuari.
 * <p>
 * Aquesta activitat permet als usuaris registrar-se a l'aplicació mitjançant un formulari.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class RegistreActivity extends AppCompatActivity {
    private EditText etNom, etCognoms, etTelefon, etCorreuElectronic, etContrasenya, etRepetirContrasenya;

    /**
     * Mètode que s'executa en crear l'activitat.
     * S'encarrega d'inicialitzar els elements de la interfície d'usuari i configurar els esdeveniments.
     * <p>
     * @param savedInstanceState Estat de l'activitat
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        // Inicialització dels elements de la interfície d'usuari
        etNom = findViewById(R.id.et_nom);
        etCognoms = findViewById(R.id.et_cognoms);
        etTelefon = findViewById(R.id.et_telefon);
        etCorreuElectronic = findViewById(R.id.et_correu_electronic);
        etContrasenya = findViewById(R.id.et_contrasenya);
        etRepetirContrasenya = findViewById(R.id.et_repetir_contrasenya);

        Button btnRegistrar = findViewById(R.id.btn_registrar);

        CheckBox checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);
        checkMostrarContrasenya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etRepetirContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    etContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etRepetirContrasenya.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        // Configuració de l'acció al clicar el botó de registre
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    registrarUsuari();
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Mètode per gestionar el registre d'un nou usuari.
     * S'obtenen les dades dels camps del formulari i es processen.
     * Es comprova si els camps estan complets i si tenen el format correcte.
     */
    private void registrarUsuari() throws ConnectException {
        // Obtenir els valors dels EditText
        String nom = etNom.getText().toString().trim();
        String cognoms = etCognoms.getText().toString().trim();
        String telefon = etTelefon.getText().toString().trim();
        String correu = etCorreuElectronic.getText().toString().trim();
        String contrasenya = etContrasenya.getText().toString().trim();
        String repetirContrasenya = etRepetirContrasenya.getText().toString().trim();

        // Verificar si algun camp està buit
        if (nom.isEmpty() || cognoms.isEmpty() || telefon.isEmpty() || correu.isEmpty() || contrasenya.isEmpty() || repetirContrasenya.isEmpty()) {
            Utils.mostrarToast(getApplicationContext(), "Si us plau, completa tots els camps");
            return;
        }

        // Comprovar el format correcte del nom
        if (!nom.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(getApplicationContext(), "El nom només pot contenir lletres");
            return;
        }

        // Comprovar el format correcte dels cognoms
        if (!cognoms.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(getApplicationContext(), "Els cognoms només poden contenir lletres");
            return;
        }

        // Comprovar el format correcte del telèfon
        if (!telefon.matches("\\d{9}")) {
            Utils.mostrarToast(getApplicationContext(), "El telèfon ha de tenir 9 dígits");
            return;
        }

        // Verificar si el correu electrònic està en el format correcte
        if (!Utils.esEmailValid(correu)) {
            Utils.mostrarToast(getApplicationContext(), "Format de correu electrònic invàlid");
            return;
        }

        // Verificar si la contrasenya i la seva repetició són iguals
        if (!contrasenya.equals(repetirContrasenya)) {
            Utils.mostrarToast(getApplicationContext(), "Les contrasenyes no coincideixen");
            return;
        }

        // Comprovar la longitud i la complexitat de la contrasenya
        if (contrasenya.length() < 8 || !contrasenya.matches(".*\\d.*") || !contrasenya.matches(".*[a-zA-Z].*")) {
            Utils.mostrarToast(getApplicationContext(), "La contrasenya ha de tenir almenys 8 caràcters alfanumèrics");
            return;
        }

        // Crear un objecte Usuari amb les dades obtingudes
        Usuari usuari = new Usuari(correu, contrasenya, nom, cognoms,telefon);

        // Crear una instancia de CrearUsuari
        RegistrarUsuari registrarUsuari = new RegistrarUsuari(new ConnexioServidor.respostaServidorListener() {
        }, usuari, RegistreActivity.this) {

        };
        // Enviar la petició al servidor
        registrarUsuari.execute();
    }
}