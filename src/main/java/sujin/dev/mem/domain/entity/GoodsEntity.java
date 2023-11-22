package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.GoodsDTO;

@Getter @Setter @Builder
public class GoodsEntity {

    private Long id;

    private String name;

    private GoodsDTO.CurrentValueEntity currentValue;

    private int stockQuantity;

    public int decrementStockQuantity(int quantity) {
        if(stockQuantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다. ");
        }
        stockQuantity -= quantity;
        return stockQuantity;
    }
    public static GoodsEntity toEntity(GoodsDTO goods) {
        return GoodsEntity.builder()
                .name(goods.getName())
                .currentValue(goods.getCurrentValue())
                .stockQuantity(goods.getStockQuantity())
                .build();
    }

}
