package antonioguerrero.ioc.fithub;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import androidx.recyclerview.widget.RecyclerView;
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
 * Proves per verificar la pantalla d'activitats.
 * <p>
 * Autor: Antonio Guerrero
 * Versió: 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestPantallaActivitats {

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
     * Prova per verificar que el RecyclerView d'activitats té almenys un element.
     */
    @Test
    public void testContenidoRecyclerView() {
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

        // Verificar que el RecyclerView té almenys un element
        onView(withId(R.id.rvActivitats)).check(matches(isDisplayed()));
        onView(withId(R.id.rvActivitats)).check((view, noViewFoundException) -> {
            if (view instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) view;
                assertThat(recyclerView.getAdapter().getItemCount(), greaterThan(0)); // Verificar que hi ha almenys un element
            } else {
                throw new AssertionError("La vista no és un RecyclerView");
            }
        });

        // Alliberar Intents després de la prova
        Intents.release();
    }
}