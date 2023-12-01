package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.CartDTO;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Builder
public class CartEntity {

    private Long id;
    private MemberEntity member;
    private List<GoodsEntity> goodsList;
    private OrdersEntity orders;

    public static CartEntity toEntity(CartDTO cart) {
        return CartEntity.builder()
                .member(cart.getMember())
                .goodsList(cart.getGoodsList())
                .orders(cart.getOrders())
                .build();
    }

    public List<GoodsEntity> getGoodsList() {
        if (this.goodsList == null) {
            this.goodsList = new ArrayList<>();
        }
        return this.goodsList;
    }

    public void clearGoodsList() {
        if (this.goodsList != null) {
            this.goodsList.clear();
        }
    }

    public GoodsEntity getGoods() {
        return this.goodsList != null && !this.goodsList.isEmpty() ? this.goodsList.get(0) : null;
    }

    public MemberEntity getMember() {
        return this.member;
    }
}