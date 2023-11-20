package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @Builder
public class OrdersEntity {

    private Long id;

    private List<CartEntity> carts;

    private MemberEntity member;

    private GoodsEntity goods;

    private int stockQuantity;
}
