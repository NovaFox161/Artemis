package me.xaanit.artemis.internal.events.pojo.user

data class UserPojo(
        val username: String,
        val id: String,
        val discriminator: String,
        val avatar: String?,
        val bot: Boolean = false
)