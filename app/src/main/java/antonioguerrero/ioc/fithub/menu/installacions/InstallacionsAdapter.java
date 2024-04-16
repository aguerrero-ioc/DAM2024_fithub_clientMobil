package antonioguerrero.ioc.fithub.menu.installacions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.R;

/**
 * Adaptador per a la llista de les instal·lacions disponibles al centre esportiu.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les dades de les instal·lacions en una llista.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class InstallacionsAdapter extends RecyclerView.Adapter<InstallacionsAdapter.ViewHolder> {

    private List<HashMap<String, String>> installacionsList;

    /**
     * Constructor de la classe.
     * <p>
     * @param installacionsList Llista de les instal·lacions disponibles al centre esportiu.
     */
    public InstallacionsAdapter(List<HashMap<String, String>> installacionsList) {
        this.installacionsList = installacionsList;
    }

    /**
     * Mètode que crea una nova instància de la classe ViewHolder.
     * <p>
     * @param parent Vista pare.
     * @param viewType Tipus de vista.
     * @return Instància de la classe ViewHolder.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_installacio, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que s'executa per a cada element de la llista.
     * S'encarrega de mostrar les dades de les instal·lacions.
     * <p>
     * @param holder Instància de la classe ViewHolder.
     * @param position Posició de l'element a la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> installacio = installacionsList.get(position);
        holder.nomInstallacio.setText(installacio.get("nomInstallacio"));
        holder.descripcioInstallacio.setText(installacio.get("descripcioInstallacio"));
        holder.tipusInstallacio.setText(installacio.get("tipusInstallacio"));
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista.
     * <p>
     * @return Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return installacionsList.size();
    }

    /**
     * Classe interna que representa una vista de la llista.
     */
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