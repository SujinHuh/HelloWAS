package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartDTO {

    private MemberDTO member;
    private List<GoodsDTO> goodsList;
    private OrderDTO orders;

    public static CartEntity toEntity(CartDTO cartDTO) {
        CartEntity cartEntity = CartEntity.builder()
                .member(MemberEntity.toEntity(cartDTO.getMember()))
                .goodsList(cartDTO.getGoodsList().stream().map(GoodsDTO::toEntity).collect(Collectors.toList()))
                .orders(OrdersEntity.toEntity(cartDTO.getOrders()))
                .build();
        return cartEntity;
    }

    public void addGoods(GoodsDTO goods) {
        if (goodsList == null) {
            goodsList = new ArrayList<>();
        }
        goodsList.add(goods);
    }

    public void clearGoodsList() {
        if (goodsList != null) {
            goodsList.clear();
        }
    }
}
