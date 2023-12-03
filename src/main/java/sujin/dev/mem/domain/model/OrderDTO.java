package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDTO {

//    private Long id;

    private List<CartDTO> carts;

    private MemberDTO member;

    private int stockQuantity;

    public static OrderDTO toDTO(OrdersEntity ordersEntity) {
        return OrderDTO.builder()
                .carts(ordersEntity.getCarts().stream().map(CartEntity::toDTO).collect(Collectors.toList()))
                .member(MemberDTO.fromEntity(ordersEntity.getMember()))
                .stockQuantity(ordersEntity.getStockQuantity())
                .build();
    }
}
