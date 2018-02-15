package me.xaanit.artemis.internal.events.pojo

class Role(
        val position: Int,
        val permissions: Int,
        val name: String,
        val mentionable: Boolean,
        val managed: Boolean,
        val id: String,
        val hoist: Boolean,
        val color: Int
)