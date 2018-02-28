package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.presence.Game
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.DiscordConstant
import me.xaanit.artemis.internal.pojo.RolePojo
import me.xaanit.artemis.internal.pojo.VoiceStatePojo
import me.xaanit.artemis.internal.pojo.channels.ChannelPojo
import me.xaanit.artemis.internal.pojo.channels.PermissionOverwritePojo
import me.xaanit.artemis.internal.pojo.events.GuildMemberChunkPojo
import me.xaanit.artemis.internal.pojo.game.GamePojo
import me.xaanit.artemis.internal.pojo.user.MemberPojo
import me.xaanit.artemis.util.Extensions.shard
import java.awt.Color
import java.util.*

class Guild(
        val id: Long,
        val name: String,
        val avatarUrl: String,
        val voiceRegion: String, // TODO: Enum
        val afkChannel: Long?, // TODO: VoiceChannels
        val afkTimeout: Int,
        val welcomeMessagesEnabled: Boolean,
        val explicitContentFilterLevel: Int, // TODO: Enum
        val large: Boolean,
        val client: Client,
        private val systemChannelId: Long,
        private val ownerId: Long,
        private val builtChannels: Array<Channel>? = null,
        private val channelData: Array<ChannelPojo>? = null,
        private val builtRoles: Map<Long, Role>? = null,
        private val roleData: Array<RolePojo>? = null,
        private val builtMembers: Map<Long, Member>? = null,
        private val memberData: Array<MemberPojo>? = null,
        private val builtVoiceStates: Map<Long, VoiceState>? = null,
        private val voiceStateData: Array<VoiceStatePojo>? = null,
        private val builtGames: Map<Long, Game>? = null,
        private val gameData: Array<GamePojo>? = null
) {
    internal var voiceStates: Map<Long, VoiceState> = mapOf()
    internal var games: Map<Long, Game> = mapOf()

    val channels: Array<Channel>
    val textChannels: Array<TextChannel>
        get() {
            var arr: Array<TextChannel> = arrayOf()
            channels.filter { it is TextChannel }.forEach { arr += it as TextChannel }
            return arr
        }

    val voiceChannels: Array<VoiceChannel>
        get() {
            var arr: Array<VoiceChannel> = arrayOf()
            channels.filter { it is VoiceChannel }.forEach { arr += it as VoiceChannel }
            return arr
        }

    private var roleCache: Map<Long, Role> = mapOf()
    val roles: Array<Role>
        get() = roleCache.values.toTypedArray()

    private var memberCache: Map<Long, Member> = mapOf()

    val members: Array<Member>
        get() = memberCache.values.toTypedArray()
    val owner: Member
    val systemChannel: TextChannel?
    val shard: Shard = this.shard()


    init {
        channels = builtChannels ?: channelData.make()
        roleCache = builtRoles ?: roleData.make()
        memberCache = builtMembers ?: memberData.make()
        owner = members.find { it.id == ownerId }!!
        systemChannel = textChannels.find { it.id == systemChannelId }
        // voiceStates = builtVoiceStates ?: voiceStateData.make()
        // games = builtGames ?: gameData.make()
    }


    internal fun handleMemberChunk(chunk: GuildMemberChunkPojo) {
        chunk.members.forEach {
            it.handle()
        }
    }

    private fun MemberPojo.handle() {
        val avatarUrl = if (user.avatar == null) DiscordConstant.USER_DEFAULT_ICON.format(user.discriminator.toInt() % 5) else (DiscordConstant.USER_ICON.format(user.id, user.avatar, if (user.avatar.startsWith("a_")) "gif" else "png"))
        var roles: Array<Role> = arrayOf()
        this.roles.forEach {
            val role = this@Guild.roles.find { r -> r.id == it.toLong() }
            if (role != null)
                roles += role // Should never NPE. Problem if it does.
        }
        memberCache +=
                user.id.toLong() to Member(
                        nickname = nick,
                        discrim = user.discriminator,
                        cli = client,
                        avatar = avatarUrl,
                        roles = roles,
                        guild = this@Guild,
                        userId = user.id.toLong(),
                        name = user.username,
                        bt = user.bot
                )
    }

    fun getMember(id: Long): Member? = memberCache[id]
    fun getMember(user: User): Member? = getMember(user.id)

    private fun Array<ChannelPojo>?.make(): Array<Channel> {
        if (this == null) return arrayOf()
        var arr: Array<Channel> = arrayOf()
        forEach {
            val channel = if (it.type == 0) createText(it) else createVoice(it)
            arr += channel
        }
        return arr
    }

    private fun Array<MemberPojo>?.make(): Map<Long, Member> {
        if (this == null) return mapOf()


        var arr: Map<Long, Member> = mapOf()

        forEach { it ->
            var roles: Array<Role> = arrayOf()
            it.roles.forEach {
                val role = this@Guild.roles.find { r -> r.id == it.toLong() }
                if (role != null)
                    roles += role // Should never NPE. Problem if it does.
            }
            val avatarUrl = if (it.user.avatar == null) DiscordConstant.USER_DEFAULT_ICON.format(it.user.discriminator.toInt() % 5) else DiscordConstant.USER_ICON.format(it.user.id, it.user.avatar, if (it.user.avatar.startsWith("a_")) "gif" else "png")
            arr +=
                    it.user.id.toLong() to Member(
                            userId = it.user.id.toLong(),
                            guild = this@Guild,
                            name = it.user.username,
                            roles = roles,
                            avatar = avatarUrl,
                            cli = client,
                            discrim = it.user.discriminator,
                            nickname = it.nick,
                            bt = it.user.bot
                    )
        }
        return arr
    }

    private fun Array<RolePojo>?.make(): Map<Long, Role> {
        if (this == null) return mapOf()


        var createdRoles: Map<Long, Role> = mapOf()
        forEach {
            createdRoles +=
                    it.id.toLong() to Role(
                            id = it.id.toLong(),
                            name = it.name,
                            client = this@Guild.client,
                            color = Color(it.color),
                            guild = this@Guild,
                            hoisted = it.hoist,
                            managed = it.managed,
                            mentionable = it.mentionable,
                            permissions = Permission.getForBitset(it.permissions),
                            position = it.position
                    )
        }
        return createdRoles
    }

    private fun createVoice(channel: ChannelPojo): Channel {
        return VoiceChannel(
                channelId = channel.id.toLong(),
                channelGuild = this,
                channelName = channel.name,
                channelOverwrites = channel.permission_overwrites.create(),
                channelParent = channel.parent_id?.toLong(),
                channelPosition = channel.position
        )
    }

    private fun createText(channel: ChannelPojo): Channel {
        return TextChannel(
                channelId = channel.id.toLong(),
                channelGuild = this,
                channelName = channel.name,
                isNsfw = channel.nsfw,
                channelOverwrites = channel.permission_overwrites.create(),
                channelParent = channel.parent_id?.toLong(),
                channelPosition = channel.position
        )
    }

    private fun Array<PermissionOverwritePojo>.create(): Array<PermissionOverwrite> {
        var overwrites: Array<PermissionOverwrite> = arrayOf()
        forEach {
            overwrites += PermissionOverwrite(type = PermissionOverwrite.Type.valueOf(it.type.toUpperCase()), allow = Permission.getForBitset(it.allow), deny = Permission.getForBitset(it.deny))
        }
        return overwrites
    }

    override fun toString(): String {
        return "Guild(id=$id, name='$name', avatarUrl='$avatarUrl', voiceRegion='$voiceRegion', afkChannel=$afkChannel, afkTimeout=$afkTimeout, welcomeMessagesEnabled=$welcomeMessagesEnabled, explicitContentFilterLevel=$explicitContentFilterLevel, systemChannel=$systemChannel, large=$large, client=$client, channels=${Arrays.toString(channels)}, roles=${Arrays.toString(roles)}, members=${Arrays.toString(members)})"
    }

}