package me.xaanit.artemis.internal.events.pojo

import me.xaanit.artemis.entities.Shard
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.Websocket

abstract class Handleable {
    internal lateinit var shardObj: Shard
    internal val clientObj: Client
        get() = shardObj.client
    internal val websocketObj: Websocket
        get() = clientObj.manager.get(shardObj)

    abstract fun handle()

}