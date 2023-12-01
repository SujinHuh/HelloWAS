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
        return new ArrayList<>(ordersList);
    }

    @Override
    public void deleteById(long id) {
        ordersList.removeIf(order -> order.getId() == id);
    }

    @Override
    public void update(OrdersEntity ordersEntity) {
        OrdersEntity existingOrder = findById(ordersEntity.getId());
        if (existingOrder != null) {
            // Update the existing order with the new information
            existingOrder.setCarts(ordersEntity.getCarts());
            existingOrder.setMember(ordersEntity.getMember());
            existingOrder.setGoods(ordersEntity.getGoods());
            existingOrder.setStockQuantity(ordersEntity.getStockQuantity());
        }
    }

    @Override
    public void insert(OrdersEntity ordersEntity) {
        ordersList.add(ordersEntity);
    }

    @Override
    public OrdersEntity findByMemberName(String memberName) {
        return ordersList.stream()
                .filter(order -> order.getMember().getName().equals(memberName))
                .findFirst()
                .orElse(null);
    }
}
