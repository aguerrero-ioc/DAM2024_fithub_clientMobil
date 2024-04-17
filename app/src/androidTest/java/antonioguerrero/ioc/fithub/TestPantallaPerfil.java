package antonioguerrero.ioc.fithub;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import antonioguerrero.ioc.fithub.menu.login.LoginActivity;

/**
 * Proves per verificar la pantalla de perfil de l'usuari.
 * <p>
 * Autor: Antonio Guerrero
 * Versió: 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestPantallaPerfil {

    // Credencials de l'usuari client
    private static final String CLIENT_EMAIL = "client@fithub.es";
    private static final String CLIENT_CONTRASENYA = "clientpass1";

    /**
     * Configuració inicial de les proves.
     */
    @Before
    public void setUp() {
        // Inicialitzar Intents per poder verificar les intencions
        Intents.init();
    }

    /**
     * Prova per verificar la presència del nom d'usuari amb text a la pantalla de perfil.
     */
    @Test
    public void testPresenciaNomUsuariAmbText() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció de perfil en el panell lateral
        onView(withId(R.id.nav_perfil_usuari)).perform(click());

        // Verificar la presència del nom d'usuari
        onView(withId(R.id.et_nom_usuari)).check(matches(isDisplayed()));
        onView(withId(R.id.et_nom_usuari)).check(matches(withText(not("")))); // Verificar que el camp de text no estigui buit

        // Alliberar Intents després de la prova
        Intents.release();
    }
}
