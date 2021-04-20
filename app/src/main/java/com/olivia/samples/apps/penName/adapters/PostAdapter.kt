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

package com.olivia.samples.apps.penName.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olivia.samples.apps.penName.HomeFeedFragment
import com.olivia.samples.apps.penName.data.PostAndUser
import com.olivia.samples.apps.penName.data.post.Post
import com.olivia.samples.apps.penName.databinding.ListItemPostBinding

/**
 * Adapter for the [RecyclerView] in [HomeFeedFragment].
 */
class PostAdapter() : ListAdapter<PostAndUser, RecyclerView.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
                ListItemPostBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    class PlantViewHolder(
            private val binding: ListItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.postAndUser?.let {
                    //TODO nothing should happen?
                }
            }
        }

        fun bind(item: PostAndUser) {
            binding.apply {
                postAndUser = item
                executePendingBindings()
            }
        }
    }
}

interface PostClickListener {
    fun onPostClicked(postId: Int)
}

private class PostDiffCallback : DiffUtil.ItemCallback<PostAndUser>() {

    override fun areItemsTheSame(oldItem: PostAndUser, newItem: PostAndUser): Boolean {
        return oldItem.post.postId == newItem.post.postId
    }

    override fun areContentsTheSame(oldItem: PostAndUser, newItem: PostAndUser): Boolean {
        return oldItem == newItem
    }
}
