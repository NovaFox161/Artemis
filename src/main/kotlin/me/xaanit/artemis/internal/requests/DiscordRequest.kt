package me.xaanit.artemis.internal.requests

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import khttp.*
import khttp.responses.Response
import me.xaanit.artemis.internal.Client
import me.xaanit.artemis.internal.exceptions.RateLimitException
import me.xaanit.artemis.internal.logger.Logger
import me.xaanit.artemis.internal.requests.MethodType.*
import org.json.JSONObject
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

class DiscordRequest<out T>(
        private val url: String,
        private val method: MethodType,
        private val client: Client,
        private val body: JsonObject = jsonObject(),
        private val make: (Response) -> T,
        private val formatter: Array<Any?> = arrayOf(),
        private val contentType: String = "application/json"
) {

    private val response: AtomicReference<T?> = AtomicReference(null)
    private val error: AtomicReference<Throwable?> = AtomicReference(null)

    private var success: (T) -> Any = {}
    private var failure: (Throwable) -> Any = {}
    private val handled = AtomicBoolean(false)
    private val headers = mapOf("Authorization" to "Bot ${client.token}", "Content-Type" to contentType)


    init {
        actionExecutor.schedule({ doAction() }, 0, TimeUnit.MILLISECONDS)
    }

    companion object {
        private val logger = Logger.getLogger(DiscordRequest::class.java)
        private var resets: Map<String, Long> = mapOf()
        private var requestsLeft: Map<String, Int> = mapOf()
        private val actionExecutor = Executors.newScheduledThreadPool(4)
    }

    private fun doAction() {
        try {
            val systemSeconds: Long = System.currentTimeMillis() / 1000
            if (resets[url] ?: systemSeconds + 1 < systemSeconds) {
                requestsLeft += url to 1
            }

            if (requestsLeft[url] ?: 1 == 0) {
                val diff = Math.abs((resets[url] ?: systemSeconds) - systemSeconds) * 1000
                logger.trace("&cyan[&time] Retrying request after $diff ms...")
                Thread.sleep(diff)
            }

            logger.trace("&cyan[&time] Trying to send request to route ${url.format(*formatter)} with type ${method} using json $body")

            val response = when (method) {
                GET -> get(url = url.format(*formatter), headers = headers)
                POST -> post(url = url.format(*formatter), headers = headers, json = JSONObject(body.toString()))
                DELETE -> delete(url = url.format(*formatter), headers = headers, json = JSONObject(body.toString()))
                PATCH -> patch(url = url.format(*formatter), headers = headers, json = JSONObject(body.toString()))
                PUT -> put(url = url.format(*formatter), headers = headers, json = JSONObject(body.toString()))
            }
            requestsLeft += url to (response.headers["X-RateLimit-Remaining"]?.toInt() ?: 1)
            resets += url to if (requestsLeft[url]!! < 1) response.headers["X-RateLimit-Reset"]?.toLong()!! else 0

            if (response.statusCode == 429) {
                throw RateLimitException()
            }
            logger.trace("&cyan[&time] Got back request json: ${response.jsonObject}")
            this.response.set(make(response))
        } catch (throwable: Throwable) {
            this.error.set(throwable)
        }
        runHandle()
    }

    private fun runHandle() {
        synchronized(handled) {
            if (!handled.get() && (response.get() != null || error.get() != null)) return
            handled.set(true)
            val response = this.response.get()
            val error = this.error.get()
            if (response != null) success(response)
            if (error != null) failure(error)
        }
    }

    fun handle(success: (T) -> Any = {}, failure: (Throwable) -> Any = {}) {
        this.success = success
        this.failure = failure
        runHandle()
    }
}