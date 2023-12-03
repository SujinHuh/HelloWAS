package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.List;

public interface CartService {
    void registerCart(CartEntity cart);

    List<CartDTO> getCartList();

    void clearCart(MemberDTO member);

    @RequiredArgsConstructor
    class CartServiceImple implements CartService {
        public final DataRepository<CartEntity> repository;
        private final GoodsService goodsService;
        private final MemberService memberService;

        @Override
        public void registerCart(CartEntity cart) {
            try {
                CartEntity byId = this.repository.findById(cart.getId());
                if (byId == null) {
                    this.repository.insert(cart);
                }
                this.repository.update(cart);
            } catch (RuntimeException re) {
                re.printStackTrace();
            }
        }

        @Override
        public List<CartDTO> getCartList() {
            // 모든 장바구니 정보를 가져와서 반환
            List<CartEntity> cartEntities = repository.findAll();
            return cartEntities.stream()
                    .map(this::mapToDTO)
                    .toList();
        }

        // CartEntity를 CartDTO로 매핑하는 메서드
        private CartDTO mapToDTO(CartEntity cartEntity) {
            return CartDTO.builder()
                    .member(MemberDTO.fromEntity(cartEntity.getMember()))
                    .goodsList(cartEntity.getGoodsList())
                    .orders(OrdersEntity.toDTO(cartEntity.getOrders()))
                    .build();
        }

        @Override
        public void clearCart(sujin.dev.mem.domain.model.MemberDTO member) {
            // MemberDTO를 MemberEntity로 변환
            MemberEntity memberEntity = MemberEntity.toEntity(member);

            // MemberEntity의 id를 기반으로 장바구니 조회
            CartEntity cart = repository.findById(memberEntity.getId());

            if (cart != null) {
                // 장바구니에 있는 상품들을 모두 삭제
                cart.getGoodsList().clear();
                // 변경된 장바구니 엔터티를 저장
                repository.update(cart);
            }
        }
    }
}
