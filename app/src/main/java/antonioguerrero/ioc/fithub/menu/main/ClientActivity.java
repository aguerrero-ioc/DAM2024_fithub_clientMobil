package antonioguerrero.ioc.fithub.menu.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsActivity;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatsAdapter;
import antonioguerrero.ioc.fithub.menu.reserves.PaginesReservesAdapter;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesPassadesFragment;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesRealitzadesFragment;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.activitats.ConsultarTotesActivitats;
import antonioguerrero.ioc.fithub.peticions.reserves.ConsultarTotesReserves;


/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends BaseActivity implements ConnexioServidor.respostaServidorListener, ConsultarTotesReserves.ConsultarTotesReservesListener {

    private RecyclerView recyclerView;
    private ActivitatsAdapter adapter;
    private Usuari usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Configurar ViewPager i TabLayout per a les pàgines de reserves
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);


        // Crear el adaptador
        PaginesReservesAdapter reservesAdapter = new PaginesReservesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(reservesAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Configura el botó flotant de missatges
        FloatingActionButton botoMostrarMissatges = findViewById(R.id.boto_mostrar_missatges);
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, Constants.PENDENT_IMPLEMENTAR));




        // Iniciar la consulta de reserves
        iniciarConsultaReserves();

    }

    private void iniciarConsultaReserves() {
        // Obtenir sessioID de l'usuari
        SharedPreferences preferences = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferences.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        ConsultarTotesReserves consultaReserves = new ConsultarTotesReserves(this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };
        //consultaReserves.consultarTotesReserves();
    }



    @Override
    public void onReservesObtingudes(List<HashMap<String, String>> reserves) {
        // Esta función se llama cuando se obtienen las reservas del servidor
        // Aquí puedes actualizar las listas de reservas en los fragmentos correspondientes
        ReservesRealitzadesFragment fragmentRealitzades = (ReservesRealitzadesFragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.view_pager, 0));
        ReservesPassadesFragment fragmentPassades = (ReservesPassadesFragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(R.id.view_pager, 1));

        if (fragmentRealitzades != null) {
            fragmentRealitzades.actualitzarReserves(reserves);
        }

        if (fragmentPassades != null) {
            fragmentPassades.actualitzarReserves(reserves);
        }
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }


    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }
}
