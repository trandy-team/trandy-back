package org.trandy.trandy_server.report.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportCategory {

    VIOLATION_INAPPROPRIATE_NAME("VIOLATION_INAPPROPRIATE_NAME", "부적절한 이름 사용"),
    PORNOGRAPHIC_POSTS("PORNOGRAPHIC_POSTS", "음란성 게시물");

    private final String categoryName;
    private final String desc;
}
