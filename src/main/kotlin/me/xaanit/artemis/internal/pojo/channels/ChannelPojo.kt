package me.xaanit.artemis.internal.pojo.channels

data class ChannelPojo(
        val type: Int,
        val topic: String?,
        val position: Int,
        val parent_id: String?,
        val nsfw: Boolean,
        val name: String,
        val last_pin_timestamp: String,
        val last_message_id: String,
        val id: String,
        val permission_overwrites: Array<PermissionOverwritePojo>,
        val bitrate: Int? = null,
        val user_limit: Int? = null
)