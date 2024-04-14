package antonioguerrero.ioc.fithub.menu.main;

import android.os.Bundle;
import android.widget.Button;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;

/**
 * Classe que representa l'activitat del administrador a l'aplicacio FitHub.
 * <p>
 * Aquesta classe permet a l'administrador realitzar diverses operacions com fer gestions i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public abstract class AdminActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Actualitza les dades del usuari quan s'obre l'activitat
        actualitzarDadesUsuari();

        // Inicialitzar els botons de reserva d'activitats
        Button botoGestio1 = findViewById(R.id.boto_gestio1);
        Button botoGestio2 = findViewById(R.id.boto_gestio2);
        Button botoGestio3 = findViewById(R.id.boto_gestio3);

        // Configurar els listeners pels botons de reserva d'activitats
        botoGestio1.setOnClickListener(v -> ferGestio("Usuari"));

        botoGestio2.setOnClickListener(v -> ferGestio("Activitat"));

        botoGestio3.setOnClickListener(v -> ferGestio("Instal·lació"));

    }

    /**
     * Realitza una acció de gestió escollida.
     * @param nomActivitat Nom de l'activitat a gestionar.
     */
    private void ferGestio(String nomActivitat) {
        Utils.mostrarToast(AdminActivity.this, "Has escollit: " + nomActivitat);
    }
}
