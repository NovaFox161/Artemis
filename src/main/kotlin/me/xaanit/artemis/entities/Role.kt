package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client
import java.awt.Color

class Role(
        val id: Long,
        val name: String,
        val color: Color,
        val hoisted: Boolean,
        val position: Int,
        val permissions: List<Permission>,
        val managed: Boolean,
        val mentionable: Boolean,
        val guild: Guild,
        val client: Client
) {
    override fun toString(): String {
        return "Role(id=$id, name='$name', color=$color, hoisted=$hoisted, position=$position, permissions=${this.permissions}, managed=$managed, mentionable=$mentionable, client=$client)"
    }
}