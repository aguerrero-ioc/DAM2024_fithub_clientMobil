package antonioguerrero.ioc.fithub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import antonioguerrero.ioc.fithub.login.LoginActivity;
import antonioguerrero.ioc.fithub.missatges.GestorMissatges;
import antonioguerrero.ioc.fithub.missatges.MissatgesFragment;
import antonioguerrero.ioc.fithub.missatges.GestorMissatges;
import antonioguerrero.ioc.fithub.perfil.PerfilActivity;

/**
 * Classe que representa l'activitat del client a l'aplicació FitHub.
 *
 * Aquesta classe permet als clients realitzar diverses operacions com fer reserves i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClientActivity extends AppCompatActivity {

    private LinearLayout layoutMenuPerfil; // Per mostrar/ocultar el menú desplegable

    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

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

        // Inicialitzar els botons de reserva d'activitats
        Button botoReserva1 = findViewById(R.id.boto_reserva1);
        Button botoReserva2 = findViewById(R.id.boto_reserva2);
        Button botoReserva3 = findViewById(R.id.boto_reserva3);
        Button botoReserva4 = findViewById(R.id.boto_reserva4);
        Button botoReserva5 = findViewById(R.id.boto_reserva5);

        // Referencias a los elementos del layout
        layoutMenuPerfil = findViewById(R.id.layoutPerfilMenu);
        fragmentContainer = findViewById(R.id.fragment_container);

        // Configurar els listeners pels botons de reserva d'activitats
        botoReserva1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferReserva("Classe");
            }
        });

        botoReserva2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferReserva("Activitat");
            }
        });

        botoReserva3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferReserva("Instal·lació");
            }
        });

        botoReserva4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferReserva("Piscina");
            }
        });

        botoReserva5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferReserva("Servei");
            }
        });

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
        enviarMissatgeAlServidor("UsuariActual", "Contingut del missatge");

        // Rebre missatge del servidor
        rebreMissatgeDelServidor();
    }

    /**
     * Envia un missatge al servidor.
     *
     * @param remitent   El remitent del missatge.
     * @param contingut  El contingut del missatge.
     */
    private void enviarMissatgeAlServidor(String remitent, String contingut) {
        String data = Utils.obtenirDataActual();
        String hora = Utils.obtenirHoraActual();

        GestorMissatges.enviarMissatge(remitent, contingut, data, hora, new GestorMissatges.GestorRespostaServidor() {
            @Override
            public void onServerResponse(String resposta) {
                // Mostrar un Toast "Missatge enviat"
                Toast.makeText(getApplicationContext(), "Missatge enviat", Toast.LENGTH_SHORT).show();
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

    // Mètode per mostrar o ocultar el menú desplegable
    public void toggleMenu() {
        if (layoutMenuPerfil.getVisibility() == View.VISIBLE) {
            layoutMenuPerfil.setVisibility(View.GONE);
        } else {
            layoutMenuPerfil.setVisibility(View.VISIBLE);
        }
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
        Button botonOcultarMissatges = findViewById(R.id.boto_ocultar_missatges);
        botonOcultarMissatges.setVisibility(View.VISIBLE);
    }

    public void ocultarFragmentMissatges(View view) {
        // Ocultar el fragment de missatges
        getSupportFragmentManager().popBackStack();

        // Ocultar el botó d'ocultar missatges
        Button botonOcultarMissatges = findViewById(R.id.boto_ocultar_missatges);
        botonOcultarMissatges.setVisibility(View.GONE);
    }




    /**
     * Mètode per a realitzar una reserva d'una activitat.
     * @param nomActivitat El nom de l'activitat a reservar.
     */
    private void ferReserva(String nomActivitat) {
        Toast.makeText(ClientActivity.this, "Activitat reservada: " + nomActivitat, Toast.LENGTH_SHORT).show();
    }

    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció 1 del perfil.
     */
    public void opcioPerfil1Clicat() {
        Intent intent = new Intent(ClientActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    /**
     * Mètode que s'executa quan l'usuari fa clic a l'opció de tancar la sessió.
     * Aquest mètode redirigeix l'usuari a la pantalla d'inici de sessió i finalitza l'activitat actual.
     */
    public void opcioTancarSessioClicat() {
        // Redirigir a la pantalla d'inici de sessió
        Intent intent = new Intent(ClientActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finalitzar l'activitat actual
    }

    /**
     * Mètode per obtenir el nom de l'usuari.
     * @param context Context de l'aplicació.
     * @return El nom de l'usuari.
     */
    public static String obtenirNomUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String nomUsuari = preferencies.getString("nomUsuari", "");
        return nomUsuari;
    }

    /**
     * Mètode per obtenir l'ID de l'usuari.
     * @param context Context de l'aplicació.
     * @return L'ID de l'usuari.
     */
    public static String obtenirIdUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String idUsuari = preferencies.getString("idUsuari", "");
        return idUsuari;
    }

    /**
     * Mètode per obtenir el tipus de client.
     * @param context Context de l'aplicació.
     * @return El tipus de client.
     */
    public static String obtenirTipusClient(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String tipusClient = preferencies.getString("tipusClient", "");
        return tipusClient;
    }
}
