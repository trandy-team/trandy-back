package org.trandy.trandy_server.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteType {
    AGREE("AGREE", "찬성"),
    DISAGREE("DISAGREE", "반대");

    private final String type;
    private final String desc;
}
