package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartDTO {
    private double totalPrice;
    private MemberDTO member;
    private OrderDTO orders;
    private List<CartGoodsDTO> cartGoods; // 1:N 관계, CartDTO to CartGoodsDTO
    public static CartDTO fromEntity(CartEntity cartEntity) {
        if (cartEntity == null) {
            return null;
        }

        // CartEntity의 MemberEntity를 MemberDTO로 변환
        MemberDTO memberDTO = cartEntity.getMember() != null ? MemberDTO.fromEntity(cartEntity.getMember()) : null;

        // CartEntity의 OrdersEntity를 OrderDTO로 변환
        OrderDTO orderDTO = cartEntity.getOrders() != null ? OrderDTO.fromEntity(cartEntity.getOrders()) : null;

        // CartEntity의 CartGoodsEntity 리스트를 CartGoodsDTO 리스트로 변환
        List<CartGoodsDTO> cartGoodsDTOs = cartEntity.getCartGoods() != null ?
                cartEntity.getCartGoods().stream().map(CartGoodsDTO::fromEntity).collect(Collectors.toList()) :
                Collections.emptyList();

        return CartDTO.builder()
                .totalPrice(cartEntity.getTotalPrice())
                .member(memberDTO)
                .orders(orderDTO)
                .cartGoods(cartGoodsDTOs)
                .build();
    }
}
