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

import antonioguerrero.ioc.fithub.login.LoginActivity;

/**
 * Classe que representa l'activitat del administrador a l'aplicacio FitHub.
 *
 * Aquesta classe permet a l'administrador realitzar diverses operacions com fer gestions i gestionar el seu perfil.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class AdminActivity extends AppCompatActivity {

    private LinearLayout layoutMenuPerfil; // Per mostrar/ocultar el menú desplegable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

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
        Button botoGestio1 = findViewById(R.id.boto_gestio1);
        Button botoGestio2 = findViewById(R.id.boto_gestio2);
        Button botoGestio3 = findViewById(R.id.boto_gestio3);

        // Configurar els listeners pels botons de reserva d'activitats
        botoGestio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferGestio("Usuari");
            }
        });

        botoGestio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferGestio("Activitat");
            }
        });

        botoGestio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ferGestio("Instal·lació");
            }
        });

        // Inicialitzar el botó de perfil
        ImageButton botoPerfil = findViewById(R.id.boto_perfil_admin);
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
        TextView opcioLogout = findViewById(R.id.opcio_logout_admin);

        // Configurar els listeners dels elements del menú
        opcioPerfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioPerfil1Clicked();
            }
        });

        opcioLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcioLogoutClicked();
            }
        });
    }


    /**
     * Realitza una acció de gestió escollida.
     * @param nomActivitat Nom de l'activitat a gestionar.
     */
    private void ferGestio(String nomActivitat) {
        Toast.makeText(AdminActivity.this, "Has escollit: " + nomActivitat, Toast.LENGTH_SHORT).show();
    }

    /**
     * Acció que es realitza en seleccionar la opció 1 del perfil.
     */
    public void opcioPerfil1Clicked() {
        Toast.makeText(AdminActivity.this, "Opció 1 seleccionada", Toast.LENGTH_SHORT).show();
    }

    /**
     * Acció que es realitza en seleccionar la opció de tancar sessió.
     */
    private void opcioLogoutClicked() {
        // Redirigir a la pantalla d'inici de sessió
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
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
     * Aquest mètode recupera l'ID de l'usuari emmagatzemat a les preferències compartides.
     * @param context El context de l'aplicació.
     * @return L'ID de l'usuari.
     */
    public static String obtenirIdUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String idUsuari = preferencies.getString("idUsuari", "");
        return idUsuari;
    }

    /**
     * Mètode per obtenir el tipus de client.
     * Aquest mètode recupera el tipus de client emmagatzemat a les preferències compartides.
     * @param context El context de l'aplicació.
     * @return El tipus de client.
     */
    public static String obtenirTipusClient(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String tipusClient = preferencies.getString("tipusClient", "");
        return tipusClient;
    }
}
