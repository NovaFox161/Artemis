package me.xaanit.artemis.internal.events.pojo.events

import me.xaanit.artemis.internal.events.pojo.Emoji
import me.xaanit.artemis.internal.events.pojo.VoiceState
import me.xaanit.artemis.internal.events.pojo.channels.Channel
import me.xaanit.artemis.internal.events.pojo.game.Game
import me.xaanit.artemis.internal.events.pojo.user.Member

data class GuildCreate(
        val voice_states: List<VoiceState>,
        val verification_level: Int,
        val unavaliable: Boolean,
        val system_channel_id: String,
        val splash: Boolean,
        val region: String,
        val owner_id: String,
        val name: String,
        val mfa_level: Int,
        val members: List<Member>,
        val presences: List<Game>,
        val member_count: Int,
        val large: Boolean,
        val joined_at: String,
        val id: String,
        val icon: String,
        val features: List<String>,
        val explicit_content_filter: Int,
        val emojis: List<Emoji>,
        val default_message_notifications: Int,
        val channels: List<Channel>,
        val application_id: String?,
        val afk_timeout: Int,
        val afk_channel_id: String?
)