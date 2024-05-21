package org.eu.sdsz.hanamaru.saucenao.process

import org.eu.sdsz.hanamaru.saucenao.data.JsonResult
import org.eu.sdsz.hanamaru.saucenao.data.Results
import org.json.JSONObject

fun processJson(jsonString: String) : JsonResult {
    val jsonResult = JsonResult(0, mutableListOf())
    val jsonObject = JSONObject(jsonString)
    val resultArray = jsonObject.getJSONArray("results")

    jsonResult.resultsReturned = jsonObject.getJSONObject("header").getInt("results_returned")
    for (i in 0 until resultArray.length()) {
        val result = resultArray.getJSONObject(i)
        val curResult = Results(
            similarity = result.getJSONObject("header").getString("similarity").toFloat(),
            indexId = result.getJSONObject("header").getInt("index_id"),
            extUrls = mutableListOf<String>()
        )
        if (result.getJSONObject("data").has("ext_urls")) {
            val urlArray = result.getJSONObject("data").getJSONArray("ext_urls")
            for (j in 0 until urlArray.length()) {
                val test = urlArray.getString(j)
                curResult.extUrls.add(test)
            }
        }
        jsonResult.results.add(curResult)
    }

    return jsonResult
}