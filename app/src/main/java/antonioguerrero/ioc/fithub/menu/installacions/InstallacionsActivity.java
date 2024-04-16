package antonioguerrero.ioc.fithub.menu.installacions;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;
import antonioguerrero.ioc.fithub.menu.usuari.PerfilActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.installacions.ConsultarTotesInstallacions;
import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogout;

public class InstallacionsActivity extends BaseActivity implements BasePeticions.respostaServidorListener {

    private RecyclerView recyclerView;
    private InstallacionsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installacions);

        recyclerView = findViewById(R.id.rvInstallacions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                Utils.mostrarToast(InstallacionsActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_serveis) {
                Utils.mostrarToast(InstallacionsActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_installacions) {
                obrirActivity(InstallacionsActivity.class);
            } else if (id == R.id.nav_reserves) {
                Utils.mostrarToast(InstallacionsActivity.this, Utils.PENDENT_IMPLEMENTAR);
            } else if (id == R.id.nav_tancar_sessio) {
                tancarSessioClicat();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });;





        ConsultarTotesInstallacions consulta = new ConsultarTotesInstallacions(this, new ConsultarTotesInstallacions.respostaServidorListener() {
            @Override
            public void respostaServidor(Object resposta) {
                // Implementació del mètode respostaServidor
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                if (resposta instanceof List) {
                    List<HashMap<String, String>> llistaInstallacions = (List<HashMap<String, String>>) resposta;
                    // Verificar si la llista no està buida abans de configurar l'adaptador
                    if (!llistaInstallacions.isEmpty()) {
                        adapter = new InstallacionsAdapter(llistaInstallacions);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Utils.mostrarToast(InstallacionsActivity.this, "La llista d'instal·lacions està buida");
                    }
                } else {
                    Utils.mostrarToast(InstallacionsActivity.this, "No s'han pogut obtenir les dades");
                }
                return null;
            }

            @Override
            public void onRespostaServidorMultiple(Object resposta) {

            }
        }) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {

                return null;
            }
        };
        consulta.consultarTotesInstallacions();
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