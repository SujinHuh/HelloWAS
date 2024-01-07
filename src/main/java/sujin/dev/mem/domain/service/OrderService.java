package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.OrdersEntity;
import sujin.dev.mem.domain.model.OrderDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public interface OrderService {

    void registerOrder(OrdersEntity order);
    List<OrderDTO> getOrders();
    @RequiredArgsConstructor
    class OrderServiceImpl implements OrderService {
        private final DataRepository<OrdersEntity> repository;


        @Override
        public void registerOrder(OrdersEntity order) {

        }

        @Override
        public List<OrderDTO> getOrders() {
            return null;
        }
    }
}
