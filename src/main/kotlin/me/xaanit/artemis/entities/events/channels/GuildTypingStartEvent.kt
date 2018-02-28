package me.xaanit.artemis.entities.events.channels

import me.xaanit.artemis.entities.Member
import me.xaanit.artemis.entities.TextChannel
import me.xaanit.artemis.entities.events.Event
import java.time.OffsetDateTime

class GuildTypingStartEvent(
        val member: Member,
        val timestamp: OffsetDateTime,
        val channel: TextChannel
) : Event(client = member.client)