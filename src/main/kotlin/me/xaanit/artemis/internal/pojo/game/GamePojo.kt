package me.xaanit.artemis.internal.pojo.game

data class GamePojo(
        val user: UserIdHolderPojo,
        val status: String,
        val game: GameInfoPojo,
        val name: String,
        val state: String? = null,
        val details: String? = null,
        val application_id: String? = null,
        val assets: RichPresenceAssestsPojo
)