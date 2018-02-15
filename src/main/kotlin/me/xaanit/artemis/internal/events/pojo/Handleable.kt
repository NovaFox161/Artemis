package me.xaanit.artemis.internal.events.pojo

import me.xaanit.artemis.entities.Shard
import me.xaanit.artemis.internal.Client

abstract class Handleable {

    internal lateinit var client: Client
    internal lateinit var shard: Shard
    abstract fun handle()

}