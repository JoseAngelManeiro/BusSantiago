package org.galio.bussantiago.features.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import org.galio.bussantiago.R
import org.galio.bussantiago.domain.model.BusStopSearch

class BusStopSearchAdapter(context: Context, busStops: List<BusStopSearch>) :
  ArrayAdapter<BusStopSearch>(context, 0, busStops) {

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
}
