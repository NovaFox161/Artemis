package me.xaanit.artemis.internal.pojo.channels

data class PermissionOverwritePojo(
        val type: String,
        val id: String,
        val deny: Long,
        val allow: Long
)