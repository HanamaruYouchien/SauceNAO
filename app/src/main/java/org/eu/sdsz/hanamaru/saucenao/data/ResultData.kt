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

data class SaucenaoResult(
    @Json(name = "header")  val header: Header,
    @Json(name = "results") val results: List<Result>?
) {
    // the only filed required is status
    data class Header(
        @Json(name = "user_id")             val userId: Int?,
        @Json(name = "account_type")        val accountType: Int?,
        @Json(name = "short_limit")         val shortLimit: Int?,
        @Json(name = "long_limit")          val longLimit: Int?,
        @Json(name = "long_remaining")      val longRemaining: Int?,
        @Json(name = "short_remaining")     val shortRemaining: Int?,
        @Json(name = "status")              val status: Int,
        @Json(name = "results_requested")   val resultsRequested: Int?,
        @Json(name = "message")             val message: String?,
        @Json(name = "index")               val index: Map<Int, Index>?,
        @Json(name = "search_depth")        val searchDepth: Int?,
        @Json(name = "minimum_similarity")  val minimumSimilarity: Float?,
        @Json(name = "query_image_display") val queryImageDisplay: String?,
        @Json(name = "query_image")         val queryImage: String?,
        @Json(name = "results_returned")    val resultsReturned: Int?
    ) {
        data class Index(
            @Json(name = "status")    val status: Int,
            @Json(name = "parent_id") val parentId: Int,
            @Json(name = "id")        val id: Int,
            @Json(name = "results")   val results: Int
        )
    }

    data class Result(
        @Json(name = "header") val header: Header,
        @Json(name = "data")   val data: Map<String, Any>
    ) {
        data class Header(
            @Json(name = "similarity") val similarity: Float,
            @Json(name = "thumbnail")  val thumbnail: String,
            @Json(name = "index_id")   val indexId: Int,
            @Json(name = "index_name") val indexName: String,
            @Json(name = "dupes")      val dupes: Int,
            @Json(name = "hidden")     val hidden: Int
        )

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
}