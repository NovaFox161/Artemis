package me.xaanit.artemis.internal.pojo.message

import me.xaanit.artemis.entities.Member
import me.xaanit.artemis.entities.Message
import me.xaanit.artemis.entities.events.channels.messages.GuildMessageReceievedEvent
import me.xaanit.artemis.internal.pojo.Makeable
import me.xaanit.artemis.internal.pojo.message.embed.EmbedPojo
import me.xaanit.artemis.internal.pojo.user.UserPojo
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class MessagePojo(
        val type: Int,
        val tts: Boolean,
        val timestamp: String,
        val pinned: Boolean,
        val nonce: Long?,
        val mention_everyone: Boolean,
        val id: String,
        val edited_timestamp: String?,
        val content: String,
        val channel_id: String,
        val mentions: Array<UserPojo>,
        val mention_roles: Array<String>,
        val author: UserPojo,
        val attachments: Array<AttachmentPojo>,
        val embeds: Array<EmbedPojo>
) : Makeable<Message?>() {
    override fun make(): Message? {
        if (author.discriminator == "0000") return null
        val channel = clientObj.getChannelById(channel_id.toLong()) ?: return null
        val guild = channel.guild!!
        val member: Member = guild.getMember(author.id.toLong())!!
        return Message(
                id = id.toLong(),
                content = content,
                client = clientObj,
                type = Message.Type.valueOf(type),
                author = member,
                channel = clientObj.getChannelById(channel_id.toLong())!!,
                formattedContent = content.discordFormat(),
                mentionsEveryone = mention_everyone,
                pinned = pinned,
                timestamp = OffsetDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME),
                editedTimestamp = if (edited_timestamp != null) OffsetDateTime.parse(edited_timestamp, DateTimeFormatter.ISO_DATE_TIME) else null,
                tts = tts,
                webhookId = null,
                userMentions = listOf(),
                roleMentions = listOf()
        )

    }

    override fun handle() {
        val message = make()
        if (message != null)
            clientObj.dispatcher.dispatch(GuildMessageReceievedEvent(message))
    }

    private fun String.discordFormat(): String {
        return this
    }


}