package me.xaanit.artemis.internal

import java.net.URI

object DiscordConstant {
    val GATEWAY_VERSION = "6"
    val GATEWAY_URI = URI("wss://gateway.discord.gg/?v=$GATEWAY_VERSION&encoding=json")
    private val BASE_IMAGE_URL = "https://cdn.discordapp.com/"
    val EMOJI_URL = "${BASE_IMAGE_URL}emojis/%s.%s"
    val GUILD_ICON = "${BASE_IMAGE_URL}icons/%s/%s.png"
    val GUILD_SPLASH = "${BASE_IMAGE_URL}icons/%s/%s.png"
    val USER_DEFAULT_ICON = "${BASE_IMAGE_URL}embed/avatars/%s.png"
    val USER_ICON = "${BASE_IMAGE_URL}avatars/%s/%s.%s"
    val APPLICATION_ICON = "${BASE_IMAGE_URL}app-icons/%s/%s.png"
}