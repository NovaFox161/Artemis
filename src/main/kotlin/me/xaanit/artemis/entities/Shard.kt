package me.xaanit.artemis.entities

import me.xaanit.artemis.internal.Client

class Shard(
        val num: Int,
        val client: Client
) {

    internal var guildCache: Map<Long, Guild> = mapOf()
    internal var userCache: Map<Long, User> = mapOf()

    val guilds: List<Guild>
        get() = guildCache.values.toList()

    val users: List<User>
        get() = userCache.values.toList()


    fun getGuildById(id: Long): Guild? = guildCache[id]
    fun getUserById(id: Long): User? = userCache[id]

    fun getChannelById(id: Long): Channel? {
        var channel: Channel? = null
        guilds.forEach {
            channel = it.getChannelById(id) ?: channel
        }
        return channel
    }

    fun getRoleById(id: Long): Role? {
        var role: Role? = null
        guilds.forEach {
            role = it.getRolebyId(id) ?: role
        }
        return role
    }

}