package me.xaanit.artemis.entities

class PermissionOverwrite(val type: Type, val id: Long,  val allow: List<Permission>, val deny: List<Permission>) {
    enum class Type {
        ROLE,
        USER, // Idk if this exists
        MEMBER;
    }

    override fun toString(): String {
        return "PermissionOverwrite(type=$type, allow=${this.allow}, deny=${this.deny})"
    }
}