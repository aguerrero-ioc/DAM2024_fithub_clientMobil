package antonioguerrero.ioc.fithub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

/**
 * Proves per verificar el funcionament de la petició de logout.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
public class PeticioLogoutTest {

    /**
     * Prova de petició de tancament de sessió.
     * <p>
     * @throws ConnectException si hi ha un error de connexió
     */
    @Test
    public void testExitLogout() throws ConnectException {
        // Arrange
        ConnexioServidor connexio = new ConnexioServidor() {
            @Override
            public Object[] enviarPeticioString(String operacio, String IDusuari, String param2, String sessioID) throws ConnectException {
                // Simulació de resposta exitosa del servidor per al logout
                Object[] resposta = new Object[2];
                resposta[0] = "true"; // Indica èxit
                resposta[1] = null; // No hi ha missatge d'error en cas d'èxit
                return resposta;
            }
        };
        // Act
        Object[] resposta = connexio.enviarPeticioString("logout", "ID_usuario_ejemplo", "null", "sesion_ID_ejemplo");

        // Assert
        assertNotNull(resposta); // Verificar que la resposta no sigui nul·la
        assertEquals(2, resposta.length); // Verificar que la resposta tingui la longitud esperada
        assertEquals("true", resposta[0]); // Verificar que el primer element sigui "true" per indicar una resposta exitosa
        assertNull(resposta[1]); // Verificar que el segon element sigui nul, ja que no hi ha missatge d'error en cas d'èxit
    }
}