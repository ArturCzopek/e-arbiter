package pl.cyganki.gateway.utils


enum class FilterType(val value: String) {
    PRE("pre"),
    ROUTING("routing"),
    POST("post"),
    ERROR("error")
}