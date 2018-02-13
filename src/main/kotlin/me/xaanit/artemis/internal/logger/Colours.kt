package me.xaanit.artemis.internal.logger

enum class Colours(private val offset: Int) {
    RESET(-30),
    BLACK(0),
    RED(1),
    GREEN(2),
    YELLOW(3),
    BLUE(4),
    PURPLE(5),
    CYAN(6),
    WHITE(7);

    val code: String = "\u001B[${offset + 30}m"


}