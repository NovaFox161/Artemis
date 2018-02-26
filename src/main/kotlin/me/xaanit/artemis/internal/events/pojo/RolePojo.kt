package me.xaanit.artemis.internal.events.pojo

class RolePojo(
        val position: Int,
        val permissions: Long,
        val name: String,
        val mentionable: Boolean,
        val managed: Boolean,
        val id: String,
        val hoist: Boolean,
        val color: Int
)