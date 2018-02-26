package me.xaanit.artemis.entities.events.channels

import me.xaanit.artemis.entities.Member
import me.xaanit.artemis.entities.TextChannel
import java.time.OffsetDateTime

class TypingStartEvent(
        val member: Member,
        val timestamp: OffsetDateTime,
        private val chnl: TextChannel
) : ChannelEvent(channel = chnl)