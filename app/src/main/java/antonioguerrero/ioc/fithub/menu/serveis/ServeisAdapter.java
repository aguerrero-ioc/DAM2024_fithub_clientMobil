package antonioguerrero.ioc.fithub.menu.serveis;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;

public class ServeisAdapter extends RecyclerView.Adapter<ServeisAdapter.ViewHolder> {

    private List<HashMap<String, String>> serveisList;
    private Context mContext;

    public ServeisAdapter(Context context, List<HashMap<String, String>> serveisList) {
        this.mContext = context;
        this.serveisList = serveisList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servei, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> servei = serveisList.get(position);
        holder.nomServei.setText(servei.get(Constants.SERVEI_NOM));
        holder.preuServei.setText(servei.get(Constants.SERVEI_PREU));
        holder.btnMesDetalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomServei = servei.get(Constants.SERVEI_NOM);
                String descripcioServei = servei.get(Constants.SERVEI_DESC);
                String preuServei = servei.get(Constants.SERVEI_PREU);
                dialegDetallsServei(nomServei, descripcioServei, preuServei);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serveisList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomServei, preuServei;

        public ViewHolder(View itemView) {
            super(itemView);
            nomServei = itemView.findViewById(R.id.tvNomServei);
            preuServei = itemView.findViewById(R.id.tvPreuServei);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    private void dialegDetallsServei(String nomServei, String descripcioServei, String tarifaServei) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_servei, null);

        TextView tvNomServei = dialogView.findViewById(R.id.tvNomServei);
        TextView tvDescripcioServei = dialogView.findViewById(R.id.tvDescripcioServei);
        TextView tvTarifaServei = dialogView.findViewById(R.id.tvTarifaServei);

        tvNomServei.setText(nomServei);
        tvDescripcioServei.setText(descripcioServei);
        tvTarifaServei.setText(tarifaServei);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
