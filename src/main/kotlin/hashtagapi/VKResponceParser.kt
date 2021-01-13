package hashtagapi

import kotlinx.serialization.json.*

data class HashtagInfo(val timestamp: Long)

fun parseVKResponse(response: JsonObject): Pair<List<HashtagInfo>, String?> {
    val res = mutableListOf<HashtagInfo>()
    val items = response["items"]!!.jsonArray
    for (i in items) {
        val item = i.jsonObject
        val timestamp = item["date"]!!.jsonPrimitive.long
        res.add(HashtagInfo(timestamp))
    }
    var continueMark: String? = null
    if ("next_from" in response) {
        continueMark = response["next_from"]!!.jsonPrimitive.toString()
    }
    return res to continueMark
}