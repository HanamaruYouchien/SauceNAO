package org.eu.sdsz.hanamaru.saucenao.process

data class Results(
    var similarity: Float,
    var index_id: Int,
    var ext_urls: MutableList<String>?
)

data class jsonResult(
    var ResultsReturned: Int,
    var Results: MutableList<Results>?
)
