package me.xaanit.artemis.internal

import java.net.URI

object DiscordConstant {
    val GATEWAY_VERSION = "6"
    val GATEWAY_URI = URI("wss://gateway.discord.gg/?v=$GATEWAY_VERSION&encoding=json")
}