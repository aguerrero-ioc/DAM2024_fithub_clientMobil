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
public class TestNavegacioClient {

    // Credencials de l'usuari client
    private static final String CLIENT_EMAIL = "client@fithub.es";
    private static final String CLIENT_CONTRASENYA = "clientpass";

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

    /**
     * Prova de navegació a les instal·lacions des del panell lateral.
     */
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

    /**
     * Prova de navegació a les activitats des del panell lateral.
     */
    @Test
    public void testNavegacioServeis() {
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
        onView(withId(R.id.nav_serveis)).perform(click());

        // Verificar la presència d'algun element a l'activitat ServeisActivity
        onView(withId(R.id.tvData)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació a la pantalla de reserves de l'usuari.
     */
    @Test
    public void testNavegacioReservesPerDia() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció de reserva per dia en el panell lateral
        onView(withId(R.id.nav_reserves_per_dia)).perform(click());

        // Verificar la presència d'algun element a l'activitat ReservesPerDiaActivity
        onView(withId(R.id.rvClassesDirigides)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació a les reserves de l'usuari des del panell lateral.
     */
    @Test
    public void testNavegacioReservesPerNom() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció de reserva per activitat en el panell lateral
        onView(withId(R.id.nav_reserves_per_activitat)).perform(click());

        // Verificar la presència d'algun element a l'activitat ReservesPerNomActivity
        onView(withId(R.id.spinnerActivitats)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació a les reserves de l'usuari des del panell lateral.
     */
    @Test
    public void testNavegacioReservesUsuari() {
        // Iniciar l'activitat LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escriure credencials als camps de text
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Fer clic al botó d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Obrir el panell lateral
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Fer clic a l'opció de les meves reserves en el panell lateral
        onView(withId(R.id.nav_reserves_usuari)).perform(click());

        // Verificar la presència d'algun element a l'activitat ReservesActivity
        onView(withId(R.id.rvReserves)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }



    /**
     * Prova de navegació a la pantalla de perfil de l'usuari.
     */
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