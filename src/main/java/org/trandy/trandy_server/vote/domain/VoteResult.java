package org.trandy.trandy_server.vote.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteResult {

    TRENDING("TRENDING", "유행"),
    NOT_TRENDING("NOT_TRENDING", "유행X");

    private final String status;
    private final String desc;
}
