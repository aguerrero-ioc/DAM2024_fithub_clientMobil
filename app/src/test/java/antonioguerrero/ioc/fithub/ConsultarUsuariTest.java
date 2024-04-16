package antonioguerrero.ioc.fithub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;

import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarUsuari;
import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.connexio.ConnexioServidor;

@RunWith(MockitoJUnitRunner.class)
public class ConsultarUsuariTest {

    @Mock
    private ConsultarUsuari.ConsultarUsuariListener listener;

    @Mock
    private ConnexioServidor connexioServidor;

    private MockConsultarUsuari consultarUsuari;

    @Before
    public void setUp() {
        consultarUsuari = new MockConsultarUsuari(listener, connexioServidor, "correo@example.com", "sessionId123");
    }

    @Test
    public void testConsultaUsuariExitosa() {
        // Simular respuesta exitosa del servidor
        Usuari usuari = new Usuari(/* datos del usuario */);
        Mockito.when(connexioServidor.consultarUsuari(Mockito.anyString(), Mockito.anyString())).thenReturn(new Object[]{ "usuari", usuari.usuari_a_hashmap() });

        // Llamar al método para consultar usuario
        consultarUsuari.consultarUsuari();

        // Verificar que el método de respuesta del listener se llama con el usuario obtenido
        Mockito.verify(listener).onUsuariObtingut(usuari);
    }

    @Test
    public void testConsultaUsuariFallida() {
        // Simular respuesta fallida del servidor
        Mockito.when(connexioServidor.consultarUsuari(Mockito.anyString(), Mockito.anyString())).thenReturn(new Object[]{ "false" });

        // Llamar al método para consultar usuario
        consultarUsuari.consultarUsuari();

        // Verificar que el método de respuesta del listener no se llama
        Mockito.verify(listener, Mockito.never()).onUsuariObtingut(Mockito.any(Usuari.class));
    }

    // Clase concreta que hereda de ConsultarUsuari para propósitos de prueba
    private class MockConsultarUsuari extends ConsultarUsuari {

        public MockConsultarUsuari(ConsultarUsuariListener listener, ConnexioServidor connexioServidor, String correuUsuari, String sessioID) {
            super(listener, connexioServidor, correuUsuari, sessioID);
        }

        @Override
        public Class<?> obtenirTipusObjecte() {
            return Object[].class;
        }

        @Override
        public List<HashMap<String, String>> respostaServidorHashmap(Object resposta) {
            return null;
        }

        @Override
        public void respostaServidor(Object[] resposta) {
            // No es necesario implementar este método en las pruebas unitarias
        }

        @Override
        protected Object doInBackground(Void... voids) {
            // No es necesario implementar este método en las pruebas unitarias
            return null;
        }
    }
}
