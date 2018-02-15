package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client
import java.awt.Color

class Role(
        val id: Long,
        val name: String,
        val color: Color,
        val hoisted: Boolean,
        val position: Int,
        val permissions: Array<Permission>,
        val managed: Boolean,
        val mentionable: Boolean,
        val guild: Guild,
        val client: Client
) {
}