package antonioguerrero.ioc.fithub.menu.activitats;

import android.annotation.SuppressLint;
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
 * Aquesta classe s'encarrega de gestionar les dades de les activitats i mostrar-les en un RecyclerView.
 * <p>
 * Aquesta classe conté un mètode per mostrar un diàleg amb els detalls de l'activitat seleccionada.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ActivitatsAdapter extends RecyclerView.Adapter<ActivitatsAdapter.ViewHolder> {

    private final List<HashMap<String, String>> activitatsList;
    private final Context mContext;

    /**
     * Constructor de la classe ActivitatsAdapter.
     * <p>
     * Aquest constructor rep un context i una llista d'activitats.
     *
     * @param context Context de l'aplicació.
     * @param activitatsList Llista d'activitats.
     */
    public ActivitatsAdapter(Context context, List<HashMap<String, String>> activitatsList) {
        this.mContext = context;
        this.activitatsList = activitatsList;
    }

    /**
     * Mètode que crea una nova instància de ViewHolder.
     * <p>
     * Aquest mètode crea una nova instància de ViewHolder a partir del disseny de la vista.
     *
     * @param parent Vista pare on s'afegirà la nova vista.
     * @param viewType Tipus de la vista.
     * @return Nova instància de ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitat, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que enllaça les dades de l'activitat amb les vistes del ViewHolder.
     * <p>
     * Aquest mètode enllaça les dades de l'activitat amb les vistes del ViewHolder.
     *
     * @param holder ViewHolder on es mostraran les dades.
     * @param position Posició de l'element a la llista.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> activitat = activitatsList.get(position);
        holder.nomActivitat.setText(activitat.get("nomActivitat"));
        // Lògica per mostrar el tipus d'activitat
        String tipusActivitat = activitat.get("tipusActivitat");
        if (tipusActivitat != null) {
            switch (tipusActivitat) {
                case "1":
                    holder.tipusActivitat.setText("Sala");
                    break;
                case "2":
                    holder.tipusActivitat.setText("Piscina");
                    break;
                default:
                    holder.tipusActivitat.setText("Desconegut");
                    break;
            }
        } else {
            holder.tipusActivitat.setText("No definit");
        }

        holder.btnMesDetalls.setOnClickListener(v -> {
            String nomActivitat = activitat.get("nomActivitat");
            String descripcioActivitat = activitat.get("descripcioActivitat");
            String aforamentActivitat = activitat.get("aforamentActivitat");
            String tipusActivitat1 = holder.tipusActivitat.getText().toString();
            dialegDetallsActivitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusActivitat1);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista.
     * <p>
     * @return Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return activitatsList.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomActivitat, tipusActivitat;

        public ViewHolder(View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            tipusActivitat = itemView.findViewById(R.id.tipusActivitat);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    /**
     * Mètode per mostrar el diàleg amb els detalls de l'activitat.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de l'activitat seleccionada.
     *
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     */
    private void dialegDetallsActivitat(String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Crear una vista personalitzada per al diàleg
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_activitats, null);

        // Configurar les vistes del disseny personalitzat
        TextView tvNomActivitat = dialogView.findViewById(R.id.tvNomActivitat);
        TextView tvDescripcioActivitat = dialogView.findViewById(R.id.tvDescripcioActivitat);
        TextView tvAforamentActivitat = dialogView.findViewById(R.id.tvAforamentActivitat);
        TextView tvTipusActivitat = dialogView.findViewById(R.id.tvTipusActivitat);

        tvNomActivitat.setText(nomActivitat);
        tvDescripcioActivitat.setText(descripcioActivitat);
        tvAforamentActivitat.setText(aforamentActivitat);
        tvTipusActivitat.setText(tipusInstallacio);

        // Crear el diàleg amb el disseny personalitzat
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}