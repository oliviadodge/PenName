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

package com.olivia.samples.apps.penName.data.post

import androidx.room.*
import com.olivia.samples.apps.penName.data.PostAndUser
import com.olivia.samples.apps.penName.data.UserAndPosts
import com.olivia.samples.apps.penName.data.user.User
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [Post] class.
 */
@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getFeed(): Flow<List<PostAndUser>>

//    @Transaction
//    @Query("SELECT * FROM post WHERE userId = :userId")
//    fun getPostsFromUser(userId: String): Flow<UserAndPosts>

    @Insert
    suspend fun insertPost(post: Post): Long

    @Delete
    suspend fun deletePost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)
}
