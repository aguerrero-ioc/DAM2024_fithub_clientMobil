package antonioguerrero.ioc.fithub.usuari;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.PeticioUsuari;

/**
 * Classe que representa l'activitat del perfil de l'usuari a l'aplicació FitHub.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PerfilActivity extends AppCompatActivity implements ConnexioServidor.OnServerResponseListener {

    private EditText etNomUsuari, etCognoms, etDataNaixement, etAdreca, etCorreuUsuari, etTelefonContacte, etDataInscripcio, etContrasenyaActual, etNovaContrasenya, etConfirmarContrasenya;
    private Button btnGuardarCanvis;
    private Usuari usuari;

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

        // Inicialització dels botons
        Button btnEditarPerfil = findViewById(R.id.btn_editar_perfil);
        btnGuardarCanvis = findViewById(R.id.btn_guardar_canvis);

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

        // Verificació si l'usuari té una imatge personalitzada
        boolean usuariTeImatgePersonalitzada = false;

        // Verificació si l'usuari té una imatge personalitzada
        if (usuariTeImatgePersonalitzada) {
            // Si té, carrega la imatge personalitzada
            // PENDENT IMPLEMENTAR lògica per carregar la imatge de l'usuari
            // ivImatgeUsuari.setImageResource(R.drawable.imagen_usuario_personalizada);
        } else {
            // Si no té, carrega la imatge predeterminada
            ivImatgeUsuari.setImageResource(R.mipmap.default_imatge_perfil);
        }

        // Configuració del clic del botó per editar el perfil
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                habilitarEdicio();
            }
        });

        // Configuració del clic del botó per desar els canvis
        btnGuardarCanvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCanvis();
            }
        });

        // Configuració del clic del botó per canviar la contrasenya
        Button btnCanviarContrasenya = findViewById(R.id.btn_canviar_contrasenya);
        btnCanviarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCanviContrasenyaButtonClick(); // Trucar al mètode corresponent
            }
        });

        // Inicialment, deshabilitar l'edició dels camps de text
        deshabilitarEdicio();
    }

    /**
     * Mètode que gestiona la resposta del servidor.
     */
    @Override
    public void onServerResponse(String resposta) {
        if (resposta != null) {
            // Processar la resposta del servidor aquí per mostrar les dades a PerfilActivity
            String[] parts = resposta.split(",");
            if (parts.length >= 10) {
                // Crear un nou objecte Usuari
                Usuari usuari = processarDadesUsuari(parts);
                // Actualitzar la interfície d'usuari amb les dades de l'usuari
                actualitzarUsuari(usuari);
            } else {
                Log.e("ConnexioServidor", "Error: Resposta del servidor amb format incorrecte");
            }
        } else {
            // Processar una resposta nul·la del servidor
            Log.e("ConnexioServidor", "Error de connexió: Resposta nul·la del servidor");
        }
    }

    /**
     * Mètode que processa les dades de l'usuari a partir de la resposta del servidor.
     */
    private Usuari processarDadesUsuari(String[] parts) {
        Usuari usuari = new Usuari();

        // Iterar sobre les dades rebudes
        for (String part : parts) {
            // Dividir la cadena en etiqueta i valor
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                String etiqueta = keyValue[0].trim();
                String valor = keyValue[1].trim();

                // Assignar el valor corresponent al camp adequat de l'objecte Usuari
                switch (etiqueta) {
                    case "correu":
                        usuari.setCorreu(valor);
                        break;
                    case "contrasenya":
                        usuari.setContrasenya(valor);
                        break;
                    case "usuariID":
                        usuari.setUsuariID(Integer.parseInt(valor));
                        break;
                    case "tipus":
                        usuari.setTipus(valor);
                        break;
                    case "dataInscripcio":
                        usuari.setDataInscripcio(valor);
                        break;
                    case "nom":
                        usuari.setNom(valor);
                        break;
                    case "cognoms":
                        usuari.setCognoms(valor);
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
        }
        return usuari;
    }


    /**
     * Mètode per actualitzar els camps de text amb les dades de l'usuari
     */
    public void actualitzarUsuari(Usuari usuari) {
        etNomUsuari.setText(usuari.getNom());
        etCognoms.setText(usuari.getCognoms());
        etDataNaixement.setText(usuari.getDataNaixement());
        etAdreca.setText(usuari.getAdreca());
        etCorreuUsuari.setText(usuari.getCorreu());
        etTelefonContacte.setText(usuari.getTelefon());
        etDataInscripcio.setText(usuari.getDataInscripcio());
    }

    /**
     * Mètode per habilitar l'edició dels camps de text.
     */
    private void habilitarEdicio() {
        etNomUsuari.setEnabled(true);
        etCognoms.setEnabled(true);
        etDataNaixement.setEnabled(true);
        etAdreca.setEnabled(true);
        etCorreuUsuari.setEnabled(true);
        etTelefonContacte.setEnabled(true);
        etDataInscripcio.setEnabled(false);
        btnGuardarCanvis.setEnabled(true);
    }

    /**
     * Mètode per deshabilitar l'edició dels camps de text.
     */
    private void deshabilitarEdicio() {
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
     * Mètode per desar els canvis realitzats per l'usuari.
     */
    private void guardarCanvis() {
        // Actualitzar les dades de l'objecte Usuari amb els valors dels EditText
        usuari.setNom(etNomUsuari.getText().toString());
        usuari.setCognoms(etCognoms.getText().toString());
        usuari.setDataNaixement(etDataNaixement.getText().toString());
        usuari.setAdreca(etAdreca.getText().toString());
        usuari.setTelefon(etTelefonContacte.getText().toString());
        usuari.setCorreu(etCorreuUsuari.getText().toString());

        //Cridar al mètode modificarUsuari de la classe PeticioUsuari per a enviar la petició al servidor
        PeticioUsuari.modificarUsuari(usuari);

        Toast.makeText(this, "Canvis guardats", Toast.LENGTH_SHORT).show();

        deshabilitarEdicio(); // Després de desar, deshabilitar l'edició de nou
    }



    /**
     * Mètode per canviar la contrasenya
     */
    private void canviContrasenya(String novaContrasenya) {
        // Actualitzar la contrasenya de l'objecte Usuari existent
        usuari.setContrasenya(novaContrasenya);

        // Cridar al mètode modificarUsuari de la classe PeticioUsuari
        // per enviar la sol·licitud de canvi de contrasenya al servidor
        PeticioUsuari.canviarContrasenya(usuari);
    }

    /**
     * Mètode que maneja el clic del botó per canviar la contrasenya
     */
    private void onCanviContrasenyaButtonClick() {
        String contrasenyaActual = etContrasenyaActual.getText().toString();
        String novaContrasenya = etNovaContrasenya.getText().toString();
        String confirmarContrasenya = etConfirmarContrasenya.getText().toString();

        // Comprovar que cap dels camps estigui buit
        if (contrasenyaActual.isEmpty() || novaContrasenya.isEmpty() || confirmarContrasenya.isEmpty()) {
            Toast.makeText(this, "Cal omplir tots els camps", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que la contrasenya actual coincideixi amb la contrasenya del usuari
        if (!contrasenyaActual.equals(usuari.getContrasenya())) {
            Toast.makeText(this, "La contrasenya actual no és correcta", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comprovar la longitud i la complexitat de la nova contrasenya
        if (novaContrasenya.length() < 8 || !novaContrasenya.matches(".*\\d.*") || !novaContrasenya.matches(".*[a-zA-Z].*")) {
            Toast.makeText(this, "La nova contrasenya ha de tenir almenys 8 caràcters alfanumèrics", Toast.LENGTH_SHORT).show();
            return;
        }

        // Comparar la nova contrasenya amb la seva repetició
        if (!novaContrasenya.equals(confirmarContrasenya)) {
            Toast.makeText(this, "Les contrasenyes noves no coincideixen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si tots els criteris de validació es compleixen, continuar amb el canvi de contrasenya
        canviContrasenya(novaContrasenya);
    }

}
