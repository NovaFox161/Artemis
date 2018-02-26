package me.xaanit.artemis.internal.events.pojo

data class EmojiPojo(
        val roles: Array<String>,
        val requires_colons: Boolean,
        val name: String,
        val managed: Boolean,
        val id: String,
        val animated: Boolean
)