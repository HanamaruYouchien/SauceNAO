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
const val keyCreator = "creator"
const val keyUrls = "ext_urls"
const val keyGetchu = "getchu_id"

const val STATUS_OK = 0
const val STATUS_URL_NOT_USABLE = -3
const val STATUS_EMPTY_IMAGE = -4
data class JsonHeader(
    @Json(name = "status")
    val status: Int,
    @Json(name = "message")
    val message: String?,
    @Json(name = "results_returned")
    val resultsReturned: Int?,
)

data class ResultHeader(
    @Json(name = "similarity")
    val similarity: Float,
    @Json(name = "index_id")
    val indexId: Int,
    @Json(name = "thumbnail")
    val thumbnail: String
)


data class Result(
    @Json(name = "header")
    val header: ResultHeader,
    @Json(name = "data")
    val data: Map<String, Any>
) {
    fun getTitle(): String {
        for (key in keysTitle) {
            if (data.containsKey(key)) {
                return data[key].toString()
            }
        }
        return ""
    }
    fun getAuthor(): String {
        for (key in keysAuthor) {
            if (data.containsKey(key)) {
                return data[key].toString()
            }
        }
        if (data.containsKey(keyCreator)) {
            if (data[keyCreator] is String) {
                return data[keyCreator].toString()
            } else if (data[keyCreator] is List<*>) {
                return (data[keyCreator] as List<*>)[0].toString()
            }
        }
        return ""
    }

    fun getUrls(): List<String> {
        if (data.containsKey(keyUrls)) {
            @Suppress("UNCHECKED_CAST")
            return data[keyUrls] as List<String> // always List<String> if ext_urls set
        }
        if (data.containsKey(keyGetchu)) {
            return listOf("http://www.getchu.com/soft.phtml?id=${data[keyGetchu].toString()}")
        }
        return listOf()
    }
}

data class JsonResult(
    @Json(name = "header")
    val resultsReturned: JsonHeader,
    @Json(name = "results")
    val results: List<Result>?
)
