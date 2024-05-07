package antonioguerrero.ioc.fithub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.net.ConnectException;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Proves per verificar el funcionament de la petició de login.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioLoginTest {

    /**
     * Prova de petició d'entrada d'usuari.
     * <p>
     * @throws ConnectException si hi ha un error de connexió
     */
    @Test
    public void testExitLogin() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String correuUsuari, String passUsuari, String idSessio) {
                // Simulació de resposta exitosa del servidor per al login
                Object[] resposta = new Object[2];
                resposta[0] = "true"; // Indica èxit
                resposta[1] = null; // No hi ha missatge d'error en cas d'èxit
                return resposta;
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

    /**
     * Prova de petició d'entrada d'usuari fallida.
     * <p>
     * @throws ConnectException si hi ha un error de connexió
     */
    @Test
    public void testFallidaLogin() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String correuUsuari, String passUsuari, String idSessio) {
                // Simulació de resposta fallida del servidor per al login
                Object[] resposta = new Object[2];
                resposta[0] = "false"; // Indica fallida
                resposta[1] = "Missatge d'error"; // Missatge d'error
                return resposta;
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