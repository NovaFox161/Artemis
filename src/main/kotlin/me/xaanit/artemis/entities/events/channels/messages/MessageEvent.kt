package me.xaanit.artemis.entities.events.channels.messages

import me.xaanit.artemis.entities.Message
import me.xaanit.artemis.entities.events.channels.ChannelEvent

open class MessageEvent(
        val message: Message
) : ChannelEvent(channel = message.channel)