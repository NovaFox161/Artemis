package me.xaanit.artemis.internal.events.pojo.events

import me.xaanit.artemis.internal.events.pojo.Handleable

class MessageDelete(
        val id: String,
        val channel_id: String
) : Handleable() {
    override fun handle() {

    }
}