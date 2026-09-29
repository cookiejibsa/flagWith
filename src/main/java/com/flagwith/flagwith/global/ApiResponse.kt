package com.flagwith.flagwith.global

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.validation.FieldError

data class ApiResponse<T> (
    val success: Boolean,
    val message: String,
    val data: T?,
) {
    companion object {
        @JvmStatic @JvmOverloads
        fun <T> ok(data: T?, message: String = "성공"): ApiResponse<T> = ApiResponse(true, message, data)
    }
}

@JsonInclude(JsonInclude.Include.NON_NULL) // errors는 검증 실패일 때만 나가게
data class ErrorResponse(
    val success: Boolean = false,
    val errorCode: String,
    val message: String,
    val errors: List<FieldError>? = null,
) {
    data class FieldError(val field: String, val message: String)
}