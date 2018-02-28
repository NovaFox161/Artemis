package me.xaanit.artemis.entities.embed

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject

class EmbedObject(
        val title: String? = null,
        val type: String = "rich",
        val description: String? = null,
        val url: String? = null,
        val timestamp: String? = null,
        val color: Int? = null,
        val footer: EmbedFooter? = null,
        val image: EmbedImage? = null,
        val thumbnail: EmbedImage? = null,
        val provider: EmbedProvider? = null,
        val author: EmbedAuthor? = null
) {
    class EmbedFooter(
            val text: String? = null,
            val icon_url: String? = null,
            val proxy_icon_url: String? = null
    )

    class EmbedImage(
            val url: String? = null,
            val proxy_url: String? = null,
            val height: Int? = null,
            val width: Int? = null
    )

    class EmbedVideo(
            val url: String? = null,
            val width: Int? = null,
            val height: Int? = null
    )


    class EmbedProvider(
            val name: String? = null,
            val url: String? = null
    )

    class EmbedAuthor(
            val name: String? = null,
            val url: String? = null,
            val icon_url: String? = null,
            val proxy_icon_url: String? = null
    )

    companion object {
        private val gson = GsonBuilder().create()
    }
    internal fun json(): JsonObject {
        return gson.toJsonTree(this).asJsonObject
    }
}