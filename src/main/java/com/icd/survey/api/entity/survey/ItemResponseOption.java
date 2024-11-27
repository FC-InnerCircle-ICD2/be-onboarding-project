package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.dto.ItemResponseOptionDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_response_option")
public class ItemResponseOption {
    @Id
    @Column(name = "option_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long optionSeq;
    @Column(name = "option", nullable = false)
    private String option;

    public static ItemResponseOption createItemResponseOptionRequest(ItemResponseOptionDto dto) {
        ItemResponseOption itemResponseOption = new ItemResponseOption();
        itemResponseOption.option = dto.getOption();
        return itemResponseOption;
    }

    public ItemResponseOptionDto of() {
        return ItemResponseOptionDto
                .builder()
                .optionSeq(this.optionSeq)
                .option(this.option)
                .build();
    }

}
