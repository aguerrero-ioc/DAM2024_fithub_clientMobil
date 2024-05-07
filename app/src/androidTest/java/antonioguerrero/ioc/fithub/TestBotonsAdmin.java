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
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;

/**
 * Proves d'integració per a la navegació de l'aplicació administrador a través dels botons de la pantalla principal.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestBotonsAdmin {

    // Credencials de l'usuari administrador
    private static final String ADMIN_EMAIL = "admin@fithub.es";
    private static final String ADMIN_CONTRASENYA = "Adminpass40";

    /**
     * Configuració inicial de les proves.
     */
    @Before
    public void setUp() {
        // Inicialitzar Intents per poder verificar les intencions
        Intents.init();
    }

    /**
     * Prova de navegació al perfil de l'administror des del botó de gestió d'usuaris.
     */
    @Test
    public void testBotoGestioUsuaris() {
        // Inicia l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriu les credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(ADMIN_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(ADMIN_CONTRASENYA));

        // Fes clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configura l'activitat AdminActivity com a escenari
        ActivityScenario<AdminActivity> adminScenario = ActivityScenario.launch(AdminActivity.class);

        // Fes clic al botó de gestió d'usuaris
        onView(withId(R.id.boto_gestio1)).perform(click());

        // Verifica la presència d'algun element a l'activitat GestioUsuarisActivity
        onView(withId(R.id.rvUsuaris)).check(matches(isDisplayed()));

        // Allibera Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació al perfil de l'administror des del botó de gestió d'activitats.
     */
    @Test
    public void testBotoGestioActivitats() {
        // Inicia l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriu les credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(ADMIN_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(ADMIN_CONTRASENYA));

        // Fes clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configura l'activitat AdminActivity com a escenari
        ActivityScenario<AdminActivity> adminScenario = ActivityScenario.launch(AdminActivity.class);

        // Fes clic al botó de gestió de actividades
        onView(withId(R.id.boto_gestio2)).perform(click());

        // Verificar la presencia d'algun element a l'activitat GestioActivitatsActivity
        onView(withId(R.id.rvActivitats)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació al perfil de l'administror des del botó de gestió d'instal·lacions.
     */
    @Test
    public void testBotoGestioInstallacions() {
        // Inicia l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriu les credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(ADMIN_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(ADMIN_CONTRASENYA));

        // Fes clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configura l'activitat AdminActivity com a escenari
        ActivityScenario<AdminActivity> adminScenario = ActivityScenario.launch(AdminActivity.class);

        // Fes clic al botó de gestió d'instal·lacions
        onView(withId(R.id.boto_gestio3)).perform(click());

        // Verificar la presencia d'algun element a l'activitat GestioInstallacionsActivity
        onView(withId(R.id.rvInstallacions)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació al perfil de l'administror des del botó de gestió de serveis.
     */
    @Test
    public void testBotoGestioServeis() {
        // Inicia l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriu les credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(ADMIN_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(ADMIN_CONTRASENYA));

        // Fes clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configurar la activitat AdminActivity com a escenari
        ActivityScenario<AdminActivity> adminScenario = ActivityScenario.launch(AdminActivity.class);

        // Fes clic al botó de gestió de serveis
        onView(withId(R.id.boto_gestio4)).perform(click());

        // Verificar la presencia d'algún element a l'activitat GestioServeisActivity
        onView(withId(R.id.rvServeis)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }
}