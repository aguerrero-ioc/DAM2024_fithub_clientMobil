package antonioguerrero.ioc.fithub.menu.installacions;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

/**
 * Activitat per mostrar les instal·lacions disponibles al centre esportiu.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class InstallacionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InstallacionsAdapter adapter;
    private List<HashMap<String, String>> installacionsLlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installacions);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<HashMap<String, String>> installacionsLlista = (List<HashMap<String, String>>) getIntent().getExtras().getSerializable("installacionsList");
        adapter = new InstallacionsAdapter(installacionsLlista);
        recyclerView.setAdapter(adapter);
    }
}