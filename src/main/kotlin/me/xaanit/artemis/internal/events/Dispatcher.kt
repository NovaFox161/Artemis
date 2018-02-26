package me.xaanit.artemis.internal.events

import me.xaanit.artemis.entities.events.Event
import me.xaanit.artemis.entities.events.EventListener
import me.xaanit.artemis.entities.events.ReadyEvent
import me.xaanit.artemis.entities.events.channels.TypingStartEvent
import me.xaanit.artemis.entities.events.channels.messages.MessageReceivedEvent
import me.xaanit.artemis.entities.events.guild.GuildCreateEvent
import me.xaanit.artemis.entities.events.shard.ShardReadyEvent
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.logger.Logger
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class Dispatcher(val client: Client) {

    private val logger: Logger = Logger.getLogger(Dispatcher::class.java)
    private var eventClasses: List<EventListener> = listOf()
    private val executor: ScheduledExecutorService = Executors.newScheduledThreadPool(4)

    fun register(vararg objs: EventListener) {
        eventClasses += objs
    }

    fun dispatch(event: Event) {
        executor.schedule({
            logger.trace("&cyan[&time] Dispatching event of type: ${event.javaClass.simpleName}")
            eventClasses.forEach {
                when (event) {
                    is ReadyEvent -> it.onReady(event)
                    is ShardReadyEvent -> it.onShardReady(event)
                    is GuildCreateEvent -> it.onGuildCreate(event)
                    is TypingStartEvent -> it.onTypingStart(event)
                    is MessageReceivedEvent -> it.onMessageReceived(event)
                }
            }
        }, 0, TimeUnit.MICROSECONDS)
    }
}