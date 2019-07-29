package org.galio.bussantiago.ui.menu

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.text.HtmlCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.menu_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.koin.android.viewmodel.ext.android.viewModel

class MenuFragment : DialogFragment() {

  private val viewModel: MenuViewModel by viewModel()

  companion object {
    private const val ID_KEY = "id_key"
    fun createArguments(id: Int): Bundle {
      val bundle = Bundle()
      bundle.putInt(ID_KEY, id)
      return bundle
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.menu_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModel.menuModel.observe(this, Observer {
      it?.let { resourceMenuModel ->
        when (resourceMenuModel.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            setUpRecyclerView(resourceMenuModel.data!!.options)
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            Toast.makeText(this.context, resourceMenuModel.exception!!.message, Toast.LENGTH_SHORT)
              .show()
          }
        }
      }
    })

    val id = arguments?.get(ID_KEY) as Int
    viewModel.loadLineDetails(id)
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }

  private fun setUpRecyclerView(menuOptionModels: List<MenuOptionModel>) {
    menuOptionsRecyclerView.visibility = View.VISIBLE
    menuOptionsRecyclerView.adapter = MenuAdapter(menuOptionModels) { onMenuOptionClicked(it) }
  }

  private fun onMenuOptionClicked(menuOptionModel: MenuOptionModel) {
    // Todo Not Implemented

    HtmlCompat.fromHtml("", HtmlCompat.FROM_HTML_MODE_LEGACY)

    Toast.makeText(context, menuOptionModel.menuType.name, Toast.LENGTH_SHORT).show()
    dismiss()
  }
}
