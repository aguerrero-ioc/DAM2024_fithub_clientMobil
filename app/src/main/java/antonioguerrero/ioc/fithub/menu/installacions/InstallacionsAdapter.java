package antonioguerrero.ioc.fithub.menu.installacions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

public class InstallacionsAdapter extends RecyclerView.Adapter<InstallacionsAdapter.ViewHolder> {

    private List<HashMap<String, String>> installacionsList;

    public InstallacionsAdapter(List<HashMap<String, String>> installacionsList) {
        this.installacionsList = installacionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_installacio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> installacio = installacionsList.get(position);
        holder.nomInstallacio.setText(installacio.get("nomInstallacio"));
        holder.descripcioInstallacio.setText(installacio.get("descripcioInstallacio"));
        holder.tipusInstallacio.setText(installacio.get("tipusInstallacio"));
    }

    @Override
    public int getItemCount() {
        return installacionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomInstallacio, descripcioInstallacio, tipusInstallacio;

        public ViewHolder(View itemView) {
            super(itemView);
            nomInstallacio = itemView.findViewById(R.id.nomInstallacio);
            descripcioInstallacio = itemView.findViewById(R.id.descripcioInstallacio);
            tipusInstallacio = itemView.findViewById(R.id.tipusInstallacio);
        }
    }
}