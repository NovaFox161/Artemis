package me.xaanit.artemis.internal.pojo.events

import me.xaanit.artemis.entities.Member
import me.xaanit.artemis.entities.TextChannel
import me.xaanit.artemis.entities.events.channels.GuildTypingStartEvent
import me.xaanit.artemis.internal.pojo.Handleable
import java.time.OffsetDateTime

class TypingStart(
        val user_id: String,
        val timestamp: Long,
        val channel_id: String
) : Handleable() {
    override fun handle() {
        //val offset: OffsetDateTime = OffsetDateTime.of(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC), ZoneOffset.UTC)

        val channel = clientObj.getChannelById(channel_id.toLong()) ?: return
        val member: Member = channel.guild!!.getMember(user_id.toLong()) ?: return

        clientObj.dispatcher.dispatch(GuildTypingStartEvent(
                member,
                OffsetDateTime.now(),
                channel as TextChannel
        ))
    }
}