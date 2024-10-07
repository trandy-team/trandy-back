package org.trandy.trandy_server.report.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportStatus {
    PENDING("PENDING","보류중"),
    APPROVED("APPROVED","승인"),
    REJECTED("REJECTED","반려");

    private final String status;
    private final String desc;
}
