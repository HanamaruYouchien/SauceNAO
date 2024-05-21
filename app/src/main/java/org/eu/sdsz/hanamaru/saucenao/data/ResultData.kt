package org.eu.sdsz.hanamaru.saucenao.data

data class Results(
    var similarity: Float,
    var indexId: Int,
    var extUrls: MutableList<String>
)

data class JsonResult(
    var resultsReturned: Int,
    var results: MutableList<Results>
)
