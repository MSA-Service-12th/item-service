package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HubInfo 단위 테스트")
class HubInfoTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final String VALID_NAME = "서울 허브";

    @Test
    @DisplayName("유효한 값으로 HubInfo 생성 성공")
    void of_validInput_success() {
        HubInfo hubInfo = HubInfo.of(VALID_ID, VALID_NAME);

        assertThat(hubInfo.getId()).isEqualTo(VALID_ID);
        assertThat(hubInfo.getName()).isEqualTo(VALID_NAME);
    }

    @Test
    @DisplayName("허브 ID가 null이면 예외 발생")
    void of_nullHubId_throwsException() {
        assertThatThrownBy(() -> HubInfo.of(null, VALID_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    @DisplayName("허브 이름이 null이면 예외 발생")
    void of_nullHubName_throwsException() {
        assertThatThrownBy(() -> HubInfo.of(VALID_ID, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    @DisplayName("허브 ID와 이름이 모두 null이면 예외 발생")
    void of_bothNull_throwsException() {
        assertThatThrownBy(() -> HubInfo.of(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("허브 정보는 필수입니다.");
    }

    @Test
    @DisplayName("허브 이름이 공백 문자열이어도 null이 아니면 생성 성공")
    void of_blankName_doesNotThrowBecauseOnlyNullIsChecked() {
        // HubInfo only validates against null, not blank strings
        HubInfo hubInfo = HubInfo.of(VALID_ID, "   ");

        assertThat(hubInfo.getName()).isEqualTo("   ");
    }

    @Test
    @DisplayName("허브 이름이 빈 문자열이어도 null이 아니면 생성 성공")
    void of_emptyName_doesNotThrowBecauseOnlyNullIsChecked() {
        // HubInfo only validates against null, not empty strings
        HubInfo hubInfo = HubInfo.of(VALID_ID, "");

        assertThat(hubInfo.getName()).isEqualTo("");
    }

    @Test
    @DisplayName("서로 다른 UUID로 생성한 HubInfo는 다른 ID를 가짐")
    void of_differentIds_haveDistinctValues() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        HubInfo info1 = HubInfo.of(id1, VALID_NAME);
        HubInfo info2 = HubInfo.of(id2, VALID_NAME);

        assertThat(info1.getId()).isNotEqualTo(info2.getId());
    }

    @Test
    @DisplayName("허브 이름이 최대 길이(50자) 경계값에서 생성 성공")
    void of_maxLengthName_success() {
        String maxLengthName = "A".repeat(50);
        HubInfo hubInfo = HubInfo.of(VALID_ID, maxLengthName);

        assertThat(hubInfo.getName()).isEqualTo(maxLengthName);
    }
}