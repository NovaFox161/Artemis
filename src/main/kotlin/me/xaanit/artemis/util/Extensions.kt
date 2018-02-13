package me.xaanit.artemis.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.stream.Stream

object Extensions {

    private val parser = JsonParser()
    private val gson = GsonBuilder().serializeNulls().create()

    fun <T> Array<T>.stream(): Stream<T> = toList().stream()

    fun <T> Stream<T>.anyFound(): T? = findAny().orElse(null)

    fun <T> Stream<T>.firstFound(): T? = findFirst().orElse(null)

    fun String.jsonObject(): JsonObject = parser.parse(this).asJsonObject

    fun Any?.json(): String = gson.toJson(this)

    fun Array<Int>.toJsonArray(): JsonArray {
        val arr = JsonArray()
        forEach { arr.add(it) }
        return arr
    }
}