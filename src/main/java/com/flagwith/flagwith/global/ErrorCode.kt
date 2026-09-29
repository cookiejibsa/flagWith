package com.flagwith.flagwith.global

import org.springframework.http.HttpStatus

enum class ErrorCode (val status: HttpStatus, val message: String) {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    AUTH_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."), // 이메일과 비밀번호를 구분하지 않는다.
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
}