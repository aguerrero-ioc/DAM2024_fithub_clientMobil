package antonioguerrero.ioc.fithub.peticions;

import android.content.Context;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import antonioguerrero.ioc.fithub.objectes.Usuari;

public abstract class BasePeticions {

    //LOCAL "192.168.0.252"
    protected static final String SERVIDOR_IP = "192.168.0.252";
    protected static final int SERVIDOR_PORT = 8080;

    public void setUsuari(Usuari usuari) {
    }


    public interface respostaServidorListener {
        void respostaServidor(Object resposta) throws ConnectException;

        List<HashMap<String, String>> respostaServidorHashmap(Object resposta);

        void onRespostaServidorMultiple(Object resposta);
    }

    protected respostaServidorListener listener;

    public BasePeticions(respostaServidorListener listener) {
        this.listener = listener;
    }

    public BasePeticions(Context context, String correuUsuari, String passUsuari) {
    }

    public abstract List<HashMap<String, String>> respostaServidor(Object resposta);

    public abstract Class<?> obtenirTipusObjecte();

    public abstract List<HashMap<String, String>> respostaServidorHashmap(Object resposta);

    public abstract void execute() throws ConnectException;


    public Object[] enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) throws ConnectException {
        String respostaHandshake = "";
        Object[] resposta = null;
        Socket clientSocket = null;
        //Handshake
        Scanner handshake = null;
        //Missatge
        ObjectInputStream entrada = null;
        ObjectOutputStream sortida = null;

        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = dada1;
        peticio[2] = dada2;
        peticio[3] = idSessio;

        try {
            // Conectar al servidor
            clientSocket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);
            Log.d("BasePeticions", "Client connectant al servidor...");

            handshake = new Scanner(clientSocket.getInputStream());
            sortida = new ObjectOutputStream(clientSocket.getOutputStream());
            entrada = new ObjectInputStream(clientSocket.getInputStream());

            // Llegir missatge de conexio
            respostaHandshake = handshake.nextLine();
            Log.d("BasePeticions","Servidor: " + respostaHandshake);

            // Envia missatge al servidor
            sortida.writeObject(peticio);

            // Llegeix resposta del servidor
            resposta = (Object[]) entrada.readObject();

        } catch (ConnectException cx) {
            throw cx;
        } catch (EOFException eq) {
            eq.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (handshake != null) handshake.close();
                if (sortida != null) sortida.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) throws ConnectException {
        String respostaHandshake = "";
        Object[] resposta = null;
        Socket clientSocket = null;

        Scanner handshake = null;

        ObjectInputStream entrada = null;
        ObjectOutputStream sortida = null;

        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = nomObjecte;
        peticio[2] = objecteMapa;
        peticio[3] = idSessio;

        try {
            // Conectar al servidor
            clientSocket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);
            Log.d("BasePeticions", "Client connectant al servidor...");

            handshake = new Scanner(clientSocket.getInputStream());
            sortida = new ObjectOutputStream(clientSocket.getOutputStream());
            entrada = new ObjectInputStream(clientSocket.getInputStream());

            // Llegir missatge de conexio
            respostaHandshake = handshake.nextLine();
            Log.d("BasePeticions","Servidor: " + respostaHandshake);

            // Envia missatge al servidor
            sortida.writeObject(peticio);

            // Llegeix resposta del servidor
            resposta = (Object[]) entrada.readObject();

        } catch (ConnectException cx) {
            throw cx;
        } catch (EOFException eq) {

            eq.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (entrada != null) entrada.close();
                if (handshake != null) handshake.close();
                if (sortida != null) sortida.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }


}