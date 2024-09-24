package org.trandy.trandy_server.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Grade {
    BRONZE("BRONZE", "브론즈"),
    SILVER("SILVER", "실버"),
    GOLD("GOLD", "골드"),
    PLATINUM("PLATINUM", "플래티넘"),
    DIAMOND("DIAMOND", "다이아몬드");

    private final String grade;
    private final String description;
}
