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

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.olivia.samples.apps.penName.data.user.User
import com.olivia.samples.apps.penName.data.user.UserDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private val plantA = User("1", "A", "", "")
    private val plantB = User("2", "B", "", "")
    private val plantC = User("3", "C", "", "")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = database.userDao()

        // Insert plants in non-alphabetical order to test that results are sorted by name
        userDao.insertAll(listOf(plantB, plantC, plantA))
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetPlants() = runBlocking {
        val plantList = userDao.getUsers().first()
        assertThat(plantList.size, equalTo(3))

        // Ensure plant list is sorted by name
        assertThat(plantList[0], equalTo(plantA))
        assertThat(plantList[1], equalTo(plantB))
        assertThat(plantList[2], equalTo(plantC))
    }

    @Test fun testGetPlantsWithGrowZoneNumber() = runBlocking {
        val plantList = userDao.getPlantsWithGrowZoneNumber(1).first()
        assertThat(plantList.size, equalTo(2))
        assertThat(userDao.getPlantsWithGrowZoneNumber(2).first().size, equalTo(1))
        assertThat(userDao.getPlantsWithGrowZoneNumber(3).first().size, equalTo(0))

        // Ensure plant list is sorted by name
        assertThat(plantList[0], equalTo(plantA))
        assertThat(plantList[1], equalTo(plantB))
    }

    @Test fun testGetPlant() = runBlocking {
        assertThat(userDao.getUser(plantA.user_id).first(), equalTo(plantA))
    }
}
