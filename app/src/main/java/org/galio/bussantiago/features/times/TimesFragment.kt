package org.galio.bussantiago.features.times

import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.busstops_fragment.progressBar
import kotlinx.android.synthetic.main.times_fragment.*
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.model.BusStopModel
import org.koin.android.viewmodel.ext.android.viewModel

class TimesFragment : DialogFragment() {

  private val viewModel: TimesViewModel by viewModel()
  private lateinit var busStopModel: BusStopModel

  companion object {
    private const val BUS_STOP_KEY = "bus_stop_key"
    fun createArguments(busStopModel: BusStopModel): Bundle {
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOP_KEY, busStopModel)
      return bundle
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
  }

  override fun onStart() {
    super.onStart()
    dialog?.let { dialog ->
      val width = ViewGroup.LayoutParams.MATCH_PARENT
      val height = ViewGroup.LayoutParams.MATCH_PARENT
      dialog.window?.setLayout(width, height)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.times_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    busStopModel = arguments?.get(BUS_STOP_KEY) as BusStopModel

    viewModel.setArgs(busStopModel.code, busStopModel.name)

    setUpToolbar()

    setUpFavoriteButton()

    viewModel.lineRemainingTimeModels.observe(this, Observer {
      it?.let { resourceLineRemainingTimeModels ->
        when (resourceLineRemainingTimeModels.status) {
          Status.LOADING -> {
            progressBar.visibility = View.VISIBLE
          }
          Status.SUCCESS -> {
            hideProgressBarIfNecessary()
            resourceLineRemainingTimeModels.data?.let { times ->
              if (times.isEmpty()) {
                noInfoTextView.visibility = View.VISIBLE
              } else {
                timesRecyclerView.adapter = TimesAdapter(times)
              }
            }
          }
          Status.ERROR -> {
            hideProgressBarIfNecessary()
            handleException(resourceLineRemainingTimeModels.exception!!)
            dismiss()
          }
        }
      }
    })

    viewModel.isFavorite.observe(this, Observer { isFavorite ->
      if (isFavorite) {
        favoriteFAB.setImageResource(R.drawable.ic_fab_favorite)
        favoriteFAB.contentDescription = getString(R.string.favorite_stop)
      } else {
        favoriteFAB.setImageResource(R.drawable.ic_fab_favorite_border)
        favoriteFAB.contentDescription = getString(R.string.no_favorite_stop)
      }
    })

    viewModel.loadTimes()
    viewModel.validateBusStop()
  }

  private fun setUpToolbar() {
    toolbar.title = busStopModel.code
    toolbar.subtitle = busStopModel.name
    toolbar.setNavigationIcon(R.drawable.ic_close)
    toolbar.setNavigationOnClickListener { dismiss() }
  }

  private fun setUpFavoriteButton() {
    favoriteFAB.setOnClickListener {
      viewModel.changeFavoriteState()
    }
  }

  private fun hideProgressBarIfNecessary() {
    if (progressBar.visibility == View.VISIBLE) {
      progressBar.visibility = View.GONE
    }
  }
}
