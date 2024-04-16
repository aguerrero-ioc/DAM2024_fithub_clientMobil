/* PENDENT D'IMPLEMENTAR

package antonioguerrero.ioc.fithub.menu.classesdirigides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.ClasseDirigida;
import antonioguerrero.ioc.fithub.menu.reserves.ReservesActivity;

public class ClasseDirigidaAdapter extends RecyclerView.Adapter<ClasseDirigidaAdapter.ViewHolder> {

    private Context context;
    private List<ClasseDirigida> llistaClassesDirigides;

    public ClasseDirigidaAdapter(Context context, List<ClasseDirigida> llistaClassesDirigides) {
        this.context = context;
        this.llistaClassesDirigides = llistaClassesDirigides;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_classe_dirigida, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClasseDirigida classeDirigida = llistaClassesDirigides.get(position);
        holder.tvNomActivitat.setText(classeDirigida.getActivitat().getNomActivitat());

        holder.tvNomActivitat.setText(classeDirigida.getActivitat().getNomActivitat());
        holder.tvNomInstallacio.setText(classeDirigida.getInstallacio().getNomInstallacio());
        holder.tvDia.setText(classeDirigida.getDia());
        holder.tvHora.setText(classeDirigida.getHoraInici());
        holder.tvDuracio.setText(String.valueOf(classeDirigida.getDuracio()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservesActivity.mostrarDialegReserva(classeDirigida);
            }
        });

    }

    @Override
    public int getItemCount() {
        return llistaClassesDirigides.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomActivitat;
        TextView tvNomInstallacio;
        TextView tvDia;
        TextView tvHora;
        TextView tvDuracio;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomActivitat = itemView.findViewById(R.id.tvNomActivitat);
            tvNomInstallacio = itemView.findViewById(R.id.tvNomInstallacio);
            tvDia = itemView.findViewById(R.id.tvDia);
            tvHora = itemView.findViewById(R.id.tvHora);
            tvDuracio = itemView.findViewById(R.id.tvDuracio);
        }
    }
}
*/