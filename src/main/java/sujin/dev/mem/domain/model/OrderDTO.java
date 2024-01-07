package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDTO {

//    private Long id;
    private int stockQuantity;
    private LocalDateTime orderDateTime;
    private double totalPrice;
    private MemberDTO member; // 1:N 관계, Member to Orders
    private List<CartDTO> cart; // 1:1 관계, Cart to Orders
    private OrderItemsDTO orderItemsDTO; //1:N 관계

    public static OrderDTO fromEntity(OrdersEntity orders) {
        if (orders == null) {
            return null;
        }

        MemberDTO memberDTO = MemberDTO.fromEntity(orders.getMember());
        CartDTO cartDTO = orders.getCart() != null ? CartDTO.fromEntity(orders.getCart()) : null;

        return OrderDTO.builder()
                .stockQuantity(orders.getStockQuantity())
                .orderDateTime(orders.getOrderDateTime())
                .totalPrice(orders.getTotalPrice())
                .member(memberDTO)
                .cart((List<CartDTO>) cartDTO)
                .build();
    }
}
