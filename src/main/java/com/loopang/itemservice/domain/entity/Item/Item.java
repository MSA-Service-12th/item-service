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

    /**
     * Creates a new Item populated with the provided embedded value objects.
     *
     * @param companyInfo the company's identifying information
     * @param hubInfo     the hub's identifying information
     * @param name        the item's name value object
     */
    private Item(CompanyInfo companyInfo, HubInfo hubInfo, ItemName name) {
        this.companyInfo = companyInfo;
        this.hubInfo = hubInfo;
        this.name = name;
    }

    /**
     * Create a new Item associated with the specified company and hub using the provided item name.
     *
     * Constructs CompanyInfo and HubInfo from the given ids and names and returns an Item containing those embedded value objects and the provided ItemName.
     *
     * @param companyId   the UUID of the company
     * @param companyName the display name of the company
     * @param hubId       the UUID of the hub
     * @param hubName     the display name of the hub
     * @param name        the ItemName value object describing the item's name
     * @return the newly created Item instance
     */
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
