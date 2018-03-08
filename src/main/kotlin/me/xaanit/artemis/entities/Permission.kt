package me.xaanit.artemis.entities

import java.util.*

enum class Permission(val offset: Int) {
    CREATE_INVITE(0),
    KICK_MEMBERS(1),
    BAN_MEMBERS(2),
    ADMINISTRATOR(3),
    MANAGE_CHANNELS(4),
    MANAGE_GUILD(5),
    ADD_REACTIONS(6),
    VIEW_AUDIT_LOGS(7),
    VIEW_CHANNEL(10),
    SEND_MESSAGE(11),
    SEND_TTS_MESSAGE(12),
    MANAGE_MESSAGES(13),
    EMBED_LINKS(14),
    ATTACH_FILES(15),
    READ_MESSAGE_HISTORY(16),
    MENTION_EVERYONE(17),
    USE_EXTERNAL_EMOJIS(18),
    VOICE_CONNECT(20),
    VOICE_SPEAK(21),
    MUTE_MEMBERS(22),
    DEAFEN_MEMBERS(23),
    MOVE_MEMBERS(24),
    USE_VAD(25),
    CHANGE_NICKNAME(26),
    MANAGE_NICKNAMES(27),
    MANAGE_ROLES(28),
    MANAGE_WEBHOOKS(29),
    MANAGE_EMOJIS(30);

    companion object {
        fun getForBitset(bitset: Long): EnumSet<Permission> {
            var arr: MutableList<Permission> = mutableListOf()
            values().forEach { if (((bitset.shr(it.offset)) and 1) == 1L) arr.add(it) }
            return if (arr.isEmpty()) EnumSet.noneOf(Permission::class.java) else EnumSet.copyOf(arr)
        }

        fun getBitset(overwrite: EnumSet<Permission>): Long {
            var raw = 0L
            overwrite.forEach {
                raw = raw or (1 shl it.offset).toLong()
            }
            return raw
        }
    }
}