package me.xaanit.artemis.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import me.xaanit.artemis.entities.Guild
import me.xaanit.artemis.entities.Shard
import me.xaanit.artemis.internal.Websocket
import java.util.stream.Stream

object Extensions {

    internal val parser = JsonParser()
    internal val seraliseNulls = GsonBuilder().serializeNulls().create()
    internal val noNulls = GsonBuilder().create()

    fun <T> Array<T>.stream(): Stream<T> = toList().stream()

    fun <T> Stream<T>.firstFound(): T? = findFirst().orElse(null)

    fun String.jsonObject(): JsonObject = parser.parse(this).asJsonObject

    fun Any.jsonObject(): JsonObject = seraliseNulls.toJsonTree(this).asJsonObject

    fun Any?.json(): String = seraliseNulls.toJson(this)

    fun Websocket.send(obj: JsonObject) {
        val json = obj.json()
        logger.trace("&cyan[&time] Trying to send message with json: $json")
        websocket.sendText(json)
    }

    fun Guild.shard(): Shard {
        return client.shards[((id shr 22) % client.shardCount).toInt()]
    }

}