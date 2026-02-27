package com.example.androiduitesting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAddCity() {
        onView(withId(R.id.button_add)).perform(click());
        // replaceText is more stable than typeText on slow emulators
        onView(withId(R.id.editText_name)).perform(replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        onView(withText("Edmonton")).check(matches(isDisplayed()));
    }

    @Test
    public void testClearCity() {
        // Add two cities
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());
        
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(replaceText("Vancouver"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Clear all
        onView(withId(R.id.button_clear)).perform(click());
        
        // Check that they are gone
        onView(withText("Edmonton")).check(doesNotExist());
        onView(withText("Vancouver")).check(doesNotExist());
    }

    @Test
    public void testListView() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Checks specifically inside the ListView adapter
        onData(is(instanceOf(String.class)))
            .inAdapterView(withId(R.id.city_list))
            .atPosition(0)
            .check(matches(withText("Edmonton")));
    }

    @Test
    public void testClickCityOpensShowActivity() {
        // Add city first
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        // Click the city in the list
        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Verify ShowActivity is displaying the right name
        onView(withId(R.id.textView_cityName)).check(matches(withText("Edmonton")));
    }

    @Test
    public void testBackButtonInShowActivity() {
        onView(withId(R.id.button_add)).perform(click());
        onView(withId(R.id.editText_name)).perform(replaceText("Edmonton"));
        onView(withId(R.id.button_confirm)).perform(click());

        onData(anything())
                .inAdapterView(withId(R.id.city_list))
                .atPosition(0)
                .perform(click());

        // Press the back button we created
        onView(withId(R.id.button_back)).perform(click());

        // Verify we are back on the main screen
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
