package me.xaanit.artemis.internal.logger

class LoggerFormatter {

    companion object {
        private var custom: Map<CharSequence, String> = mapOf()
        @JvmStatic
        fun addCustom(key: Any, value: String) {
            custom += Pair(key.toString(), value)
        }
    }

    private val builder = StringBuilder()
    fun appendTime() {
        builder.append("[&time] ")
    }

    fun appendColor(color: Colours) {
        builder.append("${color.toString().toLowerCase()}")
    }

    fun appendLevel() {
        builder.append(Logger.level)
    }

    fun append(value: CharSequence) {
        builder.append(value)
    }

    fun appendCustom(key: CharSequence) {
        builder.append(custom[key])
    }

    fun build(): String = builder.toString()
}