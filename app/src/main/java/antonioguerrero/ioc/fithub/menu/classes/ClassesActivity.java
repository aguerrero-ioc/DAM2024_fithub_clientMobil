package antonioguerrero.ioc.fithub.menu.classes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;

/**
 * Activitat per mostrar les classes disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClassesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
    }
}