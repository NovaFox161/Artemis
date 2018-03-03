package me.xaanit.artemis.internal


import me.xaanit.artemis.entities.*
import me.xaanit.artemis.entities.presence.Status
import me.xaanit.artemis.internal.pojo.message.MessagePojo
import me.xaanit.artemis.internal.requests.DiscordRequest
import me.xaanit.artemis.internal.requests.MethodType
import me.xaanit.artemis.util.Extensions

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
            return list
        }

    val roles: List<Role>
        get() {
            var list: List<Role> = listOf()
            guilds.stream().forEach { list += it.roleCache.values }
            return list
        }


    val channels: List<Channel>
        get() {
            var list: List<Channel> = listOf()
            guilds.stream().forEach { list += it.channelCache.values }
            return list
        }

    val textChannels: List<TextChannel>
        get() {
            var list: List<TextChannel> = listOf()
            guilds.forEach { list += it.textChannels }
            return list
        }

    val voiceChannels: List<VoiceChannel>
        get() {
            var list: List<VoiceChannel> = listOf()
            guilds.forEach { list += it.voiceChannels }
            return list
        }

    val users: List<User>
        get() {
            var list: List<User> = listOf()
            shards.forEach { list += it.userCache.values }
            return list
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

    fun getMessageById(channel: Channel, message: Long): Message? {
        return DiscordRequest<Message?>(
                url = "https://discordapp.com/api/v6/channels/%s/messages/%s",
                method = MethodType.GET,
                client = this,
                make = {
                    if (it.statusCode == 404) {
                        null
                    } else {
                        val message = Extensions.seraliseNulls.fromJson(it.jsonObject.toString(), MessagePojo::class.java)
                        message.shardObj = channel.guild?.client?.shards!![((channel.guild.id shr 22) % shardCount).toInt()]
                        message.make()!!
                    }
                },
                formatter = arrayOf(channel, message)
        ).block()
    }


    val status = statusTracked

}