package org.eu.sdsz.hanamaru.saucenao.data

import com.squareup.moshi.Json

val keysTitle = arrayOf(
    "title",
    "eng_name",
    "material",
    "source",
    "created_at"
)
val keysAuthor = arrayOf(
    "author",
    "author_name",
    "member_name",
    "pawoo_user_username",
    "twitter_user_handle",
    "company"
)

data class JsonHeader(
    @Json(name = "results_returned")
    val resultsReturned: Int
)

data class ResultHeader(
    @Json(name = "similarity")
    val similarity: String,
    @Json(name = "index_id")
    val index_id: Int,
    @Json(name = "thumbnail")
    val thumbnail: String
)


data class Result(
    @Json(name = "header")
    val header: ResultHeader,
    @Json(name = "data")
    val data: Map<String, Any>
) {
    fun getTitle(): Any? {
        for (key in keysTitle) {
            if (data.containsKey(key)) {
                return data[key]
            }
        }
        return null
    }
    fun getAuthor(): Any? {
        for (key in keysAuthor) {
            if (data.containsKey(key)) {
                return data[key]
            }
        }
        return null
    }
}

//@JsonClass(generateAdapter = true)
data class JsonResult(
    @Json(name = "header")
    val resultsReturned: JsonHeader?,
    @Json(name = "results")
    val results: List<Result>?
)
