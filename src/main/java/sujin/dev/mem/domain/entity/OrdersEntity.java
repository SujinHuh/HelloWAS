package sujin.dev.mem.domain.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class OrdersEntity {
    private Long id;
    private int stockQuantity;
    private LocalDateTime orderDateTime;
    private double totalPrice;
    private MemberEntity member;  // 1:N 관계, Member to Orders
    private CartEntity cart; // 1:1 관계, Cart to Orders
    private OrderItemsEntity orderItem; // 1:N 관계, Orders to OrderItems
//    private List<GoodsEntity> goods; // N:N 관계, Orders to Goods
}