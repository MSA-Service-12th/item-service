package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class HubInfoTest {

    @Test
    void of_정상적인_id와_이름으로_생성() {
        UUID hubId = UUID.randomUUID();
        String hubName = "서울 허브";

        HubInfo hubInfo = HubInfo.of(hubId, hubName);

        assertThat(hubInfo.getId()).isEqualTo(hubId);
        assertThat(hubInfo.getName()).isEqualTo(hubName);
    }

    @Test
    void of_id가_null이면_예외() {
        assertThatThrownBy(() -> HubInfo.of(null, "서울 허브"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    void of_이름이_null이면_예외() {
        UUID hubId = UUID.randomUUID();

        assertThatThrownBy(() -> HubInfo.of(hubId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    void of_id와_이름_모두_null이면_예외() {
        assertThatThrownBy(() -> HubInfo.of(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    void of_빈_문자열_이름은_허용() {
        UUID hubId = UUID.randomUUID();

        HubInfo hubInfo = HubInfo.of(hubId, "");

        assertThat(hubInfo.getName()).isEqualTo("");
    }

    @Test
    void of_공백_이름은_허용() {
        UUID hubId = UUID.randomUUID();

        HubInfo hubInfo = HubInfo.of(hubId, "   ");

        assertThat(hubInfo.getName()).isEqualTo("   ");
    }

    @Test
    void of_50자_이름_정상_생성() {
        UUID hubId = UUID.randomUUID();
        String hubName = "a".repeat(50);

        HubInfo hubInfo = HubInfo.of(hubId, hubName);

        assertThat(hubInfo.getName()).isEqualTo(hubName);
    }
}