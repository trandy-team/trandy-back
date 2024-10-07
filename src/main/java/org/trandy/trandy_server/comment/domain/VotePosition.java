package org.trandy.trandy_server.comment.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VotePosition {
    AGREE("AGREE", "찬성"),
    DISAGREE("DISAGREE", "반대");

    private final String position;
    private final String desc;
}
