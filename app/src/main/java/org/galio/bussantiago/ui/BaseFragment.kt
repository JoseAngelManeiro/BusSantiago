package org.galio.bussantiago.ui

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

abstract class BaseFragment : Fragment() {

  fun initActionBar(titleResId: Int = 0, backEnabled: Boolean = false) {
    val appCompatActivity = activity as AppCompatActivity
    appCompatActivity.supportActionBar?.setTitle(titleResId)
    appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
  }
}
