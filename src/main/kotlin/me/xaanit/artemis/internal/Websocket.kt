package me.xaanit.artemis.internal

import com.github.salomonbrys.kotson.jsonArray
import com.github.salomonbrys.kotson.jsonObject
import com.github.salomonbrys.kotson.put
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.neovisionaries.ws.client.*
import me.xaanit.artemis.internal.events.pojo.Handleable
import me.xaanit.artemis.internal.events.pojo.events.*
import me.xaanit.artemis.internal.events.pojo.message.MessagePojo
import me.xaanit.artemis.internal.logger.Logger
import me.xaanit.artemis.util.Extensions.json
import me.xaanit.artemis.util.Extensions.jsonObject
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class Websocket(uri: URI, val shard: Int, private val client: Client, val manager: WebsocketManager) : WebSocketAdapter() {
    internal val logger = Logger.getLogger("Artemis Websocket")
    private val sendHeartbeat = AtomicBoolean(true)
    private val executor = Executors.newSingleThreadScheduledExecutor()
    private var s: Int? = null
    internal val counter = AtomicInteger(0)
    internal var guildSize: Int = -1
    private var websocket: WebSocket

    companion object {
        private val gson = GsonBuilder().serializeNulls().create()
        private val factory = WebSocketFactory()
    }

    init {
        websocket = factory.createSocket(uri)
        websocket.addListener(this)
        // I personally think the best way to handle the error is let it slide through the stack.
        // Use connectAsynchronously if you disagree.
        websocket.connect()
    }

    override fun onConnected(websocket: WebSocket?, headers: MutableMap<String, MutableList<String>>?) {
        logger.trace("&cyan[&time] Got handshake from websocket with shard #$shard.")
        logger.trace("&cyan[&time] HTTP Headers: ${headers?.map { "${it.key}: ${it.value}" }?.joinToString(", ")}")
    }

    override fun onDisconnected(websocket: WebSocket?, serverCloseFrame: WebSocketFrame?, clientCloseFrame: WebSocketFrame?, closedByServer: Boolean) {
        logger.trace("&cyan[&time] Websocket closed, shard #$shard")
        if (serverCloseFrame != null) {
            logger.trace("&cyan[&time] Server close: Code: ${serverCloseFrame.closeCode}")
            logger.trace("&cyan[&time] Server close: Reason: ${serverCloseFrame.closeReason}")
        }
        if (clientCloseFrame != null) {
            logger.trace("&cyan[&time] Client close: Code: ${clientCloseFrame.closeCode}")
            logger.trace("&cyan[&time] Client close: Reason: ${clientCloseFrame.closeReason}")
        }
        logger.trace("&cyan[&time] Remote: $closedByServer")
        sendHeartbeat.set(false)
    }

    override fun onTextMessage(websocket: WebSocket?, message: String?) {
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
                    startHeartbeat(obj.get("d")?.asJsonObject?.get("heartbeat_interval")?.asLong)
                }
                11 -> {
                    if(!manager.identified.get()) {
                        manager.identified.set(true)
                        manager.runIdentities()
                    }
                }
            }
        } catch (ex: UnsupportedOperationException) {
        }

        try {
            manager.executor.schedule({ handleEvents(obj?.get("t")?.asString!!, gson.toJson(obj?.get("d"))) }, 0, TimeUnit.MICROSECONDS)
        } catch (ex: Exception) {
        }
    }


    override fun onError(websocket: WebSocket?, ex: WebSocketException?) {
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
            websocket.sendText(json)
        }, 0, delay!!, TimeUnit.MILLISECONDS)
    }

    fun identify() {
        val json: String = jsonObject(
                "op" to 2,
                "d" to jsonObject(
                        "token" to client.token,
                        "compress" to false,
                        "large_threshold" to 250,
                        "presence" to jsonObject(
                                "game" to jsonObject(
                                        "name" to "Running Artemis BETA 1.0",
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
        ).json()
        logger.trace("&cyan[&time] Identifying on shard #$shard: ${json.replace("\"token\":\"${client.token}\"", "\"token\":\"hunter3\"")}")

        websocket.sendText(json)
    }


    private fun handleEvents(event: String, data: String) {
        try {
            val clazz: Class<out Handleable> = when (event) {
                "GUILD_CREATE" -> GuildCreate::class.java
                "MESSAGE_DELETE" -> MessageDelete::class.java
                "READY" -> Ready::class.java
                "MESSAGE_CREATE" -> MessagePojo::class.java
                "TYPING_START" -> TypingStart::class.java
                "GUILD_MEMBERS_CHUNK" -> GuildMemberChunkPojo::class.java
                else -> null
            } ?: return

            logger.trace("&cyan[&time] Trying to handle event of type: $event")

            val obj = gson.fromJson(data, clazz) ?: throw RuntimeException("You should never see this.")
            synchronized(obj) {
                obj.shardObj = client.shards[shard - 1]
                obj.handle()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    // TODO: Reconnect logic
    // TODO: Helper send method family
    // TODO: zlib compression
}