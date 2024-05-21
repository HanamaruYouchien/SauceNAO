package org.eu.sdsz.hanamaru.saucenao.process

import org.json.JSONObject

fun processjson(jsonString: String?) : jsonResult {
    val Result : jsonResult
    Result = jsonResult(0, mutableListOf())
    val jsonObject = JSONObject(jsonString)
    val resultArray = jsonObject.getJSONArray("results")

    Result.ResultsReturned = jsonObject.getJSONObject("header").getInt("results_returned")
    for (i in 0 until resultArray.length()) {
        val result = resultArray.getJSONObject(i)
        val curResult : Results
        curResult = Results(1f,-1, mutableListOf())
        curResult.similarity = result.getJSONObject("header").getString("similarity").toFloat()
        curResult.index_id = result.getJSONObject("header").getInt("index_id")
        if (result.getJSONObject("data").has("ext_urls")) {
            val UrlArray = result.getJSONObject("data").getJSONArray("ext_urls")
            for (j in 0 until UrlArray.length()) {
                val test = UrlArray.getString(j)
                curResult.ext_urls?.add(test)
            }
        }
        Result.Results?.add(curResult)
    }

    return Result
}