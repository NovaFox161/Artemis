package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client

class Shard(
        val num: Int,
        val client: Client
) {

    internal var guildCache: Map<Long, Guild> = mapOf()
    internal var userCache: Map<Long, User> = mapOf()

    val guilds: List<Guild>
        get() {
            var list: List<Guild> = listOf()
            guildCache.forEach { _, g -> list += g }
            return list
        }

    val users: List<User>
        get() {
            var list: List<User> = listOf()
            userCache.forEach { _, u -> list += u }
            return list
        }

}