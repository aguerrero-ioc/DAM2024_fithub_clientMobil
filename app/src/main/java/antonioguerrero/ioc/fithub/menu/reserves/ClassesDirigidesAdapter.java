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
 * Adaptador para la lista de clases dirigidas disponibles en el centro deportivo.
 * <p>
 * Este adaptador se encarga de mostrar los datos de las clases dirigidas en una lista.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClassesDirigidesAdapter extends RecyclerView.Adapter<ClassesDirigidesAdapter.ViewHolder> {

    private final List<HashMap<String, String>> classesDirigidesList;
    private final Context mContext;

    /**
     * Constructor de la clase.
     * <p>
     * @param classesDirigidesList Lista de clases dirigidas disponibles en el centro deportivo.
     */
    public ClassesDirigidesAdapter(Context context, List<HashMap<String, String>> classesDirigidesList) {
        this.mContext = context;
        this.classesDirigidesList = classesDirigidesList;
    }

    /**
     * Método que crea una nueva instancia de la clase ViewHolder.
     * <p>
     * @param parent Vista padre.
     * @param viewType Tipo de vista.
     * @return Instancia de la clase ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classe_dirigida, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Método que se ejecuta para cada elemento de la lista.
     * Se encarga de mostrar los datos de las clases dirigidas.
     * <p>
     * @param holder Instancia de la clase ViewHolder.
     * @param position Posición del elemento en la lista.
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
     * Método que retorna el número de elementos de la lista.
     * <p>
     * @return Número de elementos de la lista.
     */
    @Override
    public int getItemCount() {
        return classesDirigidesList.size();
    }

    /**
     * Clase interna que representa una vista de la lista.
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
     * Método que muestra un diálogo con los detalles de la clase dirigida.
     * <p>
     * @param nomClasseDirigida Nombre de la clase dirigida.
     * @param horaInici Hora de inicio de la clase dirigida.
     * @param duracio Duración de la clase dirigida.
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
