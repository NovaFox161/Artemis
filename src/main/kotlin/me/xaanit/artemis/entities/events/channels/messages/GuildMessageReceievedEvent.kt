package me.xaanit.artemis.entities.events.channels.messages

import me.xaanit.artemis.entities.Message
import me.xaanit.artemis.entities.events.Event

class GuildMessageReceievedEvent(val message: Message) : Event(client = message.client)