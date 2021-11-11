package com.example.moviejam.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviejam.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun homeFragment() {

        /**
         * Check if rv_top is displayed
         * Then click on the first item
         * Check if content detail is valid
         * Then go back using back button in toolbar
         */
        onView(withId(R.id.rv_top)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_top)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText("Venom: Let There Be Carnage")))

        onView(withId(R.id.btn_back))
            .check(matches(isDisplayed()))
            .perform(click())

        /**
         * Check if rv_popular_movies is displayed
         * Then click on the item in position 3
         * Check if content detail is valid
         * Then go back using back button in toolbar
         */
        onView(withId(R.id.rv_popular_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                3
            )
        )
        onView(withId(R.id.rv_popular_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withText("Bohemian Rhapsody")))

        onView(withId(R.id.btn_back))
            .check(matches(isDisplayed()))
            .perform(click())
    }
}