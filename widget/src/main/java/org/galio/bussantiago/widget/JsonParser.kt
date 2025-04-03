package org.galio.bussantiago.widget

import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.SynopticModel
import org.json.JSONException
import org.json.JSONObject

class JsonParser {

  fun toRemainingTimeModels(jsonString: String): List<LineRemainingTimeModel> {
    val arrayListTimes = mutableListOf<LineRemainingTimeModel>()

    try {
      val jsonObjectResult = JSONObject(jsonString)
      val jsonArrayLines = jsonObjectResult.getJSONArray("lineas")
      var jsonObjectLine: JSONObject
      var lineRemainingTimeModel: LineRemainingTimeModel
      for (i in 0 until jsonArrayLines.length()) {
        jsonObjectLine = jsonArrayLines[i] as JSONObject
        lineRemainingTimeModel = LineRemainingTimeModel(
          SynopticModel(
            jsonObjectLine.getString("sinoptico"),
            jsonObjectLine.getString("estilo")
          ),
          jsonObjectLine.getInt("minutosProximoPaso")
        )
        arrayListTimes.add(lineRemainingTimeModel)
      }
    } catch (e: JSONException) {
      // If there is an exception parsing the Json, simply return the empty list
    }

    return arrayListTimes
  }
}
