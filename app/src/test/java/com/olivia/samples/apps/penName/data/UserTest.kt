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

package com.olivia.samples.apps.penName.data

import com.olivia.samples.apps.penName.data.user.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.Calendar
import java.util.Calendar.DAY_OF_YEAR

class UserTest {

    private lateinit var user: User

    @Before fun setUp() {
        user = User("1", "Tomato", "A red vegetable", "")
    }

    @Test fun test_default_values() {
        val defaultPlant = User("2", "Apple", "Description")
        assertEquals(7, defaultPlant.wateringInterval)
        assertEquals("", defaultPlant.imageUrl)
    }

    @Test fun test_shouldBeWatered() {
        Calendar.getInstance().let { now ->
            // Generate lastWateringDate from being as copy of now.
            val lastWateringDate = Calendar.getInstance()

            // Test for lastWateringDate is today.
            lastWateringDate.time = now.time
            assertFalse(user.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -0) }))

            // Test for lastWateringDate is yesterday.
            lastWateringDate.time = now.time
            assertFalse(user.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -1) }))

            // Test for lastWateringDate is the day before yesterday.
            lastWateringDate.time = now.time
            assertFalse(user.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -2) }))

            // Test for lastWateringDate is some days ago, three days ago, four days ago etc.
            lastWateringDate.time = now.time
            assertTrue(user.shouldBeWatered(now, lastWateringDate.apply { add(DAY_OF_YEAR, -3) }))
        }
    }

    @Test fun test_toString() {
        assertEquals("Tomato", user.toString())
    }
}
