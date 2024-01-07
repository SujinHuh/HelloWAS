package sujin.dev.mem.infra.repo.impl;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.OrdersEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderRepository implements DataRepository<OrdersEntity> {

    private final List<OrdersEntity> ordersList = new ArrayList<>();

    public OrderRepository(ArrayList<Object> objects) {
    }

    @Override
    public OrdersEntity findById(long id) {
        return ordersList.stream()
                .filter(order -> order.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<OrdersEntity> findAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public void update(OrdersEntity ordersEntity) {

    }

    @Override
    public void insert(OrdersEntity ordersEntity) {

    }


}
