package antonioguerrero.ioc.fithub.menu.installacions;

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
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.peticions.installacions.EliminarInstallacio;
import antonioguerrero.ioc.fithub.peticions.installacions.ModificarInstallacio;

/**
 * Classe que representa un adaptador per a la llista d'instal·lacions.
 * <p>
 * Aquest adaptador s'encarrega de gestionar les dades de les instal·lacions i mostrar-les a la llista.
 * <p>
 * Aquest adaptador permet a l'usuari administrador modificar i eliminar les instal·lacions de la llista.
 * <p>
 * Aquest adaptador també mostra un diàleg per a la modificació i eliminació de les instal·lacions.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioInstallacionsAdapter extends RecyclerView.Adapter<GestioInstallacionsAdapter.ViewHolder> {
    private final List<HashMap<String, String>> installacionsList;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;

    /**
     * Constructor de la classe GestioInstallacionsAdapter.
     * <p>
     * @param context Context de l'aplicació.
     * @param installacionsList Llista de les instal·lacions.
     */
    public GestioInstallacionsAdapter(Context context, List<HashMap<String, String>> installacionsList) {
        this.mContext = context;
        this.installacionsList = installacionsList;
    }

    /**
     * Mètode que crea un nou ViewHolder per a cada element de la llista.
     * <p>
     * @param parent Vista pare a la qual s'adjuntarà la nova vista.
     * @param viewType Tipus de la vista.
     * @return Nou ViewHolder que conté la vista de l'element de la llista.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_installacio, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que s'executa per a cada element de la llista.
     * <p>
     * Aquest mètode s'encarrega de mostrar les dades de cada element de la llista a la vista corresponent.
     * <p>
     * @param holder ViewHolder que conté les vistes de l'element de la llista.
     * @param position Posició de l'element de la llista.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> installacio = installacionsList.get(position);
        holder.nomInstallacio.setText(installacio.get("nomInstallacio"));
        String tipusInstallacio = installacio.get("tipusInstallacio");
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

        holder.btnModificar.setOnClickListener(v -> {
            String IDinstallacio = installacio.get(Constants.INS_ID);
            String nomInstallacio = installacio.get(Constants.INS_NOM);
            String descripcioInstallacio = installacio.get(Constants.INS_DESC);
            String tipusInstallacio12 = holder.tipusInstallacio.getText().toString();
            dialegModificarInstallacio(Integer.parseInt(IDinstallacio), nomInstallacio, descripcioInstallacio, tipusInstallacio12);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            String IDinstallacio = installacio.get(Constants.INS_ID);
            String nomInstallacio = installacio.get(Constants.INS_NOM);
            String descripcioInstallacio = installacio.get(Constants.INS_DESC);
            String tipusInstallacio1 = holder.tipusInstallacio.getText().toString();
            dialegEliminarInstallacio(Integer.parseInt(IDinstallacio), nomInstallacio, descripcioInstallacio, tipusInstallacio1);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista de les instal·lacions.
     * <p>
     * @return Retorna el nombre d'elements de la llista de les instal·lacions.
     */
    @Override
    public int getItemCount() {
        return installacionsList.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista d'instal·lacions.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnModificar, btnDesar, btnEliminar;
        TextView nomInstallacio, tipusInstallacio;

        public ViewHolder(View itemView) {
            super(itemView);
            nomInstallacio = itemView.findViewById(R.id.nomInstallacio);
            tipusInstallacio = itemView.findViewById(R.id.tipusInstallacio);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnDesar = itemView.findViewById(R.id.btnDesar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    /**
     * Mètode que mostra el diàleg per modificar una instal·lació.
     * <p>
     * @param IDinstallacio Identificador de la instal·lació
     * @param nomInstallacio Nom de la instal·lació
     * @param descripcioInstallacio Descripció de la instal·lació
     * @param tipusInstallacio Tipus de la instal·lació
     */
    private void dialegModificarInstallacio(int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        Installacio installacio = new Installacio();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_installacions, null);
        configurarVistesDialeg(dialegView, IDinstallacio, nomInstallacio, descripcioInstallacio, tipusInstallacio, installacio);
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode que configura les vistes del diàleg per a modificar una instal·lació.
     * <p>
     * @param dialegView Vista del diàleg
     * @param IDinstallacio Identificador de la instal·lació
     * @param nomInstallacio Nom de la instal·lació
     * @param descripcioInstallacio Descripció de la instal·lació
     * @param tipusInstallacio Tipus de la instal·lació
     * @param installacio Instància de la classe Installacio
     */
    private void configurarVistesDialeg(View dialegView, int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio, Installacio installacio) {
        EditText etIDinstallacio = dialegView.findViewById(R.id.etIDinstallacio);
        EditText etNomInstallacio = dialegView.findViewById(R.id.etNomInstallacio);
        EditText etDescripcioInstallacio = dialegView.findViewById(R.id.etDescripcioInstallacio);
        EditText etTipusInstallacio = dialegView.findViewById(R.id.etTipusInstallacio);
        Button btnModificar = dialegView.findViewById(R.id.btnModificar);
        Button btnDesar = dialegView.findViewById(R.id.btnDesar);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        etIDinstallacio.setText(String.valueOf(IDinstallacio));
        etNomInstallacio.setText(nomInstallacio);
        etDescripcioInstallacio.setText(descripcioInstallacio);
        etTipusInstallacio.setText(tipusInstallacio);

        configurarBotoModificar(btnModificar, btnDesar);
        configurarBotoDesar(btnModificar, btnDesar, etIDinstallacio, etNomInstallacio, etDescripcioInstallacio, etTipusInstallacio, installacio);
        configurarBotoTancar(botoTancar);
    }

    /**
     * Mètode per configurar el comportament del botó de modificar l'instal·lació.
     * <p>
     * @param btnModificar Botó de modificar.
     * @param btnDesar Botó de desar.
     */
    private void configurarBotoModificar(Button btnModificar, Button btnDesar) {
        btnModificar.setOnClickListener(v -> {
            estaEditant = true;
            habilitarEdicio(true);
            btnDesar.setEnabled(true);
            btnModificar.setEnabled(false);
        });
    }

    /**
     * Mètode per configurar el comportament del botó de desar els canvis de l'instal·lació.
     * <p>
     * @param btnModificar Botó de modificar.
     * @param btnDesar Botó de desar.
     * @param etIDinstallacio Camp de text de l'identificador de la instal·lació.
     * @param etNomInstallacio Camp de text del nom de la instal·lació.
     * @param etDescripcioInstallacio Camp de text de la descripció de la instal·lació.
     * @param etTipusInstallacio Camp de text del tipus de la instal·lació.
     * @param installacio Instància de la classe Installacio.
     */
    private void configurarBotoDesar(Button btnModificar, Button btnDesar, EditText etIDinstallacio, EditText etNomInstallacio, EditText etDescripcioInstallacio, EditText etTipusInstallacio, Installacio installacio) {
        btnDesar.setOnClickListener(v -> {
            int IDinstallacioModificat = Integer.parseInt(etIDinstallacio.getText().toString());
            String nomInstallacioModificat = etNomInstallacio.getText().toString();
            String descripcioInstallacioModificada = etDescripcioInstallacio.getText().toString();
            String tipusInstallacioModificat = convertTipusInstallacio(etTipusInstallacio.getText().toString());
            // Validar que cap camp estigui buit
            if (!nomInstallacioModificat.isEmpty() && !descripcioInstallacioModificada.isEmpty()
                    && !tipusInstallacioModificat.isEmpty()) {

                // Assignar els valors modificats a la instància de l'installacio
                installacio.setIDinstallacio(IDinstallacioModificat);
                installacio.setNomInstallacio(nomInstallacioModificat);
                installacio.setDescripcioInstallacio(descripcioInstallacioModificada);
                installacio.setTipus(Integer.parseInt(tipusInstallacioModificat));

                // Cridar al mètode per modificar l'installacio
                modificarInstallacio(installacio);
            } else {
                // Mostrar un missatge d'error si algun dels camps està buit
                Utils.mostrarToast(mContext, "Si us plau, omple tots els camps abans de desar els canvis.");
            }
            habilitarEdicio(false);
            btnDesar.setEnabled(false);
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
     * Mètode per convertir el tipus d'instal·lació a un valor numèric per a l'enviament al servidor.
     * <p>
     * @param tipusInstallacio Tipus de l'instal·lació.
     * @return Valor numèric del tipus d'instal·lació.
     */
    private String convertTipusInstallacio(String tipusInstallacio) {
        return switch (tipusInstallacio) {
            case "Sala" -> "1";
            case "Piscina" -> "2";
            default -> "0";
        };
    }

    /**
     * Mètode per habilitar o deshabilitar l'edició dels camps de text segons l'estat de l'edició.
     * <p>
     * @param habilitat Estat de l'edició.
     */
    private void habilitarEdicio(boolean habilitat) {
        EditText etNomInstallacio = dialegView.findViewById(R.id.etNomInstallacio);
        EditText etDescripcioInstallacio = dialegView.findViewById(R.id.etDescripcioInstallacio);
        EditText etTipusInstallacio = dialegView.findViewById(R.id.etTipusInstallacio);
        etNomInstallacio.setEnabled(habilitat);
        etDescripcioInstallacio.setEnabled(habilitat);
        etTipusInstallacio.setEnabled(habilitat);
    }

    /**
     * Mètode per modificar una instal·lació.
     * <p>
     * Aquest mètode crida a la classe ModificarInstallacio per modificar una instal·lació.
     * <p>
     * @param installacio Activitat a modificar.
     */
    private void modificarInstallacio(Installacio installacio) {
        ModificarInstallacio modificarInstallacio = new ModificarInstallacio(new ConnexioServidor.respostaServidorListener() {
                    }, mContext) {
         };
        modificarInstallacio.setInstallacio(installacio);
        modificarInstallacio.modificarInstallacio();
    }

    /**
     * Mètode per mostrar el diàleg d'eliminació de la instal·lació.
     * <p>
     * Aquest mètode mostra un diàleg amb els detalls de la instal·lació seleccionada per a la seva eliminació.
     * <p>
     * @param IDinstallacio Identificador de la instal·lació.
     * @param nomInstallacio Nom de la instal·lació.
     * @param descripcioInstallacio Descripció de la instal·lació.
     * @param tipusInstallacio Tipus de la instal·lació.
     */
    private void dialegEliminarInstallacio(int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Inflar la vista del diàleg
        View dialegView = crearDialegEliminarInstallacio(IDinstallacio, nomInstallacio, descripcioInstallacio, tipusInstallacio);

        // Mostrar el diàleg
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode per crear la vista del diàleg d'eliminació de la instal·lació amb les dades de la instal·lació seleccionada.
     * <p>
     * @param IDinstallacio Identificador de la instal·lació.
     * @param nomInstallacio Nom de la instal·lació.
     * @param descripcioInstallacio Descripció de la instal·lació.
     * @param tipusInstallacio Tipus de la instal·lació.
     * @return Vista del diàleg d'eliminació de la instal·lació.
     */
    private View crearDialegEliminarInstallacio(int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Inflar la vista del disseny personalitzat del dialeg
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialegView = inflater.inflate(R.layout.dialeg_eliminar_installacions, null);

        // Configurar les vistes del disseny personalitzat
        configurarVistesDialegEliminar(dialegView, IDinstallacio, nomInstallacio, descripcioInstallacio, tipusInstallacio);

        return dialegView;
    }

    /**
     * Mètode per configurar les vistes del diàleg d'eliminació de la instal·lació amb les dades de la instal·lació seleccionada.
     * <p>
     * @param dialegView Vista del diàleg d'eliminació de la instal·lació.
     * @param IDinstallacio Identificador de la instal·lació.
     * @param nomInstallacio Nom de la instal·lació.
     * @param descripcioInstallacio Descripció de la instal·lació.
     * @param tipusInstallacio Tipus de la instal·lació.
     */
    private void configurarVistesDialegEliminar(View dialegView, int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Obtenir referències als elements de la vista del dialeg
        TextView tvIDinstallacio = dialegView.findViewById(R.id.tvIDinstallacio);
        TextView tvNomInstallacio = dialegView.findViewById(R.id.tvNomInstallacio);
        TextView tvDescripcioInstallacio = dialegView.findViewById(R.id.tvDescripcioInstallacio);
        TextView tvTipusInstallacio = dialegView.findViewById(R.id.tvTipusInstallacio);
        Button btnEliminar = dialegView.findViewById(R.id.btnEliminar);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        // Establir les dades de la instal·lació als TextView corresponents
        tvIDinstallacio.setText(String.valueOf(IDinstallacio));
        tvNomInstallacio.setText(nomInstallacio);
        tvDescripcioInstallacio.setText(descripcioInstallacio);
        tvTipusInstallacio.setText(tipusInstallacio);

        // Configurar el botó "Eliminar"
        btnEliminar.setOnClickListener(v -> eliminarInstallacio(nomInstallacio));

        // Configurar el botó de tancament
        configurarBotoTancar(botoTancar);
    }

    /**
     * Interfície per a l'escolta de l'eliminació d'una instal·lació.
     */
    public interface OnInstallacioEliminadaListener {
    }

    /**
     * Mètode per eliminar una instal·lació.
     * <p>
     * Aquest mètode crida a la classe EliminarInstallacio per eliminar una instal·lació.
     * <p>
     * @param nomInstallacio Nom de la instal·lació a eliminar.
     */
    private void eliminarInstallacio(String nomInstallacio) {
        EliminarInstallacio eliminarInstallacio = new EliminarInstallacio(new ConnexioServidor.respostaServidorListener() {
        }, mContext, nomInstallacio) {
        };
        eliminarInstallacio.eliminarInstallacio();
    }
}