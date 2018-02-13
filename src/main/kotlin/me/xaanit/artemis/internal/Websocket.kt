package me.xaanit.artemis.internal

import com.github.salomonbrys.kotson.put
import com.google.gson.JsonObject
import me.xaanit.artemis.internal.logger.Logger
import me.xaanit.artemis.util.Extensions.json
import me.xaanit.artemis.util.Extensions.jsonObject
import me.xaanit.artemis.util.Extensions.toJsonArray
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class Websocket(uri: URI, val shard: Int, private val client: Client, val manager: WebsocketManager) : WebSocketClient(uri) {
    private val logger = Logger.getLogger("Artemis Websocket")
    private val sendHeartbeat = AtomicBoolean(true)
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var s: Int? = null

    override fun onOpen(handshakedata: ServerHandshake?) {
        logger.trace("&cyan[&time] Got handshake from websocket with shard #$shard.")
        logger.trace("&cyan[&time] HttpStatusMessage: ${handshakedata?.httpStatusMessage}")
        logger.trace("&cyan[&time] HttpStatus: ${handshakedata?.httpStatus}")
        logger.trace("&cyan[&time] Content: ${handshakedata?.content}")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        logger.trace("&cyan[&time] Websocket closed, shard #$shard")
        logger.trace("&cyan[&time] Code: $code")
        logger.trace("&cyan[&time] Reason: $reason")
        logger.trace("&cyan[&time] Remote: $remote")
        sendHeartbeat.set(false)
    }

    override fun onMessage(message: String?) {
        logger.trace("&cyan[&time] Received message from websocket on shard #$shard: $message")
        val obj = message?.jsonObject()
        s = try {
            obj?.get("s")?.asInt
        } catch (ex: UnsupportedOperationException) {
            null
        }
        when (obj?.get("op")?.asInt) {
            10 -> startHeartbeat(obj?.get("d")?.asJsonObject?.get("heartbeat_interval")?.asLong)
        }
    }

    override fun onError(ex: Exception?) {
        logger.trace("&cyan[&time] Received error from websocket on shard #$shard.")
        ex?.printStackTrace()
    }

    private fun startHeartbeat(delay: Long?) {
        logger.trace("&cyan[&time] Starting heartbeat thread with delay: $delay")
        executor.scheduleAtFixedRate({
            if (!sendHeartbeat.get()) executor.shutdownNow()
            val heartbeat = JsonObject()
            heartbeat.put(Pair("op", 1))
            heartbeat.put(Pair("d", s))
            val json = heartbeat.json()
            logger.trace("&cyan[&time] Sending heartbeat. $json")
            send(json)
        }, 0, delay!!, TimeUnit.MILLISECONDS)
    }

    internal fun identify() {
        val presence = JsonObject()
        val game = JsonObject()
        game.put(Pair("name", "Running with Artemis BETA 1.0"))
        game.put(Pair("type", 0))
        presence.put(Pair("game", game))
        presence.put(Pair("status", client.status.toString().toLowerCase()))
        presence.put(Pair("since", 91879201))
        presence.put(Pair("afk", false))

        val properties = JsonObject()
        properties.put(Pair("\$os", "windows"))
        properties.put(Pair("\$browser", "Artemis"))
        properties.put(Pair("\$device", "Artemis"))

        val identity = JsonObject()
        identity.put(Pair("token", client.token))
        identity.put(Pair("compress", true))
        identity.put(Pair("large_threshold", 250))
        identity.put(Pair("presence", presence))
        identity.put(Pair("properties", properties))
        if (client.shardCount != 1)
            identity.put(Pair("shard", arrayOf(shard, client.shardCount).toJsonArray()))

        val full = JsonObject()
        full.put(Pair("op", 2))
        full.put(Pair("d", identity))
        val json = full.json()
        logger.trace("&cyan[&time] Identifying: ${json.replace("\"token\":\"${client.token}\"", "\"token\":\"hunter3\"")}")

        send(json)
    }


}