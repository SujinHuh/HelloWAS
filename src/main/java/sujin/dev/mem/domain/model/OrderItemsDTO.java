package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.OrderItemsEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderItemsDTO {

    private int quantity; //주문 항목의 수량
    private List<OrderDTO> order; // N:1, OrderItemsDTO to OrderDTO
    private List<GoodsDTO> goods; // N:1 OrderItemsDTO to GoodsDTO

    public static OrderItemsDTO fromEntity(OrderItemsEntity orderItem) {
        if (orderItem == null) {
            return null;
        }

        List<OrderDTO> orderDTOList = orderItem.getOrder() != null
                ? orderItem.getOrder().stream().map(OrderDTO::fromEntity).collect(Collectors.toList())
                : Collections.emptyList();

        List<GoodsDTO> goodsDTOList = orderItem.getGoods() != null
                ? orderItem.getGoods().stream().map(GoodsDTO::fromEntity).collect(Collectors.toList())
                : Collections.emptyList();

        return new OrderItemsDTO(orderItem.getQuantity(), orderDTOList, goodsDTOList);
    }
}
