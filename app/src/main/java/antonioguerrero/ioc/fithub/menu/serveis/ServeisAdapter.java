package antonioguerrero.ioc.fithub.menu.serveis;

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

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;

/**
 * Adaptador per a la llista dels serveis disponibles al centre esportiu.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les dades dels serveis en una llista.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ServeisAdapter extends RecyclerView.Adapter<ServeisAdapter.ViewHolder> {
    private final List<HashMap<String, String>> serveisList;
    private final Context mContext;

    /**
     * Constructor de la classe.
     * <p>
     * @param serveisList Llista dels serveis disponibles al centre esportiu.
     */
    public ServeisAdapter(Context context, List<HashMap<String, String>> serveisList) {
        this.mContext = context;
        this.serveisList = serveisList;
    }

    /**
     * Mètode que crea una nova instància de la classe ViewHolder.
     * <p>
     * @param parent Vista pare.
     * @param viewType Tipus de vista.
     * @return Instància de la classe ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servei, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que s'executa per a cada element de la llista.
     * S'encarrega de mostrar les dades dels serveis.
     * <p>
     * @param holder Instància de la classe ViewHolder.
     * @param position Posició de l'element a la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> servei = serveisList.get(position);
        holder.nomServei.setText(servei.get(Constants.SERVEI_NOM));
        holder.preuServei.setText(servei.get(Constants.SERVEI_PREU));
        holder.btnMesDetalls.setOnClickListener(v -> {
            String nomServei = servei.get(Constants.SERVEI_NOM);
            String descripcioServei = servei.get(Constants.SERVEI_DESC);
            String preuServei = servei.get(Constants.SERVEI_PREU);
            dialegDetallsServei(nomServei, descripcioServei, preuServei);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista de serveis.
     * <p>
     * @return Nombre d'elements de la llista de serveis.
     */
    @Override
    public int getItemCount() {
        return serveisList.size();
    }

    /**
     * Classe interna que representa una vista de la llista de serveis.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomServei, preuServei;

        public ViewHolder(View itemView) {
            super(itemView);
            nomServei = itemView.findViewById(R.id.tvNomServei);
            preuServei = itemView.findViewById(R.id.tvPreuServei);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    /**
     * Mètode que mostra un diàleg amb els detalls del servei.
     * <p>
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param tarifaServei Tarifa del servei.
     */
    private void dialegDetallsServei(String nomServei, String descripcioServei, String tarifaServei) {
        // Inflar el disseny personalitzat
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_servei, null);

        // Configurar les vistes del diàleg amb les dades del servei
        TextView tvNomServei = dialogView.findViewById(R.id.tvNomServei);
        TextView tvDescripcioServei = dialogView.findViewById(R.id.tvDescripcioServei);
        TextView tvTarifaServei = dialogView.findViewById(R.id.tvTarifaServei);

        tvNomServei.setText(nomServei);
        tvDescripcioServei.setText(descripcioServei);
        tvTarifaServei.setText(tarifaServei);

        // Crear el diàleg amb les dades dels serveis
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}