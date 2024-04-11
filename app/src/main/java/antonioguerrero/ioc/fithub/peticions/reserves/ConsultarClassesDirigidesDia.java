package antonioguerrero.ioc.fithub.peticions.reserves;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.menu.installacions.InstallacionsActivity;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;


public class ConsultarClassesDirigidesDia extends BasePeticions {

    private String dia;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;
    private static final String ETIQUETA = "ConsultarClasseDirigidaDia";


    public ConsultarClassesDirigidesDia(respostaServidorListener listener, Context context, String dia) {
        super(listener);
        this.context = context;
        this.dia = dia;
        this.preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    public void obtenirClassesDirigides() {
        enviarPeticio("selectAll", "classe", dia, this.sessioID, ETIQUETA);
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return Object[].class;
    }

    @Override
    public void respostaServidor(Object resposta) {
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            if (estat != null && estat.equals("classeDirigida")) {
                ArrayList<HashMap<String, String>> dadesClassesDirigides = (ArrayList<HashMap<String, String>>) respostaArray[1];
                // Iniciar l'activitat InstallacionsActivity amb la llista d'instal·lacions
                Utils.iniciarActivitatLlista(context, ReservesActivity .class, dadesClassesDirigides, "dadesClassesDirigides");
                // Guardar les dades de les instal·lacions a SharedPreferences
                guardarDadesClassesDirigides(dadesClassesDirigides);
            } else {
                Utils.mostrarToast(context, "Error en la consulta de clases dirigidas");
            }
        } else {
            Utils.mostrarToast(context, "Error de conexión");
        }
    }

    /**
     * Guarda les dades de les classes dirigides a SharedPreferences.
     *
     * @param dadesClassesDirigides La llista de dades de les classes dirigides.
     */
    private void guardarDadesClassesDirigides(List<HashMap<String, String>> dadesClassesDirigides) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        // Guardar les dades de les classes dirigides a SharedPreferences
        for (int i = 0; i < dadesClassesDirigides.size(); i++) {
            HashMap<String, String> mapaClassesDirigides = dadesClassesDirigides.get(i);
            editor.putInt("IDClasseDirigida" + i, Integer.parseInt(mapaClassesDirigides.get("IDClasseDirigida")));
            editor.putString("dia" + i, mapaClassesDirigides.get("dia"));
            editor.putString("hora" + i, mapaClassesDirigides.get("hora"));
            editor.putInt("duracio" + i, Integer.parseInt(mapaClassesDirigides.get("duracio")));
            editor.putInt("IDactivitat" + i, Integer.parseInt(mapaClassesDirigides.get("IDactivitat")));
            editor.putInt("IDinstallacio" + i, Integer.parseInt(mapaClassesDirigides.get("IDinstallacio")));
        }

        // Guardar la quantitat de classes dirigides a SharedPreferences
        editor.putInt("numClassesDirigides", dadesClassesDirigides.size());

        // Aplicar els canvis a SharedPreferences
        editor.apply();
    }



    @Override
    public void execute() {
        obtenirClassesDirigides();
    }
}