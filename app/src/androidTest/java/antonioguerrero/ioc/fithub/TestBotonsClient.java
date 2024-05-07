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
import antonioguerrero.ioc.fithub.menu.main.ClientActivity;

/**
 * Proves d'integració per a la navegació de l'aplicació client a través dels botons de la pantalla principal.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestBotonsClient {

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
     * Prova de navegació a la pantalla de l'usuari des del botó de reservar classes per dia.
     */
    @Test
    public void testNavegacioReservaPerDia() {
        // Iniciar la actividad LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escribir credenciales en los campos de texto
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configurar la actividad ClientActivity como escenario
        ActivityScenario<ClientActivity> clientScenario = ActivityScenario.launch(ClientActivity.class);

        // Hacer clic en el botón de reserva por día
        onView(withId(R.id.boto_reserva1)).perform(click());

        // Verificar la presencia de algún elemento en la actividad ClassesPerDiaActivity
        onView(withId(R.id.rvClassesDirigides)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació a la pantalla de l'usuari des del botó de reservar classes per nom.
     */
    @Test
    public void testNavegacioClassesPerNom() {
        // Iniciar la actividad LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escribir credenciales en los campos de texto
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configurar la actividad ClientActivity como escenario
        ActivityScenario<ClientActivity> clientScenario = ActivityScenario.launch(ClientActivity.class);

        // Hacer clic en el botón de reserva por nombre
        onView(withId(R.id.boto_reserva2)).perform(click());

        // Verificar la presencia de algún elemento en la actividad ClassesPerNomActivity
        onView(withId(R.id.spinnerActivitats)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }

    /**
     * Prova de navegació  a la pantalla de l'usuari des del botó de veure les meves reserves.
     */
    @Test
    public void testNavegacioLesMevesReserves() {
        // Iniciar la actividad LoginActivity
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);

        // Escribir credenciales en los campos de texto
        Espresso.onView(ViewMatchers.withId(R.id.et_correu_usuari)).perform(ViewActions.typeText(CLIENT_EMAIL));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText(CLIENT_CONTRASENYA));

        // Hacer clic en el botón de inicio de sesión
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Configurar la actividad ClientActivity como escenario
        ActivityScenario<ClientActivity> clientScenario = ActivityScenario.launch(ClientActivity.class);

        // Hacer clic en el botón de ver mis reservas
        onView(withId(R.id.boto_reserva3)).perform(click());

        // Verificar la presencia de algún elemento en la actividad ReservesActivity
        onView(withId(R.id.rvReserves)).check(matches(isDisplayed()));

        // Alliberar Intents després de la prova
        Intents.release();
    }
}
