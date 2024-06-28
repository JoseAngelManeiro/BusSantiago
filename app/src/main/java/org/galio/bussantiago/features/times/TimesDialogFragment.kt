package org.galio.bussantiago.features.times

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.databinding.TimesDialogFragmentBinding
import org.galio.bussantiago.framework.ReviewsHelper
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimesDialogFragment : DialogFragment() {

  private var _binding: TimesDialogFragmentBinding? = null
  private val binding get() = _binding!!

  private val viewModel: TimesViewModel by viewModel()
  private lateinit var busStopModel: BusStopModel

  private val reviewsHelper: ReviewsHelper by inject()

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
  ): View {
    _binding = TimesDialogFragmentBinding.inflate(inflater, container, false)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    busStopModel = arguments?.get(BUS_STOP_KEY) as BusStopModel

    setUpToolbar(busStopModel)

    setUpFavoriteButton()

    viewModel.setArgs(busStopModel.code, busStopModel.name)

    viewModel.lineRemainingTimeModels.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          binding.progressBar.visibility = View.VISIBLE
        },
        onError = {
          binding.progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadTimes() }
        },
        onSuccess = { times ->
          with(binding) {
            progressBar.visibility = View.GONE
            if (times.isEmpty()) {
              timesRecyclerView.visibility = View.GONE
              noInfoTextView.visibility = View.VISIBLE
            } else {
              noInfoTextView.visibility = View.GONE
              timesRecyclerView.visibility = View.VISIBLE
              timesRecyclerView.adapter = TimesAdapter(times)
              reviewsHelper.launchReviews(requireActivity())
            }
          }
        }
      )
    }

    viewModel.isFavorite.observe(viewLifecycleOwner) { isFavorite ->
      with(binding) {
        if (isFavorite) {
          favoriteFAB.setImageResource(R.drawable.ic_fab_favorite)
          favoriteFAB.contentDescription = getString(R.string.favorite_stop)
        } else {
          favoriteFAB.setImageResource(R.drawable.ic_fab_favorite_border)
          favoriteFAB.contentDescription = getString(R.string.no_favorite_stop)
        }
      }
    }

    viewModel.loadTimes()
    viewModel.validateBusStop()
  }

  private fun setUpToolbar(busStopModel: BusStopModel) {
    with(binding) {
      toolbar.run {
        title = busStopModel.code
        subtitle = busStopModel.name
        setNavigationIcon(R.drawable.ic_back_button)
        setNavigationOnClickListener { dismiss() }
        inflateMenu(R.menu.times_menu)
        setOnMenuItemClickListener {
          if (it.itemId == R.id.sync_action) {
            if (!timesAreLoading()) viewModel.loadTimes()
            true
          } else {
            false
          }
        }
      }
    }
  }

  private fun setUpFavoriteButton() {
    binding.favoriteFAB.setOnClickListener {
      viewModel.changeFavoriteState()
    }
  }

  private fun timesAreLoading(): Boolean {
    return binding.progressBar.visibility == View.VISIBLE
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
