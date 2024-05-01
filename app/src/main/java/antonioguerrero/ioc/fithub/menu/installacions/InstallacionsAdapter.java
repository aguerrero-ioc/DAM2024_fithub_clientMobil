package antonioguerrero.ioc.fithub.menu.installacions;

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
    private Context mContext;

    /**
     * Constructor de la classe.
     * <p>
     * @param installacionsList Llista de les instal·lacions disponibles al centre esportiu.
     */
    public InstallacionsAdapter(Context context, List<HashMap<String, String>> installacionsList) {
        this.mContext = context;
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
        holder.nomInstallacio.setText(installacio.get(Constants.INS_NOM));

        // Obtenir el tipus de la instal·lació i mostrar-lo en la vista
        String tipusInstallacio = installacio.get(Constants.INS_TIPUS);
        if (tipusInstallacio != null) {
            switch (tipusInstallacio) {
                case "1":
                    holder.tipusInstallacio.setText("Sala");
                    break;
                case "2":
                    holder.tipusInstallacio.setText("Piscina");
                    break;
                default:
                    holder.tipusInstallacio.setText("Desconegut");
                    break;
            }
        } else {
            holder.tipusInstallacio.setText("No definit");
        }

        // Agregar un listener de clics al botón "Més detalls"
        holder.btnMesDetalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtenir les dades de la instal·lació per a mostrar-les en el diàleg
                String nom = installacio.get("nomInstallacio");
                String descripcio = installacio.get("descripcioInstallacio");
                String tipus = holder.tipusInstallacio.getText().toString(); // Obtener el tipo de la vista

                // Crear i mostrar el diàleg amb els detalls de la instal·lació
                dialegDetallsInstallacio(nom, descripcio, tipus);
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
        return installacionsList.size();
    }

    /**
     * Classe interna que representa una vista de la llista.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomInstallacio, tipusInstallacio;

        public ViewHolder(View itemView) {
            super(itemView);
            nomInstallacio = itemView.findViewById(R.id.nomInstallacio);
            tipusInstallacio = itemView.findViewById(R.id.tipusInstallacio);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }


    /**
     * Mètode que mostra un diàleg amb els detalls de la instal·lació.
     * <p>
     * @param nomInstallacio Nom de la instal·lació.
     * @param descripcioInstallacio Descripció de la instal·lació.
     * @param tipusInstallacio Tipus de la instal·lació.
     */
    private void dialegDetallsInstallacio(String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Infletem el disseny personalitzat
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_installacio, null);

        // Configurar les vistes del diàleg amb les dades de la instal·lació
        TextView tvNomInstallacio = dialogView.findViewById(R.id.tvNomInstallacio);
        TextView tvDescripcioInstallacio = dialogView.findViewById(R.id.tvDescripcioInstallacio);
        TextView tvTipusInstallacio = dialogView.findViewById(R.id.tvTipusInstallacio);

        tvNomInstallacio.setText(nomInstallacio);
        tvDescripcioInstallacio.setText(descripcioInstallacio);
        tvTipusInstallacio.setText(tipusInstallacio);

        // Crear el diálogo con el diseño personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        builder.setPositiveButton("D'acord", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}