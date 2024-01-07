package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.GoodsEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class GoodsDTO {
    private String name;
    private CurrentValueDTO currentValue;
    private int stockQuantity; // 재고수량
    private List<OrderItemsDTO> orderItem;  // 1:N 관계 GoodsDTO to OrderItemsDTO
    private List<CartGoodsDTO> cartGoods; // 1:N 관계 , GoodsDTO to CartGoodsDTO

    public static GoodsDTO fromEntity(GoodsEntity goods) {
        if (goods == null) {
            return null;
        }

        CurrentValueDTO currentValueDTO = goods.getCurrentValue() != null
                ? CurrentValueDTO.fromEntity(goods.getCurrentValue())
                : null;

        // GoodsEntity의 OrderItemsEntity 리스트를 OrderItemsDTO 리스트로 변환
        List<OrderItemsDTO> orderItemsDTOs = goods.getOrderItem() != null
                ? goods.getOrderItem().stream().map(OrderItemsDTO::fromEntity).collect(Collectors.toList())
                : Collections.emptyList();

        // GoodsEntity의 CartGoodsEntity 리스트를 CartGoodsDTO 리스트로 변환
        List<CartGoodsDTO> cartGoodsDTOs = goods.getCartGoods() != null
                ? goods.getCartGoods().stream().map(CartGoodsDTO::fromEntity).collect(Collectors.toList())
                : Collections.emptyList();

        return GoodsDTO.builder()
                .name(goods.getName())
                .currentValue(currentValueDTO)
                .stockQuantity(goods.getStockQuantity())
                .orderItem(orderItemsDTOs)
                .cartGoods(cartGoodsDTOs)
                .build();
    }
}
