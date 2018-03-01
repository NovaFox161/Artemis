package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.pojo.message.AttachmentPojo
import me.xaanit.artemis.internal.pojo.message.embed.EmbedPojo
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
        val editedTimestamp: OffsetDateTime?,
        private val attachmentData: List<AttachmentPojo>? = null,
        // private val builtAttachments: List<Attachment>? = null
        private val embedData: List<EmbedPojo>? = null,
        private val builtEmbeds: List<EmbedObject>? = null,
        // private val reactions: List<ReactionPojo>? = null,
        val pinned: Boolean,
        val webhookId: Long?,
        val type: Type,
        val channel: Channel,
        val client: Client = channel.client
) {

    val embeds: List<EmbedObject> = builtEmbeds ?: embedData.make()


    fun List<EmbedPojo>?.make(): List<EmbedObject> {
        if (this == null) return listOf()
        var list: List<EmbedObject> = listOf()
        forEach {
            var fields: List<EmbedObject.EmbedField> = listOf()
            it.fields.forEach {
                fields += EmbedObject.EmbedField(it.name, it.value, it.inline)
            }

            list += EmbedObject(
                    title = it.title,
                    description = it.description,
                    fields = fields,
                    author = EmbedObject.EmbedAuthor(name = it.author?.name, icon_url = it.author?.icon_url, proxy_icon_url = it.author?.proxy_icon_url, url = it.author?.url),
                    colorInt = it.color,
                    footer = EmbedObject.EmbedFooter(text = it.footer?.text, icon_url = it.footer?.icon_url, proxy_icon_url = it.footer?.proxy_icon_url),
                    image = EmbedObject.EmbedImage(url = it.image?.url, proxy_url = it.image?.proxy_url, height = it.image?.height, width = it.image?.width),
                    thumbnail = EmbedObject.EmbedImage(url = it.thumbnail?.url, proxy_url = it.thumbnail?.proxy_url, height = it.thumbnail?.height, width = it.thumbnail?.width),
                    timestampString = it.timestamp,
                    url = it.url
            )
        }

        return list
    }


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
                    if (it.code == value) return it
                }
                return UNKNOWN
            }
        }
    }
}