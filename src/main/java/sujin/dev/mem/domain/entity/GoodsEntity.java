package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sujin.dev.mem.domain.model.GoodsDTO;

import java.math.BigDecimal;
import java.util.Currency;

@Getter @Setter @Builder @ToString
public class GoodsEntity {

    private Long id;
    private String name;
    private CurrentValueEntity currentValue;
    private int stockQuantity;

    public int decrementStockQuantity(int quantity) {
        if (stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        stockQuantity -= quantity;
        return stockQuantity;
    }

    public static GoodsEntity toEntity(GoodsDTO goods) {
        return GoodsEntity.builder()
                .name(goods.getName())
                .currentValue(toEntity(goods.getCurrentValue()))
                .stockQuantity(goods.getStockQuantity())
                .build();
    }

    public static GoodsEntity.CurrentValueEntity toEntity(CurrentValueDTO currentValueDTO) {
        return GoodsEntity.CurrentValueEntity.builder()
                .amount(currentValueDTO.getAmount())
                .currency(currentValueDTO.getCurrency())
                .build();
    }

    // Inner class
    @Getter
    @Setter
    @Builder
    public static class CurrentValueEntity {
        private BigDecimal amount;
        private Currency currency;
    }
}