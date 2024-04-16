package antonioguerrero.ioc.fithub;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import antonioguerrero.ioc.fithub.menu.main.ClientActivity;

@RunWith(AndroidJUnit4.class)
public class ClientActivityTest {

    @Before
    public void setUp() {
        // Iniciar la actividad ClientActivity
        ActivityScenario<ClientActivity> scenario = ActivityScenario.launch(ClientActivity.class);
        Intents.init();
    }

    @Test
    public void testNavegacionPanelLateral() {
        // Iniciar la actividad ClientActivity
        ActivityScenario<ClientActivity> scenario = ActivityScenario.launch(ClientActivity.class);

        // Abrir el panel lateral deslizándolo hacia afuera
        onView(withId(R.id.boto_acces_panell)).perform(click());

        // Hacer clic en la opción del perfil en el panel lateral
        onView(withId(R.id.nav_perfil_usuari)).perform(click());

        // Verificar la presencia de algún elemento en la actividad PerfilActivity
        onView(withId(R.id.tv_titol_formulari)).check(matches(isDisplayed()));
    }


}