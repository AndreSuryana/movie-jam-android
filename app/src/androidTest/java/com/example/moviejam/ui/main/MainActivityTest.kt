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
    fun homeFragmentTopMovies() {
        /**
         * Check if rv_top is displayed
         * Then click on the first item
         * Check if content detail is valid
         * Then go back using back button in toolbar
         */

        // Idling Resource with Thread Sleep
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onView(withId(R.id.rv_top)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_top)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun homeFragmentPopularMovies() {
        /**
         * Check if rv_popular_movies is displayed
         * Then click on the item in position 3
         * Check if content detail is valid
         * Then go back using back button in toolbar
         */

        // Idling Resource with Thread Sleep
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
    }

    @Test
    fun navigateToMoviesAndClickItem() {
        onView(withId(R.id.navigation_movies)).perform(click())

        // Idling Resource with Thread Sleep
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToTvShowsAndClickItem() {
        onView(withId(R.id.navigation_tv_shows)).perform(click())

        // Idling Resource with Thread Sleep
        try {
            Thread.sleep(2000)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                5,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToProfile() {
        onView(withId(R.id.navigation_profile)).perform(click())
        onView(withId(R.id.tv_profile_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_profile_name)).check(matches(withText("Kadek Andre Suryana")))
    }
}