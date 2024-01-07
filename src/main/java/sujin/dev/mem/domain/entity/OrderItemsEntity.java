package sujin.dev.mem.domain.entity;

import lombok.*;
import java.util.List;
@Getter @ToString @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderItemsEntity {

    private Long id;
    private int quantity;  // 주문 항목의 수량
    private List<OrdersEntity> order; // N:1 ,주문 항목에 포함된 주문들의 리스트.
    private List<GoodsEntity> goods;  // N:1 ,주문 항목에 포함된 상품들의 리스트.

}
