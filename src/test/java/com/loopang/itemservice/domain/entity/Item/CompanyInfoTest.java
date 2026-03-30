package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("CompanyInfo 단위 테스트")
class CompanyInfoTest {

    private static final UUID VALID_ID = UUID.randomUUID();
    private static final String VALID_NAME = "테스트 업체";

    @Test
    @DisplayName("유효한 값으로 CompanyInfo 생성 성공")
    void of_validInput_success() {
        CompanyInfo companyInfo = CompanyInfo.of(VALID_ID, VALID_NAME);

        assertThat(companyInfo.getId()).isEqualTo(VALID_ID);
        assertThat(companyInfo.getName()).isEqualTo(VALID_NAME);
    }

    @Test
    @DisplayName("업체 ID가 null이면 예외 발생")
    void of_nullId_throwsException() {
        assertThatThrownBy(() -> CompanyInfo.of(null, VALID_NAME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @NullAndEmptySource
    @ParameterizedTest(name = "업체명이 \"{0}\"이면 예외 발생")
    @DisplayName("업체명이 null 또는 빈 값이면 예외 발생")
    void of_nullOrEmptyName_throwsException(String name) {
        assertThatThrownBy(() -> CompanyInfo.of(VALID_ID, name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @ValueSource(strings = {"   ", "\t", "\n"})
    @ParameterizedTest(name = "업체명이 공백 문자열 \"{0}\"이면 예외 발생")
    @DisplayName("업체명이 공백으로만 이루어진 경우 예외 발생")
    void of_blankName_throwsException(String blankName) {
        assertThatThrownBy(() -> CompanyInfo.of(VALID_ID, blankName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    @DisplayName("ID와 이름이 모두 null이면 ID 검증에서 먼저 예외 발생")
    void of_bothNull_throwsIdException() {
        assertThatThrownBy(() -> CompanyInfo.of(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @Test
    @DisplayName("서로 다른 UUID로 생성한 CompanyInfo는 다른 ID를 가짐")
    void of_differentIds_haveDistinctValues() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        CompanyInfo info1 = CompanyInfo.of(id1, VALID_NAME);
        CompanyInfo info2 = CompanyInfo.of(id2, VALID_NAME);

        assertThat(info1.getId()).isNotEqualTo(info2.getId());
    }

    @Test
    @DisplayName("업체명 최대 길이(100자) 허용")
    void of_maxLengthName_success() {
        String maxLengthName = "A".repeat(100);
        CompanyInfo companyInfo = CompanyInfo.of(VALID_ID, maxLengthName);

        assertThat(companyInfo.getName()).isEqualTo(maxLengthName);
    }
}