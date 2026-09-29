package com.flagwith.flagwith.global.exception

import org.springframework.http.HttpStatus

// 에러 코드는 여기 한 곳에서만 정의한다. 이름은 도메인 접두사 + UPPER_SNAKE.
enum class ErrorCode(val status: HttpStatus, val message: String) {
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "요청 값이 올바르지 않습니다."),
    AUTH_FAILED(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."), // 이메일/비밀번호 구분 안 함
    FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    TEAM_NOT_FOUND(HttpStatus.NOT_FOUND, "팀을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
}
