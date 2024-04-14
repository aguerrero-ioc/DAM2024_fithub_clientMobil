package antonioguerrero.ioc.fithub.peticions;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import antonioguerrero.ioc.fithub.Utils;
import antonioguerrero.ioc.fithub.objectes.Usuari;

public abstract class BasePeticions {
    protected static final String SERVIDOR_IP = "192.168.0.252";
    protected static final int SERVIDOR_PORT = 8080;

    public interface respostaServidorListener {
        void respostaServidor(Object resposta) throws ConnectException;
    }

    protected respostaServidorListener listener;
    protected ObjectOutputStream objectOut;
    protected ObjectInputStream objectIn;


    public BasePeticions(respostaServidorListener listener) {
        this.listener = listener;
    }

    public BasePeticions(Context context, String correuUsuari, String passUsuari) {
    }

    public abstract void respostaServidor(Object resposta);

    public abstract Class<?> obtenirTipusObjecte();

    public abstract void execute() throws ConnectException;


    public Object[] enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) throws ConnectException {
        String respostaHS = "";
        Object[] resposta = null;
        Socket clientSocket = null;
        //Handshake
        Scanner inHS = null;
        //Missatge
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = dada1;
        peticio[2] = dada2;
        peticio[3] = idSessio;

        try {
            // Conectar al servidor
            clientSocket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);
            System.out.println("***COM***           Client connectant al servidor...");

            inHS = new Scanner(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Llegir missatge de conexio
            respostaHS = inHS.nextLine();
            System.out.println("***COM***           " + respostaHS);

            // Envia missatge al servidor
            out.writeObject(peticio);

            // Llegeix resposta del servidor
            resposta = (Object[]) in.readObject();

        } catch (ConnectException cx) {
            throw cx;
        } catch (EOFException eq) {
            // Manejar la excepción EOFException
            eq.printStackTrace();
        } catch (IOException ex) {
            // Manejar la excepción IOException
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) in.close();
                if (inHS != null) inHS.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }

    public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) throws ConnectException {
        String respostaHS = "";
        Object[] resposta = null;
        Socket clientSocket = null;
        //Handshake
        Scanner inHS = null;
        //Missatge
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        Object[] peticio = new Object[4];
        peticio[0] = operacio;
        peticio[1] = nomObjecte;
        peticio[2] = objecteMapa;
        peticio[3] = idSessio;

        try {
            // Conectar al servidor
            clientSocket = new Socket(SERVIDOR_IP, SERVIDOR_PORT);
            System.out.println("***COM***           Client connectant al servidor...");

            inHS = new Scanner(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Llegir missatge de conexio
            respostaHS = inHS.nextLine();
            System.out.println("***COM***           " + respostaHS);

            // Envia missatge al servidor
            out.writeObject(peticio);

            // Llegeix resposta del servidor
            resposta = (Object[]) in.readObject();

        } catch (ConnectException cx) {
            throw cx;
        } catch (EOFException eq) {
            // Manejar la excepción EOFException
            eq.printStackTrace();
        } catch (IOException ex) {
            // Manejar la excepción IOException
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) in.close();
                if (inHS != null) inHS.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resposta;
    }


}