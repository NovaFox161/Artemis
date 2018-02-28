package me.xaanit.artemis.entities.events

import me.xaanit.artemis.entities.events.channels.GuildTypingStartEvent
import me.xaanit.artemis.entities.events.channels.messages.GuildMessageReceievedEvent
import me.xaanit.artemis.entities.events.guild.GuildCreateEvent
import me.xaanit.artemis.entities.events.shard.ShardReadyEvent

abstract class EventListener {
    open fun onReady(event: ReadyEvent) {}
    open fun onShardReady(event: ShardReadyEvent) {}
    open fun onTypingStart(event: GuildTypingStartEvent) {}
    open fun onMessage(event: GuildMessageReceievedEvent) {}
    open fun onGuildCreate(event: GuildCreateEvent) {}
}