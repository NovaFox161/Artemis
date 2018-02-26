package me.xaanit.artemis.entities.events.channels

import me.xaanit.artemis.entities.Channel
import me.xaanit.artemis.entities.events.Event

open class ChannelEvent(
        val channel: Channel
) : Event(client = channel.client)