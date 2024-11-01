package org.trandy.trandy_server.report.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UpdateReportRequest {

    @Schema(description = "신고 아이디", example = "1")
    private long reportId;

    @Schema(description = "신고 코멘트", example = "그정도는 아닌거 같아서 반려합니다.")
    private String reportReviewComment;

    @Schema(description = "신고 승인 여부", example = "APPROVED / REJECTED")
    private String reportStatus;
}
