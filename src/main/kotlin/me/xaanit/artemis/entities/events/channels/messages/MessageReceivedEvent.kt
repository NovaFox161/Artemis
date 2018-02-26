package me.xaanit.artemis.entities.events.channels.messages

import me.xaanit.artemis.entities.Message

class MessageReceivedEvent(private val msg: Message) : MessageEvent(message = msg)