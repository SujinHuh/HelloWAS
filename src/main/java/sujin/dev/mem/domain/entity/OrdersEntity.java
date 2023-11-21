package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.OrderDTO;

import java.util.List;

@Getter @Setter @Builder
public class OrdersEntity {

    private Long id;

    private List<CartEntity> carts;

    private MemberEntity member;

    private GoodsEntity goods;

    private int stockQuantity;

    public static OrdersEntity toEntity(OrderDTO order) {
        return OrdersEntity.builder()
                .carts(order.getCarts())
                .member(order.getMember())
                .goods(order.getGoods())
                .stockQuantity(order.getStockQuantity())
                .build();
    }
}
