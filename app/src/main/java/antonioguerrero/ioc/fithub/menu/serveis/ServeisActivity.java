package antonioguerrero.ioc.fithub.menu.serveis;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

/**
 * Activitat per mostrar els serveis disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ServeisActivity extends BaseActivity {

    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveis);

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
                Utils.mostrarToast(ServeisActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_serveis) {
                Utils.mostrarToast(ServeisActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_installacions) {
                obrirActivity(InstallacionsActivity.class);
            } else if (id == R.id.nav_reserves) {
                Utils.mostrarToast(ServeisActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_tancar_sessio) {
                tancarSessioClicat();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });;
    }

    /**
     * Mètode per tancar la sessió de l'usuari.
     */
    public void tancarSessioClicat() {
        SharedPreferences preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        int IDUsuari = preferencies.getInt("IDusuari", -1);
        String IDUsuariStr = String.valueOf(IDUsuari);

        if (!IDUsuariStr.equals("-1")) {
            PeticioLogout peticioLogout = new PeticioLogout((ConnexioServidor.respostaServidorListener) this, this, IDUsuariStr, Utils.SESSIO_ID);
            peticioLogout.execute();
            preferencies.edit().clear().apply();
        } else {
            Log.e("ETIQUETA", "IDusuari no definit");
        }
    }

}
