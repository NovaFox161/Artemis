package me.xaanit.artemis.internal.logger

import me.xaanit.artemis.internal.logger.LoggerLevel.*
import java.text.SimpleDateFormat
import java.util.*

class Logger private constructor(val name: String) {


    companion object {

        private fun String.loggerFormat(lvl: String = level.toString()): String {
            return (this + "&reset")
                    .replace("&time", System.currentTimeMillis().dateFormat())
                    .replace("&red", Colours.RED.code)
                    .replace("&black", Colours.BLACK.code)
                    .replace("&reset", Colours.RESET.code)
                    .replace("&green", Colours.GREEN.code)
                    .replace("&yellow", Colours.YELLOW.code)
                    .replace("&blue", Colours.BLUE.code)
                    .replace("&purple", Colours.PURPLE.code)
                    .replace("&cyan", Colours.CYAN.code)
                    .replace("&white", Colours.WHITE.code)
                    .replace("&level", lvl)
        }

        private fun Long.dateFormat(): String = SimpleDateFormat("MMM dd,yyyy HH:mm:ss").format(Date(this))

        private var loggers: Map<String, Logger> = mapOf()
        var level: LoggerLevel = TRACE

        fun <T> getLogger(clazz: Class<T>): Logger = getLogger(clazz.simpleName)

        fun getLogger(name: String): Logger = loggers[name] ?: create(name)

        private fun create(name: String): Logger {
            val logger = Logger(name)
            loggers += Pair(name, logger)
            return logger
        }

    }


    fun info(info: String) {
        if (level.shouldLog(INFO)) log(info, INFO)
    }

    fun debug(info: String) {
        if (level.shouldLog(DEBUG)) log(info, DEBUG)
    }

    fun trace(info: String) {
        if (level.shouldLog(TRACE)) log(info, TRACE)
    }


    private fun log(info: String, level: LoggerLevel) = println("[&level] $info".loggerFormat(level.toString()))
}