package antonioguerrero.ioc.fithub.administrador;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import antonioguerrero.ioc.fithub.R;
import antonioguerrero.ioc.fithub.menu.login.LoginActivity;
import antonioguerrero.ioc.fithub.menu.main.AdminActivity;

/**
 * Proves d'integració per a la navegació de l'aplicació administrador a través dels botons de la pantalla principal.
 * <p>
 * @author Antonio Guerrero
 * @version 1.0
 */
@RunWith(AndroidJUnit4.class)
public class TestAccesCRUDUsuaris {

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


    @Test
    public void testAccesCreacioUsuari() {
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

        // Hacer click en el botón para crear un nuevo usuario
        Espresso.onView(ViewMatchers.withId(R.id.btnCrearNouUsuari)).perform(ViewActions.click());

        // Verificar que la actividad CrearUsuariActivity se abre correctamente
        Espresso.onView(withId(R.id.tv_seccio_dades_usuari)).check(matches(isDisplayed()));

        // Allibera Intents després de la prova
        Intents.release();
    }

    @Test
    public void testAccesModificarUsuari() {
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

        // Fer clic al botó de modificar usuari de la primera fila de la llista
        Espresso.onView(ViewMatchers.withId(R.id.rvUsuaris)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0,
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isAssignableFrom(View.class);
                            }

                            @Override
                            public String getDescription() {
                                return "Click on modify user button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                // Encontrar el botón de modificar usuario dentro del ítem de la lista
                                View btnModificar = view.findViewById(R.id.btnModificar);
                                // Realizar clic en el botón de modificar usuario
                                btnModificar.performClick();
                            }
                        }
                )
        );
        // Esperar a que el diálogo de modificación de usuario se muestre
        Espresso.onView(withId(R.id.botoTancar)).perform(ViewActions.click());

        // Allibera Intents després de la prova
        Intents.release();
    }

    @Test
    public void testAccesEliminarUsuari() {
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

        // Fer clic al botó de modificar usuari de la primera fila de la llista
        Espresso.onView(ViewMatchers.withId(R.id.rvUsuaris)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1,
                        new ViewAction() {
                            @Override
                            public Matcher<View> getConstraints() {
                                return ViewMatchers.isAssignableFrom(View.class);
                            }

                            @Override
                            public String getDescription() {
                                return "Click on delete user button";
                            }

                            @Override
                            public void perform(UiController uiController, View view) {
                                // Encontrar el botón de eliminar usuario dentro del ítem de la lista
                                View btnEliminar = view.findViewById(R.id.btnEliminar);
                                // Realizar clic en el botón de eliminar usuario
                                btnEliminar.performClick();
                            }
                        }
                )
        );

        // Verificar que el diálogo de modificación de usuario se muestra correctamente
        Espresso.onView(withId(R.id.botoTancarContainer)).check(matches(isDisplayed()));

        // Allibera Intents després de la prova
        Intents.release();
    }

}