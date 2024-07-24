package org.example.petwif.apiPayload.code.status;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.petwif.apiPayload.code.BaseCode;
import org.example.petwif.apiPayload.code.ReasonDto;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    OK(HttpStatus.OK,"COMMON200", "성공적으로 요청을 수행하였습니다."),
    CREATED(HttpStatus.CREATED,"COMMON201", "성공적으로 생성하였습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT,"COMMON204", "성공적으로 삭제하였습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .httpStatus(httpStatus)
                .isSuccess(true)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ReasonDto getReasonHttpStatus() {
        return ReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
