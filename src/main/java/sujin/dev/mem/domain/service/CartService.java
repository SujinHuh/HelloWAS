package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.infra.repo.DataRepository;

public interface CartService {
    void registerCart(CartEntity cart);

    @RequiredArgsConstructor
    class CartServiceImple implements CartService {
        public final DataRepository<CartEntity> repository;

        @Override
        public void registerCart(CartEntity cart) {
            try {
                CartEntity byId = this.repository.findById(cart.getId());
                if(byId == null) {
                    this.repository.insert(cart);
                }
                this. repository.update(cart);
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }
    }
}
