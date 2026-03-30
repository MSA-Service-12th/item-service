package com.loopang.itemservice.domain.entity.Item;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CompanyInfoTest {

    @Test
    void of_정상적인_id와_이름으로_생성() {
        UUID companyId = UUID.randomUUID();
        String companyName = "테스트업체";

        CompanyInfo companyInfo = CompanyInfo.of(companyId, companyName);

        assertThat(companyInfo.getId()).isEqualTo(companyId);
        assertThat(companyInfo.getName()).isEqualTo(companyName);
    }

    @Test
    void of_id가_null이면_예외() {
        assertThatThrownBy(() -> CompanyInfo.of(null, "테스트업체"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @Test
    void of_이름이_null이면_예외() {
        UUID companyId = UUID.randomUUID();

        assertThatThrownBy(() -> CompanyInfo.of(companyId, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    void of_이름이_빈_문자열이면_예외() {
        UUID companyId = UUID.randomUUID();

        assertThatThrownBy(() -> CompanyInfo.of(companyId, ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    void of_이름이_공백만_있으면_예외() {
        UUID companyId = UUID.randomUUID();

        assertThatThrownBy(() -> CompanyInfo.of(companyId, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체명은 필수이며, 공백은 불가합니다.");
    }

    @Test
    void of_id와_이름_모두_null이면_id_예외가_먼저() {
        assertThatThrownBy(() -> CompanyInfo.of(null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("업체 ID는 필수입니다.");
    }

    @Test
    void of_이름에_공백이_포함된_경우_정상_생성() {
        UUID companyId = UUID.randomUUID();
        String companyName = "테스트 업체 이름";

        CompanyInfo companyInfo = CompanyInfo.of(companyId, companyName);

        assertThat(companyInfo.getName()).isEqualTo("테스트 업체 이름");
    }

    @Test
    void of_100자_이름_정상_생성() {
        UUID companyId = UUID.randomUUID();
        String companyName = "a".repeat(100);

        CompanyInfo companyInfo = CompanyInfo.of(companyId, companyName);

        assertThat(companyInfo.getName()).isEqualTo(companyName);
    }
}