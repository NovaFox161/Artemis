package me.xaanit.artemis.entities

class VoiceChannel(
        private val channelId: Long,
        private val channelName: String,
        private val channelGuild: Guild,
        private val channelPosition: Int?,
        private val channelOverwrites: Array<PermissionOverwrite>,
        private val channelParent: Long?, // TODO: Category
        val bitrate: Int,
        val userLimit: Int
) : Channel(id = channelId, name = channelName, guild = channelGuild, position = channelPosition, overwrites = channelOverwrites, nsfw = false, private = false, parent = channelParent, client = channelGuild.client, topic = "") {

}