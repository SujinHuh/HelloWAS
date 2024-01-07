package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartGoodsEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartGoodsDTO {
    private int quantity;// 수량
    private double totalPrice;
    private CartDTO cart;// N:1 관계 CartGoodsDTO to CartDTO
    private GoodsDTO goods; // N:1 관계 CartGoodsDTO to GoodsDTO

    public static CartGoodsDTO fromEntity(CartGoodsEntity cartGoods) {
        if (cartGoods == null) {
            return null;
        }

        // CartGoodsEntity의 단일 GoodsEntity를 GoodsDTO로 변환
        GoodsDTO goodsDTO = cartGoods.getGoods() != null ? GoodsDTO.fromEntity(cartGoods.getGoods()) : null;

        return CartGoodsDTO.builder()
                .quantity(cartGoods.getQuantity())
                .totalPrice(cartGoods.getTotalPrice())
                .goods(goodsDTO)  // 단일 객체로 설정
                .build();
    }
}
