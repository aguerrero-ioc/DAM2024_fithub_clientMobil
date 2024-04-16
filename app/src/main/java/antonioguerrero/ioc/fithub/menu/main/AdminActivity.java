package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

/**
 * Classe que representa l'activitat de l'administrador a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als administradors realitzar diverses operacions com gestionar usuaris, activitats i instal·lacions.
 * També poden veure els missatges rebuts.
 * <p>
 * Aquesta classe hereta de BaseActivity.
 */
public class AdminActivity extends BaseActivity implements BasePeticions.respostaServidorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Inicialitza els botons de reserva d'activitats
        Button botoGestio1 = findViewById(R.id.boto_gestio1);
        Button botoGestio2 = findViewById(R.id.boto_gestio2);
        Button botoGestio3 = findViewById(R.id.boto_gestio3);

        // Configura els listeners pels botons de reserva d'activitats
        botoGestio1.setOnClickListener(v -> ferGestio("Usuari"));
        botoGestio2.setOnClickListener(v -> ferGestio("Activitat"));
        botoGestio3.setOnClickListener(v -> ferGestio("Instal·lació"));

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Utils.PENDENT_IMPLEMENTAR));

        // Configura el menú lateral
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigation = findViewById(R.id.nav_view);

        navigation.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_perfil_usuari) {
                obrirActivity(PerfilActivity.class);
            } else if(id == R.id.nav_activitats) {
                Utils.mostrarToast(AdminActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_serveis) {
                Utils.mostrarToast(AdminActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_installacions) {
                obrirActivity(InstallacionsActivity.class);
            } else if (id == R.id.nav_reserves) {
                Utils.mostrarToast(AdminActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_tancar_sessio) {
                tancarSessioClicat();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }



    /**
     * Realitza una acció de gestió escollida.
     * @param nomActivitat Nom de l'activitat a gestionar.
     */
    private void ferGestio(String nomActivitat) {
        Utils.mostrarToast(AdminActivity.this, "Has escollit: " + nomActivitat);
    }

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }

    @Override
    public void onRespostaServidorMultiple(Object resposta) {

    }
}
