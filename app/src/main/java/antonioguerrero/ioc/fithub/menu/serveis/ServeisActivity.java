package antonioguerrero.ioc.fithub.menu.serveis;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;

/**
 * Activitat per mostrar els serveis disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ServeisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveis);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, "Pendent d'implementar. Aviat dispobible!"));
    }
}