package org.trandy.trandy_server.report.domain.request;

import lombok.Getter;

@Getter
public class RegisterReportRequest {
    private long postId;
    private String reportCategory;
    private String contents;
}
