package sujin.dev.mem.domain.entity;

import lombok.*;
import sujin.dev.hellowas.domain.entity.Cart;
import sujin.dev.mem.domain.model.CurrentValueDTO;
import sujin.dev.mem.domain.model.GoodsDTO;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class GoodsEntity {

    private Long id;
    private String name;
    private CurrentValueEntity currentValue;
    private int stockQuantity; // 재고수량
    private List<OrderItemsEntity> orderItem; // 1:N 관계 , GoodsEntity to OrderItemsEntity
    private List<CartGoodsEntity> cartGoods; // 1:N 관계 , GoodsEntity to CartGoodsEntity

    public static GoodsEntity toEntity(GoodsDTO goods) {
        CurrentValueDTO currentValueDTO = goods.getCurrentValue();
        CurrentValueEntity currentValueEntity = null;

        if (currentValueDTO != null) {
            currentValueEntity = CurrentValueEntity.builder()
                    .amount(currentValueDTO.getAmount())
                    .currency(currentValueDTO.getCurrency())
                    .build();
        }

        return GoodsEntity.builder()
                .name(goods.getName())
                .currentValue(currentValueEntity)
                .stockQuantity(goods.getStockQuantity())
                // orderItem과 cartGoods는 필요에 따라 설정합니다.
                .build();
    }

    public GoodsDTO toDTO() {
        CurrentValueDTO currentValueDTO = null;

        if (this.currentValue != null) {
            currentValueDTO = CurrentValueDTO.builder()
                    .amount(this.currentValue.getAmount())
                    .currency(this.currentValue.getCurrency())
                    .build();
        }

        return GoodsDTO.builder()
                .name(this.name)
                .currentValue(currentValueDTO)
                .stockQuantity(this.stockQuantity)
                // orderItem과 cartGoods는 필요에 따라 설정합니다.
                // 예시: .orderItem(convertOrderItemsToDTO(this.orderItem))
                .build();
    }
}