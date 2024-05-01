package antonioguerrero.ioc.fithub.menu.installacions;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.installacions.EliminarInstallacio;

/**
 * Adaptador per a la llista de les instal·lacions disponibles al centre esportiu.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les dades de les instal·lacions en una llista.
 * <p>
 * @version 1.0
 */
public class GestioInstallacionsAdapter extends RecyclerView.Adapter<GestioInstallacionsAdapter.ViewHolder> {

    private List<HashMap<String, String>> installacionsList;
    private Context mContext;

    /**
     * Constructor de la classe.
     * <p>
     * @param installacionsList Llista de les instal·lacions disponibles al centre esportiu.
     */
    public GestioInstallacionsAdapter(Context context, List<HashMap<String, String>> installacionsList) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_installacio, parent, false);
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
        holder.btnGestionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomInstallacio = installacio.get("nomInstallacio");
                String descripcioInstallacio = installacio.get("descripcioInstallacio");
                String tipusInstallacio = holder.tipusInstallacio.getText().toString();
                dialegGestionarInstallacio(nomInstallacio, descripcioInstallacio, tipusInstallacio);
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
        public View btnGestionar;
        TextView nomInstallacio, tipusInstallacio;

        public ViewHolder(View itemView) {
            super(itemView);
            nomInstallacio = itemView.findViewById(R.id.nomInstallacio);
            tipusInstallacio = itemView.findViewById(R.id.tipusInstallacio);
            btnGestionar = itemView.findViewById(R.id.btnGestionar);
        }
    }

    /**
     * Mètode que mostra un diàleg amb els detalls de la instal·lació.
     * <p>
     * @param nomInstallacio Nom de la instal·lació.
     * @param descripcioInstallacio Descripció de la instal·lació.
     * @param tipusInstallacio Tipus de la instal·lació.
     */
    private void dialegGestionarInstallacio(String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Infletem el disseny personalitzat
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_gestionar_installacions, null);

        // Configurar les vistes del diàleg amb les dades de la instal·lació
        TextView tvNomInstallacio = dialogView.findViewById(R.id.tvNomInstallacio);
        TextView tvDescripcioInstallacio = dialogView.findViewById(R.id.tvDescripcioInstallacio);
        TextView tvTipusInstallacio = dialogView.findViewById(R.id.tvTipusInstallacio);
        Button btnModificar = dialogView.findViewById(R.id.btnModificar);
        Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);
        ImageButton botoTancar = dialogView.findViewById(R.id.botoTancar);

        tvNomInstallacio.setText(nomInstallacio);
        tvDescripcioInstallacio.setText(descripcioInstallacio);
        tvTipusInstallacio.setText(tipusInstallacio);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mostrarToast(mContext, Constants.PENDENT_IMPLEMENTAR);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarInstallacio(nomInstallacio);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        botoTancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Interfície que s'executa quan s'ha eliminat una instal·lació.
     */
    public interface OnInstallacioDeletedListener {
        void onInstallacioDeleted();
    }

    /**
     * Listener per a la interfície OnInstallacioDeletedListener.
     */
    private OnInstallacioDeletedListener mListener;

    /**
     * Mètode que estableix el listener per a la interfície OnInstallacioDeletedListener.
     * <p>
     * @param listener Listener per a la interfície OnInstallacioDeletedListener.
     */
    public void setOnInstallacioDeletedListener(OnInstallacioDeletedListener listener) {
        mListener = listener;
    }

    /**
     * Mètode que elimina una instal·lació.
     * <p>
     * @param nomInstallacio Nom de la instal·lació a eliminar.
     */
    private void eliminarInstallacio(String nomInstallacio) {
        EliminarInstallacio eliminarInstallacio = new EliminarInstallacio(new ConnexioServidor.respostaServidorListener() {
            @Override
            public void respostaServidor(Object resposta) throws ConnectException {
                if (mListener != null) {
                    mListener.onInstallacioDeleted();
                }
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext, nomInstallacio) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        eliminarInstallacio.eliminarInstallacio();
    }

}
