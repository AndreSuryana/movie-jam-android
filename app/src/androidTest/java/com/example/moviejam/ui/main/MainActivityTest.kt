package com.example.moviejam.ui.main

import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moviejam.R
import com.example.moviejam.utils.EspressoIdlingResources
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }

    @Test
    fun homeFragmentTopMovies() {
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

    @Test
    fun checkComponentInMovieDetailActivity() {
        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster_bg)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_creator)).check(matches(isDisplayed()))
    }

    @Test
    fun checkComponentInTvShowDetailActivity() {
        onView(withId(R.id.navigation_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_poster_bg)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_status)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_language)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_creator)).check(matches(isDisplayed()))
    }

    @Test
    fun checkSearchMoviesFeature() {
        val query = "Naruto"
        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.sv_movies)).perform(click())
        onView(withId(R.id.search_src_text)).perform(clearText(), typeText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withSubstring(query)))
    }

    @Test
    fun checkSearchTvShowsFeature() {
        val query = "Naruto"
        onView(withId(R.id.navigation_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.sv_tv_shows)).perform(click())
        onView(withId(R.id.search_src_text)).perform(clearText(), typeText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title)).check(matches(withSubstring(query)))
    }

    @Test
    fun searchMoviesAndAddToFavorite() {
        val query = "Spider-Man: No Way Home"
        onView(withId(R.id.navigation_movies)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.sv_movies)).perform(click())
        onView(withId(R.id.search_src_text)).perform(clearText(), typeText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            .check(matches(withSubstring(query)))
        onView(withId(R.id.toggle_favorite)).check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.btn_back)).check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(0))
        onView(withId(R.id.rv_favorites)).check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            .check(matches(withSubstring(query)))
    }

    @Test
    fun searchTvShowsAndAddToFavorite() {
        val query = "Boruto: Naruto Next Generations"
        onView(withId(R.id.navigation_tv_shows)).perform(click())
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.sv_tv_shows)).perform(click())
        onView(withId(R.id.search_src_text)).perform(clearText(), typeText(query))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        onView(withId(R.id.rv_tv_shows)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_shows)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            .check(matches(withSubstring(query)))
        onView(withId(R.id.toggle_favorite)).check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.btn_back)).check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.rv_favorites)).check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
            .check(matches(withSubstring(query)))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}