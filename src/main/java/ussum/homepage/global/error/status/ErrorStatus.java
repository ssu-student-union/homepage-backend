package ussum.homepage.global.error.status;

import ussum.homepage.global.error.code.BaseErrorCode;
import ussum.homepage.global.error.dto.ErrorReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    //기본(전역) 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON_500", "서버에서 요청을 처리 하는 동안 오류가 발생했습니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON_400", "입력 값이 잘못된 요청 입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON_401", "인증이 필요 합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON_403", "금지된 요청 입니다."),

    //User 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "사용자를 찾을 수 없습니다."),

    //Group 관련 에러
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND,"GROUP_001","그룹을 찾을 수 없습니다."),
    ALREADY_GROUP_EXIST(HttpStatus.NOT_FOUND,"GROUP_002","같은 code를 가진 그룹이 존재한다."),

    //Member 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER_001","멤버를 찾을 수 없습니다."),

    //Board 관련 에러
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"BOARD_001","게시판을 찾을 수 없습니다."),

    //Post 관련 에러
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_001","게시글을 찾을 수 없습니다."),
    //PostComment 관련 에러
    POST_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_COMMENT_001","댓글을 찾을 수 없습니다."),

    //Category 관련 에러
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"CATEGORY_001","카테고리를 찾을 수 없습니다."),

    //ACL 관련 에러
    ACL_PERMISSION_DENIED(HttpStatus.FORBIDDEN,"ACL_001","ACL에 권한이 없습니다."),
    BOARD_ACL_NOT_FOUND(HttpStatus.NOT_FOUND,"BOARD_ACL_001","게시판 ACL을 찾을 수 없습니다."),
    POST_ACL_NOT_FOUND(HttpStatus.NOT_FOUND,"POST_ACL_001","포스트 ACL을 찾을 수 없습니다."),

    //Role 관련 에러
    ROLE_PERMISSION_DENIED(HttpStatus.FORBIDDEN,"ROLE_001","필요한 Role을 가지고 있지 않습니다."),
    //enum class
    INVALID_ROLE(HttpStatus.FORBIDDEN,"ENUM_001","유효하지 않은 Role입니다."),
    INVALID_MAJORCODE(HttpStatus.FORBIDDEN,"ENUM_002","유효하지 않은 MAJORCODE입니다."),
    INVALID_REACTION(HttpStatus.FORBIDDEN,"ENUM_003","유효하지 않은 REACTION입니다."),
    INVALID_BOARDCODE(HttpStatus.FORBIDDEN,"ENUM_004","유효하지 않은 BOARDCODE입니다."),
    INVALID_TYPE(HttpStatus.FORBIDDEN,"ENUM_005","유효하지 않은 TYPE입니다."),
    INVALID_TARGET(HttpStatus.FORBIDDEN,"ENUM_006","유효하지 않은 TARGET입니다."),
    INVALID_ORDER(HttpStatus.FORBIDDEN,"ENUM_007","유효하지 않은 ORDER입니다."),
    INVALID_ACTION(HttpStatus.FORBIDDEN,"ENUM_008","유효하지 않은 ACTION입니다."),

    /**
     * 401 Unauthorized, Token 관련 에러
     */
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,"TOKEN_000","토큰이 올바르지 않습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"TOKEN_001", "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_002", "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "TOKEN_003", "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_004", "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_005", "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "TOKEN_006", "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_007", "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "TOKEN_008", "일치하지 않는 리프레시 토큰입니다."),
    INVALID_AUTH_CODE(HttpStatus.UNAUTHORIZED, "TOKEN_009", "인증을 실패했습니다."),

    //Body 에러
    INVALID_BODY(HttpStatus.BAD_REQUEST, "BODY_ERROR", "Body가 올바르지 않습니다.");




    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }


}
