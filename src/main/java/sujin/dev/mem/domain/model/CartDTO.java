package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartDTO {

    private MemberEntity member;

    private GoodsEntity goods;

    private OrdersEntity orders;

}
