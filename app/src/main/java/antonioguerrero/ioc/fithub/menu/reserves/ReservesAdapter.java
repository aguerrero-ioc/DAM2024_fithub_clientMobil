package antonioguerrero.ioc.fithub.menu.reserves;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Reserva;
import antonioguerrero.ioc.fithub.peticions.reserves.EliminarReserva;

/**
 * Adaptador per a la llista de les reserves d'un usuari.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les dades de les reserves en una llista.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ReservesAdapter extends RecyclerView.Adapter<ReservesAdapter.ViewHolder> {
    private final List<HashMap<String, String>> reservesLlista;
    private final Context mContext;

    /**
     * Constructor de la classe.
     * <p>
     * @param reservesLlista Llista de les reserves d'un usuari.
     */
    public ReservesAdapter(Context context, List<HashMap<String, String>> reservesLlista) {
        this.mContext = context;
        this.reservesLlista = reservesLlista;
    }

    /**
     * Mètode que crea una nova instància de la classe ViewHolder.
     * <p>
     * @param parent Vista pare.
     * @param viewType Tipus de vista.
     * @return Instància de la classe ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que s'executa per a cada element de la llista.
     * S'encarrega de mostrar les dades de les reserves.
     * <p>
     * @param holder Instància de la classe ViewHolder.
     * @param posicio Posició de l'element a la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicio) {
        HashMap<String, String> reserva = reservesLlista.get(posicio);
        holder.nomActivitat.setText(reserva.get(Constants.ACT_NOM));
        holder.dataReserva.setText(reserva.get(Constants.CLASSE_DATA));
        holder.horaInici.setText(reserva.get(Constants.CLASSE_HORA));
        holder.estatClasse.setText(reserva.get(Constants.CLASSE_ESTAT));

        // Obtenir els ID de la reserva
        final String IDreserva = reserva.get(Constants.RESERVA_ID);
        final String IDclasseDirigida = reserva.get(Constants.CLASSE_ID);
        final String IDusuari = reserva.get(Constants.ID_USUARI);

        holder.btnMesDetalls.setOnClickListener(v -> {
            // Obtenir les dades de la classe dirigida per mostrar en el diàleg
            String nomActivitat = reserva.get(Constants.ACT_NOM);
            String nomInstallacio = reserva.get(Constants.INS_NOM);
            String dataClasse = reserva.get(Constants.CLASSE_DATA);
            String horaInici = reserva.get(Constants.CLASSE_HORA);
            String duracio = reserva.get(Constants.CLASSE_DURACIO);
            String ocupacioClasse = reserva.get(Constants.CLASSE_OCUPACIO);
            String estatClasse = reserva.get(Constants.CLASSE_ESTAT);

            // Crear i mostrar el diàleg amb la informació de la classe dirigida
            dialegDetallsReserva(nomActivitat, nomInstallacio, dataClasse, horaInici, duracio, ocupacioClasse, estatClasse, IDreserva, IDclasseDirigida, IDusuari);
        });
    }

    /**
     * Mètode per obtenir el nombre d'elements de la llista.
     * <p>
     * @return Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return reservesLlista.size();
    }

    /**
     * Classe interna ViewHolder per mantenir les referències de les vistes.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView nomActivitat, dataReserva, horaInici, estatClasse;

        public ViewHolder(View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.tvNomActivitat);
            dataReserva = itemView.findViewById(R.id.tvData);
            horaInici = itemView.findViewById(R.id.tvHoraInici);
            estatClasse = itemView.findViewById(R.id.tvEstatClasse);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    /**
     * Mètode per mostrar un diàleg amb els detalls de la reserva.
     * <p>
     * @param nomActivitat    Nom de l'activitat.
     * @param nomInstallacio   Nom de la instal·lació.
     * @param dataClasse       Data de la classe.
     * @param horaInici        Hora d'inici de la classe.
     * @param duracio          Duració de la classe.
     * @param ocupacioClasse   Ocupació de la classe.
     * @param estatClasse      Estat de la classe.
     * @param IDreserva        ID de la reserva.
     * @param IDclasseDirigida ID de la classe dirigida.
     * @param IDusuari         ID de l'usuari.
     */
    @SuppressLint("SetTextI18n")
    private void dialegDetallsReserva(String nomActivitat, String nomInstallacio, String dataClasse, String horaInici, String duracio, String ocupacioClasse, String estatClasse, String IDreserva, String IDclasseDirigida, String IDusuari) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_reserva, null);

        TextView tvNomActivitat = dialogView.findViewById(R.id.tvNomActivitat);
        TextView tvNomInstallacio = dialogView.findViewById(R.id.tvNomInstallacio);
        TextView tvDataClasse = dialogView.findViewById(R.id.tvDataClasse);
        TextView tvHoraInici = dialogView.findViewById(R.id.tvHoraInici);
        TextView tvDuracio = dialogView.findViewById(R.id.tvDurada);
        TextView tvOcupacioClasse = dialogView.findViewById(R.id.tvOcupacioClasse);
        TextView tvEstatClasse = dialogView.findViewById(R.id.tvEstatClasse);
        ImageButton botoTancar = dialogView.findViewById(R.id.botoTancar);

        tvNomActivitat.setText(nomActivitat);
        tvNomInstallacio.setText(nomInstallacio);
        tvDataClasse.setText(dataClasse);
        tvHoraInici.setText(horaInici + " hores");
        tvDuracio.setText(duracio + " hora");
        tvOcupacioClasse.setText(ocupacioClasse + " usuaris");
        tvEstatClasse.setText(estatClasse);

        // Crear una instancia de la clase Reserva
        Reserva reserva = new Reserva(IDreserva, IDclasseDirigida, IDusuari);

        // Configurar los botones de "Cancelar reserva"
        Button btnCancelarReserva = dialogView.findViewById(R.id.btnCancelarReserva);

        btnCancelarReserva.setOnClickListener(v -> {
            // Crear una instancia de la clase EliminarReserva y llamar al método para eliminar la reserva
            EliminarReserva eliminarReserva = new EliminarReserva(new ConnexioServidor.respostaServidorListener() {
            }, mContext) {
            };
            eliminarReserva.setReserva(reserva);
            eliminarReserva.eliminarReserva();
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        botoTancar.setOnClickListener(v -> dialog.dismiss());
    }
}