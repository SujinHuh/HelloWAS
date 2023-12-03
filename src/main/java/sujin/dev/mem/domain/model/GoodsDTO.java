package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.GoodsEntity;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {

    private String name;
    private CurrentValueDTO currentValue;
    private int stockQuantity;

    // Inner class
    @Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
    public static class CurrentValueDTO {
        private BigDecimal amount;
        private Currency currency;
    }

    public GoodsEntity toEntity() {
        return GoodsEntity.builder()
                .name(this.name)
                .currentValue(toEntity(this.currentValue))
                .stockQuantity(this.stockQuantity)
                .build();
    }


    // Inner class의 변환 메서드 추가
    public static GoodsEntity.CurrentValueEntity toEntity(CurrentValueDTO currentValueDTO) {
        return GoodsEntity.CurrentValueEntity.builder()
                .amount(currentValueDTO.getAmount())
                .currency(currentValueDTO.getCurrency())
                .build();
    }
}
