package org.galio.bussantiago.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.AttributeSet
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import org.galio.bussantiago.R

@Navigator.Name("dialog-fragment")
class DialogFragmentNavigator(
  private val fragmentManager: FragmentManager
) : Navigator<DialogFragmentNavigator.Destination>() {

  override fun navigate(
    destination: Destination,
    args: Bundle?,
    navOptions: NavOptions?,
    navigatorExtras: Extras?
  ) {
    val fragment = Class.forName(destination.name).newInstance() as DialogFragment
    fragment.arguments = args
    fragment.show(fragmentManager, destination.id.toString())
  }

  override fun createDestination() = Destination(this)

  override fun popBackStack() = fragmentManager.popBackStackImmediate()

  class Destination(navigator: DialogFragmentNavigator) : NavDestination(navigator) {

    // The value of <dialog-fragment app:name=""/>
    lateinit var name: String

    override fun onInflate(context: Context, attrs: AttributeSet) {
      super.onInflate(context, attrs)
      val a = context.resources.obtainAttributes(attrs, R.styleable.FragmentNavigator)
      name = a.getString(R.styleable.FragmentNavigator_android_name)
        ?: throw RuntimeException("Error while inflating XML. `name` attribute is required")
      a.recycle()
    }
  }
}
