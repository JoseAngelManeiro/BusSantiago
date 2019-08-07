package org.galio.bussantiago.common

import android.os.Bundle
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
  appCompatActivity.supportActionBar?.let { actionBar ->
    actionBar.title = title
    actionBar.subtitle = subTitle
    actionBar.setDisplayHomeAsUpEnabled(backEnabled)
  }
}

fun Fragment.handleException(exception: Exception) {
  AlertDialog.Builder(context!!)
    .setMessage(exception.message)
    .setPositiveButton(R.string.aceptar) { dialog, _ ->
      dialog.dismiss()
    }
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
