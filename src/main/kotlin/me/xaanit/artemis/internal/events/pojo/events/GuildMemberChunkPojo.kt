package me.xaanit.artemis.internal.events.pojo.events

import me.xaanit.artemis.internal.events.pojo.Handleable
import me.xaanit.artemis.internal.events.pojo.user.MemberPojo

class GuildMemberChunkPojo(
        val guild_id: String,
        val members: Array<MemberPojo>
) : Handleable() {
    override fun handle() {
        clientObj.getGuildById(guild_id.toLong())!!.handleMemberChunk(this)
    }
}