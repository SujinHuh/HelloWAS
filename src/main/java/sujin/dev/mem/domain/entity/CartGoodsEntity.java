package sujin.dev.mem.domain.entity;

import lombok.*;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class CartGoodsEntity {

    private Long id;
    private int quantity;// 수량
    private double totalPrice;
    private CartEntity cart;  // N:1 관계, CartGoodsEntity to CartEntity
    private GoodsEntity goods; // N:1 관계, CartGoodsEntity to GoodsEntity

}
