package antonioguerrero.ioc.fithub.menu.installacions;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.peticions.installacions.ConsultarTotesInstallacions;

public class InstallacionsActivity extends BaseActivity {

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
        botoMostrarMissatges.setOnClickListener(v -> Utils.mostrarToast(this, "Pendent d'implementar. Aviat dispobible!"));




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
    public void onRespostaServidorMultiple(Object resposta) {

    }


}