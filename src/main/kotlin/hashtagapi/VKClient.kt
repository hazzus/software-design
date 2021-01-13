package hashtagapi

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.net.URL
import java.net.URLEncoder
import java.time.Instant

interface Client {
    fun getData(hashtag: String, startTime: Long, continueMark: String? = null): Pair<List<HashtagInfo>, String?>
}

class VKClient : Client {
    private val version = "5.58"
    // token is my app service key
    private val token = "9a0df1319a0df1319a0df131cc9a566ed599a0d9a0df131c30d26a259f4264b29a0f979"

    override fun getData(hashtag: String, startTime: Long, continueMark: String?): Pair<List<HashtagInfo>, String?> {
        val validHashtag = URLEncoder.encode(if (hashtag.startsWith('#')) hashtag else "#$hashtag", "UTF-8")
        val params = hashMapOf(
            "start_time" to startTime.toString(),
            "count" to "200",
            "q" to validHashtag
        )
        continueMark?.let {
            params["start_from"] = continueMark
        }
        val url = buildRequestUrl(params)
        val response = Json.parseToJsonElement(URL(url).readText()).jsonObject
        if ("error" in response) {
            throw VKError(response["error"]!!.jsonObject)
        }
        return parseVKResponse(response["response"]!!.jsonObject)
    }

    private fun buildRequestUrl(paramsMap: HashMap<String, String>): String {
        val parameters = paramsMap.map {
            "${it.key}=${it.value}"
        }.joinToString("&")
        return "https://api.vk.com/method/newsfeed.search?$parameters&access_token=$token&v=$version"
    }
}