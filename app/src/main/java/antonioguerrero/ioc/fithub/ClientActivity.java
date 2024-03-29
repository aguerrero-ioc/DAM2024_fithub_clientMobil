package antonioguerrero.ioc.fithub;

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

        // Inicialitzar el botó de perfil
        ImageButton botoPerfil = findViewById(R.id.boto_perfil_client);
        layoutMenuPerfil = findViewById(R.id.layoutPerfilMenu);

        // Configurar els listeners del botó de perfil
        botoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar/ocultar el menú desplegable en fer clic al botó de perfil
                if (layoutMenuPerfil.getVisibility() == View.VISIBLE) {
                    layoutMenuPerfil.setVisibility(View.GONE);
                } else {
                    layoutMenuPerfil.setVisibility(View.VISIBLE);
                }
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
        Toast.makeText(ClientActivity.this, "Opció 1 seleccionada", Toast.LENGTH_SHORT).show();
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
