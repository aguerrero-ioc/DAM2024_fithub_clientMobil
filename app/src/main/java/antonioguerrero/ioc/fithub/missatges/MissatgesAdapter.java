package antonioguerrero.ioc.fithub.missatges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.objectes.Missatge;

/**
 * Adaptador per mostrar una llista de missatges en un RecyclerView.
 */
public class MissatgesAdapter extends RecyclerView.Adapter<MissatgesAdapter.ViewHolder> {

    private Context context;
    private List<Missatge> llistaMissatges;

    /**
     * Constructor de la classe MissatgesAdapter.
     * @param context Contexte de l'aplicació.
     * @param llistaMissatges Llista de missatges a mostrar.
     */
    public MissatgesAdapter(Context context, List<Missatge> llistaMissatges) {
        this.context = context;
        this.llistaMissatges = llistaMissatges;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_missatge, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Missatge missatge = llistaMissatges.get(position);
        holder.textViewRemitent.setText(missatge.getRemitent());
        holder.textViewContingut.setText(missatge.getContingut());
        holder.textViewData.setText(missatge.getData());
    }

    @Override
    public int getItemCount() {
        return llistaMissatges.size();
    }

    /**
     * Mètode per actualitzar la llista de missatges quan es reben nous missatges del servidor.
     * @param nousMissatges Llista de nous missatges rebuts.
     */
    public void actualitzarMissatges(List<Missatge> nousMissatges) {
        llistaMissatges.clear(); // Netegem la llista actual de missatges
        llistaMissatges.addAll(nousMissatges); // Afegim els nous missatges rebuts
        notifyDataSetChanged(); // Notifiquem al RecyclerView que les dades han canviat
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRemitent, textViewContingut, textViewData;

        public ViewHolder(@NonNull View vista) {
            super(vista);
            textViewRemitent = vista.findViewById(R.id.text_remitent);
            textViewContingut = vista.findViewById(R.id.text_contingut);
            textViewData = vista.findViewById(R.id.text_data);
        }
    }
}
