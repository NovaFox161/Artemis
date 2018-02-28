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

    init {
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
            shards.stream().map(Shard::guilds).forEach { list += it }
            return list
        }


    val channels: List<Channel>
        get() {
            var list: List<Channel> = listOf()
            guilds.stream().map(Guild::channels).forEach { list += it }
            return list
        }

    val textChannels: List<TextChannel>
        get() {
            var list: List<TextChannel> = listOf()
            guilds.stream().map(Guild::textChannels).forEach { list += it }
            return list
        }

    val voiceChannels: List<VoiceChannel>
        get() {
            var list: List<VoiceChannel> = listOf()
            guilds.stream().map(Guild::voiceChannels).forEach { list += it }
            return list
        }

    val users: List<User>
        get() {
            var list: List<User> = listOf()
            shards.stream().map(Shard::users).forEach { list += it }
            return list
        }


    fun getChannelById(id: Long): Channel? = channels.find { it.id == id }


    fun getGuildById(id: Long): Guild? = guilds.find { it.id == id }

    val status = statusTracked

}