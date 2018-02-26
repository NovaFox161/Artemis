package me.xaanit.artemis.entities

class VoiceState(
        val member: Member,
        val suppress: Boolean = false,
        val session_id: String = "",
        val self_video: Boolean = false,
        val self_mute: Boolean = false,
        val self_deaf: Boolean = false,
        val mute: Boolean = false,
        val deaf: Boolean = false,
        val channel: VoiceChannel? = null
)