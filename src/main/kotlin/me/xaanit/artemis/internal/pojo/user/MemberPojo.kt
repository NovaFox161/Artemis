package me.xaanit.artemis.internal.pojo.user


data class MemberPojo(
        val user: UserPojo,
        val roles: Array<String>,
        val nick: String? = null,
        val mute: Boolean,
        val joined_at: String,
        val deaf: Boolean
)