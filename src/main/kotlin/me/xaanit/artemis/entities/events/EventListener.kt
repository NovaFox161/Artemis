package me.xaanit.artemis.entities.events

import me.xaanit.artemis.entities.events.channels.TypingStartEvent
import me.xaanit.artemis.entities.events.channels.messages.MessageReceivedEvent
import me.xaanit.artemis.entities.events.guild.GuildCreateEvent
import me.xaanit.artemis.entities.events.shard.ShardReadyEvent

open abstract class EventListener {
    open fun onReady(event: ReadyEvent) {}
    open fun onShardReady(event: ShardReadyEvent) {}
    open fun onTypingStart(event: TypingStartEvent) {}
    open fun onMessageReceived(event: MessageReceivedEvent) {}
    open fun onGuildCreate(event: GuildCreateEvent) {}
}