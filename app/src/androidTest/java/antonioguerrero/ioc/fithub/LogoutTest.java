package antonioguerrero.ioc.fithub;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

/**
 * Aquesta classe conté les proves d'acceptació per al procés de logout (tancament de sessió).
 * Autor: Antonio Guerrero
 */
@LargeTest
public class LogoutTest {

    @Rule
    public ActivityScenarioRule<ClientActivity> userActivityRule =
            new ActivityScenarioRule<>(ClientActivity.class);

    @Rule
    public ActivityScenarioRule<AdminActivity> adminActivityRule =
            new ActivityScenarioRule<>(AdminActivity.class);

    /**
     * Prova de logout (tancament de sessió) per a un client.
     * Autor: Antonio Guerrero
     */
    @Test
    public void logoutClient() {
        // Fer clic al botó de perfil
        Espresso.onView(ViewMatchers.withId(R.id.boto_perfil)).perform(ViewActions.click());

        // Fer clic a l'opció de tancar sessió
        Espresso.onView(ViewMatchers.withId(R.id.opcio_logout)).perform(ViewActions.click());

        // Verificar si es redirigeix a la pantalla d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.tv_login_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Prova de logout (tancament de sessió) per a un administrador.
     * Autor: Antonio Guerrero
     */
    @Test
    public void logoutAdmin() {
        // Fer clic al botó de perfil
        Espresso.onView(ViewMatchers.withId(R.id.boto_perfil)).perform(ViewActions.click());

        // Fer clic a l'opció de tancar sessió
        Espresso.onView(ViewMatchers.withId(R.id.opcio_logout)).perform(ViewActions.click());

        // Verificar si es redirigeix a la pantalla d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.tv_login_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
