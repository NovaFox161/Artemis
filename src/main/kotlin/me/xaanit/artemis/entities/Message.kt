package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client
import java.time.OffsetDateTime

class Message(
        val id: Long,
        val content: String,
        val formattedContent: String,
        val userMentions: List<User>,
        val roleMentions: List<Role>,
        val author: Member,
        val timestamp: OffsetDateTime,
        val tts: Boolean,
        val mentionsEveryone: Boolean,
        // val attachments: List<Attachment>,
        // val embeds: List<Embed>,
        // val reactions: List<Reaction>
        val pinned: Boolean,
        val webhookId: Long?,
        val type: Type,
        val channel: Channel,
        val client: Client = channel.client
) {


    enum class Type(val code: Int) {
        DEFAULT(0),
        RECIPIENT_ADD(1),
        RECIPIENT_REMOVE(2),
        CALL(3),
        CHANNEL_NAME_CHANGE(4),
        CHANNEL_ICON_CHANGE(5),
        CHANNEL_PIN_MESSAGE(6),
        MEMBER_JOIN(7),
        UNKNOWN(Int.MIN_VALUE);

        companion object {
            fun valueOf(value: Int): Type {
                values().forEach {
                    if(it.code == value) return it
                }
                return UNKNOWN
            }
        }
    }
}