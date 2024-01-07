package sujin.dev.mem.infra.repo.impl;


import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartGoodsEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CartGoodsRepository implements DataRepository<CartGoodsEntity> {
    private final List<CartGoodsEntity> cartGoodsList;

    @Override
    public CartGoodsEntity findById(long id) {
        return cartGoodsList.stream()
                .filter(cartGoods -> cartGoods.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CartGoodsEntity> findAll() {
        return new ArrayList<>(cartGoodsList);
    }

    @Override
    public void deleteById(long id) {
        cartGoodsList.removeIf(cartGoods -> cartGoods.getId() == id);
    }

    @Override
    public void update(CartGoodsEntity cartGoods) {
        int index = cartGoodsList.indexOf(cartGoods);
        if (index != -1) {
            cartGoodsList.set(index, cartGoods);
        }

    }
    @Override
    public void insert(CartGoodsEntity cartGoods) {
        cartGoodsList.add(cartGoods);
    }
}