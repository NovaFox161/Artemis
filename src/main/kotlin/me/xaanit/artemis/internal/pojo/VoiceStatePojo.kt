package me.xaanit.artemis.internal.pojo

class VoiceStatePojo(
        val user_id: String,
        val suppress: Boolean,
        val session_id: String,
        val self_video: Boolean,
        val self_mute: Boolean,
        val self_deaf: Boolean,
        val mute: Boolean,
        val deaf: Boolean,
        val channel_id: String
)