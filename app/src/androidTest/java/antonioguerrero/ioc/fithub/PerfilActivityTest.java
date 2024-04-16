package antonioguerrero.ioc.fithub;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.CanviarContrasenya;
import antonioguerrero.ioc.fithub.peticions.usuaris.ConsultarUsuari;
import antonioguerrero.ioc.fithub.peticions.usuaris.ModificarUsuari;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ConsultarUsuari.class, ModificarUsuari.class, CanviarContrasenya.class})
public class PerfilActivityTest {

    private ConsultarUsuari consultarUsuari;
    private ModificarUsuari modificarUsuari;
    private CanviarContrasenya canviarContrasenya;

    @Before
    public void setUp() {
        consultarUsuari = mock(ConsultarUsuari.class);
        modificarUsuari = mock(ModificarUsuari.class);
        canviarContrasenya = mock(CanviarContrasenya.class);
    }

    @Test
    public void testConsultarUsuari() {
        Usuari usuariMock = new Usuari(/* parámetros de ejemplo */);
        when(consultarUsuari.respostaServidorHashmap(any())).thenReturn(/* respuesta simulada */);

        // Simula la llamada a la función consultarUsuari
        consultarUsuari.consultarUsuari();

        // Verifica que se haya llamado al método respuestaServidorHashmap con los parámetros correctos
        verify(consultarUsuari).respostaServidorHashmap(any());

        // Verifica que se haya llamado al método onUsuariObtingut con el usuario simulado
        verify(consultarUsuari).onUsuariObtingut(usuariMock);
    }

    @Test
    public void testModificarUsuari() {
        Usuari usuariMock = new Usuari(/* parámetros de ejemplo */);
        when(modificarUsuari.respostaServidorHashmap(any())).thenReturn(/* respuesta simulada */);

        // Simula la llamada a la función modificarUsuari
        modificarUsuari.modificarUsuari();

        // Verifica que se haya llamado al método respuestaServidorHashmap con los parámetros correctos
        verify(modificarUsuari).respostaServidorHashmap(any());

        // Verifica que se haya llamado al método onUsuariModificat con el usuario simulado
        verify(modificarUsuari).onUsuariModificat(usuariMock);
    }

    @Test
    public void testCanviarContrasenya() {
        Usuari usuariMock = new Usuari(/* parámetros de ejemplo */);
        when(canviarContrasenya.respostaServidorHashmap(any())).thenReturn(/* respuesta simulada */);

        // Simula la llamada a la función canviarContrasenya
        canviarContrasenya.canviarContrasenya();

        // Verifica que se haya llamado al método respostaServidorHashmap con los parámetros correctos
        verify(canviarContrasenya).respostaServidorHashmap(any());
    }
}
