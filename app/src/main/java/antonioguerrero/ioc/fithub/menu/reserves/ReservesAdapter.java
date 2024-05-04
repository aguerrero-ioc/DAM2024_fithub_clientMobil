package antonioguerrero.ioc.fithub.menu.reserves;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.peticions.reserves.EliminarReserva;


public class ReservesAdapter extends RecyclerView.Adapter<ReservesAdapter.ViewHolder> {

    private List<HashMap<String, String>> reservesList;
    private Context mContext;

    private SharedPreferences preferencies;
    private String sessioID;


    public ReservesAdapter(Context context, List<HashMap<String, String>> reservesList) {
        this.mContext = context;
        this.reservesList = reservesList;
        this.preferencies = context.getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserva, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int posicio) {
        HashMap<String, String> reserva = reservesList.get(posicio);
        holder.nomActivitat.setText(reserva.get(Constants.ACT_NOM));
        holder.dataReserva.setText(reserva.get(Constants.CLASSE_DATA));
        holder.horaInici.setText(reserva.get(Constants.CLASSE_HORA));
        holder.estatClasse.setText(reserva.get(Constants.CLASSE_ESTAT));

        holder.btnMesDetalls.setOnClickListener(v -> {
            // Obtener el ID de la reserva
            String IDreserva = reserva.get(Constants.RESERVA_ID);

            // Obtenir les dades de la classe dirigida per mostrar en el diàleg
            String nomActivitat = reserva.get(Constants.ACT_NOM);
            String nomInstallacio = reserva.get(Constants.INS_NOM);
            String dataClasse = reserva.get(Constants.CLASSE_DATA);
            String horaInici = reserva.get(Constants.CLASSE_HORA);
            String duracio = reserva.get(Constants.CLASSE_DURACIO);
            String ocupacioClasse = reserva.get(Constants.CLASSE_OCUPACIO);
            String estatClasse = reserva.get(Constants.CLASSE_ESTAT);


            // Crear i mostrar el diàleg amb la informació de la classe dirigida
            dialegDetallsReserva(nomActivitat, nomInstallacio, dataClasse, horaInici, duracio, ocupacioClasse, estatClasse, IDreserva);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista.
     * <p>
     * @return Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return reservesList.size();
    }

    /**
     * Classe interna que representa una vista de la llista.
     * <p>
     * Aquesta classe conté les vistes que es mostraran per a cada element de la llista.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
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

    private void dialegDetallsReserva(String nomActivitat, String nomInstallacio, String dataClasse, String horaInici, String duracio, String ocupacioClasse, String estatClasse, String IDreserva) {
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
        tvHoraInici.setText(horaInici);
        tvDuracio.setText(duracio);
        tvOcupacioClasse.setText(ocupacioClasse);
        tvEstatClasse.setText(estatClasse);

        // Configurar los botones de "Cancelar reserva"
        Button btnCancelarReserva = dialogView.findViewById(R.id.btnCancelarReserva);

        btnCancelarReserva.setOnClickListener(v -> {
            // Crear una instancia de la clase EliminarReserva y llamar al método para eliminar la reserva
            EliminarReserva eliminarReserva = new EliminarReserva(new ConnexioServidor.respostaServidorListener() {

                @Override
                public void respostaServidor(Object resposta) throws ConnectException {

                }

                @Override
                public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                    return null;
                }
            }, mContext, IDreserva) {
                @Override
                public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                    return null;
                }
            }; // Utilizar el ID de la reserva


            try {
                eliminarReserva.eliminarReserva();
            } catch (ConnectException e) {
                throw new RuntimeException(e);
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
}
