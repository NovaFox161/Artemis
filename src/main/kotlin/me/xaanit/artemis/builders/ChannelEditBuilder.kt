package me.xaanit.artemis.builders

import me.xaanit.artemis.entities.Channel
import me.xaanit.artemis.entities.Permission
import me.xaanit.artemis.entities.PermissionOverwrite
import me.xaanit.artemis.entities.VoiceChannel

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

    fun withPermissionOverrides(vararg overwrites: PermissionOverwrite): ChannelEditBuilder {
        var json: Array<PermissionOverwriteJson> = arrayOf()
        overwrites.forEach {
            json += PermissionOverwriteJson(id = it.id.toString(), type = it.type.toString().toLowerCase(), allow = Permission.getBitset(it.allow), deny = Permission.getBitset(it.deny))
        }
        this.permission_overwrites = json
        return this
    }


    private class PermissionOverwriteJson(val id: String, val type: String, val allow: Long, val deny: Long)


}