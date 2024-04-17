package antonioguerrero.ioc.fithub.menu.activitats;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

public class ActivitatsAdapter extends RecyclerView.Adapter<ActivitatsAdapter.ViewHolder> {

    private List<HashMap<String, String>> activitatsList;
    private Context mContext;

    public ActivitatsAdapter(Context context, List<HashMap<String, String>> activitatsList) {
        this.mContext = context;
        this.activitatsList = activitatsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activitat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> activitat = activitatsList.get(position);
        holder.nomActivitat.setText(activitat.get("nomActivitat"));
        // Lógica per mostrar el tipus d'activitat
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

        holder.btnMesDetalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomActivitat = activitat.get("nomActivitat");
                String descripcioActivitat = activitat.get("descripcioActivitat");
                String aforamentActivitat = activitat.get("aforamentActivitat");
                String tipusActivitat = holder.tipusActivitat.getText().toString();
                dialegDetallsActivitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusActivitat);
            }
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
    public class ViewHolder extends RecyclerView.ViewHolder {
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

        // Crear el diálogo con el diseño personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
