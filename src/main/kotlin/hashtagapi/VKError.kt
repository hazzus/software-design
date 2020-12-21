package hashtagapi

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import java.lang.Exception

class VKError(jsonObject: JsonObject) : Exception(jsonObject["error_msg"].toString()) {
    private val errorCode: Int = jsonObject["error_code"]!!.jsonPrimitive.int
}