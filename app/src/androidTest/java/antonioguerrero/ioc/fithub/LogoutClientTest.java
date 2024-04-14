package antonioguerrero.ioc.fithub;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import antonioguerrero.ioc.fithub.menu.main.ClientActivity;

/**
 * Test que verifica el procés de tancament de sessió per al client.
 * @author Antonio Guerrero
 */
@LargeTest
public class LogoutClientTest {

    @Rule
    public ActivityScenarioRule<ClientActivity> activityScenarioRule =
            new ActivityScenarioRule<>(ClientActivity.class);

    /**
     * Prova el procés de tancament de sessió per al client.
     * Esperem que en fer clic al botó de perfil i després a l'opció de tancar sessió,
     * es redirigeixi a la pantalla d'inici de sessió.
     */
    @Test
    public void logoutClient() {
        // Fer clic al botó de perfil
        Espresso.onView(withId(R.id.boto_perfil)).perform(ViewActions.click());

        // Fer clic a l'opció de tancar sessió
        Espresso.onView(withId(R.id.opcio_logout)).perform(ViewActions.click());

        // Verificar si es redirigeix a la pantalla d'inici de sessió
        Espresso.onView(ViewMatchers.withId(R.id.tv_login_title))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
