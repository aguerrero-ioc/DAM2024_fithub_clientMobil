package antonioguerrero.ioc.fithub;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockingDetails;
import org.mockito.configuration.DefaultMockitoConfiguration;
import org.mockito.internal.util.MockUtil;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

@RunWith(MockitoJUnitRunner.class)
@MockitoSettings(mockito = DefaultMockitoConfiguration.class)
public class ConnexioServidorTest {

    @Mock
    private ConnexioServidor.respostaServidorListener mockListener;

    private ConnexioServidor connexioServidor;

    @Before
    public void setUp() {
        connexioServidor = new ConnexioServidor(mockListener) {
            @Override
            public List<HashMap<String, String>> respostaServidor(Object resposta) {
                // Mock implementation
                return null;
            }

            @Override
            public Class<?> obtenirTipusObjecte() {
                // Mock implementation
                return null;
            }

            @Override
            public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
                // Mock implementation
                return null;
            }

            @Override
            public void execute() {
                // Mock implementation
            }
        };
    }

    @Test
    public void testEnviarPeticioString() throws Exception {
        // Defineix les dades per a la prova
        String operacio = "mockOperacio";
        String dada1 = "mockDada1";
        String dada2 = "mockDada2";
        String idSessio = "mockIdSessio";

        // Ignore los llamados a android.util.Log.d()
        try (MockedStatic<Log> mockedStatic = mockStatic(android.util.Log.class)) {
            mockedStatic.when(() -> android.util.Log.d(anyString(), anyString())).thenAnswer((Answer<Void>) invocation -> null);

            // Crida la funció a provar
            connexioServidor.enviarPeticioString(operacio, dada1, dada2, idSessio);

            // Verifica si s'ha cridat el mètode respostaServidor en el listener
            verify(mockListener).respostaServidor(any());
        }
    }

    @Test
    public void testEnviarPeticioHashMap() throws Exception {
        // Defineix les dades per a la prova
        String operacio = "mockOperacio";
        String nomObjecte = "mockNomObjecte";
        HashMap<String, String> objecteMapa = new HashMap<>();
        String idSessio = "mockIdSessio";

        try (MockedStatic<android.util.Log> mockedStatic = mockStatic(android.util.Log.class)) {
            mockedStatic.when(() -> android.util.Log.d(anyString(), anyString())).thenAnswer((Answer<Void>) invocation -> null);

            // Crida la funció a provar
            connexioServidor.enviarPeticioHashMap(operacio, nomObjecte, objecteMapa, idSessio);

            // Verifica si s'ha cridat el mètode respostaServidor en el listener
            verify(mockListener).respostaServidor(any());
        }
    }
}
