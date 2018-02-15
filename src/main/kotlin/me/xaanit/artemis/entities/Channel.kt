package me.xaanit.artemis.entities


open class Channel(
        val id: Long,
        val name: String,
        val guild: Guild?,
        val position: Int?,
        val overwrites: Array<PermissionOverwrite>,
        val nsfw: Boolean,
        val `private`: Boolean,
        val parent: Long // TODO: Category
        ) {
}