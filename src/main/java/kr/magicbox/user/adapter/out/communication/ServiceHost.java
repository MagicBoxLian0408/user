package kr.magicbox.user.adapter.out.communication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceHost {
    REVIEW("review");

    private final String hostName;
}