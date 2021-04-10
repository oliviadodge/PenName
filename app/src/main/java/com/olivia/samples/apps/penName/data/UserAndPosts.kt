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

import androidx.room.Embedded
import androidx.room.Relation
import com.olivia.samples.apps.penName.data.post.Post
import com.olivia.samples.apps.penName.data.user.User

/**
 * This class captures the relationship between a [User] and a user's [Post], which is
 * used by Room to fetch the related entities.
 */
data class UserAndPosts(
        @Embedded(prefix = "parent_")
    val user: User,

        @Relation(parentColumn = "userId", entityColumn = "userId")
    val posts: List<Post> = emptyList()
)
