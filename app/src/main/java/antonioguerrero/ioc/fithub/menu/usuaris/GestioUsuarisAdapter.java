package antonioguerrero.ioc.fithub.menu.usuaris;

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
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.EliminarUsuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.ModificarUsuari;

/**
 * Classe que representa un adaptador per a la llista d'usuaris.
 * <p>
 * Aquest adaptador s'encarrega de gestionar les dades dels usuaris i de mostrar-les en una llista.
 * <p>
 * Aquest adaptador permet a l'usuari administrador modificar i eliminar els usuaris de la llista.
 * <p>
 * Aquest adaptador també mostra un diàleg per a la modificació i eliminació dels usuaris.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class GestioUsuarisAdapter extends RecyclerView.Adapter<GestioUsuarisAdapter.ViewHolder> {
    private final List<HashMap<String, String>> usuaris;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;

    /**
     * Constructor de la classe
     * @param context Context de l'aplicació
     * @param usuaris Llista d'usuaris
     */
    public GestioUsuarisAdapter(Context context, List<HashMap<String, String>> usuaris) {
        this.mContext = context;
        this.usuaris = usuaris;
    }

    /**
     * Mètode que crea una nova instància de ViewHolder
     * @param parent Vista pare
     * @param viewType Tipus de vista
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_usuari, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Mètode que enllaça les dades de l'usuari amb les vistes del ViewHolder
     * @param holder ViewHolder
     * @param position Posició de l'usuari
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> usuari = usuaris.get(position);
        holder.nomUsuari.setText(usuari.get(Constants.NOM_USUARI));
        holder.cognomsUsuari.setText(usuari.get(Constants.COGNOMS_USUARI));
        holder.correuUsuari.setText(usuari.get(Constants.CORREU_USUARI));
        holder.setTipusUsuari(Integer.parseInt(usuari.get(Constants.TIPUS_USUARI)));
        holder.dataInscripcio.setText(usuari.get(Constants.DATA_INSCRIPCIO));
        holder.btnModificar.setOnClickListener(v -> {
            String IDusuari = usuari.get(Constants.ID_USUARI);
            String correuUsuari = usuari.get(Constants.CORREU_USUARI);
            String tipusUsuari = usuari.get(Constants.TIPUS_USUARI);
            String dataInscripcio = usuari.get(Constants.DATA_INSCRIPCIO);
            String nomUsuari = usuari.get(Constants.NOM_USUARI);
            String cognomsUsuari = usuari.get(Constants.COGNOMS_USUARI);
            String dataNaixement = usuari.get(Constants.DATA_NAIXEMENT);
            String adreca = usuari.get(Constants.ADRECA);
            String telefon = usuari.get(Constants.TELEFON);

            dialegModificarUsuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            String IDusuari = usuari.get(Constants.ID_USUARI);
            String correuUsuari = usuari.get(Constants.CORREU_USUARI);
            String tipusUsuari = usuari.get(Constants.TIPUS_USUARI);
            String dataInscripcio = usuari.get(Constants.DATA_INSCRIPCIO);
            String nomUsuari = usuari.get(Constants.NOM_USUARI);
            String cognomsUsuari = usuari.get(Constants.COGNOMS_USUARI);
            String dataNaixement = usuari.get(Constants.DATA_NAIXEMENT);
            String adreca = usuari.get(Constants.ADRECA);
            String telefon = usuari.get(Constants.TELEFON);

            dialegEliminarUsuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon);
        });
    }

    /**
     * Mètode que retorna el nombre d'elements de la llista d'usuaris.
     * <p>
     * @return Retorna el nombre d'elements de la llista d'usuaris.
     */
    @Override
    public int getItemCount() {
        return usuaris.size();
    }

    /**
     * Classe interna ViewHolder.
     * <p>
     * Aquesta classe s'encarrega de gestionar les vistes dels elements de la llista d'usuaris.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View btnModificar, btnDesar, btnEliminar;
        TextView nomUsuari, correuUsuari, cognomsUsuari, tipusUsuari, dataInscripcio;

        public ViewHolder(View itemView) {
            super(itemView);
            nomUsuari = itemView.findViewById(R.id.tvNomUsuari);
            correuUsuari = itemView.findViewById(R.id.tvCorreuUsuari);
            cognomsUsuari = itemView.findViewById(R.id.tvCognomsUsuari);
            tipusUsuari = itemView.findViewById(R.id.tvTipusUsuari);
            dataInscripcio = itemView.findViewById(R.id.tvDataInscripcio);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnDesar = itemView.findViewById(R.id.btnDesar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }

        /**
         * Mètode que assigna el tipus d'usuari a la vista.
         * @param tipus Tipus d'usuari
         */
        public void setTipusUsuari(int tipus) {
            String tipusText = switch (tipus) {
                case 1 -> "Administrador";
                case 2 -> "Client";
                default -> "Desconegut";
            };
            tipusUsuari.setText(tipusText);
        }
    }

    /**
     * Mètode que mostra un diàleg per a la modificació d'un usuari.
     * @param IDusuari ID de l'usuari
     * @param correuUsuari Correu de l'usuari
     * @param tipusUsuari Tipus de l'usuari
     * @param dataInscripcio Data d'inscripció de l'usuari
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     */
    private void dialegModificarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        Usuari usuari = new Usuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_usuari, null);

        configurarVistesDialegModificar(dialegView, IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon, usuari);

        mostrarDialeg(dialegView);
    }

    /**
     * Mètode que configura les vistes del diàleg de modificació d'un usuari.
     * @param dialegView Vista del diàleg
     * @param IDusuari ID de l'usuari
     * @param correuUsuari Correu de l'usuari
     * @param tipusUsuari Tipus de l'usuari
     * @param dataInscripcio Data d'inscripció de l'usuari
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     * @param usuari Usuari
     */
    @SuppressLint("SetTextI18n")
    private void configurarVistesDialegModificar(View dialegView, String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon, Usuari usuari) {
        EditText etIDusuari = dialegView.findViewById(R.id.etIDusuari);
        EditText etCorreuUsuari = dialegView.findViewById(R.id.etCorreuUsuari);
        EditText etTipusUsuari = dialegView.findViewById(R.id.etTipusUsuari);
        EditText etDataInscripcio = dialegView.findViewById(R.id.etDataInscripcio);
        EditText etNomUsuari = dialegView.findViewById(R.id.etNomUsuari);
        EditText etCognomsUsuari = dialegView.findViewById(R.id.etCognomsUsuari);
        EditText etDataNaixement = dialegView.findViewById(R.id.etDataNaixement);
        EditText etAdreca = dialegView.findViewById(R.id.etAdreca);
        EditText etTelefon = dialegView.findViewById(R.id.etTelefon);
        Button btnModificarUsuari = dialegView.findViewById(R.id.btnModificarUsuari);
        Button btnDesarCanvisUsuari = dialegView.findViewById(R.id.btnDesarCanvisUsuari);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        etIDusuari.setText(String.valueOf(IDusuari));
        etCorreuUsuari.setText(correuUsuari);
        etTipusUsuari.setText(tipusUsuari);
        if ("1".equals(tipusUsuari)) {
            etTipusUsuari.setText("Administrador");
        } else if ("2".equals(tipusUsuari)) {
            etTipusUsuari.setText("Client");
        } else {
            etTipusUsuari.setText("Desconegut");
        }
        etDataInscripcio.setText(dataInscripcio);
        etNomUsuari.setText(nomUsuari);
        etCognomsUsuari.setText(cognomsUsuari);
        etCorreuUsuari.setText(correuUsuari);
        etTelefon.setText(telefon);
        etDataNaixement.setText(dataNaixement);
        etAdreca.setText(adreca);

        configurarBotoModificar(btnModificarUsuari, btnDesarCanvisUsuari);
        configurarBotoDesar(btnModificarUsuari, btnDesarCanvisUsuari,etIDusuari, etCorreuUsuari, etTipusUsuari, etDataInscripcio, etNomUsuari, etCognomsUsuari, etTelefon, etDataNaixement, etAdreca, usuari);
        configurarBotoTancar(botoTancar);
    }

    /**
     * Mètode que configura el botó de modificar un usuari.
     * @param btnModificarUsuari Botó de modificar usuari
     * @param btnDesarCanvisUsuari Botó de desar canvis usuari
     */
    private void configurarBotoModificar(Button btnModificarUsuari, Button btnDesarCanvisUsuari) {
        btnModificarUsuari.setOnClickListener(v -> {
            estaEditant = true;
            habilitarEdicio(true);
            btnDesarCanvisUsuari.setEnabled(true);
            btnModificarUsuari.setEnabled(false);
        });
    }

    /**
     * Mètode que configura el botó de desar canvis d'un usuari.
     * @param btnModificarUsuari Botó de modificar usuari
     * @param btnDesarCanvisUsuari Botó de desar canvis usuari
     * @param etIDusuari ID de l'usuari
     * @param etCorreuUsuari Correu de l'usuari
     * @param etTipusUsuari Tipus de l'usuari
     * @param etDataInscripcio Data d'inscripció de l'usuari
     * @param etNomUsuari Nom de l'usuari
     * @param etCognomsUsuari Cognoms de l'usuari
     * @param etTelefon Telèfon de l'usuari
     * @param etDataNaixement Data de naixement de l'usuari
     * @param etAdreca Adreça de l'usuari
     * @param usuari Usuari
     */
    private void configurarBotoDesar(Button btnModificarUsuari, Button btnDesarCanvisUsuari, EditText etIDusuari, EditText etCorreuUsuari, EditText etTipusUsuari, EditText etDataInscripcio, EditText etNomUsuari, EditText etCognomsUsuari, EditText etTelefon, EditText etDataNaixement, EditText etAdreca, Usuari usuari) {
        btnDesarCanvisUsuari.setOnClickListener(v -> {
            String nomUsuariModificat = etNomUsuari.getText().toString().trim();
            String cognomsUsuariModificat = etCognomsUsuari.getText().toString().trim();
            String telefonModificat = etTelefon.getText().toString().trim();
            String dataNaixementModificada = etDataNaixement.getText().toString().trim();
            String adrecaModificada = etAdreca.getText().toString().trim();
            String tipusUsuariText = etTipusUsuari.getText().toString().trim();
            int tipusUsuari = determinarTipusUsuari(tipusUsuariText);
            etTipusUsuari.setText(String.valueOf(tipusUsuari));
            String correuUsuari = etCorreuUsuari.getText().toString().trim();
            String IDusuari = etIDusuari.getText().toString().trim();
            String dataInscripcio = etDataInscripcio.getText().toString().trim();
            String passUsuari = usuari.getPassUsuari();

            // Validar campos
            if (!validarCamps(nomUsuariModificat, cognomsUsuariModificat, dataNaixementModificada, adrecaModificada, telefonModificat)) {
                return;
            }

            // Actualizar datos del usuario
            usuari.setNomUsuari(nomUsuariModificat);
            usuari.setCognomsUsuari(cognomsUsuariModificat);
            usuari.setTelefon(telefonModificat);
            usuari.setDataNaixement(dataNaixementModificada);
            usuari.setAdreca(adrecaModificada);
            usuari.setIDusuari(Integer.parseInt(IDusuari));
            usuari.setPassUsuari(passUsuari);
            usuari.setCorreuUsuari(correuUsuari);
            usuari.setDataInscripcio(dataInscripcio);
            usuari.setTipusUsuari(tipusUsuari);

            // Modificar usuario en la base de datos
            modificarUsuari(usuari);

            // Deshabilitar edición y actualizar estado de botones
            habilitarEdicio(false);
            btnDesarCanvisUsuari.setEnabled(false);
            btnModificarUsuari.setEnabled(true);
        });
    }

    /**
     * Mètode que determina el tipus d'usuari.
     * @param tipusUsuariText Tipus d'usuari
     * @return Retorna el tipus d'usuari
     */
    private int determinarTipusUsuari(String tipusUsuariText) {
        return switch (tipusUsuariText.toLowerCase()) {
            case "administrador" -> 1;
            case "client" -> 2;
            default -> 0;
        };
    }

    /**
     * Mètode que valida els camps del formulari de modificació d'usuari.
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     * @return Retorna true si els camps són correctes, false altrament
     */
    private boolean validarCamps(String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        // Comprovar si tots els camps estan complets
        if (nomUsuari.isEmpty() || cognomsUsuari.isEmpty() || dataNaixement.isEmpty() || adreca.isEmpty() || telefon.isEmpty()) {
            Utils.mostrarToast(mContext, "Si us plau, omple tots els camps");
            return false;
        }

        // Comprovar el format correcte del nom
        if (!nomUsuari.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(mContext, "El nom només pot contenir lletres");
            return false;
        }

        // Comprovar el format correcte dels cognoms
        if (!cognomsUsuari.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(mContext, "Els cognoms només poden contenir lletres");
            return false;
        }

        // Comprovar el format correcte de la data de naixement
        if (!dataNaixement.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Utils.mostrarToast(mContext, "El format de la data de naixement ha de ser dd/MM/yyyy");
            return false;
        }

        // Comprovar el format correcte del telèfon
        if (!telefon.matches("\\d{9}")) {
            Utils.mostrarToast(mContext, "El telèfon ha de tenir 9 dígits");
            return false;
        }
        return true;
    }

    /**
     * Mètode que configura el botó de tancar el diàleg.
     * @param botoTancar Botó de tancar
     */
    private void configurarBotoTancar(ImageButton botoTancar) {
        botoTancar.setOnClickListener(v -> ((AlertDialog) dialegView.getTag()).dismiss());
    }

    /**
     * Mètode que mostra un diàleg.
     * @param dialegView Vista del diàleg
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
     * Mètode que habilita o deshabilita l'edició dels camps del formulari de modificació d'usuari.
     * @param habilitar Booleà que indica si s'ha d'habilitar o deshabilitar l'edició
     */
    private void habilitarEdicio(boolean habilitar) {
        EditText etNomUsuari = dialegView.findViewById(R.id.etNomUsuari);
        EditText etCognomsUsuari = dialegView.findViewById(R.id.etCognomsUsuari);
        EditText etTelefon = dialegView.findViewById(R.id.etTelefon);
        EditText etDataNaixement = dialegView.findViewById(R.id.etDataNaixement);
        EditText etAdreca = dialegView.findViewById(R.id.etAdreca);

        etNomUsuari.setEnabled(habilitar);
        etCognomsUsuari.setEnabled(habilitar);
        etTelefon.setEnabled(habilitar);
        etDataNaixement.setEnabled(habilitar);
        etAdreca.setEnabled(habilitar);
    }

    /**
     * Mètode que modifica un usuari.
     * @param usuari Usuari a modificar
     */
    private void modificarUsuari(Usuari usuari) {
        ModificarUsuari modificarUsuari = new ModificarUsuari(new ConnexioServidor.respostaServidorListener() {
        }, mContext) {
        };
        // Configuración del usuario y llamada al método de modificación
        modificarUsuari.setUsuari(usuari);
        modificarUsuari.modificarUsuari();
    }

    /**
     * Mètode que mostra un diàleg per a l'eliminació d'un usuari.
     * @param IDusuari ID de l'usuari
     * @param correuUsuari Correu de l'usuari
     * @param tipusUsuari Tipus de l'usuari
     * @param dataInscripcio Data d'inscripció de l'usuari
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     */
    private void dialegEliminarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        View dialegView = crearDialegEliminarUsuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari,cognomsUsuari,dataNaixement,adreca,telefon);
        mostrarDialeg(dialegView);
    }

    /**
     * Mètode que crea un diàleg per a l'eliminació d'un usuari.
     * @param IDusuari ID de l'usuari
     * @param correuUsuari Correu de l'usuari
     * @param tipusUsuari Tipus de l'usuari
     * @param dataInscripcio Data d'inscripció de l'usuari
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     * @return Retorna la vista del diàleg
     */
    private View crearDialegEliminarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialegView = inflater.inflate(R.layout.dialeg_eliminar_usuari, null);
        configurarVistesDialegEliminar(dialegView, IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari,cognomsUsuari,dataNaixement,adreca,telefon);
        return dialegView;
    }

    /**
     * Mètode que configura les vistes del diàleg d'eliminació d'un usuari.
     * @param dialegView Vista del diàleg
     * @param IDusuari ID de l'usuari
     * @param correuUsuari Correu de l'usuari
     * @param tipusUsuari Tipus de l'usuari
     * @param dataInscripcio Data d'inscripció de l'usuari
     * @param nomUsuari Nom de l'usuari
     * @param cognomsUsuari Cognoms de l'usuari
     * @param dataNaixement Data de naixement de l'usuari
     * @param adreca Adreça de l'usuari
     * @param telefon Telèfon de l'usuari
     */
    private void configurarVistesDialegEliminar(View dialegView, String IDusuari,String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        TextView tvIDusuari = dialegView.findViewById(R.id.tvIDusuari);
       TextView tvCorreuUsuari = dialegView.findViewById(R.id.tvCorreuUsuari);
        TextView tvTipusUsuari = dialegView.findViewById(R.id.tvTipusUsuari);
        TextView tvDataInscripcio = dialegView.findViewById(R.id.tvDataInscripcio);
        TextView tvNomUsuari = dialegView.findViewById(R.id.tvNomUsuari);
        TextView tvCognomsUsuari = dialegView.findViewById(R.id.tvCognomsUsuari);
        TextView tvDataNaixement = dialegView.findViewById(R.id.tvDataNaixement);
        TextView tvAdreca = dialegView.findViewById(R.id.tvAdreca);
        TextView tvTelefon = dialegView.findViewById(R.id.tvTelefon);

        Button btnEliminarUsuari = dialegView.findViewById(R.id.btnEliminarUsuari);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        tvIDusuari.setText(String.valueOf(IDusuari));
        tvCorreuUsuari.setText(correuUsuari);
        tvTipusUsuari.setText(tipusUsuari);
        tvDataInscripcio.setText(dataInscripcio);
        tvNomUsuari.setText(nomUsuari);
        tvCognomsUsuari.setText(cognomsUsuari);
        tvDataNaixement.setText(dataNaixement);
        tvAdreca.setText(adreca);
        tvTelefon.setText(telefon);

        btnEliminarUsuari.setOnClickListener(v -> eliminarUsuari(correuUsuari));

        configurarBotoTancar(botoTancar);
    }

    /**
     * Interfície per a l'escolta de l'eliminació d'un usuari.
     */
    public interface OnUsuariEliminatListener {
    }

    /**
     * Mètode que elimina un usuari.
     * @param nomUsuari Nom de l'usuari
     */
    private void eliminarUsuari(String nomUsuari) {
        EliminarUsuari eliminarUsuari = new EliminarUsuari(new ConnexioServidor.respostaServidorListener() {
        }, mContext, nomUsuari) {
        };
        eliminarUsuari.eliminarUsuari();
    }
}