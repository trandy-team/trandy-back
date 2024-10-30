package org.trandy.trandy_server.comment.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @NotNull
    @Schema(description = "게시물 ID", example = "1")
    private long postId;

    @NotNull
    @Schema(description = "댓글", example = "난 반대한다.")
    private String contents;
}
