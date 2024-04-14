package antonioguerrero.ioc.fithub.peticions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Activitat;
import antonioguerrero.ioc.fithub.objectes.Installacio;
import antonioguerrero.ioc.fithub.objectes.Servei;
import antonioguerrero.ioc.fithub.objectes.Usuari;

public class Consulta extends BasePeticions {
    private Context context;
    private static final String ETIQUETA = "ConsultarEntidad";
    private String sessioID;
    private String tipusEntitat;

    public Consulta(respostaServidorListener listener, Context context, String sessioID, String tipoEntidad) {
        super(listener);
        this.context = context;
        this.sessioID = sessioID;
        this.tipusEntitat = tipusEntitat;

        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        this.sessioID = preferencies.getString(Utils.SESSIO_ID, Utils.VALOR_DEFAULT);
    }

    @SuppressLint("StaticFieldLeak")
    public void consultaUnica(String parametre) {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    String operacio = "select";
                    String entitat = tipusEntitat;
                    String sessioID = Consulta.this.sessioID;

                    switch (entitat) {
                        case "usuari":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "activitat":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "installacio":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "servei":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        default:
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                    }
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Object resposta) {
                respostaServidor(resposta);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void consultaMultiple() {
        new AsyncTask<Void, Void, Object>() {
            @Override
            protected Object doInBackground(Void... voids) {
                try {
                    String operacio = "select";
                    String entitat = tipusEntitat;
                    String parametre = null;
                    String sessioID = Consulta.this.sessioID;

                    switch (entitat) {
                        case "usuari":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "activitat":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "installacio":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        case "servei":
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                        default:
                            return enviarPeticioString(operacio, entitat, parametre, sessioID);
                    }
                } catch (ConnectException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void onPostExecute(Object resposta) {
                respostaServidorMultiple(resposta);
            }
        }.execute();
    }



        @Override
        public void respostaServidor(Object resposta) {
            Log.d(ETIQUETA, "Resposta rebuda: " + resposta.toString());
            if (resposta instanceof Object[]) {
                Object[] arrayResposta = (Object[]) resposta;
                String estat = (String) arrayResposta[0];

                switch (estat) {
                    case "usuari":
                        HashMap<String, String> mapaUsuari = (HashMap<String, String>) arrayResposta[1];
                        Usuari usuari = (Usuari) Utils.HashMapAObjecte(mapaUsuari, Usuari.class);
                        break;

                    case "activitat":
                        HashMap<String, String> mapaActivitat = (HashMap<String, String>) arrayResposta[1];
                        Activitat activitat = (Activitat) Utils.HashMapAObjecte(mapaActivitat, Activitat.class);
                        break;

                    case "installacio":
                        HashMap<String, String> mapaInstallacio = (HashMap<String, String>) arrayResposta[1];
                        Installacio installacio = (Installacio) Utils.HashMapAObjecte(mapaInstallacio, Installacio.class);
                        break;

                    case "servei":
                        HashMap<String, String> mapaServei = (HashMap<String, String>) arrayResposta[1];
                        Servei servei = (Servei) Utils.HashMapAObjecte(mapaServei, Servei.class);
                        break;

                    default:
                        Utils.mostrarToast(context, "Error en la consulta de l'usuari");
                        break;
                }
            } else {
                String missatgeError = "Error: La resposta del servidor no és un array d'objectes. Resposta rebuda: " + resposta.toString();
                Log.e(ETIQUETA, missatgeError);
                Utils.mostrarToast(context, "Error en la resposta del servidor");
            }
        }



    public void respostaServidorMultiple(Object resposta) {
        Log.d(ETIQUETA, "Resposta del servidor: " + resposta);
        if (resposta instanceof Object[]) {
            Object[] respostaArray = (Object[]) resposta;
            String estat = (String) respostaArray[0];
            List<HashMap<String, String>> llistaEntitats;

            switch (estat) {
                case "usuariLlista":
                    llistaEntitats = (List<HashMap<String, String>>) respostaArray[1];
                    guardarDades(llistaEntitats,"usuari");
                    break;

                case "activitatLlista":
                    llistaEntitats = (List<HashMap<String, String>>) respostaArray[1];
                    guardarDades(llistaEntitats,"activitat");
                    break;

                case "installacioLlista":
                    llistaEntitats = (List<HashMap<String, String>>) respostaArray[1];
                    guardarDades(llistaEntitats,"installacio");
                    break;

                case "serveiLlista":
                    llistaEntitats = (List<HashMap<String, String>>) respostaArray[1];
                    guardarDades(llistaEntitats,"servei");
                    break;

                default:
                    Utils.mostrarToast(context, "No s'han pogut obtenir les dades");
                    break;
            }
        } else {
            Utils.mostrarToast(context, "Error de connexió");
        }
    }

    private void guardarDades(List<HashMap<String, String>> llistaEntitats, String tipusEntitat) {
        SharedPreferences preferencies = context.getSharedPreferences(Utils.PREFERENCIES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencies.edit();

        switch (tipusEntitat) {
            case "usuari":
                for (int i = 0; i < llistaEntitats.size(); i++) {
                    HashMap<String, String> mapaUsuari = llistaEntitats.get(i);
                    editor.putString("IDUsuari" + i, mapaUsuari.get("IDUsuari"));
                    editor.putString("nomUsuari" + i, mapaUsuari.get("nomUsuari"));
                    editor.putString("cognomsUsuari" + i, mapaUsuari.get("cognomsUsuari"));
                    editor.putString("correuUsuari" + i, mapaUsuari.get("correuUsuari"));
                    editor.putString("passUsuari" + i, mapaUsuari.get("passUsuari"));
                    editor.putString("dataNaixementUsuari" + i, mapaUsuari.get("dataNaixementUsuari"));
                    editor.putString("adreca" + i, mapaUsuari.get("adreca"));
                    editor.putString("telefon" + i, mapaUsuari.get("telefon"));
                    editor.putString("dataInscripcio" + i, mapaUsuari.get("dataInscripcio"));

                }
                editor.putInt("numUsuaris", llistaEntitats.size());
                break;

            case "activitat":
                for (int i = 0; i < llistaEntitats.size(); i++) {
                    HashMap<String, String> mapaActivitat = llistaEntitats.get(i);
                    editor.putString("idActivitat" + i, mapaActivitat.get("idActivitat"));
                    editor.putString("nomActivitat" + i, mapaActivitat.get("nomActivitat"));
                    editor.putString("descripcioActivitat" + i, mapaActivitat.get("descripcioActivitat"));
                    editor.putString("tipusActivitat" + i, mapaActivitat.get("tipusActivitat"));
                    editor.putString("aforamentActivitat" + i, mapaActivitat.get("aforamentActivitat"));
                }
                editor.putInt("numActivitats", llistaEntitats.size());
                break;

            case "installacio":
                for (int i = 0; i < llistaEntitats.size(); i++) {
                    HashMap<String, String> mapaInstallacio = llistaEntitats.get(i);
                    editor.putInt("idInstallacio"+ i, Integer.parseInt(mapaInstallacio.get("idInstallacio")));
                    editor.putString("nomInstallacio" + i, mapaInstallacio.get("nomInstallacio"));
                    editor.putString("descripcioInstallacio" + i, mapaInstallacio.get("descripcioInstallacio"));
                    editor.putString("tipusInstallacio" + i, mapaInstallacio.get("tipusInstallacio"));
                }
                editor.putInt("numInstalacions", llistaEntitats.size());
                break;

            case "servei":
                for (int i = 0; i < llistaEntitats.size(); i++) {
                    HashMap<String, String> mapaServei = llistaEntitats.get(i);
                    editor.putString("IDServei" + i, mapaServei.get("IDServei"));
                    editor.putString("nomServei" + i, mapaServei.get("nomServei"));
                    editor.putString("descripcioServei" + i, mapaServei.get("descripcioServei"));
                    editor.putString("aforamentServei", mapaServei.get("aforamentServei"));
                    editor.putString("tipusInstallacio", mapaServei.get("tipusInstallacio"));
                    editor.putString("personalServei" + i, mapaServei.get("personalServei"));
                    editor.putString("preuServei" + i, mapaServei.get("preuServei"));
                }
                editor.putInt("numServeis", llistaEntitats.size());
                break;

            default:
                break;
        }
        editor.apply();
    }

    @Override
    public Class<?> obtenirTipusObjecte() {
        return null;
    }

    @Override
    public void execute() throws ConnectException {

    }
}