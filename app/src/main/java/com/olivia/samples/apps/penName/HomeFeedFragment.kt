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

package com.olivia.samples.apps.penName

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.olivia.samples.apps.penName.adapters.Listener
import com.olivia.samples.apps.penName.adapters.FeedAdapter
import com.olivia.samples.apps.penName.databinding.FragmentHomeFeedBinding
import com.olivia.samples.apps.penName.viewmodels.HomeFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFeedFragment : Fragment() {

    private val viewModel: HomeFeedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeFeedBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = FeedAdapter(object : Listener {
            override fun onProfileClicked(profileId: String) {
                val direction = BottomNavFragmentDirections.actionHomeFeedToProfile(profileId)
                val navController = Navigation.findNavController(activity!!, R.id.nav_host_main)
                navController.navigate(direction)
            }

        })
        binding.feedRecyclerView.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }

    private fun subscribeUi(adapter: FeedAdapter) {
        viewModel.feed.observe(viewLifecycleOwner) { feed ->
            adapter.submitList(feed)
        }
    }
}
