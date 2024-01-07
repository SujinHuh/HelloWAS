package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartGoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.CartDTO;
import sujin.dev.mem.domain.model.CartGoodsDTO;
import sujin.dev.mem.domain.model.GoodsDTO;
import sujin.dev.mem.domain.model.MemberDTO;
import sujin.dev.mem.infra.repo.DataRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface CartGoodsService {
    void register(CartGoodsEntity cartGoods);
    List<CartGoodsDTO> getCartGoodsList(String memberId);
    void update(CartGoodsEntity cartGoods);
    void delete(Long cartGoodsId);

    @RequiredArgsConstructor
    class CartGoodsServiceImple implements CartGoodsService {
        private final DataRepository<CartGoodsEntity> repository;
        private final MemberService memberService;

        @Override
        public void register(CartGoodsEntity cartGoods) {
            // CartGoodsEntity를 데이터베이스에 저장하는 로직 구현
            repository.insert(cartGoods);
        }

        @Override
        public List<CartGoodsDTO> getCartGoodsList(String memberId) {
            // MemberEntity 찾기
            MemberEntity memberEntity = memberService.findMemberEntityByMemberId(memberId);
            if (memberEntity == null) {
                return Collections.emptyList(); // 회원이 존재하지 않으면 빈 리스트 반환
            }

            // 해당 회원의 CartGoods 조회
            return repository.findAll().stream()
                    .filter(cartGoods -> cartGoods.getCart().getMember().getId().equals(memberEntity.getId()))
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public void update(CartGoodsEntity cartGoods) {
            // CartGoodsEntity를 업데이트하는 로직 구현
            repository.update(cartGoods);
        }

        @Override
        public void delete(Long cartGoodsId) {
            // CartGoodsEntity를 삭제하는 로직 구현
            repository.deleteById(cartGoodsId);
        }

        private CartGoodsDTO convertToDTO(CartGoodsEntity cartGoods) {
            // CartGoodsEntity를 CartGoodsDTO로 변환
            GoodsDTO goodsDTO = cartGoods.getGoods() != null ? GoodsDTO.fromEntity(cartGoods.getGoods()) : null;
            CartDTO cartDTO = cartGoods.getCart() != null ? CartDTO.fromEntity(cartGoods.getCart()) : null;

            return CartGoodsDTO.builder()
                    .quantity(cartGoods.getQuantity())
                    .totalPrice(cartGoods.getTotalPrice())
                    .goods(goodsDTO)
                    .cart(cartDTO)
                    .build();
        }
    }
}