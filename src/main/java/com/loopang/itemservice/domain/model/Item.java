package com.loopang.itemservice.domain.model;

import com.loopang.common.domain.BaseUserEntity;
import com.loopang.common.exception.BadRequestException;
import com.loopang.common.exception.CustomException;
import com.loopang.itemservice.domain.service.CompanyProvider;
import com.loopang.itemservice.domain.service.ItemCheck;
import com.loopang.itemservice.domain.service.RoleCheck;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/*
 *
 * 1. 상품 비활성화 시 deleted_by, deleted_at 필드를 통해 관리됩니다.
 * 2. 마스터 관리자 : 모든 기능에 대한 권한이 있는 관리자입니다.
 * 3. 허브 관리자
 *      - 담당 허브에 속하는 상품의 생성, 수정, 삭제, 조회 및 검색이 가능합니다.
 * 4. 배송 담당자 :
 *      - 상품의 조회 및 검색만 가능합니다.
 * 5. 업체 담당자 :
 *      - 본인 업체에 관련된 상품의 생성, 수정, 조회 및 검색이 가능합니다.
 *
 * 6. 상품 생성
 *      - [업체, 상품명]이 중복되는 상품은 생성할 수 없습니다.
 *      - 상품명은 필수이며 공백은 불가합니다.
 *      - 상품명은 255자 이하입니다.
 *      - 권한있는 사용자만 접근할 수 있습니다. (마스터 관리자, (담당)허브 관리자 , (본인)업체 담당자)
 * 7. 상품 수정
 *      - 이미 삭제된 상품은 수정할 수 없습니다.
 *      - 상품명은 필수이며 공백은 불가합니다.
 *      - 상품명은 255자 이하입니다.
 *      - 권한있는 사용자만 접근할 수 있습니다. (마스터 관리자, (담당)허브 관리자 , (본인)업체 담당자)
 * 8. 상품 삭제
 *      - 이미 삭제된 상품은 삭제할 수 없습니다.
 *      - 권한있는 사용자만 접근할 수 있습니다. (마스터 관리자, (담당)허브 관리자)
 *
 */
@Getter
@Entity
@Table(name = "p_item")
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private UUID id;

    @Column(nullable = false)
    private String name;  // 상품명

    @Embedded
    private Associate associate;


    // 마스터 관리자, (담당)허브 관리자 , (본인)업체 담당자
    @Builder
    public Item(String name, UUID companyId, CompanyProvider companyProvider, RoleCheck roleCheck, ItemCheck itemCheck) {

        if (name == null || name.isBlank()) {
            throw new BadRequestException("상품명은 필수이며 공백은 불가합니다.", "name");
        }

        String normalizedName = (name != null) ? name.trim().toUpperCase() : null;

        // [업체, 상품명]이 중복일 경우
        checkDuplicated(normalizedName, companyId, itemCheck);

        this.associate = new Associate(companyId, companyProvider);
        checkEditable(roleCheck);
        setName(normalizedName);
    }

    private void setName(String name) {

        if(name == null || name.isBlank()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "상품명은 필수이며 공백은 불가합니다.", "name");
        }
        if (name.length() > 255) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "상품명은 255자 이하입니다.", "name");
        }

        this.name = name;
    }

    // 마스터 관리자, (담당)허브 관리자 , (본인)업체 담당자
    // todo: 수정할 시, 이벤트 발행
    public void changeName(String name, RoleCheck roleCheck, ItemCheck itemCheck) {
        if (isDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 상품입니다.");
        }
        checkEditable(roleCheck);

        String normalizedName = (name != null) ? name.trim().toUpperCase() : null;

        UUID companyId = this.associate.getCompany().getId();
        // 현재 상품 제외하고 중복 체크 필요 (ItemCheck 인터페이스 수정 필요할 수 있음)
        checkDuplicated(normalizedName, companyId, itemCheck);
        setName(name);
    }


    // 마스터 관리자, (담당)허브 관리자
    public void delete(UUID userId, RoleCheck roleCheck) {
        if (isDeleted()) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "이미 삭제된 상품입니다.");
        }
        // todo: null 예외처리

        checkDeletable(roleCheck);
        super.delete(userId);

        // todo: common 모듈 수정
//        super.delete(null);
    }

    // 마스터 관리자, (담당)허브 관리자 , (본인)업체 담당자
    private void checkEditable(RoleCheck roleCheck) {
        UUID companyId = this.associate.getCompany().getId();
        if (!(roleCheck.hasRole("MASTER")
            || (roleCheck.hasRole("HUB") && roleCheck.isMyHub(companyId))
            || (roleCheck.hasRole("COMPANY") && roleCheck.isMyCompany(companyId)))) {
            throw new CustomException(HttpStatus.FORBIDDEN, "해당 상품에 대한 생성 또는 수정 권한이 없습니다.");
        }
    }

    // 마스터 관리자, (담당)허브 관리자
    private void checkDeletable(RoleCheck roleCheck) {
        UUID companyId = this.associate.getCompany().getId();
        if (!(roleCheck.hasRole("MASTER")
            || (roleCheck.hasRole("HUB") && roleCheck.isMyHub(companyId)))) {
            throw new CustomException(HttpStatus.FORBIDDEN, "해당 상품에 대한 삭제 권한이 없습니다.");
        }
    }

    private void checkDuplicated(String name, UUID companyId, ItemCheck itemCheck) {
        if (itemCheck.isDuplicated(name, companyId)) {
            throw new CustomException(HttpStatus.CONFLICT, "이미 등록된 상품입니다. 상품 이름: " + name);
        }
    }
}
