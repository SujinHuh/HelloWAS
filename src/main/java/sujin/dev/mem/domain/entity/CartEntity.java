package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.CartDTO;

@Getter @Setter @Builder
public class CartEntity {

    private Long id;

    private MemberEntity member;

    private GoodsEntity goods;

    private OrdersEntity orders;

    public static CartEntity toEntity(CartDTO cart) {
        return CartEntity.builder()
                .member(cart.getMember())
                .goods(cart.getGoods())
                .orders(cart.getOrders())
                .build();
    }
}
