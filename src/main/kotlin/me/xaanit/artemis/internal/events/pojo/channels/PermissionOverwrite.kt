package me.xaanit.artemis.internal.events.pojo.channels

data class PermissionOverwrite(
        val type: String,
        val id: String,
        val deny: Int,
        val allow: Int
)