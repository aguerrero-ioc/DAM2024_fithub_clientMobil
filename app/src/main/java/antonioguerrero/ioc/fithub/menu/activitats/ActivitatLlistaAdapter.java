package antonioguerrero.ioc.fithub.menu.activitats;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;

/*
PENDENT D'IMPLEMENTAR

public class ActivitatLlistaAdapter extends RecyclerView.Adapter<ActivitatLlistaAdapter.ActivitatViewHolder> {

    private List<Activitat> llistaActivitats;

    public ActivitatLlistaAdapter(List<Activitat> llistaActivitats) {
        this.llistaActivitats = llistaActivitats;
    }

    @NonNull
    @Override
    public ActivitatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitat_item, parent, false);
        return new ActivitatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitatViewHolder holder, int position) {
        Activitat activitat = llistaActivitats.get(position);
        holder.nomActivitat.setText(activitat.getNom());
        // Configurar las demás vistas del ViewHolder según sea necesario
    }

    @Override
    public int getItemCount() {
        return llistaActivitats.size();
    }

    static class ActivitatViewHolder extends RecyclerView.ViewHolder {
        TextView nomActivitat;
        // Otras vistas según sea necesario

        public ActivitatViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            // Inicializar las demás vistas
        }
    }
}*/