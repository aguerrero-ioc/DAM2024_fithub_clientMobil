package antonioguerrero.ioc.fithub.menu.activitats;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.activitats.ConsultarActivitat;
import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarUsuari;

/**
 * Activitat per mostrar les activitats disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ActivitatsActivity extends AppCompatActivity implements ConsultarActivitat.ConsultarActivitatListener {
    private Context context;
    private String sessioID;
    private Activitat activitat;


    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitats);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ActivitatListFragment fragment = new ActivitatListFragment();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        }

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, "Pendent d'implementar. Aviat dispobible!"));
    }

    @Override
    public void onActivitatObtinguda(Activitat activitat) {
        this.activitat = activitat;

    }
}