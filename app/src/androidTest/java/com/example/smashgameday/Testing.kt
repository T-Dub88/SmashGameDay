package com.example.smashgameday

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class AppFunctionalityTests {
    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    // Tests the navigation of the app.
    @Test
    fun nextRoundButtonFunctionality() {
        onView(withId(R.id.start_game_day)).perform(click())

        onView(withId(R.id.next_round_button)).perform(click())
        onView(withId(R.id.next_round_button)).perform(click())

        onView(withId(R.id.next_round_button))
            .check(matches(withText(containsString("3"))))
    }

    // Test the ability to enter players
    @Test
    fun playerEntry() {
        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))

        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_list))
            .check(matches(withText(containsString("Tyler"))))

    }

    // Tests the ability to delete the first player.
    @Test
    fun deleteFirstPlayer() {
        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Vaan"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("TK"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
        onView(withId(R.id.delete_player)).perform(click())

        onView(withId(R.id.player_list))
            .check(matches(withText("Vaan\nTK\n")))
    }

    // Tests the ability to delete the last player.
    @Test
    fun deleteLastPlayer() {
        onView(withId(R.id.player_name_text)).perform(typeText("Bob"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Vaan"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("TK"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
        onView(withId(R.id.delete_player)).perform(click())

        onView(withId(R.id.player_list))
            .check(matches(withText("Bob\nVaan\nTK\n")))
    }

    // Tests the ability to delete the middle player.
    @Test
    fun deleteMiddlePlayer() {
        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Vaan"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("TK"))
        onView(withId(R.id.add_player)).perform(click())

        onView(withId(R.id.player_name_text)).perform(typeText("Vaan"))
        onView(withId(R.id.delete_player)).perform(click())

        onView(withId(R.id.player_list))
            .check(matches(withText("Tyler\nTK\n")))
    }

    // Tests the apps score keeping functionality for player 1.
//    @Test
//    fun scoreKeepingPlayerOne() {
//        onView(withId(R.id.player_name_text)).perform(typeText("Tyler"))
//        onView(withId(R.id.add_player)).perform(click())
//
//        onView(withId(R.id.player_name_text)).perform(typeText("Vaan"))
//        onView(withId(R.id.add_player)).perform(click())
//
//        onView(withId(R.id.player_name_text)).perform(typeText("TK"))
//        onView(withId(R.id.add_player)).perform(click())
//
//        onView(withId(R.id.start_game_day)).perform(click())
//
//    }
//
//    // Tests the apps score keeping functionality for player 2.
//    @Test
//    fun scoreKeepingPlayerTwo() {
//
//    }
//
//    // Tests the apps score keeping functionality for player 3.
//    @Test
//    fun scoreKeepingPlayerThree() {
//
//    }
}