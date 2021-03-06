package me.xaanit.artemis.entities

import java.util.*

class PermissionOverwrite(val type: Type, val id: Long,  val allow: EnumSet<Permission>, val deny: EnumSet<Permission>) {
    enum class Type {
        ROLE,
        MEMBER;
    }

    override fun toString(): String {
        return "PermissionOverwrite(type=$type, allow=$allow, deny=$allow)"
    }
}