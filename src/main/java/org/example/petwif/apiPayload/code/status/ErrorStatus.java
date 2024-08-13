package org.example.petwif.apiPayload.code.status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.petwif.apiPayload.code.BaseErrorCode;
import org.example.petwif.apiPayload.code.ErrorReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 로그인 관련
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "user already exists", "이미 존재하는 아이디입니다."),
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "LOGIN FAIL", "아이디 또는 비밀번호를 확인하세요"),

    // Member Error
    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMENT4001", "댓글이 없습니다."),

    // Follow Error
    FRIEND_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "FRIEND4001", "이미 친구입니다."),
    FRIEND_NOT_FOUND(HttpStatus.BAD_REQUEST, "FRIEND4002", "친구가 아닙니다."),

    // Album Error
    ALBUM_NOT_FOUND(HttpStatus.BAD_REQUEST, "ALBUM4001", "앨범을 찾을 수 없습니다."),
    ALBUM_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "ALBUM4011", "앨범에 대한 권한이 없습니다."),

    BLOCK_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BLOCK4001", "이미 차단된 사용자입니다."),
    BLOCK_NOT_FOUND(HttpStatus.BAD_REQUEST, "BLOCK4002", "차단한 내역을 찾을 수 없습니다."),
    BLOCK_PAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "BLOCK4003", "차단 리스트를 확인할 수 없습니다. 유효하지 않은 페이지입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}