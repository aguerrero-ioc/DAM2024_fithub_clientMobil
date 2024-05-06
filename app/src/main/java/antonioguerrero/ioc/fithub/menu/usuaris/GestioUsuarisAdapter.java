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

import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.EliminarUsuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.ModificarUsuari;

public class GestioUsuarisAdapter extends RecyclerView.Adapter<GestioUsuarisAdapter.ViewHolder> {

    private final List<HashMap<String, String>> usuarisList;
    private final Context mContext;
    private View dialegView;

    private boolean estaEditant = false;
    private Context context;


    public GestioUsuarisAdapter(Context context, List<HashMap<String, String>> usuarisList) {
        this.mContext = context;
        this.usuarisList = usuarisList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_usuari, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> usuari = usuarisList.get(position);
        holder.nomUsuari.setText(usuari.get(Constants.NOM_USUARI));
        holder.cognomsUsuari.setText(usuari.get(Constants.COGNOMS_USUARI));
        holder.correuUsuari.setText(usuari.get(Constants.CORREU_USUARI));
        holder.setTipusUsuari(Integer.parseInt(usuari.get(Constants.TIPUS_USUARI)));
        holder.dataInscripcio.setText(usuari.get(Constants.DATA_INSCRIPCIO));
        holder.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarisList.size();
    }

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

        public void setTipusUsuari(int tipus) {
            String tipusText;
            switch (tipus) {
                case 1:
                    tipusText = "Administrador";
                    break;
                case 2:
                    tipusText = "Client";
                    break;
                default:
                    tipusText = "Desconegut";
                    break;
            }
            tipusUsuari.setText(tipusText);
        }
    }

    private void dialegModificarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        Usuari usuari = new Usuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_usuari, null);

        configurarVistesDialegModificar(dialegView, IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari, cognomsUsuari, dataNaixement, adreca, telefon, usuari);

        mostrarDialeg(dialegView);
    }

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

    private void configurarBotoModificar(Button btnModificarUsuari, Button btnDesarCanvisUsuari) {
        btnModificarUsuari.setOnClickListener(v -> {
            estaEditant = true;
            habilitarEdicio(true);
            btnDesarCanvisUsuari.setEnabled(true);
            btnModificarUsuari.setEnabled(false);
        });
    }

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

    private int determinarTipusUsuari(String tipusUsuariText) {
        switch (tipusUsuariText.toLowerCase()) {
            case "administrador":
                return 1;
            case "client":
                return 2;
            default:
                return 0;
        }
    }

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


    private void configurarBotoTancar(ImageButton botoTancar) {
        botoTancar.setOnClickListener(v -> {
            ((AlertDialog) dialegView.getTag()).dismiss();
        });
    }

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

    private void habilitarEdicio(boolean habilitar) {
        int visibility = habilitar ? View.VISIBLE : View.GONE;
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

    private void modificarUsuari(Usuari usuari) {
        ModificarUsuari modificarUsuari = new ModificarUsuari(new ConnexioServidor.respostaServidorListener() {

            @Override
            public void respostaServidor(Object resposta) {
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            /**
             * Mètode que retorna la resposta del servidor
             *
             * @param resposta Resposta del servidor
             */
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            /**
             * Mètode que retorna la resposta del servidor
             *
             * @param resposta Resposta del servidor
             */
            @Override
            public void respostaServidor(Object[] resposta) {

            }

            /**
             * Mètode que s'executa en segon pla
             *
             * @param voids
             */
            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }
        };



        // Configuración del usuario y llamada al método de modificación
        modificarUsuari.setUsuari(usuari);
        modificarUsuari.modificarUsuari();
    }




    private void dialegEliminarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        View dialegView = crearDialegEliminarUsuari(IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari,cognomsUsuari,dataNaixement,adreca,telefon);
        mostrarDialeg(dialegView);
    }

    private View crearDialegEliminarUsuari(String IDusuari, String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialegView = inflater.inflate(R.layout.dialeg_eliminar_usuari, null);
        configurarVistesDialegEliminar(dialegView, IDusuari, correuUsuari, tipusUsuari, dataInscripcio, nomUsuari,cognomsUsuari,dataNaixement,adreca,telefon);
        return dialegView;
    }

    private void configurarVistesDialegEliminar(View dialegView, String IDusuari,String correuUsuari, String tipusUsuari, String dataInscripcio, String nomUsuari, String cognomsUsuari, String dataNaixement, String adreca, String telefon) {
        TextView tvIDusuari = dialegView.findViewById(R.id.tvIDusuari);
;       TextView tvCorreuUsuari = dialegView.findViewById(R.id.tvCorreuUsuari);
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

    public interface OnUsuariEliminatListener {
        void onUsuariEliminat();
    }

    private void eliminarUsuari(String nomUsuari) {
        EliminarUsuari eliminarUsuari = new EliminarUsuari(new ConnexioServidor.respostaServidorListener() {
            @Override
            public void respostaServidor(Object resposta) throws ConnectException {
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext, nomUsuari) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        eliminarUsuari.eliminarUsuari();
    }

}
