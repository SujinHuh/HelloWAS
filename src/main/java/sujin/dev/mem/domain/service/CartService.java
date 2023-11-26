package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;
import java.util.stream.Collectors;

public interface CartService {
    void registerCart(CartEntity cart);

    List<CartDTO> getCartList();

    @RequiredArgsConstructor
    class CartServiceImple implements CartService {
        public final DataRepository<CartEntity> repository;
        private final GoodsService goodsService;
        private final MemberService memberService;

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

        @Override
        public List<CartDTO> getCartList() {
            return this.repository.findAll().stream()
                    .map(c -> {
                        CartDTO cartDTO = new CartDTO();
                        cartDTO.setGoods(GoodsEntity.toEntity(GoodsDTO.builder()
                                .name(c.getGoods().getName())
                                        .build()));
                        cartDTO.setMember(MemberEntity.toEntity(MemberDTO.builder()
                                .name(c.getMember().getName())
                                .phone(c.getMember().getPhone())
                                .build()));
                        cartDTO.setGoods(GoodsEntity.toEntity(GoodsDTO.builder()
                                .stockQuantity(c.getGoods().getStockQuantity())
                                .build()));
                        return cartDTO;
                    })
                    .collect(Collectors.toList());
        }
    }
}
