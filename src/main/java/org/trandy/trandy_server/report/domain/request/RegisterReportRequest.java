package org.trandy.trandy_server.report.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.trandy.trandy_server.report.domain.ReportCategory;

@Getter
public class RegisterReportRequest {
    @Schema(description = "게시물 아이디", example = "1")
    private long postId;

    @Schema(description = "신고 카테고리", example = "VIOLATION_INAPPROPRIATE_NAME / PORNOGRAPHIC_POSTS",
            allowableValues = "VIOLATION_INAPPROPRIATE_NAME, PORNOGRAPHIC_POSTS")
    private ReportCategory reportCategory;

    @Schema(description = "신고 내용", example = "부적절한 이름을 사용하였습니다.")
    private String contents;
}
