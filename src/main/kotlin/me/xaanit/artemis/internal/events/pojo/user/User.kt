package me.xaanit.artemis.internal.events.pojo.user

data class User(
        val username: String,
        val id: String,
        val discriminator: String,
        val avatar: String
)