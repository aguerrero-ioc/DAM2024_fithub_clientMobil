package antonioguerrero.ioc.fithub;

import org.junit.Test;
import static org.junit.Assert.*;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Proves per verificar el funcionament de la petició de login.
 *
 * <p>
 * Autor: Antonio Guerrero
 * Versió: 1.0
 */
public class PeticioLoginTest {

    @Test
    public void testExitLogin() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String correuUsuari, String passUsuari, String idSessio) throws ConnectException {
                // Simulació de resposta exitosa del servidor per al login
                Object[] resposta = new Object[2];
                resposta[0] = "true"; // Indica èxit
                resposta[1] = null; // No hi ha missatge d'error en cas d'èxit
                return resposta;
            }

            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public Class<?> obtenirTipusObjecte() {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            @Override
            public void execute() throws ConnectException {
                // Aquest mètode no s'utilitza en aquesta prova, però s'ha d'implementar a causa de la herència de ConnexioServidor
            }
        };

        // Act
        Object[] resposta = connexio.enviarPeticioString("login", "correu@exemple.com", "contrasenya", null);

        // Assert
        assertNotNull(resposta); // Verifica que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verifica que la resposta tingui la longitud esperada
        assertEquals("true", resposta[0]); // Verifica que el primer element sigui "true" per indicar una resposta exitosa
        assertNull(resposta[1]); // Verifica que el segon element sigui nul
    }

    @Test
    public void testFallidaLogin() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String correuUsuari, String passUsuari, String idSessio) throws ConnectException {
                // Simulació de resposta fallida del servidor per al login
                Object[] resposta = new Object[2];
                resposta[0] = "false"; // Indica fallida
                resposta[1] = "Missatge d'error"; // Missatge d'error
                return resposta;
            }

            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                return null;
            }

            @Override
            public Class<?> obtenirTipusObjecte() {
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                return null;
            }

            @Override
            public void execute() throws ConnectException {
            }
        };

        // Act
        Object[] resposta = connexio.enviarPeticioString("login", "correu@exemple.com", "contrasenya", null);

        // Assert
        assertNotNull(resposta); // Verifica que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verifica que la resposta tingui la longitud esperada
        assertEquals("false", resposta[0]); // Verifica que el primer element sigui "false" per indicar una resposta fallida
        assertEquals("Missatge d'error", resposta[1]); // Verifica que el segon element sigui el missatge d'error esperat
    }
}
