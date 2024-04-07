package antonioguerrero.ioc.fithub.menu.reserves;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.menu.activitats.ActivitatQuadriculaAdapter;
import antonioguerrero.ioc.fithub.objectes.Activitat;

/**
 * Activitat per gestionar les reserves d'activitats.
 *
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class ReservesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);
    }
}

/*
PENDENT D'IMPLEMENTAR
public class ReservesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Activitat> llistaActivitats;
    private Activitat[][] llistaActivitatsCuadricula;
    RecyclerView.Adapter adaptador;

    DatePicker datePicker = findViewById(R.id.datePicker);



    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // Per defecte, mode llista

        // Obtenció de la llista d'activitats del servidor
        obtenirActivitatsDesDelServidor();

        // Creació de l'adaptador i establiment al RecyclerView
        adaptador = new ReservesAdapter(llistaReserves);
        recyclerView.setAdapter(adaptador);

        // Afegir botons d'alternança per canviar entre modes de visualització
        Button botoQuadricula = findViewById(R.id.boto_quadricula);
        botoQuadricula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el DatePicker
                datePicker.setVisibility(View.VISIBLE);
                // Cambiar a la vista de cuadrícula
                recyclerView.setLayoutManager(new GridLayoutManager(ReservesActivity.this, 7));
                adaptador = new ActivitatQuadriculaAdapter(llistaActivitatsCuadricula);
                recyclerView.setAdapter(adaptador);
            }
        });

        Button botoLlista = findViewById(R.id.boto_llista);
        botoLlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar el DatePicker
                datePicker.setVisibility(View.VISIBLE);
                // Cambiar a la vista de lista
                recyclerView.setLayoutManager(new LinearLayoutManager(ReservesActivity.this));
                adaptador = new ActivitatLlistaAdapter(llistaActivitats);
                recyclerView.setAdapter(adaptador);
            }
        });
    }

    /**
     * Mètode per obtenir les activitats des del servidor.

    private void obtenirActivitatsDesDelServidor() {
        // Obtener las actividades del servidor
        llistaActivitats = new ArrayList<>();
        llistaActivitatsCuadricula = new Activitat[14][7]; // 14 franjas horarias, 7 días a la semana

        // Aquí realitzaries la lògica per fer la consulta al servidor i obtenir la llista d'activitats
        // De moment, afegim algunes activitats d'exemple
        llistaActivitats.add(new Activitat(1, "Ioga", "Dilluns", 10, "11:00"));
        llistaActivitats.add(new Activitat(2, "Pilates", "Dimarts", 15, "16:00"));
        llistaActivitats.add(new Activitat(3, "Zumba", "Dimecres", 17, "18:00"));

        // Insertar las actividades en la cuadrícula
        for (Activitat activitat : llistaActivitats) {
            int dia = obtenirDiaDeLaSetmana(activitat.getDia());
            int hora = obtenirFranjaHoraria(activitat.getHoraInici());
            llistaActivitatsCuadricula[hora][dia] = activitat;
        }

    }

    /**
     * Mètode per obtenir el dia de la setmana a partir del nom del dia.
     *
     * @param dia El nom del dia de la setmana.
     * @return El número del dia de la setmana.

    private int obtenirDiaDeLaSetmana(String dia) {
        switch (dia.toLowerCase()) {
            case "dilluns":
                return 0;
            case "dimarts":
                return 1;
            case "dimecres":
                return 2;
            case "dijous":
                return 3;
            case "divendres":
                return 4;
            case "dissabte":
                return 5;
            case "diumenge":
                return 6;
            default:
                throw new IllegalArgumentException("Dia desconegut: " + dia);
        }
    }

        /**
     * Mètode per obtenir la franja horària a partir de l'hora d'inici.
     *
     * @param horaInici L'hora d'inici de l'activitat.
     * @return La franja horària corresponent a l'hora d'inici.

    private int obtenirFranjaHoraria(String horaInici) {
        // Suponemos que la hora de inicio está en el formato "HH:mm"
        int hora = Integer.parseInt(horaInici.split(":")[0]);

        // Restamos 7 porque nuestras franjas horarias comienzan a las 7
        int franjaHoraria = hora - 7;

        if (franjaHoraria < 0 || franjaHoraria > 13) {
            throw new IllegalArgumentException("Hora de inicio fuera del rango permitido: " + horaInici);
        }

        return franjaHoraria;
    }

    /**
     * Mètode per mostrar un diàleg amb els detalls d'una activitat.
     *
     * @param activitat L'activitat de la qual es mostraran els detalls.

    private void mostrarDialeg(Activitat activitat) {
        AlertDialog.Builder constructor = new AlertDialog.Builder(this);
        constructor.setTitle("Detalls de l'Activitat");

        // Construir el contingut del diàleg amb les dades de l'activitat
        StringBuilder missatge = new StringBuilder();
        missatge.append("Nom: ").append(activitat.getNom()).append("\n");
        missatge.append("Dia: ").append(activitat.getDia()).append("\n");
        missatge.append("Hora d'Inici: ").append(activitat.getHoraInici()).append("\n");
        missatge.append("Hora de Fi: ").append(activitat.getHoraFi()).append("\n");

        constructor.setMessage(missatge.toString());

        // Verificar disponibilitat i mostrar botó de reserva si està disponible
        if (activitat.isDisponible()) {
            constructor.setPositiveButton("Reservar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Implementar la lògica de reserva aquí
                    // Per exemple, pots mostrar un Toast indicant que la reserva s'ha realitzat amb èxit
                    Toast.makeText(ReservesActivity.this, "Activitat reservada amb èxit", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            constructor.setPositiveButton("Tancar", null);
        }

        // Mostrar el diàleg
        constructor.create().show();
    }
}
*/
