package antonioguerrero.ioc.fithub.menu.usuari;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;

import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.CanviarContrasenya;
import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarUsuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.ModificarUsuari;

/**
 * Classe que representa l'activitat del perfil de l'usuari a l'aplicació FitHub.
 * Aquesta activitat permet a l'usuari veure i modificar les seves dades personals.
 * A més, també permet canviar la contrasenya de l'usuari.
 *
 * @Author Antonio Guerrero
 * @Version 1.0
 */
public class PerfilActivity extends AppCompatActivity implements ConnexioServidor.respostaServidorListener {

    private EditText etNomUsuari, etCognoms, etDataNaixement, etAdreca, etCorreuUsuari, etTelefonContacte, etDataInscripcio, etContrasenyaActual, etNovaContrasenya, etConfirmarContrasenya;
    private Button btnGuardarCanvis, btnEditarPerfil, btnCanviContrasenya, btnGuardarCanviContrasenya;
    private Context context;
    private SharedPreferences preferencies;
    private String sessioID;
    private Usuari usuari;
    private ObjectOutputStream objecteSortida;
    private ObjectInputStream objecteEntrada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicialització de les vistes
        etNomUsuari = findViewById(R.id.et_nom_usuari);
        etCognoms = findViewById(R.id.et_cognoms_usuari);
        etDataNaixement = findViewById(R.id.et_data_naixement);
        etAdreca = findViewById(R.id.et_adreca);
        etCorreuUsuari = findViewById(R.id.et_correu_usuari);
        etTelefonContacte = findViewById(R.id.et_telefon_contacte);
        etDataInscripcio = findViewById(R.id.et_data_inscripcio);

        // EditText per a la contrasenya actual, nova contrasenya i confirmació de contrasenya
        etContrasenyaActual = findViewById(R.id.et_contrasenya_actual);
        etNovaContrasenya = findViewById(R.id.et_nova_contrasenya);
        etConfirmarContrasenya = findViewById(R.id.et_confirmar_contrasenya);

        // Inicialització dels botons d'edició i desament de dades d'usuari
        btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        btnGuardarCanvis = findViewById(R.id.btn_guardar_canvis);

        // Inicialització dels botons d'edició i desament de contrasenya
        btnCanviContrasenya = findViewById(R.id.btn_canviar_contrasenya);
        btnGuardarCanviContrasenya = findViewById(R.id.btn_guardar_contrasenya);

        // Obtenció de les dades de l'usuari de l'Intent
        Usuari usuari = getIntent().getParcelableExtra("usuari");

        // Actualització de la interfície amb les dades de l'usuari
        if (usuari != null) {
            actualitzarUsuari(usuari);
        } else {
            // Maneig del cas en què no es rebin les dades de l'usuari correctament
            Log.e("PerfilActivity", "Error: No s'han rebut les dades de l'usuari correctament");
        }

        // Obtenció de la referència de l'ImageView de la imatge de l'usuari
        ImageView ivImatgeUsuari = findViewById(R.id.iv_imatge_usuari);

