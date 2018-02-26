package me.xaanit.artemis.entities

import java.util.*

class PermissionOverwrite(val type: Type, val allow: Array<Permission>, val deny: Array<Permission>) {
    enum class Type {
        ROLE,
        USER, // Idk if this exists
        MEMBER;
    }

    override fun toString(): String {
        return "PermissionOverwrite(type=$type, allow=${Arrays.toString(allow)}, deny=${Arrays.toString(deny)})"
    }
}