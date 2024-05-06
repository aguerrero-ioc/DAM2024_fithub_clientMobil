package antonioguerrero.ioc.fithub.menu.usuaris;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Constants;
import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.menu.BaseActivity;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.CanviarContrasenya;
import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarUsuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.ModificarUsuariActual;

/**
 * Classe que representa l'activitat del perfil de l'usuari a l'aplicació FitHub.
 * Aquesta activitat permet a l'usuari veure i modificar les seves dades personals.
 * A més, també permet canviar la contrasenya de l'usuari.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PerfilActivity extends BaseActivity implements ConsultarUsuari.ConsultarUsuariListener, ModificarUsuariActual.ModificarUsuariListener, ConnexioServidor.respostaServidorListener {

    private EditText etNomUsuari, etCognoms, etDataNaixement, etAdreca, etCorreuUsuari, etTelefon,
            etDataInscripcio, etContrasenyaActual, etNovaContrasenya, etConfirmarContrasenya,
            etTipusUsuari, etIDusuari;
    private Button btnGuardarCanvis, btnEditarPerfil, btnCanviContrasenya, btnGuardarCanviContrasenya;
    private Context context;
    private String sessioID;
    private Usuari usuari;

    SharedPreferences preferencies;


    private ModificarUsuariActual modificarUsuariActual;

    public PerfilActivity() {
    }

    /**
     * Mètode que s'executa quan es crea l'activitat.
     * @param savedInstanceState L'estat guardat de l'activitat.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflarDissenySegonsTipusUsuari();

        // Configura el menú desplegable
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            handleNavigationItemSelected(menuItem);
            return true;
        });

        // Infla el layout de la capçalera del NavigationView
        View headerView = navigationView.getHeaderView(0);

        // Obtenir referències a les vistes en el nav_header
        tvNomUsuari = headerView.findViewById(R.id.tvNomUsuari);
        tvCorreuElectronic = headerView.findViewById(R.id.tvCorreuElectronic);

        // Obtenir les dades de l'usuari de SharedPreferences
        preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String nomUsuari = preferencies.getString(Constants.NOM_USUARI, "Nom d'Usuari");
        String correuElectronic = preferencies.getString(Constants.CORREU_USUARI, "correu@fithub.es");

        // Actualitzar el text de les vistes amb les dades de l'usuari
        tvNomUsuari.setText(nomUsuari);
        tvCorreuElectronic.setText(correuElectronic);


        // Inicialització de les vistes
        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etCognoms = findViewById(R.id.et_cognoms_usuari);
        etDataNaixement = findViewById(R.id.et_data_naixement);
        etAdreca = findViewById(R.id.et_adreca);
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etTelefon = findViewById(R.id.et_telefon_contacte);
        etDataInscripcio = findViewById(R.id.et_data_inscripcio);
        etContrasenyaActual = findViewById(R.id.et_contrasenya_actual);
        etNovaContrasenya = findViewById(R.id.et_nova_contrasenya);
        etConfirmarContrasenya = findViewById(R.id.et_confirmar_contrasenya);
        etTipusUsuari = findViewById(R.id.et_tipus_usuari);
        etIDusuari = findViewById(R.id.et_id_usuari);
        btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        btnGuardarCanvis = findViewById(R.id.btn_guardar_canvis);
        btnCanviContrasenya = findViewById(R.id.btn_canviar_contrasenya);
        btnGuardarCanviContrasenya = findViewById(R.id.btn_guardar_contrasenya);

        // Configuració del clic al ImageView
        ImageView ivImatgeUsuari = findViewById(R.id.iv_imatge_usuari);
        ivImatgeUsuari.setOnClickListener(v -> Utils.mostrarToast(PerfilActivity.this, "Pendent d'implementar"));

        // Configuració del clic del botó per editar el perfil
        btnEditarPerfil.setOnClickListener(v -> habilitarEdicioDades());

        // Configuració del clic del botó per desar els canvis de les dades d'Usuari
        btnGuardarCanvis.setOnClickListener(v -> {
            try {
                guardarCanvis();
            } catch (ConnectException e) {
                throw new RuntimeException(e);
            }
        });

        // Configuració del clic del botó per canviar la contrasenya
        btnCanviContrasenya.setOnClickListener(v -> habilitarEdicioContrasenya());

        // Configuració del clic del botó per desar els canvis de la contrasenya
        btnGuardarCanviContrasenya.setOnClickListener(v -> {
            try {
                onCanviContrasenyaButtonClick();
            } catch (ConnectException e) {
                throw new RuntimeException(e);
            }
        });
        // Obtenir sessioID de l'usuari
        preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);
        String sessioID = preferencies.getString(Constants.SESSIO_ID, Constants.VALOR_DEFAULT);
        String correuUsuari = preferencies.getString("correuUsuari", Constants.VALOR_DEFAULT);

        // Obtenir les dades de l'usuari
        ConsultarUsuari consultarUsuari = new ConsultarUsuari(this, this, correuUsuari, sessioID) {

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
        };
        consultarUsuari.consultarUsuari();

        modificarUsuariActual = new ModificarUsuariActual(this, this, sessioID) {
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
        };

        CanviarContrasenya canviarContrasenya = new CanviarContrasenya(this, this, sessioID) {

            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }
        };
    }

    /**
     * Mètode per inflar el disseny XML de l'activitat segons el tipus d'usuari.
     */
    private void inflarDissenySegonsTipusUsuari() {
        SharedPreferences preferencies = getSharedPreferences(Constants.PREFERENCIES, Context.MODE_PRIVATE);

        // Obtenir el tipus d'usuari com a cadena o un enter, depenent de com s'hagi guardat prèviament
        Object tipusUsuariObject = preferencies.getAll().get(Constants.TIPUS_USUARI);

        // Verificar si el valor emmagatzemat és una cadena o un enter
        if (tipusUsuariObject instanceof String) {
            String tipusUsuariString = (String) tipusUsuariObject;
            // Inflar el disseny XML corresponent segons el tipus d'usuari com a cadena
            if (tipusUsuariString.equals("1")) { // Administrador
                setContentView(R.layout.activity_perfil_admin);
            } else if (tipusUsuariString.equals("2")) { // Client
                setContentView(R.layout.activity_perfil_client);
            } else {
                // Manejar qualsevol altre tipus d'usuari o error aquí
            }
        } else if (tipusUsuariObject instanceof Integer) {
            int tipusUsuariInt = (int) tipusUsuariObject;
            // Inflar el disseny XML corresponent segons el tipus d'usuari com a enter
            if (tipusUsuariInt == 1) { // Administrador
                setContentView(R.layout.activity_perfil_admin);
            } else if (tipusUsuariInt == 2) { // Client
                setContentView(R.layout.activity_perfil_client);
            } else {
                // Manejar qualsevol altre tipus d'usuari o error aquí
            }
        } else {
            // Manejar el cas en què el valor no sigui ni una cadena ni un enter
        }
    }

    /**
     * Mètode per actualitzar les dades de l'usuari a l'activitat.
     *
     * @param usuari L'objecte Usuari amb les dades a actualitzar.
     */
    private void actualitzarUsuari(Usuari usuari) {
        etNomUsuari.setText(usuari.getNomUsuari());
        etCognoms.setText(usuari.getCognomsUsuari());
        etDataNaixement.setText(usuari.getDataNaixement());
        etAdreca.setText(usuari.getAdreca());
        etCorreuUsuari.setText(usuari.getCorreuUsuari());
        etTelefon.setText(usuari.getTelefon());
        etDataInscripcio.setText(usuari.getDataInscripcio());
        int tipusUsuari = usuari.getTipusUsuari();
        if (tipusUsuari == 1) {
            etTipusUsuari.setText("Administrador");
        } else if (tipusUsuari == 2) {
            etTipusUsuari.setText("Client");
        } else {
            etTipusUsuari.setText("Desconegut");
        }
        etIDusuari.setText(String.valueOf(usuari.getIDusuari()));
    }

    /**
     * Mètode per habilitar l'edició dels camps de text de dades personals de l'usuari.
     */
    private void habilitarEdicioDades() {
        etNomUsuari.setEnabled(true);
        etCognoms.setEnabled(true);
        etDataNaixement.setEnabled(true);
        etAdreca.setEnabled(true);
        etTelefon.setEnabled(true);
        etDataInscripcio.setEnabled(false);
        btnEditarPerfil.setEnabled(false);
        btnGuardarCanvis.setEnabled(true);
    }

    /**
     * Mètode per deshabilitar l'edició dels camps de text de dades personals de l'usuari.
     */
    private void deshabilitarEdicioDades() {
        etNomUsuari.setEnabled(false);
        etCognoms.setEnabled(false);
        etDataNaixement.setEnabled(false);
        etAdreca.setEnabled(false);
        etTelefon.setEnabled(false);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(false);
    }

    /**
     * Mètode per habilitar l'edició dels camps de text
     */
    private void habilitarEdicioContrasenya() {
        etContrasenyaActual.setEnabled(true);
        etNovaContrasenya.setEnabled(true);
        etConfirmarContrasenya.setEnabled(true);
        btnCanviContrasenya.setEnabled(false);
        btnGuardarCanviContrasenya.setEnabled(true);
    }

    /**
     * Mètode per deshabilitar l'edició dels camps de text
     */
    private void deshabilitarEdicioContrasenya() {
        etContrasenyaActual.setEnabled(false);
        etNovaContrasenya.setEnabled(false);
        etConfirmarContrasenya.setEnabled(false);
        btnCanviContrasenya.setEnabled(true);
        btnGuardarCanviContrasenya.setEnabled(false);
    }

    /**
     * Mètode per desar els canvis realitzats per l'usuari.
     */
    private void guardarCanvis() throws ConnectException {
        // Obtenir els valors dels EditText
        String nomUsuari = etNomUsuari.getText().toString();
        String cognomsUsuari = etCognoms.getText().toString();
        String dataNaixement = etDataNaixement.getText().toString();
        String adreca = etAdreca.getText().toString();
        String correuUsuari = etCorreuUsuari.getText().toString();
        String telefon = etTelefon.getText().toString();

        // Comprovar si tots els camps estan complets
        if (nomUsuari.isEmpty() || cognomsUsuari.isEmpty() || dataNaixement.isEmpty() || adreca.isEmpty() || telefon.isEmpty()) {
            Utils.mostrarToast(getApplicationContext(), "Si us plau, omple tots els camps");
            return;
        }

        // Comprovar el format correcte del nom
        if (!nomUsuari.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(getApplicationContext(), "El nom només pot contenir lletres");
            return;
        }

        // Comprovar el format correcte dels cognoms
        if (!cognomsUsuari.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(getApplicationContext(), "Els cognoms només poden contenir lletres");
            return;
        }
        // Comprovar el format correcte de la data de naixement
        if (!dataNaixement.matches("\\d{2}/\\d{2}/\\d{4}")) {
            Utils.mostrarToast(getApplicationContext(), "El format de la data de naixement ha de ser dd/MM/yyyy");
            return;
        }

        // Comprovar el format correcte del telèfon
        if (!telefon.matches("\\d{9}")) {
            Utils.mostrarToast(getApplicationContext(), "El telèfon ha de tenir 9 dígits");
            return;
        }

        // Actualitzar les dades de l'objecte Usuari amb els valors dels EditText

        usuari.setNomUsuari(nomUsuari);
        usuari.setCognomsUsuari(cognomsUsuari);
        usuari.setDataNaixement(dataNaixement);
        usuari.setAdreca(adreca);
        usuari.setTelefon(telefon);
        usuari.setCorreuUsuari(correuUsuari);

        //Cridar al mètode modificarUsuari de la clase ModificarUsuari para enviar la petición al servidor
        ModificarUsuariActual modificarUsuariActual = new ModificarUsuariActual((ModificarUsuariActual.ModificarUsuariListener) this, this, sessioID) {

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
        };
        modificarUsuariActual.setUsuari(usuari);
        modificarUsuariActual.modificarUsuari();

        deshabilitarEdicioDades();
        btnEditarPerfil.setEnabled(true);
        btnGuardarCanvis.setEnabled(false);
    }

    /**
     * Mètode per canviar la contrasenya
     */
    private void canviContrasenya(String novaContrasenya) throws ConnectException {
        // Actualitzar la contrasenya de l'objecte Usuari existent
        usuari.setPassUsuari(novaContrasenya);

        // Cridar al mètode canviarContrasenya de la classe CanviarContrasenya
        // per enviar la sol·licitud de canvi de contrasenya al servidor
        CanviarContrasenya canviarContrasenya = new CanviarContrasenya((ModificarUsuariActual.ModificarUsuariListener) this, this, sessioID) {
            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            @Override
            protected Object doInBackground(Void... voids) {
                canviarContrasenya();
                return null;
            }
        };
        canviarContrasenya.setUsuari(usuari);
        canviarContrasenya.canviarContrasenya();

        deshabilitarEdicioContrasenya();
        btnGuardarCanviContrasenya.setEnabled(false);
        btnCanviContrasenya.setEnabled(true);
    }


    /**
     * Mètode que gestiona el clic del botó per canviar la contrasenya
     */
    private void onCanviContrasenyaButtonClick() throws ConnectException {
        String contrasenyaActual = etContrasenyaActual.getText().toString();
        String novaContrasenya = etNovaContrasenya.getText().toString();
        String confirmarContrasenya = etConfirmarContrasenya.getText().toString();

        // Comprovar si s'ha obtingut l'usuari correctament
        if (usuari == null) {
            Utils.mostrarToast(getApplicationContext(), "Error: No s'ha pogut obtenir l'usuari");
        }

        // Comprovar que cap dels camps estigui buit
        if (contrasenyaActual.isEmpty() || novaContrasenya.isEmpty() || confirmarContrasenya.isEmpty()) {
            Utils.mostrarToast(getApplicationContext(), "Cal omplir tots els camps");
            return;
        }

        // Verificar que la contrasenya actual coincideixi amb la contrasenya del usuari
        if (!contrasenyaActual.equals(usuari.getPassUsuari())) {
            Utils.mostrarToast(getApplicationContext(), "La contrasenya actual no és correcta");
            return;
        }

        // Comprovar si la nova contraseña es igual a la actual
        if (contrasenyaActual.equals(novaContrasenya)) {
            Utils.mostrarToast(getApplicationContext(), "La nova contrasenya no pot ser igual a l'actual");
            return;
        }

        // Comprovar la longitud i la complexitat de la nova contrasenya
        if (novaContrasenya.length() < 8 || !novaContrasenya.matches(".*\\d.*") || !novaContrasenya.matches(".*[a-zA-Z].*")) {
            Utils.mostrarToast(getApplicationContext(), "La nova contrasenya ha de tenir almenys 8 caràcters alfanumèrics");
            return;
        }

        // Comparar la nova contrasenya amb la seva repetició
        if (!novaContrasenya.equals(confirmarContrasenya)) {
            Utils.mostrarToast(getApplicationContext(), "Les contrasenyes noves no coincideixen");
            return;
        }

        // Si tots els criteris de validació es compleixen, continuar amb el canvi de contrasenya
        canviContrasenya(novaContrasenya);
        deshabilitarEdicioContrasenya(); // Després de guardar, deshabilitar l'edició de nou
    }

    @Override
    public void respostaServidor(Object resposta) throws ConnectException {

    }

    @Override
    public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
        return null;
    }

    @Override
    public void onUsuariObtingut(Usuari usuari) {
        this.usuari = usuari;
        actualitzarUsuari(usuari);
        modificarUsuariActual.setUsuari(usuari);
    }

    @Override
    public void onUsuariModificat(Usuari usuari) {
        this.usuari = usuari;
        actualitzarUsuari(usuari);
    }

}