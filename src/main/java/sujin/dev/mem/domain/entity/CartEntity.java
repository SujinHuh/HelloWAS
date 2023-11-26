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
    // CartEntity 클래스에 다음 메서드를 추가합니다.
    public GoodsEntity getGoods() {
        return this.goods;
    }

    public MemberEntity getMember() {
        return this.member;
    }

}
