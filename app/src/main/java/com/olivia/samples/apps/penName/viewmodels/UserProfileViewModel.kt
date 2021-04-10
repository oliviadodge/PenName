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

import androidx.lifecycle.*
import com.olivia.samples.apps.penName.BuildConfig
import com.olivia.samples.apps.penName.UserProfileFragment
import com.olivia.samples.apps.penName.data.UserAndPosts
import com.olivia.samples.apps.penName.data.post.PostRepository
import com.olivia.samples.apps.penName.data.user.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

/**
 * The ViewModel used in [UserProfileFragment].
 */
class UserProfileViewModel @AssistedInject constructor(
        userRepository: UserRepository,
        private val postRepository: PostRepository,
        @Assisted private val userId: String
) : ViewModel() {

    val user = userRepository.getUser(userId).asLiveData()
//TODO fixme
//    val userAndPosts: LiveData<UserAndPosts> =
//            postRepository.getPostsFromUser(userId).asLiveData()

    fun createPost() {
        viewModelScope.launch {
            postRepository.createPost(userId)
        }
    }

    fun hasValidUnsplashKey() = (BuildConfig.UNSPLASH_ACCESS_KEY != "null")

    companion object {
        fun provideFactory(
                assistedFactory: UserProfileViewModelFactory,
                userId: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(userId) as T
            }
        }
    }
}

@AssistedFactory
interface UserProfileViewModelFactory {
    fun create(userId: String): UserProfileViewModel
}
