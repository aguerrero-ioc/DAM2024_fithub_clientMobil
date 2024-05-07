package antonioguerrero.ioc.fithub.menu.activitats;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.peticions.activitats.EliminarActivitat;
import antonioguerrero.ioc.fithub.peticions.activitats.ModificarActivitat;

/**
 * Classe que representa un adaptador per a la llista d'activitats.
 * <p>
 * Aquest adaptador s'encarrega de gestionar les dades de les activitats i mostrar-les en una llista.
 * <p>
 * Aquest adaptador permet a l'usuari administrador modificar i eliminar les activitats de la llista.
 * <p>
 * Aquest adaptador també mostra un diàleg per a la modificació i eliminació de les activitats.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioActivitatsAdapter extends RecyclerView.Adapter<GestioActivitatsAdapter.ViewHolder> {
    private final List<HashMap<String, String>> activitatsList;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;

    /**
     * Constructor de la classe GestioActivitatsAdapter.
     * <p>
     * @param context Context de l'aplicació.
     * @param activitatsList Llista d'activitats.
     */
    public GestioActivitatsAdapter(Context context, List<HashMap<String, String>> activitatsList) {
        this.mContext = context;
        this.activitatsList = activitatsList;
    }

    /**
     * Mètode per crear una nova instància de ViewHolder.
     * <p>
     * @param parent Vista pare.
     * @param viewType Tipus de vista.
     * @return Nova instància de ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_activitat, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode per enllaçar les dades de l'activitat amb les vistes del ViewHolder.
     * <p>
     * @param holder ViewHolder.
     * @param position Posició de l'element.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> activitat = activitatsList.get(position);
        holder.nomActivitat.setText(activitat.get("nomActivitat"));
        // Lògica per mostrar el tipus d'activitat
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

        holder.btnModificar.setOnClickListener(v -> {
            String IDactivitat = activitat.get(Constants.ACT_ID);
            String nomActivitat = activitat.get(Constants.ACT_NOM);
            String descripcioActivitat = activitat.get(Constants.ACT_DESC);
            String aforamentActivitat = activitat.get(Constants.ACT_AFORAMENT);
            String tipusActivitat12 = holder.tipusActivitat.getText().toString();
            dialegModificarActivitat(Integer.parseInt(IDactivitat), nomActivitat, descripcioActivitat, aforamentActivitat, tipusActivitat12);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            String IDactivitat = activitat.get(Constants.ACT_ID);
            String nomActivitat = activitat.get(Constants.ACT_NOM);
            String descripcioActivitat = activitat.get(Constants.ACT_DESC);
            String aforamentActivitat = activitat.get(Constants.ACT_AFORAMENT);
            String tipusActivitat1 = holder.tipusActivitat.getText().toString();
            dialegEliminarActivitat(Integer.parseInt(IDactivitat), nomActivitat, descripcioActivitat, aforamentActivitat, tipusActivitat1);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista de les activitats.
     * <p>
     * @return Retorna el nombre d'elements de la llista de les activitats.
     */
    @Override
    public int getItemCount() {
        return activitatsList.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista d'activitats.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnModificar, btnDesar, btnEliminar;
        TextView nomActivitat, tipusActivitat;

        public ViewHolder(View itemView) {
            super(itemView);
            nomActivitat = itemView.findViewById(R.id.nomActivitat);
            tipusActivitat = itemView.findViewById(R.id.tipusActivitat);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnDesar = itemView.findViewById(R.id.btnDesar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    /**
     * Mètode per mostrar el diàleg de modificació de l'activitat.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de l'activitat seleccionada per a la seva modificació.
     * <p>
     * @param IDactivitat ID de l'activitat.
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     */
    private void dialegModificarActivitat(int IDactivitat, String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Crear una nova instància de l'activitat
        Activitat activitat = new Activitat();

        // Crear una vista personalitzada per al diàleg
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_activitats, null);

        // Configurar les vistes del disseny personalitzat
        configurarVistesDialegModificar(dialegView, IDactivitat, nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio, activitat);

        // Mostrar el diàleg
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode per configurar les vistes del disseny personalitzat del dialeg de modificació de l'activitat.
     * <p>
     * @param dialegView Vista del dialeg amb el disseny personalitzat.
     * @param IDactivitat ID de l'activitat.
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     * @param activitat Instància de l'activitat.
     */
    private void configurarVistesDialegModificar(View dialegView, int IDactivitat, String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio, Activitat activitat) {
        // Obtenir referències als elements de la vista del dialeg
        EditText etIDactivitat = dialegView.findViewById(R.id.etIDactivitat);
        EditText etNomActivitat = dialegView.findViewById(R.id.etNomActivitat);
        EditText etDescripcioActivitat = dialegView.findViewById(R.id.etDescripcioActivitat);
        EditText etAforamentActivitat = dialegView.findViewById(R.id.etAforamentActivitat);
        EditText etTipusActivitat = dialegView.findViewById(R.id.etTipusActivitat);
        Button btnModificar = dialegView.findViewById(R.id.btnModificar);
        Button btnDesar = dialegView.findViewById(R.id.btnDesar);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        // Omplir els camps de text amb les dades de l'activitat
        etIDactivitat.setText(String.valueOf(IDactivitat));
        etNomActivitat.setText(nomActivitat);
        etDescripcioActivitat.setText(descripcioActivitat);
        etAforamentActivitat.setText(aforamentActivitat);
        etTipusActivitat.setText(tipusInstallacio);

        // Configurar el comportament del botó "Modificar"
        configurarBotoModificar(btnModificar, btnDesar);
        // Configurar el comportament del botó "Desar"
        configurarBotoDesar(btnModificar, btnDesar, etIDactivitat, etNomActivitat, etDescripcioActivitat, etAforamentActivitat, etTipusActivitat, activitat);
        // Configurar el comportament del botó de tancament
        configurarBotoTancar(botoTancar);
    }

    /**
     * Mètode per configurar el comportament del botó "Modificar".
     * <p>
     * Aquest mètode configura el comportament del botó "Modificar" per a l'edició de l'activitat.
     * <p>
     * @param btnModificar Botó "Modificar".
     * @param btnDesar Botó "Desar".
     */
    private void configurarBotoModificar(Button btnModificar, Button btnDesar) {
        btnModificar.setOnClickListener(v -> {
            // Canviar l'estat d'edició al fer clic a "Modificar activitat"
            estaEditant = true;
            // Habilitar l'edició dels camps de text
            habilitarEdicio(true);
            // Habilitar el botó de desar canvis
            btnDesar.setEnabled(true);
            btnModificar.setEnabled(false);
        });
    }

    /**
     * Mètode per configurar el comportament del botó "Desar" per a desar els canvis realitzats a l'activitat.
     * <p>
     * @param btnModificar Botó "Modificar".
     * @param btnDesar Botó "Desar".
     * @param etIDactivitat Camp de text de l'ID de l'activitat.
     * @param etNomActivitat Camp de text del nom de l'activitat.
     * @param etDescripcioActivitat Camp de text de la descripció de l'activitat.
     * @param etAforamentActivitat Camp de text de l'aforament de l'activitat.
     * @param etTipusActivitat Camp de text del tipus de l'activitat.
     * @param activitat Instància de l'activitat.
     */
    private void configurarBotoDesar(Button btnModificar, Button btnDesar, EditText etIDactivitat, EditText etNomActivitat, EditText etDescripcioActivitat, EditText etAforamentActivitat, EditText etTipusActivitat, Activitat activitat) {
        btnDesar.setOnClickListener(v -> {
            // Obtenir els valors dels camps de text
            int IDactivitatModificat = Integer.parseInt(etIDactivitat.getText().toString());
            String nomActivitatModificat = etNomActivitat.getText().toString();
            String descripcioActivitatModificada = etDescripcioActivitat.getText().toString();
            String aforamentActivitatModificat = etAforamentActivitat.getText().toString();
            String tipusActivitatModificat = convertTipusActivitat(etTipusActivitat.getText().toString());

            // Validar que cap camp estigui buit
            if (!nomActivitatModificat.isEmpty() && !descripcioActivitatModificada.isEmpty()
                    && !aforamentActivitatModificat.isEmpty() && !tipusActivitatModificat.isEmpty()) {
                // Assignar els valors modificats a la instància de l'activitat
                activitat.setIDactivitat(IDactivitatModificat);
                activitat.setNomActivitat(nomActivitatModificat);
                activitat.setDescripcioActivitat(descripcioActivitatModificada);
                activitat.setAforamentActivitat(Integer.parseInt(aforamentActivitatModificat));
                activitat.setTipusInstallacio(Integer.parseInt(tipusActivitatModificat));

                // Cridar al mètode per modificar l'activitat
                modificarActivitat(activitat);
            } else {
                // Mostrar un missatge d'error si algun dels camps està buit
                Utils.mostrarToast(mContext, "Si us plau, omple tots els camps abans de desar els canvis.");
            }

            // Deshabilitar l'edició dels camps de text
            habilitarEdicio(false);
            // Deshabilitar el botó de desar canvis
            btnDesar.setEnabled(false);
            // Habilitar el botó "Modificar"
            btnModificar.setEnabled(true);
        });
    }

    /**
     * Mètode per configurar el comportament del botó de tancament del diàleg.
     * <p>
     * @param botoTancar Botó de tancament.
     */
    private void configurarBotoTancar(ImageButton botoTancar) {
        botoTancar.setOnClickListener(v -> {
            // Tancar el diàleg
            ((AlertDialog) dialegView.getTag()).dismiss();
        });
    }

    /**
     * Mètode per mostrar el diàleg amb el disseny personalitzat passat com a paràmetre.
     * <p>
     * @param dialegView Vista del diàleg amb el disseny personalitzat.
     */
    private void mostrarDialeg(View dialegView) {
        // Crear el diàleg amb el disseny personalitzat
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(dialegView);
        AlertDialog dialeg = builder.create();
        dialeg.show();

        // Assignar el dialegView a la variable d'instància
        this.dialegView = dialegView;
        dialegView.setTag(dialeg); // Permetre tancar el diàleg des del botó de tancament
    }

    /**
     * Mètode per convertir el tipus d'activitat a un valor numèric per a l'enviament al servidor.
     * <p>
     * @param tipusActivitat Tipus de l'activitat.
     * @return Valor numèric del tipus d'activitat.
     */
    private String convertTipusActivitat(String tipusActivitat) {
        return switch (tipusActivitat) {
            case "Sala" -> "1";
            case "Piscina" -> "2";
            default -> "Desconegut";
        };
    }

    /**
     * Mètode per habilitar o deshabilitar l'edició dels camps de text segons l'estat de l'edició.
     * <p>
     * @param habilitat Estat de l'edició.
     */
    private void habilitarEdicio(boolean habilitat) {
        // Obtenir referències als EditText dins del diàleg de modificació
        EditText etNomActivitat = dialegView.findViewById(R.id.etNomActivitat);
        EditText etDescripcioActivitat = dialegView.findViewById(R.id.etDescripcioActivitat);
        EditText etAforamentActivitat = dialegView.findViewById(R.id.etAforamentActivitat);
        EditText etTipusActivitat = dialegView.findViewById(R.id.etTipusActivitat);
        // Habilitar o deshabilitar l'edició segons l'estat
        etNomActivitat.setEnabled(habilitat);
        etDescripcioActivitat.setEnabled(habilitat);
        etAforamentActivitat.setEnabled(habilitat);
        etTipusActivitat.setEnabled(habilitat);
    }

    /**
     * Mètode per modificar una activitat.
     * <p>
     * Aquest mètode crida a la classe ModificarActivitat per modificar una activitat.
     * <p>
     * @param activitat Activitat a modificar.
     */
    private void modificarActivitat(Activitat activitat) {
        ModificarActivitat modificarActivitat = new ModificarActivitat(new ConnexioServidor.respostaServidorListener() {
        }, mContext) {
        };
        modificarActivitat.setActivitat(activitat);
        modificarActivitat.modificarActivitat();
    }

    /**
     * Mètode per mostrar el diàleg d'eliminació de l'activitat seleccionada per a la seva eliminació.
     * <p>
     * @param IDactivitat ID de l'activitat.
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     */
    private void dialegEliminarActivitat(int IDactivitat, String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Inflar la vista del diàleg
        View dialegView = crearDialegEliminarActivitat(IDactivitat, nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio);

        // Mostrar el diàleg
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode per crear la vista del diàleg d'eliminació de l'activitat amb les dades de l'activitat seleccionada.
     * <p>
     * @param IDactivitat ID de l'activitat.
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     * @return Vista del diàleg d'eliminació de l'activitat.
     */
    private View crearDialegEliminarActivitat(int IDactivitat, String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Inflar la vista del disseny personalitzat del dialeg
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_eliminar_activitats, null);

        // Configurar les vistes del disseny personalitzat
        configurarVistesDialegEliminar(dialogView, IDactivitat, nomActivitat, descripcioActivitat, aforamentActivitat, tipusInstallacio);

        return dialogView;
    }

    /**
     * Mètode per configurar les vistes del disseny personalitzat del dialeg d'eliminació de l'activitat.
     * <p>
     * @param dialogView Vista del dialeg amb el disseny personalitzat.
     * @param IDactivitat ID de l'activitat.
     * @param nomActivitat Nom de l'activitat.
     * @param descripcioActivitat Descripció de l'activitat.
     * @param aforamentActivitat Aforament de l'activitat.
     * @param tipusInstallacio Tipus de l'instal·lació on es realitza l'activitat.
     */
    private void configurarVistesDialegEliminar(View dialogView, int IDactivitat, String nomActivitat, String descripcioActivitat, String aforamentActivitat, String tipusInstallacio) {
        // Obtindre referències als elements de la vista del dialeg
        TextView tvIDactivitat = dialogView.findViewById(R.id.tvIDactivitat);
        TextView tvNomActivitat = dialogView.findViewById(R.id.tvNomActivitat);
        TextView tvDescripcioActivitat = dialogView.findViewById(R.id.tvDescripcioActivitat);
        TextView tvAforamentActivitat = dialogView.findViewById(R.id.tvAforamentActivitat);
        TextView tvTipusActivitat = dialogView.findViewById(R.id.tvTipusActivitat);
        Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);
        ImageButton botoTancar = dialogView.findViewById(R.id.botoTancar);

        // Establir les dades de l'activitat als TextView corresponents
        tvIDactivitat.setText(String.valueOf(IDactivitat));
        tvNomActivitat.setText(nomActivitat);
        tvDescripcioActivitat.setText(descripcioActivitat);
        tvAforamentActivitat.setText(aforamentActivitat);
        tvTipusActivitat.setText(tipusInstallacio);

        // Configurar el botó "Eliminar"
        btnEliminar.setOnClickListener(v -> eliminarActivitat(nomActivitat));

        // Configurar el botó de tancament
        configurarBotoTancar(botoTancar);
    }

    /**
     * Mètode per eliminar una activitat.
     * <p>
     * Aquest mètode crida a la classe EliminarActivitat per eliminar una activitat.
     * <p>
     * @param nomActivitat Nom de l'activitat a eliminar.
     */
    private void eliminarActivitat(String nomActivitat) {
        EliminarActivitat eliminarActivitat = new EliminarActivitat(new ConnexioServidor.respostaServidorListener() {
        }, mContext, nomActivitat) {
        };
        eliminarActivitat.eliminarActivitat();
    }

    /**
     * Interfície que defineix el listener per a l'eliminació d'una activitat.
     */
    public interface OnActivitatEliminadaListener {
    }
}