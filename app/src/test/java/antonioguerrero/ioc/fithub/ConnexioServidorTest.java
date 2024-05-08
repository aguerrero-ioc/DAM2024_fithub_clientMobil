package antonioguerrero.ioc.fithub;

import org.junit.Test;
import static org.junit.Assert.*;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Proves unitàries per verificar el funcionament de la classe ConnexioServidor.
 * Simula totes les peticions al servidor necessàries per al funcionament de l'aplicació.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class ConnexioServidorTest {

    /**
     * Verifica si una petició amb strings retorna un objecte String.
     * Simula una petició de Logout.
     */
    @Test
    public void PeticioAmbStringsRetornaString() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                resposta[0] = true; // Simular "true" com a primer element de la resposta
                resposta[1] = null; // Simular null com a segon element de la resposta
                return resposta;
            }
        };

        // Act
        Object[] resposta = connexio.enviarPeticioString("operacio", "dada1", "dada2", "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertTrue((Boolean) resposta[0]); // Verificar que el primer element sigui un booleà "true" o "false"
        assertNull(resposta[1]); // Verificar que el segon element sigui null
    }

    /**
     * Verifica si una petició amb strings retorna un objecte HashMap.
     * Simula una petició de consulta d'un objecte.
     */
    @Test
    public void PeticioAmbStringsRetornaObjecteHashMap() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                resposta[0] = "nomObjecte"; // Simular "nomObjecte" com a primer element de la resposta
                resposta[1] = new HashMap<String, String>(); // Simular un HashMap com a segon element de la resposta
                return resposta;
            }
        };

        // Act
        Object[] resposta = connexio.enviarPeticioString("operacio", "nomObjecte", "dada", "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("nomObjecte", resposta[0]); // Verificar que el primer element sigui "nomObjecte"
        assertNotNull(resposta[1]); // Verificar que el segon element no sigui null
        assertTrue(resposta[1] instanceof HashMap); // Verificar que el segon element sigui un HashMap
    }

    /**
     * Verifica si una petició amb strings retorna una llista d'objectes HashMap.
     * Simula una petició de Consulta de tots els objectes d'un tipus.
     */
    @Test
    public void PeticioAmbStringsRetornaLlistaHashmap() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String dada1, String dada2, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                resposta[0] = "nomLlista"; // Simular "nomLlista" com a primer element de la resposta
                resposta[1] = new ArrayList<HashMap<String, String>>(); // Simular una llista de HashMaps com a segon element de la resposta
                return resposta;
            }
        };
        // Act
        Object[] resposta = connexio.enviarPeticioString("operacio", "dada1", null, "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("nomLlista", resposta[0]); // Verificar que el primer element sigui "nomLlista"
        assertNotNull(resposta[1]); // Verificar que el segon element no sigui null
        assertTrue(resposta[1] instanceof List); // Verificar que el segon element sigui una llista
    }

    /**
     * Verifica si una petició amb HashMap retorna "true".
     * Simula una petició de Registre.
     */
    @Test
    public void PeticioAmbHashMapSenseIDRetornaTrue() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                // Simular resposta exitosa
                resposta[0] = "true"; // Indica èxit
                resposta[1] = null;  // Sense missatge d'error
                return resposta;
            }
        };
        // Act
        Object[] resposta = connexio.enviarPeticioHashMap("operacio", "nomObjecte", new HashMap<>(), null);

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("true", resposta[0]); // Verificar que el primer element sigui "true"
        assertNull(resposta[1]); // Verificar que el segon element sigui null (sense missatge d'error)
    }

    /**
     * Verifica si una petició amb HashMap retorna "true".
     * Simula una petició de Creació d'un objecte
     */
    @Test
    public void PeticioAmbHashMapAmbIDRetornaTrue() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                // Simular resposta exitosa
                resposta[0] = "true"; // Indica èxit
                resposta[1] = null;  // Sense missatge d'error
                return resposta;
            }
        };
        // Act
        Object[] resposta = connexio.enviarPeticioHashMap("operacio", "nomObjecte", new HashMap<>(), "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("true", resposta[0]); // Verificar que el primer element sigui "true"
        assertNull(resposta[1]); // Verificar que el segon element sigui null (sense missatge d'error)
    }

    /**
     * Verifica si una petició amb HashMap retorna "false" i un missatge d'error.
     * Simula la resposta negativa a la petició de creació d'un objecte.
     */
    @Test
    public void PeticioAmbHashMapRetornaFalse() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
                // Simulació de resposta fallida del servidor
                Object[] resposta = new Object[2];
                resposta[0] = "false"; // Indica fallada
                resposta[1] = "Missatge d'error"; // Missatge d'error
                return resposta;
            }

        };
        // Act
        Object[] resposta = connexio.enviarPeticioHashMap("operacio", "nomObjecte", new HashMap<>(), "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("false", resposta[0]); // Verificar que el primer element sigui "false" per indicar una resposta fallida
        assertEquals("Missatge d'error", resposta[1]); // Verificar que el segon element sigui el missatge d'error esperat
    }

    /**
     * Verifica si una petició amb HashMap retorna un objecte HashMap.
     * Simula una petició de Modificació (d'usuari o contrasenya)
     */
    @Test
    public void PeticioAmbHashMapRetornaObjecteHashMap() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioHashMap(String operacio, String nomObjecte, HashMap<String, String> objecteMapa, String idSessio) {
                // Simulació de resposta del servidor
                Object[] resposta = new Object[2];
                // Simular resposta exitosa
                resposta[0] = "nomObjecte"; // Indica èxit amb el nom de l'objecte
                resposta[1] = new HashMap<String, String>();  // Simulació de l'objecte HashMap
                return resposta;
            }
        };
        // Act
        Object[] resposta = connexio.enviarPeticioHashMap("operacio", "nomObjecte", new HashMap<>(), "idSessio");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals("nomObjecte", resposta[0]); // Verificar que el primer element sigui "nomObjecte"
        assertNotNull(resposta[1]); // Verificar que el segon element no sigui nul·la
        assertTrue(resposta[1] instanceof HashMap); // Verificar que el segon element sigui un HashMap
    }
}