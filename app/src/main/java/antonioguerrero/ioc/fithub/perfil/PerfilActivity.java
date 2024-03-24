package antonioguerrero.ioc.fithub.perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;

public class PerfilActivity extends AppCompatActivity {

    private EditText etNombreUsuario, etDireccion, etCorreo, etTelefonoContacte, etCognoms, etDataNaixement, etDataInscripcio;
    private Button btnEditarPerfil, btnGuardarCanvis;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializar vistas
        etNombreUsuario = findViewById(R.id.et_nom_usuari);
        etDataNaixement = findViewById(R.id.et_data_naixement);
        etDireccion = findViewById(R.id.et_adreca);
        etCorreo = findViewById(R.id.et_correu_usuari);
        etTelefonoContacte = findViewById(R.id.et_telefon_contacte);
        etDataInscripcio = findViewById(R.id.et_data_inscripcio);

        btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        btnGuardarCanvis = findViewById(R.id.btn_guardar_canvis);

        // Obtener referencia del ImageView de la imagen de usuario
        ImageView ivImagenUsuario = findViewById(R.id.iv_imatge_usuari);


        // Verificar si el usuario tiene una imagen personalizada
        boolean usuarioTieneImagenPersonalizada = false;

        // Verificar si el usuario tiene una imagen personalizada
        if (usuarioTieneImagenPersonalizada) {
            // Si tiene, carga la imagen personalizada
            // Aquí deberías tener tu propia lógica para cargar la imagen del usuario
            //ivImagenUsuario.setImageResource(R.drawable.imagen_usuario_personalizada);
        } else {
            // Si no tiene, carga la imagen predeterminada
            ivImagenUsuario.setImageResource(R.mipmap.default_imatge_perfil);
        }

        // Configurar clic del botón para editar perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEdicion();
            }
        });

        // Configurar clic del botón para guardar cambios
        btnGuardarCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        // Inicialmente, deshabilitar la edición de los campos de texto
        deshabilitarEdicion();
    }

    // Método para habilitar la edición de los campos de texto
    private void habilitarEdicion() {
        etNombreUsuario.setEnabled(true);
        etDireccion.setEnabled(true);
        etCorreo.setEnabled(true);
        etTelefonoContacte.setEnabled(true);
        etDataNaixement.setEnabled(true);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(true);
    }

    // Método para deshabilitar la edición de los campos de texto
    private void deshabilitarEdicion() {
        etNombreUsuario.setEnabled(false);
        etDataNaixement.setEnabled(false);
        etDireccion.setEnabled(false);
        etCorreo.setEnabled(false);
        etTelefonoContacte.setEnabled(false);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(false);
    }

    // Método para guardar los cambios realizados
    private void guardarCambios() {
        // Aquí puedes implementar la lógica para guardar los cambios en la base de datos o en SharedPreferences
        Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
        deshabilitarEdicion(); // Después de guardar, deshabilitar la edición de nuevo
    }
}
