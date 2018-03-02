package me.xaanit.artemis.entities.events.channels.messages

import me.xaanit.artemis.entities.Member
import me.xaanit.artemis.entities.Message
import me.xaanit.artemis.entities.TextChannel
import me.xaanit.artemis.entities.events.Event

class GuildMessageReceievedEvent(
        val message: Message,
        val channel: TextChannel = message.channel as TextChannel,
        val author: Member = message.author.getMember(channel.guild!!)!!
) : Event(client = message.client)