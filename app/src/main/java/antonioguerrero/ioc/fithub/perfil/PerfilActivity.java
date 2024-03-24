package antonioguerrero.ioc.fithub.perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import antonioguerrero.ioc.fithub.R;

/**
 * Classe que representa l'activitat del perfil de l'usuari a l'aplicació FitHub.
 */
public class PerfilActivity extends AppCompatActivity {

    private EditText etNomUsuari, etDataNaixement, etAdreca, etCorreuUsuari, etTelefonContacte, etDataInscripcio;
    private Button btnEditarPerfil, btnGuardarCanvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicialitzar les vistes
        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etDataNaixement = findViewById(R.id.et_data_naixement);
        etAdreca = findViewById(R.id.et_adreca);
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etTelefonContacte = findViewById(R.id.et_telefon_contacte);
        etDataInscripcio = findViewById(R.id.et_data_inscripcio);

        btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        btnGuardarCanvis = findViewById(R.id.btn_guardar_canvis);

        // Obtindre referència de l'ImageView de la imatge de l'usuari
        ImageView ivImatgeUsuari = findViewById(R.id.iv_imatge_usuari);

        // Verificar si l'usuari té una imatge personalitzada
        boolean usuariTeImatgePersonalitzada = false;

        // Verificar si l'usuari té una imatge personalitzada
        if (usuariTeImatgePersonalitzada) {
            // Si té, carrega la imatge personalitzada
            // PENDENT IMPLEMENTAR lògica per carregar la imatge de l'usuari
            // ivImatgeUsuari.setImageResource(R.drawable.imagen_usuario_personalizada);
        } else {
            // Si no té, carrega la imatge predeterminada
            ivImatgeUsuari.setImageResource(R.mipmap.default_imatge_perfil);
        }

        // Configurar clic del botó per editar el perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEdicio();
            }
        });

        // Configurar clic del botó per guardar els canvis
        btnGuardarCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCanvis();
            }
        });

        // Inicialment, deshabilitar l'edició dels camps de text
        deshabilitarEdicio();
    }

    // Mètode per habilitar l'edició dels camps de text
    private void habilitarEdicio() {
        etNomUsuari.setEnabled(true);
        etDataNaixement.setEnabled(true);
        etAdreca.setEnabled(true);
        etCorreuUsuari.setEnabled(true);
        etTelefonContacte.setEnabled(true);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(true);
    }

    // Mètode per deshabilitar l'edició dels camps de text
    private void deshabilitarEdicio() {
        etNomUsuari.setEnabled(false);
        etDataNaixement.setEnabled(false);
        etAdreca.setEnabled(false);
        etCorreuUsuari.setEnabled(false);
        etTelefonContacte.setEnabled(false);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(false);
    }

    // Mètode per guardar els canvis realitzats
    private void guardarCanvis() {
        // Aquí pots implementar la lògica per desar els canvis a la base de dades o a SharedPreferences
        Toast.makeText(this, "Canvis guardats", Toast.LENGTH_SHORT).show();
        deshabilitarEdicio(); // Després de desar, deshabilitar l'edició de nou
    }
}
