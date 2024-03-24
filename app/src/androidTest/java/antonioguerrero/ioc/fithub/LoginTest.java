package antonioguerrero.ioc.fithub;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;


import org.junit.Rule;
import org.junit.Test;

import antonioguerrero.ioc.fithub.login.LoginActivity;

/**
 * Aquesta classe conté les proves d'acceptació per al procés de login (inici de sessió).
 * @author Antonio Guerrero
 */
@LargeTest
public class LoginTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Prova de login (inici de sessió) correcte per a un client.
     */
    @Test
    public void testLoginClientCorrecte() {
        // Introdueix les credencials de client correctes
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("client@fithub.es"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("passClient"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verifica si s'ha iniciat la nova activitat de client
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_reserves)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de login (inici de sessió) correcte per a un administrador.
     */
    @Test
    public void testLoginAdminCorrecte() {
        // Introdueix les credencials d'administrador correctes
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("administrador@fithub.es"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("passAdministrador"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verifica si s'ha iniciat la nova activitat d'administrador
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_gestions)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de login (inici de sessió) fallida.
     */
    @Test
    public void testLoginIncorrecte() {
        // Introdueix credencials incorrectes
        Espresso.onView(ViewMatchers.withId(R.id.et_nomusuari)).perform(ViewActions.typeText("clientRandom"));
        Espresso.onView(ViewMatchers.withId(R.id.et_contrasenya)).perform(ViewActions.typeText("contrasenyaIncorrecta"));
        Espresso.onView(ViewMatchers.withId(R.id.btn_login)).perform(ViewActions.click());

        // Verifica que no s'hagi iniciat l'activitat de client
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_reserves)).check(ViewAssertions.doesNotExist());

        // Verifica que no s'hagi iniciat l'activitat d'administrador
        Espresso.onView(ViewMatchers.withId(R.id.text_titol_gestions)).check(ViewAssertions.doesNotExist());

        // Verifica que l'activitat de login segueixi sent visible
        Espresso.onView(ViewMatchers.withId(R.id.tv_login_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
