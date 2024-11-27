package com.icd.survey.api.entity.survey;

import com.icd.survey.api.entity.dto.ItemResponseDto;
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
@Table(name = "item_response")
public class ItemResponse {
    @Id
    @Column(name = "response_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long responseSeq;
    @Column(name = "response", nullable = false)
    private String response;

    public static ItemResponse createItemResponseRequest(ItemResponseDto dto) {
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.response = dto.getResponse();
        return itemResponse;
    }

    public ItemResponseDto of() {
        return ItemResponseDto
                .builder()
                .responseSeq(this.responseSeq)
                .response(this.response)
                .build();
    }
}
