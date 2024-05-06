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
        holder.correuUsuari.setText(usuari.get(Constants.CORREU_USUARI));
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
            nomUsuari = itemView.findViewById(R.id.nomUsuari);
            correuUsuari = itemView.findViewById(R.id.correuUsuari);
            cognomsUsuari = itemView.findViewById(R.id.cognomsUsuari);
            tipusUsuari = itemView.findViewById(R.id.tipusUsuari);
            dataInscripcio = itemView.findViewById(R.id.dataInscripcio);
            btnModificar = itemView.findViewById(R.id.btnModificar);
            btnDesar = itemView.findViewById(R.id.btnDesar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
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
        Button btnModificar = dialegView.findViewById(R.id.btnModificar);
        Button btnDesar = dialegView.findViewById(R.id.btnDesar);
        ImageButton botoTancar = dialegView.findViewById(R.id.botoTancar);

        etIDusuari.setText(String.valueOf(IDusuari));
        etCorreuUsuari.setText(correuUsuari);
        etTipusUsuari.setText(tipusUsuari);
        etDataInscripcio.setText(dataInscripcio);
        etNomUsuari.setText(nomUsuari);
        etCorreuUsuari.setText(correuUsuari);
        etTelefon.setText(telefon);
        etDataNaixement.setText(dataNaixement);
        etAdreca.setText(adreca);

        configurarBotoModificar(btnModificar, btnDesar);
        configurarBotoDesar(btnModificar, btnDesar, etNomUsuari, etCognomsUsuari, etTelefon, etDataNaixement, etAdreca, usuari);
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

    private void configurarBotoDesar(Button btnModificar, Button btnDesar, EditText etNomUsuari, EditText etCognomsUsuari, EditText etTelefon, EditText etDataNaixement, EditText etAdreca, Usuari usuari) {
        btnDesar.setOnClickListener(v -> {
            String nomUsuariModificat = etNomUsuari.getText().toString();
            String cognomsUsuariModificat = etCognomsUsuari.getText().toString();
            String telefonModificat = etTelefon.getText().toString();
            String dataNaixementModificada = etDataNaixement.getText().toString();
            String adrecaModificada = etAdreca.getText().toString();


            if (!nomUsuariModificat.isEmpty() && !cognomsUsuariModificat.isEmpty() && !telefonModificat.isEmpty() && !dataNaixementModificada.isEmpty() && !adrecaModificada.isEmpty()) {
                usuari.setNomUsuari(nomUsuariModificat);
                usuari.setCognomsUsuari(cognomsUsuariModificat);
                usuari.setTelefon(telefonModificat);
                usuari.setDataNaixement(dataNaixementModificada);
                usuari.setAdreca(adrecaModificada);

                modificarUsuari(usuari);
            } else {
                Utils.mostrarToast(context, "Si us plau, omple tots els camps.");
            }

            habilitarEdicio(false);
            btnDesar.setEnabled(false);
            btnModificar.setEnabled(true);
        });
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

        Button btnEliminar = dialegView.findViewById(R.id.btnEliminar);
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

        btnEliminar.setOnClickListener(v -> eliminarUsuari(nomUsuari));

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
