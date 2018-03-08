package me.xaanit.artemis.entities

import me.xaanit.artemis.entities.presence.Game

class Presence(
        val status: Status,
        val game: Game
) {
    internal lateinit var memberTracked: Member
    val member: Member
        get() = memberTracked
}