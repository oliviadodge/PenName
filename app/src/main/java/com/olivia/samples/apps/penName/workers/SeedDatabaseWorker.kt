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
        seedUsersTable()
        seedPostsTable()
        return Result.success()
    }

    private suspend fun seedUsersTable() {
        applicationContext.assets.open(USERS_DATA_FILENAME).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val type = object : TypeToken<List<User>>() {}.type
                val userList: List<User> = Gson().fromJson(jsonReader, type)

                val database = AppDatabase.getInstance(applicationContext)
                database.userDao().insertAll(userList)
            }
        }
    }

    private suspend fun seedPostsTable() {
        applicationContext.assets.open(POSTS_DATA_FILENAME).use { inputStream ->
            JsonReader(inputStream.reader()).use { jsonReader ->
                val type = object : TypeToken<List<Post>>() {}.type
                val posts: List<Post> = Gson().fromJson(jsonReader, type)

                val database = AppDatabase.getInstance(applicationContext)
                val rowIds = database.postDao().insertAll(posts)
                print(rowIds.toString())
            }
        }
    }

    companion object {
        private const val TAG = "SeedDatabaseWorker"
    }
}
