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

import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.peticions.serveis.EliminarServei;
import antonioguerrero.ioc.fithub.peticions.serveis.ModificarServei;

public class GestioServeisAdapter extends RecyclerView.Adapter<GestioServeisAdapter.ViewHolder> {

    private final List<HashMap<String, String>> serveisList;
    private final Context mContext;
    private View dialegView;
    private boolean estaEditant = false;
    private Context context;

    public GestioServeisAdapter(Context context, List<HashMap<String, String>> serveisList) {
        this.mContext = context;
        this.serveisList = serveisList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gestio_servei, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> servei = serveisList.get(position);
        holder.nomServei.setText(servei.get(Constants.SERVEI_NOM));
        holder.preuServei.setText(servei.get(Constants.SERVEI_PREU));

        holder.btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IDServei = servei.get(Constants.SERVEI_ID);
                String nomServei = servei.get(Constants.SERVEI_NOM);
                String descripcioServei = servei.get(Constants.SERVEI_DESC);
                String preuServei = servei.get(Constants.SERVEI_PREU);
                dialegModificarServei(Integer.parseInt(IDServei), nomServei, descripcioServei, preuServei);
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IDServei = servei.get(Constants.SERVEI_ID);
                String nomServei = servei.get(Constants.SERVEI_NOM);
                String descripcioServei = servei.get(Constants.SERVEI_DESC);
                String preuServei = servei.get(Constants.SERVEI_PREU);
                dialegEliminarServei(Integer.parseInt(IDServei), nomServei, descripcioServei, preuServei);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serveisList.size();
    }

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

    private void dialegModificarServei(int IDServei, String nomServei, String descripcioServei, String preuServei) {
        Servei servei = new Servei(IDServei, nomServei, descripcioServei, preuServei);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        dialegView = inflater.inflate(R.layout.dialeg_modificar_serveis, null);

        configurarVistesDialeg(dialegView, IDServei, nomServei, descripcioServei, preuServei, servei);

        mostrarDialeg(dialegView);
    }

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

    private void configurarBotoModificar(Button btnModificar, Button btnDesar) {
        btnModificar.setOnClickListener(v -> {
            estaEditant = true;
            habilitarEdicio(true);
            btnDesar.setEnabled(true);
            btnModificar.setEnabled(false);
        });
    }

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
                Utils.mostrarToast(context, "Si us plau, omple tots els camps abans de desar els canvis.");
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
        EditText etNomServei = dialegView.findViewById(R.id.etNomServei);
        EditText etDescripcioServei = dialegView.findViewById(R.id.etDescripcioServei);
        EditText etPreuServei = dialegView.findViewById(R.id.etPreuServei);
        etNomServei.setEnabled(habilitar);
        etDescripcioServei.setEnabled(habilitar);
        etPreuServei.setEnabled(habilitar);
    }

    private void modificarServei(Servei servei) {
        ModificarServei modificarServei = new ModificarServei(new ConnexioServidor.respostaServidorListener() {
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
        modificarServei.setServei(servei);
        modificarServei.modificarServei();
    }

    private void dialegEliminarServei(int IDservei, String nomServei, String descripcioServei, String preuServei) {
        View dialegView = crearDialegEliminarServei(IDservei, nomServei, descripcioServei, preuServei);
        mostrarDialeg(dialegView);
    }

    private View crearDialegEliminarServei(int IDservei, String nomServei, String descripcioServei, String preuServei) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.dialeg_eliminar_serveis, null);
        configurarVistesDialegEliminarServei(dialogView, IDservei, nomServei, descripcioServei, preuServei);
        return dialogView;
    }

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

    public interface OnServeiEliminatListener {
        void onServeiEliminat();
    }


    private void eliminarServei(String nomServei) {
        EliminarServei eliminarServei = new EliminarServei(new ConnexioServidor.respostaServidorListener() {

            @Override
            public void respostaServidor(Object resposta) throws ConnectException {

            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        }, mContext, nomServei) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };

        eliminarServei.eliminarServei();
    }
}
