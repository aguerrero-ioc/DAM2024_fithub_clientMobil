package antonioguerrero.ioc.fithub.menu.activitats;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;

/**
 * Classe que implementa l'adaptador per a la quadricula d'activitats.
 * Hereta de RecyclerView.Adapter.
 *
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class ActivitatQuadriculaAdapter extends RecyclerView.Adapter<ActivitatQuadriculaAdapter.ActivitatViewHolder> {

    private Activitat[][] llistaActivitats;

    public ActivitatQuadriculaAdapter(Activitat[][] llistaActivitats) {
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
        int dia = position % 7;
        int hora = position / 7;
        Activitat activitat = llistaActivitats[hora][dia];
        if (activitat != null) {
            holder.nomActivitat.setText(activitat.getNom());
            // Configurar las demás vistas del ViewHolder según sea necesario
        } else {
            holder.nomActivitat.setText("");
            // Limpiar las demás vistas del ViewHolder
        }
    }

    @Override
    public int getItemCount() {
        return 14 * 7; // 14 franjas horarias, 7 días a la semana
    }

    static class ActivitatViewHolder extends RecyclerView.ViewHolder {
        TextView nomActivitat;
        // Altres vistes segons sigui necessari

        public ActivitatViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            // Inicialitzar altres vistes
        }
    }
}