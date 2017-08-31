package com.example.sony.bakingapp;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.ActionBar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Created by SONY on 8/31/2017.
 */
@RunWith(AndroidJUnit4.class)
public class ChooseRecipeTest {
    @Rule public IntentsTestRule<MainActivity> intentsTestRule=new IntentsTestRule<>(MainActivity.class);
    @Test
    public void clickRecipe(){
        onData(anything()).inAdapterView(withId(R.id.tv_recipe)).atPosition(1).perform(click());
//check for the actionbar title
    }
}
