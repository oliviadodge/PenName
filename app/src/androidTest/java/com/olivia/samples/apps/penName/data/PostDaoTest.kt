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
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import com.olivia.samples.apps.penName.data.post.Post
import com.olivia.samples.apps.penName.data.post.PostDao
import com.olivia.samples.apps.penName.utilities.testCalendar
import com.olivia.samples.apps.penName.utilities.testGardenPlanting
import com.olivia.samples.apps.penName.utilities.testPlant
import com.olivia.samples.apps.penName.utilities.testPlants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PostDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var postDao: PostDao
    private var testGardenPlantingId: Long = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        postDao = database.postDao()

        database.userDao().insertAll(testPlants)
        testGardenPlantingId = postDao.insertPost(testGardenPlanting)
    }

    @After fun closeDb() {
        database.close()
    }

    @Test fun testGetGardenPlantings() = runBlocking {
        val gardenPlanting2 = Post(
            testPlants[1].user_id,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        postDao.insertPost(gardenPlanting2)
        assertThat(postDao.getPosts().first().size, equalTo(2))
    }

    @Test fun testDeleteGardenPlanting() = runBlocking {
        val gardenPlanting2 = Post(
            testPlants[1].user_id,
            testCalendar,
            testCalendar
        ).also { it.gardenPlantingId = 2 }
        postDao.insertPost(gardenPlanting2)
        assertThat(postDao.getPosts().first().size, equalTo(2))
        postDao.deletePost(gardenPlanting2)
        assertThat(postDao.getPosts().first().size, equalTo(1))
    }

    @Test fun testGetGardenPlantingForPlant() = runBlocking {
        assertTrue(postDao.isPlanted(testPlant.user_id).first())
    }

    @Test fun testGetGardenPlantingForPlant_notFound() = runBlocking {
        assertFalse(postDao.isPlanted(testPlants[2].user_id).first())
    }

    @Test fun testGetPlantAndGardenPlantings() = runBlocking {
        val plantAndGardenPlantings = postDao.getPostsFromUser().first()
        assertThat(plantAndGardenPlantings.size, equalTo(1))

        /**
         * Only the [testPlant] has been planted, and thus has an associated [Post]
         */
        assertThat(plantAndGardenPlantings[0].user, equalTo(testPlant))
        assertThat(plantAndGardenPlantings[0].posts.size, equalTo(1))
        assertThat(plantAndGardenPlantings[0].posts[0], equalTo(testGardenPlanting))
    }
}
