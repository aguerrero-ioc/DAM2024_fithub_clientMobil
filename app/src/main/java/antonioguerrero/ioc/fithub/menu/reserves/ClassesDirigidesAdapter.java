package antonioguerrero.ioc.fithub.menu.reserves;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;

/**
 * Adaptador per a la llista de classes dirigides disponibles en el centre esportiu.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les classes dirigides disponibles en el centre esportiu.
 * Per a cada classe dirigida, es mostra el nom, l'hora d'inici i un botó per a més detalls.
 * Quan es fa clic al botó "Més detalls", es mostra un diàleg amb la informació de la classe dirigida.
 * Aquest diàleg mostra el nom de la classe, l'hora d'inici i la durada de la classe.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */

public class ClassesDirigidesAdapter extends RecyclerView.Adapter<ClassesDirigidesAdapter.ViewHolder> {

    private final List<HashMap<String, String>> classesDirigidesList;
    private final Context mContext;

    /**
     * Constructor de la classe.
     * <p>
     * @param context Context de l'aplicació.
     * @param classesDirigidesList Llista de classes dirigides.
     */
    public ClassesDirigidesAdapter(Context context, List<HashMap<String, String>> classesDirigidesList) {
        this.mContext = context;
        this.classesDirigidesList = classesDirigidesList;
    }

    /**
     * Mètode que crea una nova instància de la classe ViewHolder.
     * <p>
     * @param parent Vista pare on es mostrarà la nova vista.
     * @param viewType Tipus de la nova vista.
     * @return Instància de la classe ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classe_dirigida, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que enllaça les dades de la llista amb les vistes de la llista.
     * <p>
     * @param holder Vista de la llista.
     * @param posicio Posició de l'element de la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicio) {
        HashMap<String, String> classeDirigida = classesDirigidesList.get(posicio);
        holder.nomClasseDirigida.setText(classeDirigida.get("nomClasseDirigida"));
        holder.horaInici.setText(classeDirigida.get("horaInici"));

        // Obtenir la data i hora actual
        String dataActual = Utils.obtenirDataActual();
        String horaActual = Utils.obtenirHoraActual();

        // Verificar si la data o hora d'inici és anterior a la data o hora actual
        if (Utils.esDataAnterior(classeDirigida.get("data")) || Utils.esHoraAnterior(classeDirigida.get("horaInici"))) {
            // Si la data o hora d'inici és anterior, inhabilitar el botó o establir un altre estat
            holder.btnMesDetalls.setEnabled(false);
            // També pots canviar el color de fons o qualsevol altra propietat visual per indicar que està inhabilitat
        } else {
            // Si la data i hora d'inici són posteriors, habilitar el botó i establir l'estat desitjat
            holder.btnMesDetalls.setEnabled(true);
        }

        // Afegir un listener de clics al botó "Més detalls"
        holder.btnMesDetalls.setOnClickListener(v -> {
            // Obtenir les dades de la classe dirigida per mostrar en el diàleg
            String IDclasseDirigida = classeDirigida.get("IDclasseDirigida");
            String nomActivitat = classeDirigida.get("nomActivitat");
            String nomInstallacio = classeDirigida.get("nomInstallacio");
            String dataClasse = classeDirigida.get("dataClasse");
            String horaInici = classeDirigida.get("horaInici");
            String duracio = classeDirigida.get("duracio");
            String ocupacioClasse = classeDirigida.get("ocupacioClasse");
            String estatClasse = classeDirigida.get("estatClasse");


            // Crear i mostrar el diàleg amb la informació de la classe dirigida
            dialegDetallsClasseDirigida(nomActivitat, nomInstallacio, dataClasse, horaInici, duracio, ocupacioClasse, estatClasse);
        });
    }


    /**
     * Mètode que retorna el nombre d'elements de la llista.
     * <p>
     * @return Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return classesDirigidesList.size();
    }

    /**
     * Classe interna que representa una vista de la llista.
     * <p>
     * Aquesta classe conté les vistes que es mostraran per a cada element de la llista.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomClasseDirigida, horaInici, estatClasse;

        public ViewHolder(View itemView) {
            super(itemView);
            nomClasseDirigida = itemView.findViewById(R.id.tvNomActivitat);
            horaInici = itemView.findViewById(R.id.tvHoraInici);
            estatClasse = itemView.findViewById(R.id.tvEstatClasse);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }


    /**
     * Mètode per mostrar el diàleg amb els detalls de la classe dirigida.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de la classe dirigida seleccionada.
     *
     * @param nomActivitat Nom de l'activitat.
     * @param nomInstallacio Nom de la instal·lació on es realitza l'activitat.
     * @param dataClasse Data de la classe dirigida.
     * @param horaInici Hora d'inici de la classe dirigida.
     * @param duracio Durada de la classe dirigida.
     * @param ocupacioClasse Ocupació de la classe dirigida.
     * @param estatClasse Estat de la classe dirigida.
     */
private void dialegDetallsClasseDirigida(String nomActivitat, String nomInstallacio, String dataClasse, String horaInici, String duracio, String ocupacioClasse, String estatClasse){
    // Inflar el diseño personalizado del diálogo
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View dialogView = inflater.inflate(R.layout.dialeg_detalls_classe_dirigida, null);

    // Configurar las vistas del diseño personalizado
    TextView tvNomActivitat = dialogView.findViewById(R.id.tvNomActivitat);
    TextView tvNomInstallacio = dialogView.findViewById(R.id.tvNomInstallacio);
    TextView tvDataClasse = dialogView.findViewById(R.id.tvDataClasse);
    TextView tvHoraInici = dialogView.findViewById(R.id.tvHoraInici);
    TextView tvDuracio = dialogView.findViewById(R.id.tvDurada);
    TextView tvOcupacioClasse = dialogView.findViewById(R.id.tvOcupacioClasse);
    TextView tvEstatClasse = dialogView.findViewById(R.id.tvEstatClasse);

    tvNomActivitat.setText(nomActivitat);
    tvNomInstallacio.setText(nomInstallacio);
    tvDataClasse.setText(dataClasse);
    tvHoraInici.setText(horaInici);
    tvDuracio.setText(duracio);
    tvOcupacioClasse.setText(ocupacioClasse);
    tvEstatClasse.setText(estatClasse);

    // Configurar los botones de "Reservar" y "Cancelar reserva"
    Button btnReservar = dialogView.findViewById(R.id.btnReservar);
    Button btnCancelarReserva = dialogView.findViewById(R.id.btnCancelarReserva);

    btnReservar.setOnClickListener(v -> {
        // Aquí va el código para manejar el clic en el botón "Reservar"
    });

    btnCancelarReserva.setOnClickListener(v -> {
        // Aquí va el código para manejar el clic en el botón "Cancelar reserva"
    });

    // Crear el diálogo con el diseño personalizado
    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setView(dialogView);
    AlertDialog dialog = builder.create();
    dialog.show();
}
}
