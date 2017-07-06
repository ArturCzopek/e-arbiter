package pl.cyganki.gateway.utils

/**
 * @Author arturczopek
 * @Date 06-07-2017
 */
enum class FilterType(val value: String) {
    PRE("pre"),
    ROUTING("routing"),
    POST("post"),
    ERROR("error")
}