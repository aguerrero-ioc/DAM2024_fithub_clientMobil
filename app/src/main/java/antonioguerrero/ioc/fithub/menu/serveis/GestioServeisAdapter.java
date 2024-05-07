package antonioguerrero.ioc.fithub.menu.serveis;

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
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.peticions.serveis.EliminarServei;
import antonioguerrero.ioc.fithub.peticions.serveis.ModificarServei;

/**
 * Classe que representa un adaptador per a la llista de serveis.
 * <p>
 * Aquest adaptador s'encarrega de gestionar les dades dels serveis i de mostrar-les en una llista.
 * <p>
 * Aquest adaptador permet a l'usuari administrador modificar i eliminar els serveis de la llista.
 * <p>
 * Aquest adaptador també mostra un diàleg per a la modificació i eliminació dels serveis.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioServeisAdapter extends RecyclerView.Adapter<GestioServeisAdapter.ViewHolder> {
    private final List<HashMap<String, String>> serveisList;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;

    /**
     * Constructor de la classe GestioServeisAdapter.
     * <p>
     * @param context Context de l'aplicació.
     * @param serveisList Llista de serveis.
     */
    public GestioServeisAdapter(Context context, List<HashMap<String, String>> serveisList) {
        this.mContext = context;
        this.serveisList = serveisList;
    }

    /**
     * Mètode que crea una nova instància de ViewHolder.
     * <p>
     * @param parent Vista pare.
     * @param viewType Tipus de vista.
     * @return Retorna una nova instància de ViewHolder.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_servei, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que enllaça les dades dels serveis amb les vistes del ViewHolder.
     * <p>
     * @param holder ViewHolder.
     * @param position Posició del servei.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> servei = serveisList.get(position);
        holder.nomServei.setText(servei.get(Constants.SERVEI_NOM));
        holder.preuServei.setText(servei.get(Constants.SERVEI_PREU));

        holder.btnModificar.setOnClickListener(v -> {
            String IDServei = servei.get(Constants.SERVEI_ID);
            String nomServei = servei.get(Constants.SERVEI_NOM);
            String descripcioServei = servei.get(Constants.SERVEI_DESC);
            String preuServei = servei.get(Constants.SERVEI_PREU);
            dialegModificarServei(Integer.parseInt(IDServei), nomServei, descripcioServei, preuServei);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            String IDServei = servei.get(Constants.SERVEI_ID);
            String nomServei = servei.get(Constants.SERVEI_NOM);
            String descripcioServei = servei.get(Constants.SERVEI_DESC);
            String preuServei = servei.get(Constants.SERVEI_PREU);
            dialegEliminarServei(Integer.parseInt(IDServei), nomServei, descripcioServei, preuServei);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista de serveis.
     * <p>
     * @return Retorna el nombre d'elements de la llista de serveis.
     */
    @Override
    public int getItemCount() {
        return serveisList.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista de serveis.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnModificar, btnDesar, btnEliminar;
        TextView nomServei, preuServei;

        public ViewHolder(View itemView) {
            super(itemView);
            nomServei = itemView.findViewById(R.id.nomServei);
            preuServei = itemView.findViewById(R.id.preuServei);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnDesar = itemView.findViewById(R.id.btnDesar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    /**
     * Mètode que mostra el diàleg per modificar una instal·lació.
     * <p>
     * @param IDServei Identificador del servei.
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param preuServei Preu del servei.
     */
    private void dialegModificarServei(int IDServei, String nomServei, String descripcioServei, String preuServei) {
        Servei servei = new Servei(IDServei, nomServei, descripcioServei, preuServei);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_serveis, null);

        configurarVistesDialeg(dialegView, IDServei, nomServei, descripcioServei, preuServei, servei);

        mostrarDialeg(dialegView);
    }

    /**
     * Mètode que configura les vistes del diàleg per a la modificació d'un servei.
     * <p>
     * @param dialegView Vista del diàleg.
     * @param IDServei Identificador del servei.
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param preuServei Preu del servei.
     * @param servei Servei.
     */
    private void configurarVistesDialeg(View dialegView, int IDServei, String nomServei, String descripcioServei, String preuServei, Servei servei) {
        EditText etIDServei = dialegView.findViewById(R.id.etIDservei);
        EditText etNomServei = dialegView.findViewById(R.id.etNomServei);
        EditText etDescripcioServei = dialegView.findViewById(R.id.etDescripcioServei);
        EditText etPreuServei = dialegView.findViewById(R.id.etPreuServei);
        Button btnModificar = dialegView.findViewById(R.id.btnModificar);
        Button btnDesar = dialegView.findViewById(R.id.btnDesar);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        etIDServei.setText(String.valueOf(IDServei));
        etNomServei.setText(nomServei);
        etDescripcioServei.setText(descripcioServei);
        etPreuServei.setText(preuServei);

        configurarBotoModificar(btnModificar, btnDesar);
        configurarBotoDesar(btnModificar, btnDesar, etIDServei, etNomServei, etDescripcioServei, etPreuServei, servei);
        configurarBotoTancar(botoTancar);
    }

    /**
     * Mètode que configura el botó de modificar.
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
     * Mètode que configura el botó de desar.
     * <p>
     * @param btnModificar Botó de modificar.
     * @param btnDesar Botó de desar.
     * @param etIDServei Identificador del servei.
     * @param etNomServei Nom del servei.
     * @param etDescripcioServei Descripció del servei.
     * @param etPreuServei Preu del servei.
     * @param servei Servei.
     */
    private void configurarBotoDesar(Button btnModificar, Button btnDesar, EditText etIDServei, EditText etNomServei, EditText etDescripcioServei, EditText etPreuServei, Servei servei) {
        btnDesar.setOnClickListener(v -> {
            int IDServeiModificat = Integer.parseInt(etIDServei.getText().toString());
            String nomServeiModificat = etNomServei.getText().toString();
            String descripcioServeiModificada = etDescripcioServei.getText().toString();
            String preuServeiModificat = etPreuServei.getText().toString();

            if (!nomServeiModificat.isEmpty() && !descripcioServeiModificada.isEmpty() && !preuServeiModificat.isEmpty()) {
                servei.setIDservei(IDServeiModificat);
                servei.setNomServei(nomServeiModificat);
                servei.setDescripcioServei(descripcioServeiModificada);
                servei.setPreuServei(preuServeiModificat);

                modificarServei(servei);
            } else {
                Utils.mostrarToast(mContext, "Si us plau, omple tots els camps abans de desar els canvis.");
            }

            habilitarEdicio(false);
            btnDesar.setEnabled(false);
            btnModificar.setEnabled(true);
        });
    }

    /**
     * Mètode que configura el botó de tancar.
     * <p>
     * @param botoTancar Botó de tancar.
     */
    private void configurarBotoTancar(ImageButton botoTancar) {
        botoTancar.setOnClickListener(v -> ((AlertDialog) dialegView.getTag()).dismiss());
    }

    /**
     * Mètode que mostra el diàleg.
     * <p>
     * @param dialegView Vista del diàleg.
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
     * Mètode que habilita l'edició dels camps del diàleg.
     * <p>
     * @param habilitar Booleà que indica si s'ha d'habilitar l'edició dels camps.
     */
    private void habilitarEdicio(boolean habilitar) {
        EditText etNomServei = dialegView.findViewById(R.id.etNomServei);
        EditText etDescripcioServei = dialegView.findViewById(R.id.etDescripcioServei);
        EditText etPreuServei = dialegView.findViewById(R.id.etPreuServei);
        etNomServei.setEnabled(habilitar);
        etDescripcioServei.setEnabled(habilitar);
        etPreuServei.setEnabled(habilitar);
    }

    /**
     * Mètode que modifica un servei.
     * <p>
     * @param servei Servei.
     */
    private void modificarServei(Servei servei) {
        ModificarServei modificarServei = new ModificarServei(new ConnexioServidor.respostaServidorListener() {
        }, mContext) {
        };
        modificarServei.setServei(servei);
        modificarServei.modificarServei();
    }

    /**
     * Mètode que mostra el diàleg per eliminar un servei.
     * <p>
     * @param IDservei Identificador del servei.
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param preuServei Preu del servei.
     */
    private void dialegEliminarServei(int IDservei, String nomServei, String descripcioServei, String preuServei) {
        View dialegView = crearDialegEliminarServei(IDservei, nomServei, descripcioServei, preuServei);
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode que crea el diàleg per eliminar un servei.
     * <p>
     * @param IDservei Identificador del servei.
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param preuServei Preu del servei.
     * @return Retorna la vista del diàleg.
     */
    private View crearDialegEliminarServei(int IDservei, String nomServei, String descripcioServei, String preuServei) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_eliminar_serveis, null);
        configurarVistesDialegEliminarServei(dialogView, IDservei, nomServei, descripcioServei, preuServei);
        return dialogView;
    }

    /**
     * Mètode que configura les vistes del diàleg per a l'eliminació d'un servei.
     * <p>
     * @param dialogView Vista del diàleg.
     * @param IDservei Identificador del servei.
     * @param nomServei Nom del servei.
     * @param descripcioServei Descripció del servei.
     * @param preuServei Preu del servei.
     */
    private void configurarVistesDialegEliminarServei(View dialogView, int IDservei, String nomServei, String descripcioServei, String preuServei) {
        TextView tvIDservei = dialogView.findViewById(R.id.tvIDservei);
        TextView tvNomServei = dialogView.findViewById(R.id.tvNomServei);
        TextView tvDescripcioServei = dialogView.findViewById(R.id.tvDescripcioServei);
        TextView tvPreuServei = dialogView.findViewById(R.id.tvPreuServei);
        Button btnEliminar = dialogView.findViewById(R.id.btnEliminar);
        ImageButton botoTancar = dialogView.findViewById(R.id.botoTancar);

        tvIDservei.setText(String.valueOf(IDservei));
        tvNomServei.setText(nomServei);
        tvDescripcioServei.setText(descripcioServei);
        tvPreuServei.setText(preuServei);

        btnEliminar.setOnClickListener(v -> eliminarServei(nomServei));
        configurarBotoTancar(botoTancar);
    }

    /**
     * Interfície per a l'escolta de l'eliminació d'un servei.
     */
    public interface OnServeiEliminatListener {
    }

    /**
     * Mètode que elimina un servei.
     * <p>
     * Aquest mètode crida a la classe ELiminarServei per a eliminar un servei.
     * <p>
     * @param nomServei Nom del servei.
     */
    private void eliminarServei(String nomServei) {
        EliminarServei eliminarServei = new EliminarServei(new ConnexioServidor.respostaServidorListener() {

        }, mContext, nomServei) {
        };

        eliminarServei.eliminarServei();
    }
}
