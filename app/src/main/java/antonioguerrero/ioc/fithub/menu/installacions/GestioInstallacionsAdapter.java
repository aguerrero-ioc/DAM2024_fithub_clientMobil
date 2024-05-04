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

import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.peticions.installacions.EliminarInstallacio;
import antonioguerrero.ioc.fithub.peticions.installacions.ModificarInstallacio;

public class GestioInstallacionsAdapter extends RecyclerView.Adapter<GestioInstallacionsAdapter.ViewHolder> {

    private final List<HashMap<String, String>> installacionsList;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;
    private Context context;

    public GestioInstallacionsAdapter(Context context, List<HashMap<String, String>> installacionsList) {
        this.mContext = context;
        this.installacionsList = installacionsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_installacio, parent, false);
        return new ViewHolder(view);
    }

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

        holder.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IDinstallacio = installacio.get(Constants.INS_ID);
                String nomInstallacio = installacio.get(Constants.INS_NOM);
                String descripcioInstallacio = installacio.get(Constants.INS_DESC);
                String tipusInstallacio = holder.tipusInstallacio.getText().toString();
                dialegModificarInstallacio(Integer.parseInt(IDinstallacio), nomInstallacio, descripcioInstallacio, tipusInstallacio);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IDinstallacio = installacio.get(Constants.INS_ID);
                String nomInstallacio = installacio.get(Constants.INS_NOM);
                String descripcioInstallacio = installacio.get(Constants.INS_DESC);
                String tipusInstallacio = holder.tipusInstallacio.getText().toString();
                dialegEliminarInstallacio(Integer.parseInt(IDinstallacio), nomInstallacio, descripcioInstallacio, tipusInstallacio);
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
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista.
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

    private void configurarBotoModificar(Button btnModificar, Button btnDesar) {
        btnModificar.setOnClickListener(v -> {
            estaEditant = true;
            habilitarEdicio(true);
            btnDesar.setEnabled(true);
            btnModificar.setEnabled(false);
        });
    }

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
                Utils.mostrarToast(context, "Si us plau, omple tots els camps abans de desar els canvis.");
            }
            habilitarEdicio(false);
            btnDesar.setEnabled(false);
            btnModificar.setEnabled(true);
        });
    }
    /**
     * Mètode per configurar el comportament del botó de tancament.
     * <p>
     * Aquest mètode configura el comportament del botó de tancament del diàleg.
     *
     * @param botoTancar Botó de tancament.
     */
    private void configurarBotoTancar(ImageButton botoTancar) {
        botoTancar.setOnClickListener(v -> {
            // Tancar el diàleg
            ((AlertDialog) dialegView.getTag()).dismiss();
        });
    }

    /**
     * Mètode per mostrar el diàleg amb el disseny personalitzat.
     * <p>
     * Aquest mètode mostra el diàleg amb el disseny personalitzat passat com a paràmetre.
     *
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
     * Mètode per convertir el tipus d'instal·lació a un valor numèric.
     * <p>
     * Aquest mètode converteix el tipus d'instal·lació a un valor numèric per a l'enviament al servidor.
     *
     * @param tipusInstallacio Tipus de l'instal·lació.
     * @return Valor numèric del tipus d'instal·lació.
     */
    private String convertTipusInstallacio(String tipusInstallacio) {
        switch (tipusInstallacio) {
            case "Sala":
                return "1";
            case "Piscina":
                return "2";
            default:
                return "0";
        }
    }

    /**
     * Mètode per habilitar o deshabilitar l'edició dels camps de text.
     * <p>
     * Aquest mètode habilita o deshabilita l'edició dels camps de text segons l'estat de l'edició.
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
     *
     * @param installacio Activitat a modificar.
     */
    private void modificarInstallacio(Installacio installacio) {
        ModificarInstallacio modificarInstallacio = new ModificarInstallacio(new ConnexioServidor.respostaServidorListener() {
            @Override
            public void respostaServidor(Object resposta) {
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            @Override
            public void respostaServidor(Object[] resposta) {

            }

            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }
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
     * Mètode per crear la vista del diàleg d'eliminació de la instal·lació.
     * <p>
     * Aquest mètode crea la vista del diàleg d'eliminació de la instal·lació amb les dades de la instal·lació seleccionada.
     *
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

    // Mètode per configurar les vistes del disseny personalitzat del dialeg d'eliminació de la instal·lació
    private void configurarVistesDialegEliminar(View dialegView, int IDinstallacio, String nomInstallacio, String descripcioInstallacio, String tipusInstallacio) {
        // Obtindre referències als elements de la vista del dialeg
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
        btnEliminar.setOnClickListener(v -> {
            eliminarInstallacio(nomInstallacio);
        });

        // Configurar el botó de tancament
        configurarBotoTancar(botoTancar);
    }

    public interface OnInstallacioEliminadaListener {
        void onInstallacioEliminada();
    }

    private void eliminarInstallacio(String nomInstallacio) {
        EliminarInstallacio eliminarInstallacio = new EliminarInstallacio(new ConnexioServidor.respostaServidorListener() {

            @Override
            public void respostaServidor(Object resposta) throws ConnectException {

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
