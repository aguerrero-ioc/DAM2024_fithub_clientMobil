package antonioguerrero.ioc.fithub.menu.reserves;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Reserva;

/**
 * Classe que implementa l'adaptador per a la llista de reserves.
 * Hereta de RecyclerView.Adapter.
 *
 * @autor Antonio Guerrero
 * @version 1.0
 */
public class ReservesAdapter extends RecyclerView.Adapter<ReservesAdapter.ReservesViewHolder> {

    private List<Reserva> reserves;
    private Activitat[][] llistaActivitats;

    public ReservesAdapter(List<Reserva> reserves, Activitat[][] llistaActivitats) {
        this.reserves = reserves;
        this.llistaActivitats = llistaActivitats;
    }

    @NonNull
    @Override
    public ReservesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitat_item, parent, false);
        return new ReservesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservesViewHolder holder, int position) {
        // Comprovar si la llista de reserves no és nul·la i no està buida
        if (reserves != null && !reserves.isEmpty() && position < reserves.size()) {
            Reserva reserva = reserves.get(position);
            // Actualitzar ViewHolder amb les dades de la reserva
        } else {
            // Gestionar el cas de llista de reserves buida o posició invàlida
        }

        // Comprovar si la llista d'activitats no és nul·la
        if (llistaActivitats != null && position / 7 < llistaActivitats.length && position % 7 < llistaActivitats[0].length) {
            Activitat activitat = llistaActivitats[position / 7][position % 7];
            if (activitat != null) {
                holder.nomActivitat.setText(activitat.getNom());
                holder.diaActivitat.setText(activitat.getDia());
                holder.horaIniciActivitat.setText(activitat.getHoraInici());
            } else {
                holder.nomActivitat.setText("");
                // Netejar les altres vistes del ViewHolder
            }
        } else {
            // Gestionar el cas de llista d'activitats buida o posició invàlida
        }
    }

    @Override
    public int getItemCount() {
        return 14 * 7; // 14 franges horàries, 7 dies a la setmana
    }

    static class ReservesViewHolder extends RecyclerView.ViewHolder {
        TextView nomActivitat;
        TextView diaActivitat;
        TextView horaIniciActivitat;

        public ReservesViewHolder(@NonNull View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            diaActivitat = itemView.findViewById(R.id.diaActivitat);
            horaIniciActivitat = itemView.findViewById(R.id.horaIniciActivitat);

            // Comprovar si els TextViews s'han inicialitzat correctament
            if (nomActivitat == null || diaActivitat == null || horaIniciActivitat == null) {
                throw new RuntimeException("Error al inicialitzar els TextViews en el ViewHolder");
            }
        }
}
}