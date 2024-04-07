package antonioguerrero.ioc.fithub.menu.installacions;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;

/**
 * Activitat per mostrar les instal·lacions disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class InstallacionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installacions);
    }
}