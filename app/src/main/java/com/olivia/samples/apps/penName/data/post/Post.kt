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
import com.olivia.samples.apps.penName.data.user.User
import java.util.Calendar

@Entity(
        foreignKeys = [
            ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"])
        ]
)
data class Post(
        val userId: String,
        val description: String = "",
        val imageUrl: String = "",
) {
    @PrimaryKey(autoGenerate = true) var postId: Int = 0
}