package antonioguerrero.ioc.fithub;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import antonioguerrero.ioc.fithub.objectes.Usuari;
import antonioguerrero.ioc.fithub.peticions.BasePeticions;
import antonioguerrero.ioc.fithub.peticions.usuaris.CanviarContrasenya;

public class CanviarContrasenyaTest {

    @Mock
    private BasePeticions.respostaServidorListener listener;

    @Mock
    private Usuari usuari;

    private CanviarContrasenya canviarContrasenya;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        canviarContrasenya = new CanviarContrasenya(listener, usuari);
    }

    @Test
    public void testCanviarContrasenya() {
        canviarContrasenya.canviarContrasenya();
        verify(usuari, times(1)).getNomUsuari();
        verify(usuari, times(1)).getCognomsUsuari();
        verify(usuari, times(1)).getDataNaixement();
        verify(usuari, times(1)).getAdreca();
        verify(usuari, times(1)).getTelefon();
        verify(usuari, times(1)).getCorreuUsuari();
        verify(usuari, times(1)).getPassUsuari();
    }
}