package me.xaanit.artemis.internal

import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.put
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import me.xaanit.artemis.internal.events.pojo.events.GuildCreate
import me.xaanit.artemis.internal.logger.Logger
import me.xaanit.artemis.util.Extensions.json
import me.xaanit.artemis.util.Extensions.jsonObject
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

    companion object {
        private val gson = GsonBuilder().serializeNulls().create()
    }

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
        try {
            when (obj?.get("op")?.asInt) {
                10 -> {
                    startHeartbeat(obj?.get("d")?.asJsonObject?.get("heartbeat_interval")?.asLong)
                }
            }
        } catch (ex: UnsupportedOperationException) {
        }

        try {
            when (obj?.get("t")?.asString) {
                "GUILD_CREATE" -> logger.trace("&cyan[&time] Received GUILD_CREATE, created obj: ${gson.fromJson(message, GuildCreate::class.java)}")
            }
        } catch (ex: UnsupportedOperationException) {
        }
    }

    override fun onError(ex: Exception?) {
        logger.trace("&cyan[&time] Received error from websocket on shard #$shard.")
        ex?.printStackTrace()
    }

    private fun startHeartbeat(delay: Long?) {
        logger.trace("&cyan[&time] Starting heartbeat thread on shard #$shard with delay: $delay")
        executor.scheduleAtFixedRate({
            if (!sendHeartbeat.get()) executor.shutdownNow()
            val heartbeat = JsonObject()
            heartbeat.put(Pair("op", 1))
            heartbeat.put(Pair("d", s))
            val json = heartbeat.json()
            logger.trace("&cyan[&time] Sending heartbeat on shard #$shard. $json")
            send(json)
        }, 0, delay!!, TimeUnit.MILLISECONDS)
    }

    fun identify() {
   /*     val presence = JsonObject()

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
        identity.put(Pair("compress", false))
        identity.put(Pair("large_threshold", 250))
        identity.put(Pair("presence", presence))
        identity.put(Pair("properties", properties))
        if (client.shardCount != 1)
            identity.put(Pair("shard", arrayOf(shard - 1, client.shardCount).toJsonArray()))

        val full = JsonObject()
        full.put(Pair("op", 2))
        full.put(Pair("d", identity))*/

        val base: JsonObject = jsonObject(
                "op" to 2,
                "d" to jsonObject(
                        "token" to client.token,
                        "compress" to false,
                        "large_threshold" to 250,
                        "presence" to jsonObject(
                                "game" to jsonObject(
                                        "name" to "Event dispatching",
                                        "type" to 0
                                ),
                                "status" to client.status.toString(),
                                "since" to 91879201,
                                "afk" to false
                        ),
                        "properties" to jsonObject(
                                "\$os" to "windows",
                                "\$browser" to "Artemis",
                                "\$device" to "Artemis"
                        ),
                    "shard" to jsonArray(
                            shard - 1,
                            client.shardCount
                    )
                )
        )
        val json = base.json()
        logger.trace("&cyan[&time] Identifying on shard #$shard: ${json.replace("\"token\":\"${client.token}\"", "\"token\":\"hunter3\"")}")

        send(json)
    }


}