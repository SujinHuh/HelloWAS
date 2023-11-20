package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;

import java.util.List;
@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDTO {

//    private Long id;

    private List<CartEntity> carts;

    private MemberEntity member;

    private GoodsEntity goods;

    private int stockQuantity;
}
