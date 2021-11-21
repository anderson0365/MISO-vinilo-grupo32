package com.miso_vinilo_grupo32;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.miso_vinilo_grupo32.UtilitiesKt.waitFor;
import static org.hamcrest.Matchers.allOf;

import com.miso_vinilo_grupo32.ui.UserLoginView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListArtistsTest {

    @Rule
    public ActivityTestRule<UserLoginView> mActivityTestRule = new ActivityTestRule<>(UserLoginView.class);

    @Test
    public void listArtistsContentAndFlowTest() {

        ViewInteraction userButton = onView(allOf(withId(R.id.user_button), withText(R.string.user_button),isDisplayed()));
        userButton.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction artistTap = onView((allOf(withText(R.string.artist_tap), isDisplayed())));
        artistTap.perform(click());

        onView(isRoot()).perform(waitFor(5000));

        //Se valida que desplegó el recyclerview asociado a la lista de artists
        onView(withId(R.id.artistRv)).check(matches(isDisplayed()));

        ViewInteraction albumTap = onView((allOf(withText(R.string.albums_tap), isDisplayed())));
        albumTap.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        onView(allOf(withId(R.id.back_button_main), withText(R.string.back_button),isDisplayed())).perform(click());

        onView(isRoot()).perform(waitFor(1000));

        //Se valida que el bottón de atras me retorno al menu principal
        onView(withId(R.id.user_button)).check(matches(isDisplayed()));
    }
}