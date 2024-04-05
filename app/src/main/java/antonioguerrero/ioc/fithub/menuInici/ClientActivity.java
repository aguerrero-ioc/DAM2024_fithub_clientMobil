package antonioguerrero.ioc.fithub.menuInici;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.LoginActivity;
import antonioguerrero.ioc.fithub.missatges.GestorMissatges;
import antonioguerrero.ioc.fithub.missatges.MissatgesFragment;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.usuari.PerfilActivity;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 * <p>
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends AppCompatActivity {

    private LinearLayout layoutMenuPerfil; // Per mostrar/ocultar el menú desplegable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Actualitza les dades del usuari quan s'obre l'activitat
        actualitzarDadesUsuari();

        // Referencias a los elementos del layout
        layoutMenuPerfil = findViewById(R.id.layoutPerfilMenu);

        // Inicialitzar els botons de reserva d'activitats
        Button[] botonsReserva = {
                findViewById(R.id.boto_reserva1),
                findViewById(R.id.boto_reserva2),
                findViewById(R.id.boto_reserva3),
                findViewById(R.id.boto_reserva4),
                findViewById(R.id.boto_reserva5)
        };

        // Configurar els listeners pels botons de reserva d'activitats
        String[] nomsActivitats = {"Classe", "Activitat", "Instal·lació", "Piscina", "Servei"};
        for (int i = 0; i < botonsReserva.length; i++) {
            final int index = i;
            botonsReserva[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ferReserva(nomsActivitats[index]);
                }
            });
        }

        // Configura el botó de perfil
        ImageButton botoPerfil = findViewById(R.id.boto_perfil_client);
        botoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        // Trobar les referències als elements del menú
        TextView opcioPerfil1 = findViewById(R.id.opcio_perfil1);
        TextView opcioLogout = findViewById(R.id.opcio_logout_client);

        // Configurar els listeners dels elements del menú
        opcioPerfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioPerfil1Clicat();
            }
        });

        opcioLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioTancarSessioClicat();
            }
        });

        // Enviar missatge al servidor
        enviarMissatgeAlServidor();

        // Rebre missatge del servidor
        rebreMissatgeDelServidor();
    }

    // Mètode per mostrar o ocultar el menú desplegable
    public void toggleMenu() {
        layoutMenuPerfil.setVisibility(layoutMenuPerfil.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    // Mètode per mostrar el fragment de missatges quan es fa clic en un botó
    public void mostrarFragmentMissatges(View view) {
        // Crear una instància del fragment de missatges
        MissatgesFragment missatgesFragment = new MissatgesFragment();

        // Obtenir el FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();

        // Iniciar una transacció de fragment
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Reemplaçar el contingut del contenidor amb el fragment
        transaction.replace(R.id.fragment_container, missatgesFragment);

        // Afegir la transacció a la pila d'atràs
        transaction.addToBackStack(null);

        // Confirmar la transacció
        transaction.commit();

        // Mostrar el botó d'ocultar missatges
        findViewById(R.id.boto_ocultar_missatges).setVisibility(View.VISIBLE);
    }

    public void ocultarFragmentMissatges(View view) {
        getSupportFragmentManager().popBackStack();
        findViewById(R.id.boto_ocultar_missatges).setVisibility(View.GONE);
    }

    /**
     * Mètode per a realitzar una reserva d'una activitat.
     * @param nomActivitat El nom de l'activitat a reservar.
     */
    private void ferReserva(String nomActivitat) {
        Utils.mostrarToast(ClientActivity.this, "Activitat reservada: " + nomActivitat);
    }

    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció 1 del perfil.
     */
    public void opcioPerfil1Clicat() {
        startActivity(new Intent(ClientActivity.this, PerfilActivity.class));
    }

    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció de tancar la sessió.
     * Aquest mètode redirigeix l'usuari a la pantalla d'inici de sessió i finalitza l'activitat actual.
     */
    public void opcioTancarSessioClicat() {
        startActivity(new Intent(ClientActivity.this, LoginActivity.class));
        finish(); // Finalitzar l'activitat actual
    }

    private void actualitzarDadesUsuari() {


        // Obtenir el nom, ID i tipus de client
        String nomUsuari = obtenirNomUsuari(this);
        String idUsuari = obtenirIdUsuari(this);
        String tipusClient = obtenirTipusClient(this);

        // Concatenar el nom d'usuari i l'ID d'usuari
        String textUsuari = nomUsuari + " (" + idUsuari + ")";

        // Trobar el textViewUsuari i establir el text
        TextView textViewUsuari = findViewById(R.id.tv_usuari);
        textViewUsuari.setText(textUsuari);

        // Trobar el textViewTipusClient i establir el text
        TextView textViewTipusClient = findViewById(R.id.tv_tipus_client);
        textViewTipusClient.setText(tipusClient);

    }

    /**
     * Mètode per obtenir el nom de l'usuari.
     * @param context Context de l'aplicació.
     * @return El nom de l'usuari.
     */
    public static String obtenirNomUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("nomUsuari", "");
    }

    /**
     * Mètode per obtenir l'ID de l'usuari.
     * @param context Context de l'aplicació.
     * @return L'ID de l'usuari.
     */
    public static String obtenirIdUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("idUsuari", "");
    }

    /**
     * Mètode per obtenir el tipus de client.
     *
     * @param context Context de l'aplicació.
     * @return El tipus de client.
     */
    public static String obtenirTipusClient(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        return preferencies.getString("tipusClient", "");
    }

    /**
     * Envia un missatge al servidor.
     *
     */
    private void enviarMissatgeAlServidor() {
        String data = Utils.obtenirDataActual();
        String hora = Utils.obtenirHoraActual();

        GestorMissatges.enviarMissatge("UsuariActual", "Contingut del missatge", data, hora, new GestorMissatges.GestorRespostaServidor() {
            @Override
            public void onServerResponse(String resposta) {
                // Mostrar un Toast "Missatge enviat"
                Utils.mostrarToast(getApplicationContext(), "Missatge enviat");
            }
        });
    }

    /**
     * Rep un missatge del servidor i el mostra en el fragment de missatges.
     */
    private void rebreMissatgeDelServidor() {
        GestorMissatges.rebreMissatge(new GestorMissatges.GestorRespostaServidor() {
            @Override
            public void onServerResponse(String resposta) {
                // Crear una instància del fragment de missatges
                MissatgesFragment missatgesFragment = new MissatgesFragment();

                // Passar el missatge rebut com a argument al fragment
                Bundle args = new Bundle();
                args.putString("missatge", resposta);
                missatgesFragment.setArguments(args);

                // Obtenir el FragmentManager
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Iniciar una transacció de fragment
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Reemplaçar el contingut del contenidor amb el fragment
                transaction.replace(R.id.fragment_container, missatgesFragment);

                // Afegir la transacció a la pila d'atràs
                transaction.addToBackStack(null);

                // Confirmar la transacció
                transaction.commit();
            }
        });
    }
}
