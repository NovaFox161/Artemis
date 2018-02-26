package me.xaanit.artemis.internal

import me.xaanit.artemis.entities.Shard
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class WebsocketManager(val uri: URI, val client: Client) {

    private var websockets: Map<Int, Websocket> = mapOf()
    internal val counter = AtomicInteger(0)
    internal val identified = AtomicBoolean(false)
    internal val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(4)

    fun addWebsocket(shard: Int): Websocket {
        websockets += Pair(shard, Websocket(uri = uri, shard = shard, client = client, manager = this))
        client.shards += Shard(num = shard, client = client)
        val socket = websockets[shard]!!
        Thread { socket.run() }.start()
        return socket
    }

    fun get(shard: Shard): Websocket = websockets[shard.num]!!


    fun runIdentities() {
        val exec = Executors.newSingleThreadScheduledExecutor()
        val curr = AtomicInteger(1)
        exec.scheduleAtFixedRate({
            if (curr.get() > client.shardCount) {
                exec.shutdownNow()
            }
            websockets[curr.getAndIncrement()]?.identify()
        }, 0, (5.5 * 1000).toLong(), TimeUnit.MILLISECONDS)
    }
}