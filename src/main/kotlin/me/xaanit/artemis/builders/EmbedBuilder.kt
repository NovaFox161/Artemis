package me.xaanit.artemis.builders

import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.entities.embed.EmbedObject.*
import java.awt.Color
import java.time.OffsetDateTime

class EmbedBuilder {
    private var title: String? = null
    private var url: String? = null
    private var desc: String? = null
    private var timestamp: OffsetDateTime? = null
    private var color: Color = Color(0)
    private var footer: EmbedFooter? = null
    private var author: EmbedAuthor? = null
    private var thumbnail: EmbedImage? = null
    private var image: EmbedImage? = null
    private var fields: Array<EmbedField> = arrayOf()

    fun withAuthor(
            name: String? = null,
            url: String? = null,
            iconUrl: String? = null
    ): EmbedBuilder {
        this.author = EmbedAuthor(name, url, iconUrl)
        return this
    }

    fun withFooter(
            text: String? = null,
            iconUrl: String? = null
    ): EmbedBuilder {
        this.footer = EmbedFooter(text, iconUrl)
        return this
    }

    fun withThumbnail(url: String? = null): EmbedBuilder {
        this.thumbnail = EmbedImage(url)
        return this
    }

    fun withImage(url: String? = null): EmbedBuilder {
        this.image = EmbedImage(url)
        return this
    }

    fun appendField(
            name: String = "\u200B",
            text: String = "\u200B",
            inline: Boolean = false
    ): EmbedBuilder {
        fields += EmbedField(name, text, inline)
        return this
    }

    fun withTitle(title: String? = null): EmbedBuilder {
        this.title = title
        return this
    }

    fun withUrl(url: String? = null): EmbedBuilder {
        this.url = url
        return this
    }

    fun withDescription(desc: String? = null): EmbedBuilder {
        this.desc = desc
        return this
    }

    fun withTimestamp(timestamp: OffsetDateTime? = null): EmbedBuilder {
        this.timestamp = timestamp
        return this
    }

    fun withColor(color: Color? = null): EmbedBuilder {
        this.color = color ?: this.color
        return this
    }

    fun build(): EmbedObject {
        return EmbedObject(
                title = title,
                url = url,
                author = author,
                timestamp = timestamp?.toString(),
                description = desc,
                color = ((color.red shl 16) + (color.green shl 8) + (color.blue))
        )
    }

}