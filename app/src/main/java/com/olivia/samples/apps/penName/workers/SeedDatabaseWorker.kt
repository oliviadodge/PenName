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

package com.olivia.samples.apps.penName.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.olivia.samples.apps.penName.data.AppDatabase
import com.olivia.samples.apps.penName.data.post.Post
import com.olivia.samples.apps.penName.data.user.User
import com.olivia.samples.apps.penName.utilities.POSTS_DATA_FILENAME
import com.olivia.samples.apps.penName.utilities.USERS_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(
        context: Context,
        workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            seedDatabaseTables()
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }
    }

    private suspend fun seedDatabaseTables(): Result {
        AppDatabase.getInstance(applicationContext).apply {
            userDao().insertAll(getUserListFromJsonFile())
            postDao().insertAll(getPostListFromJsonFile())
        }

        return Result.success()
    }

    private fun getPostListFromJsonFile(): List<Post> {
        var postsList: List<Post>
        applicationContext.assets.open(POSTS_DATA_FILENAME).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val type = object : TypeToken<List<Post>>() {}.type
                postsList = Gson().fromJson(jsonReader, type)
            }
        }
        return postsList
    }

    private fun getUserListFromJsonFile(): List<User> {
        var userList: List<User>
        applicationContext.assets.open(USERS_DATA_FILENAME).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val type = object : TypeToken<List<User>>() {}.type
                userList = Gson().fromJson(jsonReader, type)
            }
        }
        return userList
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
