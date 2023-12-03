package sujin.dev.mem.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder
public class CartEntity {

    private Long id;
    private MemberEntity member;
    private List<GoodsEntity> goodsList;
    private OrdersEntity orders;

    public static CartEntity toEntity(CartDTO cartDTO) {
        return CartEntity.builder()
                .member(MemberEntity.toEntity(cartDTO.getMember()))
                .goodsList(cartDTO.getGoodsList().stream().map(GoodsDTO::toEntity).collect(Collectors.toList()))
                .orders(OrdersEntity.toEntity(cartDTO.getOrders()))
                .build();
    }
    public static CartDTO toDTO(CartEntity cartEntity) {
        return CartDTO.builder()
                .member(MemberDTO.fromEntity(cartEntity.getMember()))
                .goodsList(cartEntity.getGoodsList().stream().map(GoodsEntity::toDTO).collect(Collectors.toList()))
                .orders(OrdersEntity.toDTO(cartEntity.getOrders()))
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