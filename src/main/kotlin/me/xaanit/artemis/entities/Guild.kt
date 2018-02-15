package me.xaanit.artemis.entities

import me.xaanit.artemis.util.Extensions.firstFound
import me.xaanit.artemis.util.Extensions.stream

class Guild(
        val id: Long,
        val name: String,
        val avatarUrl: String,
        val owner: Member,
        val voiceRegion: String, // TODO: Enum
        val afkChannel: Long?, // TODO: VoiceChannels
        val afkTimeout: Int,
        val widgetEnbaled: Boolean,
        val welcomeMessagesEnabled: Boolean,
        val explicitContentFilterLevel: Int, // TODO: Enum
        val roles: Array<Long>, // TODO: Role
        val members: Array<Member>,
        val widgetId: Long,
        val systemChannel: Long, // TODO: Channel
        val large: Boolean,
        val channels: Array<Long> // TODO: Channel
) {

    fun getMember(id: Long): Member? = members.stream().filter { it.id == id }.firstFound()
    fun getMember(user: User): Member? = getMember(user.id)
}