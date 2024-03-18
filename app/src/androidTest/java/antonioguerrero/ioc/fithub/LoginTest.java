package antonioguerrero.ioc.fithub;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

/**
 * Aquesta classe conté les proves d'acceptació per al procés de login (inici de sessió).
 * Autor: Antonio Guerrero
 */
@LargeTest
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Prova de login (inici de sessió) reeixit per a un client.
     * Autor: Antonio Guerrero
     */
    @Test
    public void loginSuccessClient() {
        // Introduir les dades de login correctes i fer clic al botó d'iniciar sessió
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("usuari_test"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenya_test"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar si s'ha iniciat la nova activitat de client
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_reserves)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de login (inici de sessió) reeixit per a un administrador.
     * Autor: Antonio Guerrero
     */
    @Test
    public void loginSuccessAdmin() {
        // Introduir les dades de login correctes i fer clic al botó d'iniciar sessió
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("usuari_test"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenya_test"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar si s'ha iniciat la nova activitat d'administrador
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_gestions)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de login (inici de sessió) fallida.
     * Autor: Antonio Guerrero
     */
    @Test
    public void loginFailure() {
        // Introduir les dades de login incorrectes i fer clic al botó d'iniciar sessió
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("usuari_incorrecte"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenya_incorrecte"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verificar si es mostra el missatge d'error de login incorrecte
        Espresso.onView(ViewMatchers.withText("Usuari o contrasenya incorrectes")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de gestió d'error en la sol·licitud de login (inici de sessió).
     * Autor: Antonio Guerrero
     */
    @Test
    public void testRequestError() {
        // Introduir les dades de login correctes i fer clic al botó d'iniciar sessió
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("usuari_test"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenya_test"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Esperar un segon perquè es mostri el toast
        SystemClock.sleep(1000);

        // Verificar si es mostra el missatge d'error de sol·licitud
        Espresso.onView(ViewMatchers.withText("Error en la sol·licitud")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
}
