package antonioguerrero.ioc.fithub.menu.missatges;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import antonioguerrero.ioc.fithub.objectes.Missatge;

/**
 * Classe encarregada de gestionar l'enviament i recepció de missatges amb el servidor.
 *
 * @author Antonio Guerrero
 * @version 1.0
 */
/*
public class GestorMissatges {


    /**
     * Mètode per enviar un missatge al servidor.
     *
     * @param remitent  El remitent del missatge.
     * @param contingut El contingut del missatge.
     * @param data      La data del missatge.
     * @param hora      L'hora del missatge.
     * @param listener  El listener per gestionar la resposta del servidor.

    public static void enviarMissatge(String remitent, String contingut, String data, String hora, GestorRespostaServidor listener) {
        new TascaEnviarMissatge(remitent, contingut, data, hora, listener).execute();
    }

    /**
     * Mètode per rebre un missatge del servidor.
     *
     * @param listener El listener per gestionar la resposta del servidor.

    public static void rebreMissatge(GestorRespostaServidor listener) {
        new TascaRebreMissatge(listener).execute();
    }

    /**
     * Interface per gestionar les respostes del servidor.

    public interface GestorRespostaServidor  {
        /**
         * Mètode cridat quan s'ha rebut una resposta del servidor.
         *
         * @param resposta La resposta rebuda del servidor.

        void respostaServidor(String resposta);
    }
*/
    /*
    private static class TascaEnviarMissatge extends AsyncTask<Void, Void, String> {
        private String remitent;
        private String contingut;
        private String data;
        private String hora;
        private GestorRespostaServidor listener;

        public TascaEnviarMissatge(String remitent, String contingut, String data, String hora, GestorRespostaServidor listener) {
            this.remitent = remitent;
            this.contingut = contingut;
            this.data = data;
            this.hora = hora;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
              return "";
        }

        @Override
        protected void onPostExecute(String resposta) {
            if (listener != null) {
                listener.respostaServidor(resposta);
            }
        }
    }

    private static class TascaRebreMissatge extends AsyncTask<Void, Void, String> {
        private GestorRespostaServidor listener;

        public TascaRebreMissatge(GestorRespostaServidor listener) {
            this.listener = listener;
        }

        @Override
        protected String doInBackground(Void... voids) {
            // PENDENTE implementar la lògica per rebre un missatge del servidor
            return "";
        }

        @Override
        protected void onPostExecute(String resposta) {
            if (listener != null) {
                listener.respostaServidor(resposta);
            }
        }
    }
    public static List<Missatge> obtenirMissatgesServidor() {
        // Crear una llista per emmagatzemar els missatges
        List<Missatge> missatges = new ArrayList<>();

        // Missatges de prova
        Missatge missatge1 = new Missatge("2022-01-01", "12:00", "Antonio", "Hola, com estàs?");
        Missatge missatge2 = new Missatge("2022-01-01", "12:01", "Usuari", "Hola Antonio, estic bé!");

        // Afegir els missatges a la llista
        missatges.add(missatge1);
        missatges.add(missatge2);

        // Tornar la llista de missatges
        return missatges;
    }


*/