package sujin.dev.mem.domain.model;

import lombok.*;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.entity.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
public class CartDTO {

    private MemberEntity member;
    private List<GoodsEntity> goodsList;
    private OrdersEntity orders;

    // 다음 두 메서드를 추가합니다.
    public void addGoods(GoodsEntity goods) {
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
