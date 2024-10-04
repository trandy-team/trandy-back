package org.trandy.trandy_server.member.domain.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoInfoRequest {
    private String nickName;
    private String image;

    public KakaoInfoRequest(String nickName, String image) {
        this.nickName = nickName;
        this.image = image;
    }
}
