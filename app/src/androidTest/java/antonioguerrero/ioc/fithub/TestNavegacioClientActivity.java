package antonioguerrero.ioc.fithub;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
 * Proves d'integració per a la navegació de l'aplicació client.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestNavegacioClientActivity {

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
     * Prova de navegació al perfil de l'usuari des del panell lateral.
     */
    @Test
    public void testNavegacioPerfil() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Abrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció del perfil en el panell lateral
        onView(withId(R.id.nav_perfil_usuari)).perform(click());

        // Verificar la presència d'algun element a l'activitat PerfilActivity
        onView(withId(R.id.tv_titol_formulari)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació a les activitats des del panell lateral.
     */
    @Test
    public void testNavegacioActivitats() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció d'activitats en el panell lateral
        onView(withId(R.id.nav_activitats)).perform(click());

        // Verificar la presència d'algun element a l'activitat ActivitatsActivity
        onView(withId(R.id.tvData)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    @Test
    public void testNavegacioInstallacions() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció d'instal·lacions en el panell lateral
        onView(withId(R.id.nav_installacions)).perform(click());

        // Verificar la presència d'algun element a l'activitat InstallacionsActivity
        onView(withId(R.id.tvData)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    @Test
    public void testNavegacioLogout() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral deslitzant-lo cap a fora
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció de tancar sessió en el panell lateral
        onView(withId(R.id.nav_tancar_sessio)).perform(click());

        // Verificar la presència d'algun element a l'activitat InstallacionsActivity
        onView(withId(R.id.tv_login_title)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }
}