/*
 * Copyright 2019 Google LLC
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.olivia.samples.apps.penName.databinding.FragmentBottomNavBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomNavFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentBottomNavBinding.inflate(inflater, container, false).also {
            bindViews(it)
            return it.root
        }
    }

    private fun bindViews(binding: FragmentBottomNavBinding) {
//        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar) TODO fix?
        setupBottomNav(binding.bottomNavigation)
    }

    private fun setupBottomNav(bottomNavigation: BottomNavigationView) {
        val bottomNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_with_bottom_nav) as NavHostFragment
        val navController = bottomNavHostFragment.navController
        bottomNavigation.setupWithNavController(navController)
    }
}
