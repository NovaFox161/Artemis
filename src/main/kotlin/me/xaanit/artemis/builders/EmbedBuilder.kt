package me.xaanit.artemis.builders

import me.xaanit.artemis.entities.embed.EmbedObject
import me.xaanit.artemis.entities.embed.EmbedObject.*
import java.awt.Color
import java.time.OffsetDateTime

class EmbedBuilder {
    var title: String? = null
    var url: String? = null
    var desc: String? = null
    var timestamp: OffsetDateTime? = null
    var color: Color = Color(0)
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

    fun build(): EmbedObject {
        return EmbedObject(
                title = title,
                url = url,
                author = author,
                timestamp = timestamp.toString(),
                description = desc,
//                color = color.colorSpace.rg
        )
    }

}