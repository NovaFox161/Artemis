package me.xaanit.artemis.internal.events.pojo.user


data class Member(
        val user: User,
        val roles: Array<String>,
        val nick: String? = null,
        val mute: Boolean,
        val joined_at: String,
        val deaf: Boolean
)