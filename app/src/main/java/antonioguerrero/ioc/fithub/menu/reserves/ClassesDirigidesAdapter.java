package antonioguerrero.ioc.fithub.menu.reserves;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

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
     * @param position Posició de l'element de la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> classeDirigida = classesDirigidesList.get(position);
        holder.nomClasseDirigida.setText(classeDirigida.get("nomClasseDirigida"));
        holder.horaInici.setText(classeDirigida.get("horaInici"));

        // Agregar un listener de clics al botón "Más detalles"
        holder.btnReservar.setOnClickListener(v -> {
            // Obtener los datos de la clase dirigida para mostrar en el diálogo
            String nom = classeDirigida.get("nomClasseDirigida");
            String horaInici = classeDirigida.get("horaInici");
            String duracio = classeDirigida.get("duracio");

            // Crear y mostrar el diálogo con la información de la clase dirigida
            dialegDetallsClasseDirigida(nom, horaInici, duracio);
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
        public View btnReservar;
        TextView nomClasseDirigida, horaInici;

        public ViewHolder(View itemView) {
            super(itemView);
            nomClasseDirigida = itemView.findViewById(R.id.nomClasseDirigida);
            horaInici = itemView.findViewById(R.id.horaInici);
            btnReservar = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    /**
     * Mètode per mostrar un diàleg amb els detalls de la classe dirigida.
     * <p>
     * Aquest mètode crea un diàleg personalitzat amb la informació de la classe dirigida.
     * El diàleg mostra el nom de la classe, l'hora d'inici i la durada de la classe.
     * <p>
     * @param nomClasseDirigida Nom de la classe dirigida.
     * @param horaInici Hora d'inici de la classe dirigida.
     * @param duracio Durada de la classe dirigida.
     */
    private void dialegDetallsClasseDirigida(String nomClasseDirigida, String horaInici, String duracio) {
        // Inflar el diseño personalizado del diálogo
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_classe_dirigida, null);

        // Configurar las vistas del diseño personalizado
        TextView tvNomClasseDirigida = dialogView.findViewById(R.id.tvNomClasseDirigida);
        TextView tvHoraInici = dialogView.findViewById(R.id.tvHoraInici);
        TextView tvDuracio = dialogView.findViewById(R.id.tvDuracio);

        tvNomClasseDirigida.setText(nomClasseDirigida);
        tvHoraInici.setText(horaInici);
        tvDuracio.setText(duracio);

        // Crear el diálogo con el diseño personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
