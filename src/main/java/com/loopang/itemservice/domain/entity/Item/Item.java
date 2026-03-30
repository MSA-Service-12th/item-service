package com.loopang.itemservice.domain.entity.Item;

import com.loopang.itemservice.common.entity.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private CompanyInfo companyInfo;

    @Embedded
    private HubInfo hubInfo;

    @Embedded
    private ItemName name;

    private Item(CompanyInfo companyInfo, HubInfo hubInfo, ItemName name) {
        this.companyInfo = companyInfo;
        this.hubInfo = hubInfo;
        this.name = name;
    }

    public static Item of(UUID companyId, String companyName, UUID hubId, String hubName, ItemName name) {

        CompanyInfo companyInfo = CompanyInfo.of(companyId, companyName);
        HubInfo hubInfo = HubInfo.of(hubId, hubName);

        return Item.builder()
                .companyInfo(companyInfo)
                .hubInfo(hubInfo)
                .name(name)
                .build();
    }
}
