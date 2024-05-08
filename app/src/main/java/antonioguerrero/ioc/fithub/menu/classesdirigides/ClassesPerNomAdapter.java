package antonioguerrero.ioc.fithub.menu.classesdirigides;

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
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.reserves.CrearReserva;

/**
 * Adaptador per a la llista de classes dirigides disponibles per nom d'activitat.
 * <p>
 * Aquest adaptador s'encarrega de mostrar les classes dirigides disponibles per nom d'activitat.
 * Per a cada classe dirigida, es mostra la data, l'hora d'inici i un botó per a més detalls.
 * Quan es fa clic al botó "Més detalls", es mostra un diàleg amb la informació de la classe dirigida.
 * Aquest diàleg mostra el nom de l'activitat, el nom de la instal·lació, la data, l'hora d'inici, la durada, l'ocupació i l'estat de la classe dirigida.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ClassesPerNomAdapter extends RecyclerView.Adapter<ClassesPerNomAdapter.ViewHolder> {
    private final List<HashMap<String, String>> classesDirigidesLlista;
    private final Context mContext;

    /**
     * Constructor de la classe.
     *
     * @param context               Context de l'aplicació.
     * @param classesDirigidesLlista    Llista de classes dirigides.
     */
    public ClassesPerNomAdapter(Context context, List<HashMap<String, String>> classesDirigidesLlista) {
        this.mContext = context;
        this.classesDirigidesLlista = classesDirigidesLlista;
    }

    /**
     * Mètode que crea una nova instància de la classe ViewHolder.
     * <p>
     * @param parent    Vista pare on es mostrarà la nova vista.
     * @param viewType  Tipus de la nova vista.
     * @return          Instància de la classe ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classe_dirigida_nom, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que enllaça les dades de la llista amb les vistes de la llista.
     * <p>
     * @param holder    Vista de la llista.
     * @param posicio   Posició de l'element de la llista.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicio) {
        HashMap<String, String> classeDirigida = classesDirigidesLlista.get(posicio);
        holder.dataClasse.setText(classeDirigida.get(Constants.CLASSE_DATA));
        holder.horaInici.setText(classeDirigida.get(Constants.CLASSE_HORA));
        holder.estatClasse.setText(classeDirigida.get(Constants.CLASSE_ESTAT));

        // Obtenir el ID de la classe dirigida i l'ID de l'usuari
        String IDclasseDirigida = classeDirigida.get(Constants.CLASSE_ID);
        String IDusuari = String.valueOf(Usuari.obtenirUsuari().getIDusuari());

        // Afegir un listener de clics al botó "Més detalls"
        holder.btnMesDetalls.setOnClickListener(v -> {
            // Obtenir les dades de la classe dirigida per mostrar en el diàleg
            String nomActivitat = classeDirigida.get(Constants.ACT_NOM);
            String nomInstallacio = classeDirigida.get(Constants.INS_NOM);
            String dataClasse = classeDirigida.get(Constants.CLASSE_DATA);
            String horaInici = classeDirigida.get(Constants.CLASSE_HORA);
            String duracio = classeDirigida.get(Constants.CLASSE_DURACIO);
            String ocupacioClasse = classeDirigida.get(Constants.CLASSE_OCUPACIO);
            String estatClasse = classeDirigida.get(Constants.CLASSE_ESTAT);

            // Crear i mostrar el diàleg amb la informació de la classe dirigida
            dialegDetallsClasseDirigida(nomActivitat, nomInstallacio, dataClasse, horaInici, duracio, ocupacioClasse, estatClasse, IDclasseDirigida, IDusuari);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista.
     * <p>
     * @return  Nombre d'elements de la llista.
     */
    @Override
    public int getItemCount() {
        return classesDirigidesLlista.size();
    }

    /**
     * Classe interna que representa una vista de la llista.
     * <p>
     * Aquesta classe conté les vistes que es mostraran per a cada element de la llista.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnMesDetalls;
        TextView dataClasse, horaInici, estatClasse;

        public ViewHolder(View itemView) {
            super(itemView);
            dataClasse = itemView.findViewById(R.id.tvDataClasse);
            horaInici = itemView.findViewById(R.id.tvHoraInici);
            estatClasse = itemView.findViewById(R.id.tvEstatClasse);
            btnMesDetalls = itemView.findViewById(R.id.btnMesDetalls);
        }
    }

    /**
     * Mètode per mostrar el diàleg amb els detalls de la classe dirigida.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de la classe dirigida seleccionada.
     * <p>
     * @param nomActivitat      Nom de l'activitat.
     * @param nomInstallacio    Nom de la instal·lació on es realitza l'activitat.
     * @param dataClasse        Data de la classe dirigida.
     * @param horaInici         Hora d'inici de la classe dirigida.
     * @param duracio           Durada de la classe dirigida.
     * @param ocupacioClasse    Ocupació de la classe dirigida.
     * @param estatClasse       Estat de la classe dirigida.
     * @param IDclasseDirigida  ID de la classe dirigida.
     * @param IDusuari          ID de l'usuari.
     */
    private void dialegDetallsClasseDirigida(String nomActivitat, String nomInstallacio, String dataClasse, String horaInici, String duracio, String ocupacioClasse, String estatClasse, String IDclasseDirigida, String IDusuari) {
        // Inflar el disseny personalitzat del diàleg
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_detalls_classe_dirigida, null);

        // Configurar les vistes del disseny personalitzat
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

        // Crear una instancia de la classe Reserva
        Reserva reserva = new Reserva(IDclasseDirigida, IDusuari);

        // Configurar els botons de "Reservar" i "Cancelar reserva"
        Button btnReservar = dialogView.findViewById(R.id.btnReservar);

        btnReservar.setOnClickListener(v -> {
            // Crear una instancia de la clase CrearReserva i cridar al mètode per crear la reserva
            CrearReserva crearReserva = new CrearReserva(new ConnexioServidor.respostaServidorListener() {
            }, mContext) {
            };

            crearReserva.setReserva(reserva);
            crearReserva.crearReserva(ClassesPerNomActivity.class);
        });

        // Crear el diàleg amb el disseny personalitzat
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialogView);
        AlertDialog dialeg = builder.create();
        dialeg.show();

        botoTancar.setOnClickListener(v -> {
            // Tanca el diàleg
            dialeg.dismiss();
        });
    }
}