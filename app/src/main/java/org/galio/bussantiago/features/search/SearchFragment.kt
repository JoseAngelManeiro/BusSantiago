package org.galio.bussantiago.features.search

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.search_fragment.closeButtonImageView
import kotlinx.android.synthetic.main.search_fragment.myLocationFAB
import kotlinx.android.synthetic.main.search_fragment.progressBar
import kotlinx.android.synthetic.main.search_fragment.searchAutocompleteTextView
import org.galio.bussantiago.R
import org.galio.bussantiago.common.animateToLatLng
import org.galio.bussantiago.common.clearText
import org.galio.bussantiago.common.disableMapButtons
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.hideKeyboard
import org.galio.bussantiago.common.initActionBar
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.moveToLatLng
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.common.showKeyboard
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.features.favorites.FavoritesDialogFragment
import org.galio.bussantiago.features.times.TimesDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val MAP_ZOOM = 18f

class SearchFragment : Fragment() {

  private val viewModel: SearchViewModel by viewModel()

  private var mapView: MapView? = null
  private var googleMap: GoogleMap? = null
  private var markerMap = mutableMapOf<Int, Marker>()
  private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
  private lateinit var fusedLocationClient: FusedLocationProviderClient
  // The default location is the center of the city (Santiago de Compostela)
  private val defaultLocation = LatLng(42.877295815283944, -8.544272857240758)

  override fun onCreate(bundle: Bundle?) {
    super.onCreate(bundle)

    // Listener to intercept back button clicks
    activity?.onBackPressedDispatcher?.addCallback(this,
      object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
          activity?.finish()
        }
      }
    )

    requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) enableMyLocation()
      }

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val rootView = inflater.inflate(R.layout.search_fragment, container, false)

    mapView = rootView.findViewById(R.id.mapView)
    mapView?.onCreate(savedInstanceState)
    mapView?.getMapAsync { setUpMap(it) }

    return rootView
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    initActionBar(title = getString(R.string.app_name))

    setUpMenu()

    viewModel.searchEvent.observe(viewLifecycleOwner) { event ->
      when(event) {
        SearchEvent.ClearSearchText -> clearSearchText()
        is SearchEvent.NavigateToTimes -> navigateToTimesScreen(event.busStopModel)
        is SearchEvent.ShowMapInfoWindow -> showMapInfoWindow(event.busStopSearch)
        SearchEvent.ShowMapMyLocation -> centerInMyLocation()
      }
    }

    viewModel.busStops.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onLoading = {
          progressBar.visibility = View.VISIBLE
          searchAutocompleteTextView.isEnabled = false
          closeButtonImageView.isEnabled = false
        },
        onError = {
          progressBar.visibility = View.GONE
          handleException(it) { viewModel.loadBusStops() }
        },
        onSuccess = { busStops ->
          progressBar.visibility = View.GONE
          setUpAutocompleteTextView(busStops)
          setUpCloseButtonImageView()
          addBusStopMarkers(busStops)
        }
      )
    }

    viewModel.loadBusStops()
  }

  private fun setUpMap(map: GoogleMap) {
    googleMap = map
    googleMap?.run {
      disableMapButtons()
      // We set the camera first in the default location
      moveToLatLng(defaultLocation, MAP_ZOOM)
      setOnInfoWindowClickListener {
        viewModel.onMapInfoWindowClicked(BusStopModel(it.title, it.snippet))
      }
    }
    checkForLocationPermission()
  }

  private fun checkForLocationPermission() {
    if (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      enableMyLocation()
    } else {
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
  }

  @SuppressLint("MissingPermission")
  private fun enableMyLocation() {
    googleMap?.isMyLocationEnabled = true
    myLocationFAB.visibility = View.VISIBLE
    myLocationFAB.setOnClickListener {
      viewModel.onMyLocationButtonClicked()
    }
    centerInMyLocation()
  }

  @SuppressLint("MissingPermission")
  private fun centerInMyLocation() {
    val locationResult = fusedLocationClient.lastLocation
    locationResult.addOnCompleteListener(requireActivity()) { task ->
      if (task.isSuccessful) {
        // Set the map's camera position to the current location of the device.
        val lastKnownLocation = task.result
        if (lastKnownLocation != null) {
          googleMap?.animateToLatLng(
            latLng = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude),
            zoom = MAP_ZOOM
          )
        }
      }
    }
  }

  private fun setUpAutocompleteTextView(busStops: List<BusStopSearch>) {
    val adapter = BusStopSearchAdapter(context = requireContext(), busStops = busStops)
    searchAutocompleteTextView.run {
      setAdapter(adapter)
      setOnItemClickListener { _, _, position, _ ->
        hideKeyboard()
        searchAutocompleteTextView.clearFocus()
        adapter.getItem(position)?.let { viewModel.onSuggestionItemClicked(it) }
      }
      isEnabled = true
      clearText()
      clearFocus()
    }
  }

  private fun setUpCloseButtonImageView() {
    closeButtonImageView.run {
      isEnabled = true
      setOnClickListener { viewModel.onClearTextButtonClicked() }
    }
  }

  private fun addBusStopMarkers(busStops: List<BusStopSearch>) {
    busStops.forEach { busStop ->
      val latLng = LatLng(busStop.coordinates.latitude, busStop.coordinates.longitude)

      googleMap?.addMarker(
        MarkerOptions()
          .position(latLng)
          .title(busStop.code)
          .snippet(busStop.name)
      )?.let { markerMap[busStop.id] = it }
    }
  }

  private fun showMapInfoWindow(busStopSearch: BusStopSearch) {
    // Set the text truncated in the edit text
    val width: Int = searchAutocompleteTextView.measuredWidth -
      (searchAutocompleteTextView.paddingLeft + searchAutocompleteTextView.paddingRight)

    val truncatedText = TextUtils.ellipsize(
      busStopSearch.toString(),
      searchAutocompleteTextView.paint,
      width.toFloat(),
      TextUtils.TruncateAt.END
    )
    if (truncatedText.isNotEmpty()) {
      searchAutocompleteTextView.setText(truncatedText)
    }

    markerMap[busStopSearch.id]?.showInfoWindow()
    googleMap?.animateToLatLng(
      latLng = LatLng(busStopSearch.coordinates.latitude, busStopSearch.coordinates.longitude),
      zoom = MAP_ZOOM
    )
  }

  private fun navigateToTimesScreen(busStopModel: BusStopModel) {
    navigateSafe(R.id.actionShowTimes, TimesDialogFragment.createArguments(busStopModel))
  }

  private fun clearSearchText() {
    searchAutocompleteTextView.run {
      clearText()
      showKeyboard()
    }
  }

  private fun setUpMenu() {
    val menuHost: MenuHost = requireActivity()

    menuHost.addMenuProvider(object : MenuProvider {
      override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
      }

      override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
          R.id.show_favorites_action -> {
            FavoritesDialogFragment(::navigateToTimesScreen)
              .show(childFragmentManager, "FavoritesDialogFragment")
            true
          }
          R.id.lines_action -> {
            navigateSafe(R.id.actionShowLines)
            true
          }
          R.id.about_action -> {
            navigateSafe(R.id.actionShowAbout)
            true
          }
          else -> false
        }
      }
    }, viewLifecycleOwner)
  }

  override fun onResume() {
    super.onResume()
    mapView?.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView?.onPause()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView?.onLowMemory()
  }

  override fun onDestroyView() {
    hideKeyboard()
    super.onDestroyView()
    mapView?.onDestroy()
  }
}
