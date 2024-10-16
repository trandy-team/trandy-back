package org.trandy.trandy_server.member.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.trandy.trandy_server.member.service.KakaoService;

@RestController
@AllArgsConstructor
@RequestMapping("/oauth")
public class KakaoController {
    private final KakaoService kakaoService;

    @ResponseBody
    @GetMapping("/kakao")
    public void kakaoCallback(@RequestParam String code) {
        String accessToken = kakaoService.getKakaoAccessToken(code);
        System.out.println("인가코드 : " + code);
        System.out.println("Controller accessToken : " + accessToken);
    }
}
