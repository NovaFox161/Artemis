package me.xaanit.artemis.builders

import me.xaanit.artemis.entities.*
import java.util.*

class ChannelEditBuilder(@Transient val channel: Channel) {

    private var name: String = channel.name
    private var position: Int = channel.position ?: 0
    private var topic: String = channel.topic
    private var nsfw: Boolean = channel.nsfw
    private var bitrate: Int = (channel as? VoiceChannel)?.bitrate ?: 8000
    private var user_limit: Int = (channel as? VoiceChannel)?.userLimit ?: 0
    private var permission_overwrites: Array<PermissionOverwriteJson> = arrayOf()


    fun withName(name: String): ChannelEditBuilder {
        this.name = name
        return this
    }

    fun withPosition(position: Int): ChannelEditBuilder {
        this.position = position
        return this
    }

    fun withTopic(topic: String): ChannelEditBuilder {
        this.topic = topic
        return this
    }

    fun withNsfw(nsfw: Boolean): ChannelEditBuilder {
        this.nsfw = nsfw
        return this
    }

    fun withBitrate(bitrate: Int): ChannelEditBuilder {
        this.bitrate = bitrate
        return this
    }

    fun withUserLimit(limit: Int): ChannelEditBuilder {
        this.user_limit = limit
        return this
    }

    fun withOverride(
            user: User,
            allow: EnumSet<Permission> = EnumSet.noneOf(Permission::class.java),
            deny: EnumSet<Permission> = EnumSet.noneOf(Permission::class.java)
    ): ChannelEditBuilder = override(PermissionOverwrite(
            type = PermissionOverwrite.Type.MEMBER,
            allow = allow,
            deny = deny,
            id = user.id
    ))

    fun withOverride(
            role: Role,
            allow: EnumSet<Permission> = EnumSet.noneOf(Permission::class.java),
            deny: EnumSet<Permission> = EnumSet.noneOf(Permission::class.java)
    ): ChannelEditBuilder = override(PermissionOverwrite(
            type = PermissionOverwrite.Type.ROLE,
            allow = allow,
            deny = deny,
            id = role.id
    ))

    private fun override(overwrite: PermissionOverwrite): ChannelEditBuilder {
        this.permission_overwrites += PermissionOverwriteJson(id = overwrite.id.toString(), type = overwrite.type.toString().toLowerCase(), allow = Permission.getBitset(overwrite.allow), deny = Permission.getBitset(overwrite.deny))
        return this
    }


    private class PermissionOverwriteJson(val id: String, val type: String, val allow: Long, val deny: Long)


}