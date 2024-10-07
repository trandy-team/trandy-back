package org.trandy.trandy_server.comment.domain.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RegisterCommentRequest {
    @NotNull
    @Schema(description = "게시물 ID", example = "1")
    private long postId;

    @NotNull
    @Schema(description = "댓글", example = "난 반대한다.")
    private String contents;

    @NotNull
    @Schema(description = "찬성 / 반대", example = "AGREE / DISAGREE")
    private String votePosition;
}
