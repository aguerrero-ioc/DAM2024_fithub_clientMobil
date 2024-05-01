package antonioguerrero.ioc.fithub.menu.installacions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.peticions.installacions.CrearInstallacio;

public class CrearInstallacioActivity extends AppCompatActivity {

    private EditText etNomInstallacio, etDescripcioInstallacio, etCapacitatInstallacio, etTipusInstallacio;
    private Button btnCrearInstallacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_installacio);

        // Referenciar los elementos del layout
        etNomInstallacio = findViewById(R.id.et_nom_installacio);
        etDescripcioInstallacio = findViewById(R.id.et_descripcio_installacio);
        etTipusInstallacio = findViewById(R.id.et_tipus_installacio);
        btnCrearInstallacio = findViewById(R.id.btn_creacio_installacio);

        // Configurar el clic del botón
        btnCrearInstallacio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores de los EditText
                String nomInstallacio = etNomInstallacio.getText().toString().trim();
                String descripcioInstallacio = etDescripcioInstallacio.getText().toString().trim();

                // Obtener el tipo de instalación
                String tipusInstallacioInput = etTipusInstallacio.getText().toString().trim().toLowerCase();
                int tipusInstallacio;

                // Convertir la entrada del usuario en el tipo de instalación correspondiente
                if (tipusInstallacioInput.equals("1") || tipusInstallacioInput.equals("sala")) {
                    tipusInstallacio = 1; // Sala
                } else if (tipusInstallacioInput.equals("2") || tipusInstallacioInput.equals("piscina")) {
                    tipusInstallacio = 2; // Piscina
                } else {
                    // Si la entrada no es válida, mostrar un mensaje de error y salir del método
                    Toast.makeText(CrearInstallacioActivity.this, "Tipus d'instal·lació no vàlid", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Obtener sessioID del usuario
                SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
                String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);

                // Crear un objeto Installacio con los valores obtenidos
                Installacio installacio = new Installacio(nomInstallacio, descripcioInstallacio, tipusInstallacio);

                // Crear y ejecutar la petición al servidor para crear la instalación
                CrearInstallacio crearInstallacio = new CrearInstallacio(new ConnexioServidor.respostaServidorListener() {
                    @Override
                    public void respostaServidor(Object resposta) {
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                }, installacio, CrearInstallacioActivity.this, sessioID) {
                    @Override
                    protected Object doInBackground(Void... voids) {
                        return null;
                    }

                    @Override
                    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                        return null;
                    }
                };

                // Ejecutar la petición en un hilo en segundo plano
                try {
                    crearInstallacio.execute();
                } catch (ConnectException e) {
                    e.printStackTrace();
                }

                // Limpiar los EditText
                etNomInstallacio.setText("");
                etDescripcioInstallacio.setText("");
                etCapacitatInstallacio.setText("");
                etTipusInstallacio.setText("");
            }
        });
    }
}
