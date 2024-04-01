package antonioguerrero.ioc.fithub.usuari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.PeticioUsuari;


/**
 * Activitat per gestionar el registre d'un nou usuari.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class RegistreActivity extends AppCompatActivity {

    // Elements de la interfície d'usuari
    private EditText etNom, etCognoms, etTelefon, etCorreuElectronic, etContrasenya, etRepetirContrasenya;
    private Button btnRegistrar;
    private CheckBox checkMostrarContrasenya;

    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();

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


        btnRegistrar = findViewById(R.id.btn_registrar);

        checkMostrarContrasenya = findViewById(R.id.check_mostrar_contrasenya);

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
                registrarUsuari();
            }
        });
    }

    /**
     * Mètode per gestionar el registre d'un nou usuari.
     * S'obtenen les dades dels camps del formulari i es processen.
     */
    private void registrarUsuari() {
        // Obtindre els valors dels EditText
        String nom = etNom.getText().toString().trim();
        String cognoms = etCognoms.getText().toString().trim();
        String telefon = etTelefon.getText().toString().trim();
        String correu = etCorreuElectronic.getText().toString().trim();
        String contrasenya = etContrasenya.getText().toString().trim();
        String repetirContrasenya = etRepetirContrasenya.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dataInscripcio = dateFormat.format(date);

        // Verificar si algun camp està buit
        if (nom.isEmpty() || cognoms.isEmpty() || telefon.isEmpty() || correu.isEmpty() || contrasenya.isEmpty() || repetirContrasenya.isEmpty()) {
            Toast.makeText(this, "Si us plau, completa tots els camps", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el correu electrònic està en el format correcte
        if (!esEmailValid(correu)) {
            Toast.makeText(this, "Format de correu electrònic invàlid", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si la contrasenya i la seva repetició són iguals
        if (!contrasenya.equals(repetirContrasenya)) {
            Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprovar la longitud i la complexitat de la contrasenya
        if (contrasenya.length() < 8 || !contrasenya.matches(".*\\d.*") || !contrasenya.matches(".*[a-zA-Z].*")) {
            Toast.makeText(this, "La contrasenya ha de tenir almenys 8 caràcters alfanumèrics", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un objecte Usuari amb les dades obtingudes
        Usuari usuari = new Usuari(correu, contrasenya, null, null, dataInscripcio, nom, cognoms, null, null, telefon);

        // Enviar la petició al servidor
        enviarPeticioServidor(usuari);
    }



    /**
     * Envia una petició al servidor per registrar un usuari nou.
     *
     * @param usuari Objecte Usuari que representa les dades del nou usuari
     * @return true si la petició s'ha enviat amb èxit; false si hi ha hagut algun problema
     */
    private boolean enviarPeticioServidor(Usuari usuari) {
        // Crear una instància de la classe de connexió al servidor
        ConnexioServidor serverConnection = new ConnexioServidor(new ConnexioServidor.OnServerResponseListener() {
            @Override
            public void onServerResponse(String resposta) {
                Context context = getApplicationContext();
                if (resposta != null && resposta.equals("True")) {
                    // Registre exitós
                    Toast.makeText(context, "Registre exitós", Toast.LENGTH_SHORT).show();
                    Log.d("Registre", "Registre exitós"); // Registrar al Logcat
                } else {
                    // Error en el registre
                    Toast.makeText(context, "Error en el registre", Toast.LENGTH_SHORT).show();
                    Log.e("Registre", "Error en el registre"); // Registrar al Logcat com a error
                }
            }
        });

        // Enviar la petició de registre al servidor
        PeticioUsuari.crearUsuari(usuari);

        // Retornar true com a valor temporal fins que es rebi una resposta real del servidor
        return true;
    }

    /**
     * Mètode per validar el format d'un correu electrònic utilitzant una expressió regular.
     *
     * @param correu  El correu electrònic a validar.
     * @return Cert si el correu electrònic té el format vàlid, fals altrament.
     */
    private boolean esEmailValid(String correu) {
        // Patró per validar el format d'un correu electrònic
        String patroCorreu = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // Comprova si el correu electrònic coincideix amb el patró
        return correu.matches(patroCorreu);
    }


}
