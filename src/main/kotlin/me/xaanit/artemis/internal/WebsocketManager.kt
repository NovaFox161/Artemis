package me.xaanit.artemis.internal

import me.xaanit.artemis.entities.Shard
import java.net.URI
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

class WebsocketManager(val uri: URI, val client: Client) {

    private var websockets: Map<Int, Websocket> = mapOf()

    fun addWebsocket(shard: Int): Websocket {
        websockets += Pair(shard, Websocket(uri = uri, shard = shard, client = client, manager = this))
        client.shards += Shard(num = shard, client = client)
        return websockets[shard]!!
    }

    fun get(shard: Shard): Websocket = websockets[shard.num]!!

    fun run() {
        websockets.forEach { _, w -> w.run() }

        val exec = Executors.newSingleThreadScheduledExecutor()
        val curr = AtomicInteger(1)
        exec.scheduleAtFixedRate({
            if(curr.get() > client.shardCount)
                exec.shutdownNow()
            websockets[curr.getAndIncrement()]?.identify()
        }, 0, (5.5 * 1000).toLong(), TimeUnit.SECONDS)
    }
}