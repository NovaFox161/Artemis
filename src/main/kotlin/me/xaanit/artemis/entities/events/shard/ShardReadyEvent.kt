package me.xaanit.artemis.entities.events.shard

import me.xaanit.artemis.entities.Shard
import me.xaanit.artemis.entities.events.Event

class ShardReadyEvent(val shard: Shard) : Event(client = shard.client)