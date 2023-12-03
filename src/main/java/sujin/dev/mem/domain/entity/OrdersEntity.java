package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.domain.model.OrderDTO;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder
public class  OrdersEntity {

    private Long id;

    private List<CartEntity> carts;

    private MemberEntity member;

    private GoodsEntity goods;

    private int stockQuantity;

    public static OrdersEntity toEntity(OrderDTO order) {
        List<CartEntity> cartEntities = order.getCarts().stream()
                .map(CartDTO::toEntity)
                .collect(Collectors.toList());

        return OrdersEntity.builder()
                .carts(cartEntities)
                .member(MemberEntity.toEntity(order.getMember()))
                .stockQuantity(order.getStockQuantity())
                .build();
    }
}
