/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.clakestudio.pc.countries.testing

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.clakestudio.pc.countries.R
import kotlinx.android.synthetic.main.single_fragment_activity.*

/**
 * Used for testing fragments inside a fake activity.
 */
class SingleFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*val content = FrameLayout(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            id = R.id.container
        }*/

        setContentView(R.layout.single_fragment_activity)

        setSupportActionBar(toolbar)
    }

   // override fun onSupportNavigateUp(): Boolean {
      //  return navController().navigateUp()
    //}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

//    override fun supportFragmentInjector() = dispatchingAndroidInjector

  //  private fun navController() = findNavController(R.id.navHostFragment)

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, "TEST")
            .commitAllowingStateLoss()
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}
