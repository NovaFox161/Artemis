package me.xaanit.artemis.internal.events.pojo.game

data class Game(
        val user: UserIdHolder,
        val status: String,
        val game: GameInfo,
        val name: String,
        val state: String? = null,
        val details: String? = null,
        val application_id: String? = null,
        val assets: RichPresenceAssests
)