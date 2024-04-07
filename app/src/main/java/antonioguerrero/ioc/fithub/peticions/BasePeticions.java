package antonioguerrero.ioc.fithub.peticions;

/**
 * Classe abstracta base per a les peticions al servidor.
 * Conté mètodes per establir la connexió amb el servidor i processar les respostes.
 */
public abstract class BasePeticions {

    protected respostaServidorListener listener;

    /**
     * Constructor de la classe BasePeticions.
     * @param listener L'objecte que escoltarà les respostes del servidor.
     */
    public BasePeticions(respostaServidorListener listener) {
        this.listener = listener;
    }

    /**
     * Mètode abstracte per a executar la petició.
     */
    public abstract void execute();

    /**
     * Mètode abstracte per a obtenir el tipus de l'objecte.
     * @return La classe de l'objecte.
     */
    public abstract Class<?> obtenirTipusObjecte();


    /**
     * Mètode abstracte per a gestionar la resposta del servidor.
     * @param resposta La resposta rebuda del servidor.
     */
    public abstract void respostaServidor(Object resposta);

    /**
     * Interfície per a escoltar les respostes del servidor.
     */
    public interface respostaServidorListener {
        /**
         * Mètode cridat quan es rep una resposta del servidor.
         * @param resposta La resposta rebuda del servidor.
         */
        void respostaServidor(Object resposta);
    }
}
