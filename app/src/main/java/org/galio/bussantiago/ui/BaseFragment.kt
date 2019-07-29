package org.galio.bussantiago.ui

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

abstract class BaseFragment : Fragment() {

  fun initActionBar(titleResId: Int = 0, backEnabled: Boolean = false) {
    val appCompatActivity = activity as AppCompatActivity
    appCompatActivity.supportActionBar?.setTitle(titleResId)
    appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
  }
}
