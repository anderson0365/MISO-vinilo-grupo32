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
import static org.hamcrest.core.IsNot.not;

import com.miso_vinilo_grupo32.ui.UserLoginView;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DetailAlbumViewTest {

    @Rule
    public ActivityTestRule<UserLoginView> mActivityTestRule = new ActivityTestRule<>(UserLoginView.class);

    @Test
    public void albumDetailContentAndFlowTest() {

        ViewInteraction userButton = onView(allOf(withId(R.id.user_button), withText(R.string.user_button),isDisplayed()));
        userButton.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction albumTap = onView((allOf(withText(R.string.albums_tap), isDisplayed())));
        albumTap.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction detail_button = onView((allOf(withId(R.id.album_detail_button),withText(R.string.album_detail_button), isDisplayed())));
        detail_button.perform(click());

        onView(isRoot()).perform(waitFor(5000));

        //Se valida que el contendio base aparece y que la lista de canciones no aparece
        onView(withId(R.id.basic_content)).check(matches(isDisplayed()));
        onView(withId(R.id.songs_layout)).check(matches(not(isDisplayed())));

        ViewInteraction tracksButton = onView(withId(R.id.songs_display));
        tracksButton.perform(click());

        onView(isRoot()).perform(waitFor(2000));

        //Se valida que el contenido base desapareció y que la lista de cacnciones apareció
        onView(withId(R.id.basic_content)).check(matches(not(isDisplayed())));
        //  onView(withId(R.id.songs_layout)).check(matches(isDisplayed()));

        tracksButton.perform(click());

        //Se valida que el contendio base aparece y que la lista de canciones no aparece
        onView(withId(R.id.basic_content)).check(matches(isDisplayed()));
        onView(withId(R.id.songs_layout)).check(matches(not(isDisplayed())));

        onView(isRoot()).perform(waitFor(2000));

        ViewInteraction backButton = onView(allOf(withId(R.id.back_button_album_detail), withText(R.string.back_button),isDisplayed()));
        backButton.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        onView(allOf(withId(R.id.back_button_main), withText(R.string.back_button),isDisplayed())).perform(click());

        onView(isRoot()).perform(waitFor(1000));

        //Se valida que el bottón de atras me retorno al menu principal
        onView(withId(R.id.user_button)).check(matches(isDisplayed()));

    }
}