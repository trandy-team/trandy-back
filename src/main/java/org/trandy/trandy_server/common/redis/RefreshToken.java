package org.trandy.trandy_server.common.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "email")
@Getter
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    private String email;
    private String refreshToken;

    @TimeToLive
    private Long expiration;

    public RefreshToken updateToken(String refreshToken, Long expiration) {
        this.refreshToken = refreshToken;
        this.expiration = expiration;
        return this;
    }
}