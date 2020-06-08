package org.galio.bussantiago.common

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.galio.bussantiago.R

fun Fragment.initActionBar(
  title: String = "",
  subTitle: String = "",
  backEnabled: Boolean = false
) {
  val appCompatActivity = activity as AppCompatActivity
  appCompatActivity.supportActionBar?.apply {
    this.title = title
    this.subtitle = subTitle
    this.setDisplayHomeAsUpEnabled(backEnabled)
  }
}

fun Fragment.handleException(
  exception: Exception,
  cancel: () -> Unit = {},
  retry: () -> Unit = {}
) {
  AlertDialog.Builder(context!!)
    .setTitle(exception.message)
    .setMessage(R.string.what_want_to_do)
    .setNegativeButton(R.string.cancel) { dialog, _ ->
      dialog.dismiss()
      cancel()
    }
    .setPositiveButton(R.string.retry) { dialog, _ ->
      dialog.dismiss()
      retry()
    }
    .setCancelable(false)
    .create()
    .show()
}

fun Fragment.navigateSafe(resId: Int, args: Bundle? = null) {
  val navController = findNavController()
  val action = navController.currentDestination?.getAction(resId)
  if (action != null) {
    navController.navigate(resId, args)
  }
}

fun Fragment.hideKeyboard() {
  val inputMethodManager = activity
    ?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}
