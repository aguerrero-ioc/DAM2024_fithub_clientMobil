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

public class UserActivity extends AppCompatActivity {

    private LinearLayout layoutPerfilMenu; // Per mostrar/ocultar el menú desplegable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_usuari);

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
        ImageButton botoPerfil = findViewById(R.id.boto_perfil);
        layoutPerfilMenu = findViewById(R.id.layoutPerfilMenu);

        // Configurar els listeners del botó de perfil
        botoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar/ocultar el menú desplegable en fer clic al botó de perfil
                if (layoutPerfilMenu.getVisibility() == View.VISIBLE) {
                    layoutPerfilMenu.setVisibility(View.GONE);
                } else {
                    layoutPerfilMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        // Trobar les referències als elements del menú
        TextView opcioPerfil1 = findViewById(R.id.opcio_perfil1);
        TextView opcioLogout = findViewById(R.id.opcio_logout);

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

    private void ferReserva(String nomActivitat) {
        Toast.makeText(UserActivity.this, "Activitat reservada: " + nomActivitat, Toast.LENGTH_SHORT).show();
    }

    public void opcioPerfil1Clicked() {
        Toast.makeText(UserActivity.this, "Opció 1 seleccionada", Toast.LENGTH_SHORT).show();
    }

    public void opcioLogoutClicked() {
        // Redirigir a la pantalla d'inici de sessió
        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finalitzar l'activitat actual
    }

    // Mètode per obtenir el nom de l'usuari
    public static String obtenirNomUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String nomUsuari = preferencies.getString("nomUsuari", "");
        return nomUsuari;
    }

    // Mètode per obtenir l'ID de l'usuari
    public static String obtenirIdUsuari(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String idUsuari = preferencies.getString("idUsuari", "");
        return idUsuari;
    }

    // Mètode per obtenir el tipus de client
    public static String obtenirTipusClient(Context context) {
        SharedPreferences preferencies = context.getSharedPreferences("Preferències", Context.MODE_PRIVATE);
        String tipusClient = preferencies.getString("tipusClient", "");
        return tipusClient;
    }
}
