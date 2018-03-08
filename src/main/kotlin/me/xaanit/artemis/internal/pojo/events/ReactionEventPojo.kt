package me.xaanit.artemis.internal.pojo.events

import me.xaanit.artemis.internal.pojo.EmojiPojo
import me.xaanit.artemis.internal.pojo.Handleable

class ReactionEventPojo(
        var type: String = "",
        val user_id: String,
        val message_id: String,
        val emoji: EmojiPojo,
        val channel_id: String
) : Handleable() {
    override fun handle() {
    /*    val message = clientObj.getMessageById(clientObj.getChannelById(channel_id.toLong())!!, message_id.toLong())!!
        var list: List<Role> = listOf()
        emoji.roles.forEach {
            list += message.channel.guild?.getRolebyId(it.toLong())!!
        }
        val reaction = Reaction(
                name = emoji.name,
                animated = emoji.animated,
                managed = emoji.managed,
                message = message,
                id = emoji.id?.toLong(),
                requiresColons = emoji.requires_colons,
                roles = list
        )
        when (type) {
            "MESSAGE_REACTION_REMOVE" -> clientObj.dispatcher.dispatch()
        }*/
    }
}