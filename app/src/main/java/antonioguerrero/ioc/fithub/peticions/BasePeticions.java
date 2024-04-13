package antonioguerrero.ioc.fithub.peticions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public abstract class BasePeticions {
    protected static final String SERVIDOR_IP = "192.168.0.252";
    protected static final int SERVIDOR_PORT = 8080;

    public interface respostaServidorListener {
        void respostaServidor(Object resposta);
    }

    protected respostaServidorListener listener;
    protected ObjectOutputStream objectOut;
    protected ObjectInputStream objectIn;

    public BasePeticions(Context context, String correuUsuari, String passUsuari) {
    }

    public BasePeticions(respostaServidorListener listener, ObjectOutputStream objectOut, ObjectInputStream objectIn) {
        this.listener = listener;
        this.objectOut = objectOut;
        this.objectIn = objectIn;
    }

    protected void enviarPeticio(Object[] peticio) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket socket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                    objectOut.writeObject(peticio);
                    objectOut.flush();

                    String peticioString = Arrays.toString(peticio);
                    Log.d("PeticioInfo:", "Petición enviada: " + peticioString);

                    objectOut.close();
                    socket.close();
                } catch (IOException e) {
                    Log.e("PeticioError:", "Error al enviar la petición", e);
                }
                return null;
            }
        }.execute();
    }

    public abstract Class<?> obtenirTipusObjecte();

    public abstract void execute();


    protected void enviarPeticioHashmap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket socket = new Socket("192.168.0.252", 8080);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                    Object[] peticio = new Object[4];
                    peticio[0] = operacio;
                    peticio[1] = nomObjecte;
                    peticio[2] = objecteMapa;
                    peticio[3] = idSessio;

                    objectOut.writeObject(peticio);
                    objectOut.flush();


                    String peticioString = Arrays.toString(peticio);
                    Log.d("PeticioInfo:", "Petición enviada: " + peticioString);
                } catch (IOException e) {
                    Log.e("PeticioError:", "Error al enviar la petición", e);
                }
                return null;
            }
        }.execute();
    }

    protected void enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Socket socket = new Socket("192.168.0.252", 8080);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());

                    Object[] peticio = new Object[4];
                    peticio[0] = operacio;
                    peticio[1] = dada1;
                    peticio[2] = dada2;
                    peticio[3] = idSessio;

                    objectOut.writeObject(peticio);
                    objectOut.flush();


                    String peticioString = Arrays.toString(peticio);
                    Log.d("PeticioInfo:", "Petición enviada: " + peticioString);
                } catch (IOException e) {
                    Log.e("PeticioError:", "Error al enviar la petición", e);
                }
                return null;
            }
        }.execute();
    }

    public abstract void respostaServidor(Object resposta);


}