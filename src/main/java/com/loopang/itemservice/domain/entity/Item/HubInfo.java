package com.loopang.itemservice.domain.entity.Item;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access =AccessLevel.PROTECTED)
public class HubInfo {

    @Column(name = "hub_id")
    private UUID id;

    @Column(length = 50, name = "hub_name")
    private String name;

    public static HubInfo of(UUID hubId, String hubName) {
        if (hubId == null || hubName == null) {
            throw new IllegalArgumentException("허브 정보는 필수입니다.");
        }

        return new HubInfo(hubId, hubName);
    }
}
