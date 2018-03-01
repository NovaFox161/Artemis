package me.xaanit.artemis.internal.pojo.message.embed

class EmbedPojo(
        val type: String,
        val title: String? = null,
        val url: String? = null,
        val timestamp: String? = null,
        val color: Int,
        val description: String? = null,
        val author: EmbedAuthorPojo? = null,
        val fields: Array<EmbedFieldPojo> = arrayOf(),
        val footer: EmbedFooterPojo? = null,
        val image: EmbedImagePojo? = null,
        val thumbnail: EmbedImagePojo? = null
)