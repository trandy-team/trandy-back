package org.trandy.trandy_server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionStatus {
    // 다뤄져야할 예외처리 케이스가 있을 경우, 추가한다.

    // 인증 관련
    MemberIdDuplicatedException("UserIdDuplicatedException", HttpStatus.BAD_REQUEST, "이미 존재하는 아이디 입니다."),
    MemberNotFoundException("MemberNotFoundException", HttpStatus.BAD_REQUEST, "정보와 일치하는 회원이 없습니다."),
    PasswordNotMatchedException("PasswordNotMatchedException", HttpStatus.BAD_REQUEST, "로그인 패스워드가 일치하지 않습니다."),
    AuthenticationException("AuthenticationException", HttpStatus.UNAUTHORIZED, "인증에 실패하였습니다."),
    // Jwt
    TokenSecurityExceptionOrMalformedJwtException("TokenSecurityExceptionOrMalformedJwtException", HttpStatus.BAD_REQUEST, "유효하지 않은 JWT 서명 입니다."),
    TokenExpiredJwtException("TokenExpiredJwtException", HttpStatus.SEE_OTHER, "만료된 JWT token 입니다."),
    TokenUnsupportedJwtException("TokenUnsupportedJwtException", HttpStatus.BAD_REQUEST, "지원되지 않는 JWT 토큰 입니다."),
    TokenIllegalArgumentException("TokenIllegalArgumentException", HttpStatus.BAD_REQUEST, "JWT claims is empty, 잘못된 JWT 토큰 입니다."),
    RefreshTokenNotValidException("RefreshTokenValidException", HttpStatus.BAD_REQUEST, "refreshToken이 유효하지 않습니다."),
    RefreshTokenNotFoundException("RefreshTokenNotFoundException", HttpStatus.SEE_OTHER, "refreshToken이 존재하지 않습니다."),
    IpNotMatchedException("IpNotMatchedException", HttpStatus.SEE_OTHER, "로그인 시점과 Ip가 일치하지 않습니다. 재로그인 해주세요."),
    TokenNeedReIssueException("TokenNeedReIssueException", HttpStatus.SEE_OTHER, "액세스 토큰이 만료되었습니다. 재발급 해주세요."),
    InValidAccessTokenException("InValidAccessTokenException", HttpStatus.BAD_REQUEST, "액세스 토큰이 유효하지 않습니다."),
    AccessTokenCreateFailedException("AccessTokenCreateFailedException", HttpStatus.BAD_REQUEST, "액세스 토큰 발급 실패"),
    RefreshTokenCreateFailedException("RefreshTokenCreateFailedException", HttpStatus.BAD_REQUEST, "리프레시 토큰 발급 실패"),

    // Category
    CategoryNameIsDuplicatedException("CategoryNameIsDuplicatedException", HttpStatus.BAD_REQUEST, "이미 존재하는 카테고리 입니다. (삭제된 카테고리 포함)"),

    // Comment
    CommentAlreadyResisteredException("CommentAlreadyResisteredException", HttpStatus.BAD_REQUEST, "이미 투표를 진행한 회원입니다."),

    // DB
    DataNotFoundException("DataNotFoundException", HttpStatus.BAD_REQUEST, "데이터가 존재하지 않습니다."),

    // S3
    FileUploadFailedException("FileUploadFailedException", HttpStatus.BAD_REQUEST, "S3 파일 업로드에 실패했습니다");
    private final String exceptionName;
    private final HttpStatus statusCode;
    private final String errorMessage;
}
