/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.olivia.samples.apps.penName

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasType
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.olivia.samples.apps.penName.utilities.chooser
import com.olivia.samples.apps.penName.utilities.testPlant
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDetailFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun jumpToPlantDetailFragment() {
        activityTestRule.activity.apply {
            runOnUiThread {
                val bundle = Bundle().apply { putString("plantId", testPlant.user_id) }
                findNavController(R.id.nav_host_main).navigate(R.id.bottom_nav_menu_profile, bundle)
            }
        }
    }

    @Ignore("Share button redesign pending")
    @Test
    fun testShareTextIntent() {
        val shareText = activityTestRule.activity.getString(
            R.string.share_text_plant,
            testPlant.name
        )

        Intents.init()
        onView(withId(R.id.action_share)).perform(click())
        intended(
            chooser(
                allOf(
                    hasAction(Intent.ACTION_SEND),
                    hasType("text/plain"),
                    hasExtra(Intent.EXTRA_TEXT, shareText)
                )
            )
        )
        Intents.release()

        // dismiss the Share Dialog
        InstrumentationRegistry.getInstrumentation()
            .uiAutomation
            .performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }
}
