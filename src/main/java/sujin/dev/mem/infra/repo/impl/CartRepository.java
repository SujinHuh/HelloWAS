package sujin.dev.mem.infra.repo.impl;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

@RequiredArgsConstructor
public class CartRepository implements DataRepository<CartEntity> {

    private final List<CartEntity> carts;

    @Override
    public CartEntity findById(long id) {
        return carts.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CartEntity> findAll() {
        return this.carts;
    }

    @Override
    public void deleteById(long id) {
        this.carts.removeIf(c -> c.getId() == id);
    }

    @Override
    public void update(CartEntity cart) {
        this.carts.stream().filter(c -> c.getId() == cart.getId()).forEach(
                c1 -> c1 = cart
        );
    }

    @Override
    public void insert(CartEntity cart) {
        this.carts.add(cart);
    }
}
