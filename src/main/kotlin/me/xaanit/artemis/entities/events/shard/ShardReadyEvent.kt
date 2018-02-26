package me.xaanit.artemis.entities.events.shard

import me.xaanit.artemis.entities.Shard

class ShardReadyEvent(private val s: Shard): ShardEvent(shard = s)