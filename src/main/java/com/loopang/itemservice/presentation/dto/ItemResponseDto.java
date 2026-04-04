package com.loopang.itemservice.presentation.dto;

import com.loopang.itemservice.domain.model.Company;
import com.loopang.itemservice.domain.model.Hub;
import com.loopang.itemservice.domain.model.Item;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ItemResponseDto {

    private UUID id;
    private String name;
    private UUID companyId;
    private String companyName;
    private UUID hubId;
    private String hubName;


    public static ItemResponseDto from(Item item) {
        Company company = item.getAssociate().getCompany();
        Hub hub = item.getAssociate().getHub();
        return ItemResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .companyId(company.getId())
                .companyName(company.getName())
                .hubId(hub.getId())
                .hubName(hub.getName())
                .build();
    }
}
