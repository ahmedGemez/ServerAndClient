package com.example.clientapp.presentation.ui

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.clientapp.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.matches

@MediumTest
@RunWith(AndroidJUnit4::class)
class LocationFragmentTest{

    @Test
    fun articleDetailsFragmentTest_DisplayedInUi()  {

        var bundle = Bundle()
        launchFragmentInContainer<LocationFragment>(bundle,R.style.AppTheme)

        onView(withId(R.id.lat_tv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.long_tv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.lat_value)).check(ViewAssertions.matches(ViewMatchers.withText("")))

        Thread.sleep(3000)
    }
}