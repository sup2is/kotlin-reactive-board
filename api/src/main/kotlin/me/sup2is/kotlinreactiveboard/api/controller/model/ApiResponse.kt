package me.sup2is.kotlinreactiveboard.api.controller.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiResponse<T> (
    val data: T?,
    var result: Boolean = true,
    var message: String? = null
) {

    companion object {
        fun success(data: Any): ApiResponse<Any> {
            return ApiResponse(data)
        }

        fun failed(message: String? = null): ApiResponse<Any> {
            return ApiResponse(null, false, message)
        }
    }
}
