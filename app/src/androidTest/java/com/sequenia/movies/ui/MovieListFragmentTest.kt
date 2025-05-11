package com.sequenia.movies.ui

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.sequenia.movies.MainActivity
import com.sequenia.movies.R
import com.sequenia.movies.di.appModule
import com.sequenia.movies.utils.KoinTestRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieListFragmentTest {

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(appModule)
    )

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    fun getContext(): Context {
        var context: Context? = null
        activityRule.scenario.onActivity {
            context = it
        }
        return context!!
    }

    @Test
    fun pickGenreTest() {
        runBlocking {
            delay(5000L)
        }
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.genre_item_text),
                ViewMatchers.withText("Биография")
            )
        ).perform(ViewActions.click())
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.genre_item_text),
                ViewMatchers.withText("Биография")
            )
        ).check(
            ViewAssertions.matches(
                withBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.yellow
                    )
                )
            )
        )
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.genre_item_text),
                ViewMatchers.withText("Биография")
            )
        ).perform(ViewActions.click())
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.genre_item_text),
                ViewMatchers.withText("Биография")
            )
        ).check(
            ViewAssertions.matches(
                withBackgroundColor(
                    ContextCompat.getColor(
                        getContext(),
                        R.color.white
                    )
                )
            )
        )
    }

    @Test
    fun pickMovieTest() {
        runBlocking {
            delay(5000L)
        }
        Espresso.onView(ViewMatchers.withText("Бойцовский клуб"))
            .perform(ViewActions.scrollTo(), ViewActions.click())
        runBlocking {
            delay(1000L)
        }
        Espresso.onView(ViewMatchers.withText("Fight Club"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.movie_details_back_button))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.movie_list_header))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

fun withBackgroundColor(color: Int): Matcher<View>? {
    return object : BoundedMatcher<View, TextView>(TextView::class.java) {

        override fun matchesSafely(item: TextView): Boolean {
            return color == (item.background as ColorDrawable).color
        }

        override fun describeTo(description: Description?) {
            description?.appendText("with background color: ")
        }
    }
}