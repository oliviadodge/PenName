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
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olivia.samples.apps.penName.R
import com.olivia.samples.apps.penName.data.PlantAndGardenPlantings
import com.olivia.samples.apps.penName.databinding.ListItemGardenPlantingBinding
import com.olivia.samples.apps.penName.viewmodels.PlantAndGardenPlantingsViewModel

class GardenPlantingAdapter(private val listener: Listener) :
    ListAdapter<PlantAndGardenPlantings, GardenPlantingAdapter.ViewHolder>(
        GardenPlantDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_garden_planting,
                parent,
                false
            ),
                listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemGardenPlantingBinding,
        private val listener: Listener,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { view ->
                binding.viewModel?.plantId?.let { plantId ->
                    navigateToPlant(plantId, view)
                }
            }
        }

        private fun navigateToPlant(plantId: String, view: View) {
            listener.onProfileClicked(plantId)
        }

        fun bind(plantings: PlantAndGardenPlantings) {
            with(binding) {
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                executePendingBindings()
            }
        }
    }
}

private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {

    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.post.postId == newItem.post.postId
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.post == newItem.post
    }
}
