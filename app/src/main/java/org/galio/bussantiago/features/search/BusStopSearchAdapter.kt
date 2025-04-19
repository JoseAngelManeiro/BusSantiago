package org.galio.bussantiago.features.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.core.model.BusStopSearch

class BusStopSearchAdapter(
  context: Context,
  busStops: List<BusStopSearch>,
  private val searchUtils: SearchUtils
) : ArrayAdapter<BusStopSearch>(context, 0, busStops) {

  private val originalData: List<BusStopSearch> = ArrayList(busStops)

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = convertView ?: LayoutInflater.from(context)
      .inflate(R.layout.busstop_item, parent, false)

    getItem(position)?.let { busStop ->
      view.findViewById<TextView>(R.id.codeTextView).apply {
        text = busStop.code
      }
      view.findViewById<TextView>(R.id.nameTextView).apply {
        text = busStop.name
      }
    }

    return view
  }

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        val filteredList = if (!constraint.isNullOrBlank()) {
          searchUtils.filterBusStops(originalData, constraint.toString())
        } else {
          emptyList()
        }

        results.values = filteredList
        results.count = filteredList.size
        return results
      }

      override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        clear()
        val filteredList = (results?.values as? List<*>)?.filterIsInstance<BusStopSearch>()
        if (!filteredList.isNullOrEmpty()) {
          addAll(filteredList)
          notifyDataSetChanged()
        }
      }
    }
  }
}
