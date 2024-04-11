package antonioguerrero.ioc.fithub;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;

import antonioguerrero.ioc.fithub.peticions.usuaris.PeticioLogin;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Toast.class })
public class PeticioLoginTest {

    @Mock
    private Context context;

    @Mock
    private SharedPreferences sharedPreferences;

    @Mock
    private SharedPreferences.Editor editor;

    @Mock
    private Utils.LogWrapper logWrapper;

    @Mock
    private Utils utils;

    @Mock
    private Toast toast;

    private PeticioLogin peticioLogin;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock SharedPreferences
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        doNothing().when(editor).apply();

        // Mock Log
        doNothing().when(logWrapper).d(anyString(), anyString());

        // Mock Utils
        doNothing().when(utils).mostrarToast(any(Context.class), anyString());

        // Mock Toast
        PowerMockito.mockStatic(Toast.class);
        when(Toast.makeText(any(Context.class), anyString(), anyInt())).thenReturn(toast);
    }

    @Before
    public void preparar() {
        MockitoAnnotations.openMocks(this);
        peticioLogin = new PeticioLogin(context, "prova@prova.com", "contrasenya");
    }

    @Test
    public void provaPeticioLoginAmbDiferentEntrada() {
        peticioLogin = new PeticioLogin(context, "unaltre@prova.com", "unaltreContrasenya");
        peticioLogin.execute();
        // Verificar que se llama al método enviarPeticio con los argumentos correctos
        verify(peticioLogin).enviarPeticio("login", "unaltre@prova.com", "unaltreContrasenya", null, "PeticioLogin");
    }

    @Test
    public void provaPeticioLoginAmbEntradaInvalida() {
        peticioLogin = new PeticioLogin(context, "", "");
        peticioLogin.execute();
        // Verificar que se llama al método enviarPeticio con los argumentos correctos
        verify(peticioLogin).enviarPeticio("login", "", "", null, "PeticioLogin");
    }

    @Test
    public void provaRespostaServidor() {
        Object resposta = new Object();
        peticioLogin.respostaServidor(resposta);
        // Verificar que se llama al método respostaServidor con los argumentos correctos
        verify(peticioLogin).respostaServidor(resposta);
    }

    @Test
    public void provaRespostaServidorAmbRespostaValida() {
        Object[] resposta = new Object[]{"usuariActiu", new HashMap<String, String>()};
        peticioLogin.respostaServidor(resposta);
        // Verificar que se llama al método respostaServidor con los argumentos correctos
        verify(peticioLogin).respostaServidor(resposta);
    }

    @Test
    public void provaRespostaServidorAmbRespostaInvalida() {
        Object resposta = new Object();
        peticioLogin.respostaServidor(resposta);
        // Verificar que se llama al método respostaServidor con los argumentos correctos
        verify(peticioLogin).respostaServidor(resposta);
    }
}