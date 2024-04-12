/*package antonioguerrero.ioc.fithub.menu.reserves;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
//import antonioguerrero.ioc.fithub.menu.classesdirigides.ClasseDirigidaAdapter;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.ClasseDirigida;
import antonioguerrero.ioc.fithub.objectes.Installacio;

import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.reserves.ConsultarClassesDirigidesDia;
import antonioguerrero.ioc.fithub.peticions.reserves.CrearReserva;
import antonioguerrero.ioc.fithub.peticions.reserves.EliminarReserva;


public class ReservesActivity extends AppCompatActivity {

    private TextView tvData;
    private RecyclerView rvClassesDirigides;
    private List<ClasseDirigida> llistaClassesDirigides;
    private Context context;


    SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
    String sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserves);

        tvData = findViewById(R.id.tvData);
        rvClassesDirigides = findViewById(R.id.rvClassesDirigides);

        // Obtenir la data actual i convertir-la a String
        String dataActual = Utils.obtenirDataActual();
        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        String dataActualString = format.format(dataActual);

        // Mostrar la data actual com a títol del llistat
        tvData.setText(dataActualString);

        // Enviar la petició ConsultarClassesDirigidesDia
        llistaClassesDirigides = ConsultarClassesDirigidesDia(dataActualString);

        // Llistar cada un dels elements d'aquest ArrayList
        ClasseDirigidaAdapter adapter = new ClasseDirigidaAdapter(this, llistaClassesDirigides);
        rvClassesDirigides.setLayoutManager(new LinearLayoutManager(this));
        rvClassesDirigides.setAdapter(adapter);
    }

    private List<ClasseDirigida> ConsultarClassesDirigidesDia(String data) {
        List<ClasseDirigida> llistaClassesDirigides = new ArrayList<>();
        ConsultarClassesDirigidesDia peticio = new ConsultarClassesDirigidesDia(new ConsultarClassesDirigidesDia.respostaServidorListener() {
            @Override
            public void respostaServidor(Object response) {
                if (response instanceof Object[]) {
                    Object[] resposta = (Object[]) response;
                    for (Object objecte : resposta) {
                        HashMap<String, Object> classeDirigida = (HashMap<String, Object>) objecte;
                        String IDClasseDirigida = (String) classeDirigida.get("IDClasseDirigida");
                        Activitat activitat = (Activitat) classeDirigida.get("activitat");
                        Installacio installacio = (Installacio) classeDirigida.get("installacio");
                        String dia = (String) classeDirigida.get("dia");
                        String horaInici = (String) classeDirigida.get("horaInici");
                        int duracio = Integer.parseInt((String) classeDirigida.get("duracio"));
                        int reservesActuals = Integer.parseInt((String) classeDirigida.get("reservesActuals"));
                        ClasseDirigida classe = new ClasseDirigida(IDClasseDirigida, activitat, installacio, dia, horaInici, duracio, reservesActuals);
                        llistaClassesDirigides.add(classe);
                    }
                }
            }
        }, this);
        peticio.execute();
        return llistaClassesDirigides;
    }

    public static void mostrarDialegReserva(Context context, ClasseDirigida classeDirigida) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Detalls de la classe dirigida");
        builder.setMessage("Activitat: " + classeDirigida.getNomActivitat() + "\n" +
                "Instal·lació: " + classeDirigida.getNomInstallacio() + "\n" +
                "Día: " + classeDirigida.getDia() + "\n" +
                "Hora: " + classeDirigida.getHoraInici() + "\n" +
                "Duració: " + classeDirigida.getDuracio());

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        String IDUsuari = preferencies.getString("IDUsuari", Utils.VALOR_DEFAULT);

        // Comprueba si ya se ha realizado una reserva para esta clase dirigida
        if (classeDirigida.estaReservat()) {
            builder.setPositiveButton("Cancelar reserva", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Obtén el ID de la clase dirigida
                    String IDClasseDirigida = classeDirigida.getIDClasseDirigida();
                    // Llama a EliminarReserva con el ID de la clase dirigida
                    new EliminarReserva(null, IDClasseDirigida).execute();

                    classeDirigida.setEstaReservat(false);
                }
            });
        } else {
            builder.setPositiveButton("Realitzar reserva", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Obtén el ID de la clase dirigida
                    String IDClasseDirigida = classeDirigida.getIDClasseDirigida();
                    // Llama a CrearReserva con el ID de la clase dirigida
                    new CrearReserva(null, new Reserva(IDClasseDirigida, IDUsuari)).execute();

                    classeDirigida.setEstaReservat(true);
                }
            });
        }

        builder.setNegativeButton("Cerrar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}*/