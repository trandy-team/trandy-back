package org.trandy.trandy_server.common;

// 프로젝트에 사용할 상수값 정의
public class Constants {

    // COMMON
    public static final Boolean DELETED = true;
    public static final Boolean DELETED_NOT = false;

    public static final String API_RESPONSE_SUCCESSED = "SUCCESSED";
    public static final String API_RESPONSE_FAILED = "FAILED";

    // Jwt
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String Bearer_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final long ACCESS_TOKEN_TIME = 2 * 60 * 60 * 1000; // 2 HOURS
    public static final long REFRESH_TOKEN_TIME  = 14 * 24 * 60 * 60 * 1000L; // 2 WEEKS

}
