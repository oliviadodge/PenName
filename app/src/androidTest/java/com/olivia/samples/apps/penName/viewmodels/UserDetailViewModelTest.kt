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

package com.olivia.samples.apps.penName.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.olivia.samples.apps.penName.data.AppDatabase
import com.olivia.samples.apps.penName.data.post.PostRepository
import com.olivia.samples.apps.penName.data.user.UserRepository
import com.olivia.samples.apps.penName.utilities.getValue
import com.olivia.samples.apps.penName.utilities.testPlant
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject
import kotlin.jvm.Throws

@HiltAndroidTest
class UserDetailViewModelTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var viewModel: UserProfileViewModel
    private val hiltRule = HiltAndroidRule(this)
    private val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = RuleChain
            .outerRule(hiltRule)
            .around(instantTaskExecutorRule)

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var gardenPlantRepository: PostRepository

    @Before
    fun setUp() {
        hiltRule.inject()

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        viewModel = UserProfileViewModel(userRepository, gardenPlantRepository, testPlant.user_id)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun testDefaultValues() {
        assertFalse(getValue(viewModel.isPlanted))
    }
}
