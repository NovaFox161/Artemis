package me.xaanit.artemis.internal.pojo

data class EmojiPojo(
        val roles: Array<String> = arrayOf(),
        val requires_colons: Boolean = true,
        val name: String,
        val managed: Boolean = false,
        val id: String?,
        val animated: Boolean
)