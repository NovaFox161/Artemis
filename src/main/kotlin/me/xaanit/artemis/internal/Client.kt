package me.xaanit.artemis.internal


import me.xaanit.artemis.entities.Guild
import me.xaanit.artemis.entities.Shard
import me.xaanit.artemis.entities.User
import me.xaanit.artemis.entities.presence.Status
import java.io.File

class Client(val token: String, val shardCount: Int = 1) {
    internal var shards: List<Shard> = listOf()
    private var statusTracked: Status = Status.ONLINE

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val manager = WebsocketManager(DiscordConstant.GATEWAY_URI, Client(token = File("${System.getProperty("user.dir")}\\token.txt").readLines().joinToString(), shardCount = 2))
            for (i in 0 until manager.client.shardCount) {
                manager.addWebsocket(i + 1)
            }
            manager.runIdentities()
        }
    }

    fun logout() {

    }

    val guilds: List<Guild>
        get() {
            var list: List<Guild> = listOf()
            shards.stream().map(Shard::guilds).forEach { list += it }
            return list
        }

    val users: List<User>
        get() {
            var list: List<User> = listOf()
            shards.stream().map(Shard::users).forEach { list += it }
            return list
        }

    val status = statusTracked

}