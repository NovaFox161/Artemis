package me.xaanit.artemis.internal


import me.xaanit.artemis.entities.*
import me.xaanit.artemis.entities.presence.Status

class Client(val token: String, val shardCount: Int = 1) {
    internal var shards: List<Shard> = listOf()
    private var statusTracked: Status = Status.ONLINE
    internal lateinit var usTracked: User
    internal val manager: WebsocketManager = WebsocketManager(DiscordConstant.GATEWAY_URI, this)

    val ourUser: User
        get() {
            return usTracked
        }

    fun login() {
        for (i in 0 until manager.client.shardCount) {
            manager.addWebsocket(i + 1)
        }
        manager.runIdentities()
    }

    val dispatcher = Dispatcher(this)
    fun logout() {

    }

    val guilds: List<Guild>
        get() {
            var list: List<Guild> = listOf()
            shards.stream().forEach { list += it.guildCache.values }
            return list.distinct()
        }

    val roles: List<Role>
        get() {
            var list: List<Role> = listOf()
            guilds.stream().forEach { list += it.roleCache.values }
            return list.distinct()
        }


    val channels: List<Channel>
        get() {
            var list: List<Channel> = listOf()
            guilds.stream().forEach { list += it.channelCache.values }
            return list.distinct()
        }

    val textChannels: List<TextChannel>
        get() {
            var list: List<TextChannel> = listOf()
            guilds.forEach { list += it.textChannels }
            return list.distinct()
        }

    val voiceChannels: List<VoiceChannel>
        get() {
            var list: List<VoiceChannel> = listOf()
            guilds.forEach { list += it.voiceChannels }
            return list.distinct()
        }

    val users: List<User>
        get() {
            var list: List<User> = listOf()
            shards.forEach { list += it.userCache.values }
            return list.distinct()
        }


    fun getChannelById(id: Long): Channel? {
        var channel: Channel? = null
        shards.forEach {
            channel = it.getChannelById(id) ?: channel
        }
        return channel
    }

    fun getRoleById(id: Long): Role? {
        var role: Role? = null
        shards.forEach {
            role = it.getRoleById(id) ?: role
        }
        return role
    }


    fun getGuildById(id: Long): Guild? {
        var guild: Guild? = null
        shards.forEach {
            guild = it.getGuildById(id) ?: guild
        }
        return guild
    }

    fun getUserById(id: Long): User? {
        var user: User? = null
        shards.forEach {
            user = it.getUserById(id) ?: user
        }
        return user
    }


    val status = statusTracked

}