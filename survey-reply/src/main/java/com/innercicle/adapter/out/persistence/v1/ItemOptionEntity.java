package com.innercicle.adapter.out.persistence.v1;

import com.innercicle.domain.ItemOption;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class ItemOptionEntity {

    private String option;
    private boolean checked;

    public static ItemOptionEntity from(ItemOption itemOption) {
        ItemOptionEntity itemOptionEntity = new ItemOptionEntity();
        itemOptionEntity.option = itemOption.option();
        itemOptionEntity.checked = itemOption.checked();
        return itemOptionEntity;
    }

    public ItemOption mapToDomain() {
        return ItemOption.builder()
            .option(this.option)
            .checked(this.checked)
            .build();
    }

}
