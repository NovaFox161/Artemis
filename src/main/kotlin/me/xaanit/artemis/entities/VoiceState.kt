package me.xaanit.artemis.entities

class VoiceState(
        val suppress: Boolean = false,
        val sessionId: String = "",
        val selfDeaf: Boolean = false,
        val selfVideo: Boolean = false,
        val selfMute: Boolean = false,
        val mute: Boolean = false,
        val deaf: Boolean = false,
        val channel: VoiceChannel? = null
){
    internal lateinit var memberTracked: Member
    val member: Member
        get() = memberTracked
}