        // Configuració del clic al ImageView
        ivImatgeUsuari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.mostrarToast(PerfilActivity.this, "Pendent d'implementar");
            }
        });

        // Verificació si l'usuari té una imatge personalitzada
        boolean usuariTeImatgePersonalitzada = false;

        // Verificació si l'usuari té una imatge personalitzada
        if (usuariTeImatgePersonalitzada) {
            // Si té, carrega la imatge personalitzada
            // PENDENT IMPLEMENTAR
        } else {
            // Si no té, carrega la imatge predeterminada
            ivImatgeUsuari.setImageResource(R.mipmap.default_imatge_perfil);
        }

        // Inicialment, deshabilitar l'edició dels camps de text
        deshabilitarEdicioDades();

        // Configuració del clic del botó per editar el perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEdicioDades();
            }
        });


        // Configuració del clic del botó per desar els canvis de les dades d'Usuari
        btnGuardarCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCanvis();
            }
        });




        // Configuració del clic del botó per canviar la contrasenya
        btnCanviContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEdicioContrasenya();
            }
        });


        // Configuració del clic del botó per desar els canvis de la contrasenya
        btnGuardarCanviContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCanviContrasenyaButtonClick(); // Trucar al mètode corresponent
            }
        });

        context = this;
        preferencies = getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);

        // Obtenir les dades de l'usuari de l'Intent
        usuari = getIntent().getParcelableExtra("usuari");

        // Actualitzar la interfície amb les dades de l'usuari
        if (usuari != null) {
            actualitzarUsuari(usuari);
        } else {
            Log.e("PerfilActivity", "Error: No s'han rebut les dades de l'usuari correctament");
        }

    }

    /**
     * Mètode per gestionar la resposta del servidor.
     *
     * @param resposta La resposta del servidor.
     */
    @Override
    public void respostaServidor(String resposta) {
        if (resposta != null) {
            String[] parts = resposta.split(",");
            if (parts.length >= 10) {
                Usuari usuari = new Usuari();
                assignarDadesUsuari(parts, usuari);
                actualitzarUsuari(usuari);
            } else {
                Log.e("ConnexioServidor", "Error: Resposta del servidor amb format incorrecte");
            }
        } else {
            Log.e("ConnexioServidor", "Error de connexió: Resposta nul·la del servidor");
        }
    }

    /**
     * Mètode per assignar les dades de l'usuari a l'objecte Usuari.
     *
     * @param parts L'array de parts de la resposta del servidor.
     * @param usuari L'objecte Usuari al qual assignar les dades.
     */
    private void assignarDadesUsuari(String[] parts, Usuari usuari) {
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                assignarValorAUsuari(keyValue, usuari);
            }
        }
    }

    /**
     * Mètode per assignar un valor a un camp de l'objecte Usuari.
     *
     * @param valorClau L'array de parts de la resposta del servidor.
     * @param usuari L'objecte Usuari al qual assignar les dades.
     */
    private void assignarValorAUsuari(String[] valorClau, Usuari usuari) {
        String etiqueta = valorClau[0].trim();
        String valor = valorClau[1].trim();

        switch (etiqueta) {
            case "correuUsuari":
                usuari.setCorreuUsuari(valor);
                break;
            case "passUsuari":
                usuari.setPassUsuari(valor);
                break;
            case "IDUsuari":
                usuari.setIDUsuari(Integer.parseInt(valor));
                break;
            case "tipusUsuari":
                usuari.setTipusUsuari(Integer.parseInt(valor));
                break;
            case "dataInscripcio":
                usuari.setDataInscripcio(valor);
                break;
            case "nomUsuari":
                usuari.setNomUsuari(valor);
                break;
            case "cognomsUsuari":
                usuari.setCognomsUsuari(valor);
                break;
            case "dataNaixement":
                usuari.setDataNaixement(valor);
                break;
            case "adreca":
                usuari.setAdreca(valor);
                break;
            case "telefon":
                usuari.setTelefon(valor);
                break;
            default:
                break;
        }
    }



    /**
     * Mètode per actualitzar les dades de l'usuari a la interfície.
     *
     * @param usuari L'objecte Usuari amb les dades a actualitzar.
     */
    public void actualitzarUsuari(Usuari usuari) {

        // Crear una instancia de ConsultarUsuari
        ConsultarUsuari consultarUsuariInstance = new ConsultarUsuari((BasePeticions.respostaServidorListener) this, getApplicationContext(), usuari.getCorreuUsuari(), sessioID, objecteSortida, objecteEntrada) {
            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }
        };
        // Ejecutar la petición al servidor
        consultarUsuariInstance.execute();

        etNomUsuari.setText(usuari.getNomUsuari());
        etCognoms.setText(usuari.getCognomsUsuari());
        etDataNaixement.setText(usuari.getDataNaixement());
        etAdreca.setText(usuari.getAdreca());
        etCorreuUsuari.setText(usuari.getCorreuUsuari());
        etTelefonContacte.setText(usuari.getTelefon());
        etDataInscripcio.setText(usuari.getDataInscripcio());
    }

    /**
     * Mètode per habilitar l'edició dels camps de text de dades personals de l'usuari.
     */
    private void habilitarEdicioDades() {
        etNomUsuari.setEnabled(true);
        etCognoms.setEnabled(true);
        etDataNaixement.setEnabled(true);
        etAdreca.setEnabled(true);
        etCorreuUsuari.setEnabled(true);
        etTelefonContacte.setEnabled(true);
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
        etCorreuUsuari.setEnabled(false);
        etTelefonContacte.setEnabled(false);
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
    private void guardarCanvis() {
        // Obtenir els valors dels EditText
        String nom = etNomUsuari.getText().toString();
        String cognoms = etCognoms.getText().toString();
        String dataNaixement = etDataNaixement.getText().toString();
        String adreca = etAdreca.getText().toString();
        String correu = etCorreuUsuari.getText().toString();
        String telefon = etTelefonContacte.getText().toString();

        // Comprovar si tots els camps estan complets
        if (nom.isEmpty() || cognoms.isEmpty() || dataNaixement.isEmpty() || adreca.isEmpty() || correu.isEmpty() || telefon.isEmpty()) {
            Utils.mostrarToast(getApplicationContext(), "Si us plau, completa tots els camps");
            return;
        }

        // Comprovar el format correcte del nom
        if (!nom.matches("[a-zA-ZÀ-ÿ\\s]+")) {
            Utils.mostrarToast(getApplicationContext(), "El nom només pot contenir lletres");
            return;
        }

        // Comprovar el format correcte dels cognoms
        if (!cognoms.matches("[a-zA-ZÀ-ÿ\\s]+")) {
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
        usuari.setNomUsuari(nom);
        usuari.setCognomsUsuari(cognoms);
        usuari.setDataNaixement(dataNaixement);
        usuari.setAdreca(adreca);
        usuari.setTelefon(telefon);
        usuari.setCorreuUsuari(correu);

        //Cridar al mètode modificarUsuari de la classe ModificarUsuari per enviar la petició al servidor
        ModificarUsuari modificarUsuariInstance = new ModificarUsuari((BasePeticions.respostaServidorListener) this, sessioID, objecteSortida, objecteEntrada) {
            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }
        };
        modificarUsuariInstance.modificarUsuari(usuari);

        Utils.mostrarToast(getApplicationContext(), "Canvis guardats");

        deshabilitarEdicioDades(); // Després de guardar, deshabilitar l'edició de nou
    }


    /**
     * Mètode per canviar la contrasenya
     */
    private void canviContrasenya(String novaContrasenya) {
        // Actualitzar la contrasenya de l'objecte Usuari existent
        usuari.setPassUsuari(novaContrasenya);

        // Cridar al mètode canviarContrasenya de la classe CanviarContrasenya
        // per enviar la sol·licitud de canvi de contrasenya al servidor
        CanviarContrasenya canviarContrasenyaInstance = new CanviarContrasenya((BasePeticions.respostaServidorListener) this, usuari, objecteSortida, objecteEntrada) {
            @Override
            protected Object doInBackground(Void... voids) {
                return null;
            }
        };
        canviarContrasenyaInstance.canviarContrasenya();
    }

    /**
     * Mètode que maneja el clic del botó per canviar la contrasenya
     */
    private void onCanviContrasenyaButtonClick() {
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

}
