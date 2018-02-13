package me.xaanit.artemis.internal.logger

enum class LoggerLevel(private val priority: Int) {
    NONE(4),
    INFO(3),
    DEBUG(2),
    TRACE(1);


    fun shouldLog(other: LoggerLevel): Boolean = other.priority >= priority
}