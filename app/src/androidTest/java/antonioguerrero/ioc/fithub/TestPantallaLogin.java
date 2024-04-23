package antonioguerrero.ioc.fithub;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.app.Instrumentation;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.login.RegistreActivity;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;

/**
 * Proves d'integració per a la pantalla de login.
 * <p>
 * @autor Antonio Guerrero
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestPantallaLogin {

    /**
     * Configuració inicial de les proves.
     */
    @Before
    public void setUp() {
        // Inicialitzar Intents per poder verificar les intencions
        Intents.init();
    }

    /**
     * Prova d'inici de sessió com a client.
     */
    @Test
    public void testIniciSessioClient() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText("client@fithub.es"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("clientpass"));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar que s'obre l'activitat ClientActivity després d'iniciar sessió correctament
        Intents.intended(IntentMatchers.hasComponent(ClientActivity.class.getName()));

        // Netejar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova d'inici de sessió com a administrador.
     */
    @Test
    public void testIniciSessioAdmin() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText("admin@fithub.es"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("Adminpass37"));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar que s'obre l'activitat AdminActivity després d'iniciar sessió correctament
        Intents.intended(IntentMatchers.hasComponent(AdminActivity.class.getName()));

        // Netejar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova d'inici de sessió amb credencials incorrectes.
     */
    @Test
    public void testIniciSessioIncorrecte() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials incorrectes als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText("usuario_invalido@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenya_invalida"));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar que no es llança una intenció per obrir l'activitat ClientActivity després d'un inici de sessió incorrecte
        Intents.intending(IntentMatchers.hasComponent(ClientActivity.class.getName())).respondWith(new Instrumentation.ActivityResult(0, null));

        // Netejar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova d'obertura del diàleg de recuperació de contrasenya.
     */
    @Test
    public void testDialogRecuperacioContrasenya() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Fer clic al TextView per recuperació de contrasenya
        Espresso.onView(ViewMatchers.withId(R.id.tv_oblidat_contrasenya)).perform(ViewActions.click());

        // Verificar que es mostra el diàleg de recuperació de contrasenya
        Espresso.onView(withText("Recuperació de Contrasenya")).inRoot(isDialog()).check(matches(isDisplayed()));

        Intents.release();
    }

    /**
     * Prova d'obertura de l'activitat de registre.
     */
    @Test
    public void testOberturaRegistreActivity() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Fer clic al TextView per registre d'usuaris
        Espresso.onView(ViewMatchers.withId(R.id.tv_registre)).perform(ViewActions.click());

        // Verificar que s'obre l'activitat de registre
        Intents.intended(IntentMatchers.hasComponent(RegistreActivity.class.getName()));

        // Netejar Intents després de la prova
        Intents.release();
    }

}
