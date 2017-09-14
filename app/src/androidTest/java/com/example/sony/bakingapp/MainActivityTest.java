package com.example.sony.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recyclerview), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction textView = onView(
                allOf(withText("Cheesecake"),
                        childAtPosition(
                                allOf(withId(R.id.my_toolbar),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Cheesecake")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.tv_recipe_ingredients), withText("Recipe Ingredients"),
                        withParent(allOf(withId(R.id.recipe_fragment),
                                withParent(withId(R.id.my_container)))),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.tv_detail), isDisplayed()));
        appCompatTextView2.perform(replaceText("1) 2CUP  Graham Cracker crumbs\n\n2) 6TBLSP  unsalted butter, melted\n\n3) 250G  granulated sugar\n\n4) 1TSP  salt\n\n5) 4TSP  vanilla,divided\n\n6) 680G  cream cheese, softened\n\n7) 3UNIT  large whole eggs\n\n8) 2UNIT  large egg yolks\n\n9) 250G  heavy cream\n\n"), closeSoftKeyboard());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_detail), withText("1) 2CUP  Graham Cracker crumbs\n\n2) 6TBLSP  unsalted butter, melted\n\n3) 250G  granulated sugar\n\n4) 1TSP  salt\n\n5) 4TSP  vanilla,divided\n\n6) 680G  cream cheese, softened\n\n7) 3UNIT  large whole eggs\n\n8) 2UNIT  large egg yolks\n\n9) 250G  heavy cream\n\n"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.my_container2),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
