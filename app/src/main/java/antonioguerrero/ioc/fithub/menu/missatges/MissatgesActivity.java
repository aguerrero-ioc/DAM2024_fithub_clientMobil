package antonioguerrero.ioc.fithub.menu.missatges;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;

public class MissatgesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missatges);
    }
}
/* PENDENT DE REVISAR
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Missatge;

/* PENDENT DE REVISAR
public class MissatgesActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MissatgesAdapter missatgesAdapter;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missatges);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Missatge> missatges = GestorMissatges.obtenirMissatgesServidor();
        missatgesAdapter = new MissatgesAdapter(this, missatges);
        recyclerView.setAdapter(missatgesAdapter);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actualitzarMissatges();
            }
        });

        editTextMessage = findViewById(R.id.edit_text_message);
        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMissatge();
            }
        });
    }

    /**
     * Actualitza la llista de missatges.

    private void actualitzarMissatges() {
        List<Missatge> nousMissatges = GestorMissatges.obtenirMissatgesServidor();
        if (nousMissatges != null) {
            missatgesAdapter.actualitzarMissatges(nousMissatges);
            Toast.makeText(this, "Missatges actualitzats", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error en actualitzar missatges", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    /**
     * Envia un missatge al servidor.
     *

    private void enviarMissatge() {
        String data = Utils.obtenirDataActual();
        String hora = Utils.obtenirHoraActual();
        String contingut = editTextMessage.getText().toString();

        GestorMissatges.enviarMissatge("UsuariActual", contingut, data, hora, new GestorMissatges.GestorRespostaServidor() {
            @Override
            public void respostaServidor(String resposta) {
                // Mostrar un Toast "Missatge enviat"
                Utils.mostrarToast(getApplicationContext(), "Missatge enviat");
                // Limpiar el campo de texto
                editTextMessage.setText("");
                // Actualizar la lista de mensajes
                actualitzarMissatges();
            }
        });
    }

    /**
     * Rep un missatge del servidor i el mostra en el fragment de missatges.

    private void rebreMissatge() {
        GestorMissatges.rebreMissatge(new GestorMissatges.GestorRespostaServidor() {
            @Override
            public void respostaServidor(String resposta) {
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

                // Mostrar un Toast "Missatge enviat"
                Utils.mostrarToast(MissatgesActivity.this, "Missatge rebut");
            }
        });
    }
}*/