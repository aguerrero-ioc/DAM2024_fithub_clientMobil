package antonioguerrero.ioc.fithub.menu.activitats;

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
import antonioguerrero.ioc.fithub.peticions.activitats.EliminarActivitat;

public class GestioActivitatsAdapter extends RecyclerView.Adapter<GestioActivitatsAdapter.ViewHolder> {

    private List<HashMap<String, String>> activitatsList;
    private Context mContext;

    public GestioActivitatsAdapter(Context context, List<HashMap<String, String>> activitatsList) {
        this.mContext = context;
        this.activitatsList = activitatsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_activitat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> activitat = activitatsList.get(position);
        holder.nomActivitat.setText(activitat.get("nomActivitat"));
        // Lógica per mostrar el tipus d'activitat
        String tipusActivitat = activitat.get("tipusActivitat");
        if (tipusActivitat != null) {
            switch (tipusActivitat) {
                case "1":
                    holder.tipusActivitat.setText("Sala");
                    break;
                case "2":
                    holder.tipusActivitat.setText("Piscina");
                    break;
                default:
                    holder.tipusActivitat.setText("Desconegut");
                    break;
            }
        } else {
            holder.tipusActivitat.setText("No definit");
        }

        holder.btnGestionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomActivitat = activitat.get("nomActivitat");
                String descripcioActivitat = activitat.get("descripcioActivitat");
                String aforamentActivitat = activitat.get("aforamentActivitat");
                String tipusActivitat = holder.tipusActivitat.getText().toString();
                dialegGestionarActivitat(nomActivitat, descripcioActivitat, aforamentActivitat, tipusActivitat);
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
        return activitatsList.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View btnGestionar;
        TextView nomActivitat, tipusActivitat;

        public ViewHolder(View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            tipusActivitat = itemView.findViewById(R.id.tipusActivitat);
            btnGestionar = itemView.findViewById(R.id.btnGestionar);
        }
    }

    /**
     * Mètode per mostrar el diàleg amb els detalls de l'activitat.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de l'activitat seleccionada.
     *
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     */
    private void dialegGestionarActivitat(String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Crear una vista personalizada para el diálogo
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_gestionar_activitats, null);

        // Configurar las vistas del diseño personalizado
        TextView tvNomActivitat = dialogView.findViewById(R.id.tvNomActivitat);
        TextView tvDescripcioActivitat = dialogView.findViewById(R.id.tvDescripcioActivitat);
        TextView tvAforamentActivitat = dialogView.findViewById(R.id.tvAforamentActivitat);
        TextView tvTipusActivitat = dialogView.findViewById(R.id.tvTipusActivitat);
        Button btnModificar = dialogView.findViewById(R.id.btnModificar);
        Button btnEliminar = dialogView.findViewById(R.id.btnCancelar);
        ImageButton botoTancar = dialogView.findViewById(R.id.botoTancar);

        tvNomActivitat.setText(nomActivitat);
        tvDescripcioActivitat.setText(descripcioActivitat);
        tvAforamentActivitat.setText(aforamentActivitat);
        tvTipusActivitat.setText(tipusInstallacio);

        // Configurar los botones
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mostrarToast(mContext, Constants.PENDENT_IMPLEMENTAR);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarActivitat(nomActivitat);            }
        });

        // Crear el diálogo con el diseño personalizado
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        botoTancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tancar el diàleg
                dialog.dismiss();
            }
        });
    }

    public interface OnActivitatDeletedListener {
        void onActivitatDeleted();
    }

    private OnActivitatDeletedListener mListener;

    public void setOnActivitatDeletedListener(OnActivitatDeletedListener listener) {
        mListener = listener;
    }

    private void eliminarActivitat(String nomActivitat) {
        EliminarActivitat eliminarActivitat = new EliminarActivitat(new ConnexioServidor.respostaServidorListener() {
            @Override
            public void respostaServidor(Object resposta) throws ConnectException {
                if (mListener != null) {
                    mListener.onActivitatDeleted();
                }
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext, nomActivitat) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        eliminarActivitat.eliminarActivitat();
    }

}
