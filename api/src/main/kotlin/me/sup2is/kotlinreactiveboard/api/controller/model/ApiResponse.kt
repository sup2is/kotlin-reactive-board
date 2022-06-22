package me.sup2is.kotlinreactiveboard.api.controller.model

class ApiResponse<T> (
    val data: T
) {
    var result: Boolean = true
    var message: String? = null
}
