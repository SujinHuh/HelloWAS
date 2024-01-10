package sujin.dev.mem.domain.entity;

import lombok.*;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.CartGoodsDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class CartEntity {

    private Long id;
    private BigDecimal totalPrice;
    private MemberEntity member;
    private OrdersEntity orders;
    private List<CartGoodsEntity> cartGoods; //1:N 관계 CartEntity to CartGoodsEntity

}