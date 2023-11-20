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
            try {
                OrdersEntity byId = this.repository.findById(order.getId());
                if (byId == null) {
                    this.repository.insert(order);
                }
                this.repository.update(order);
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }

        @Override
        public List<OrderDTO> getOrders() {
            return this.repository.findAll().stream().map(o ->{
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setCarts(o.getCarts());
                orderDTO.setMember(o.getMember());
                orderDTO.setGoods(o.getGoods());
                orderDTO.setStockQuantity(o.getStockQuantity());
                return orderDTO;
            }).toList();
        }
    }
}
