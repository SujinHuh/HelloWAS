package sujin.dev.mem.domain.service;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.CartGoodsEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.*;
import sujin.dev.mem.infra.repo.DataRepository;
import sujin.dev.mem.infra.repo.impl.CartRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface CartService {
    void registerCart(CartEntity cart);

    List<CartDTO> getCartList(MemberDTO member);

    void clearCart(MemberDTO member);
    CartDTO addToCart(MemberDTO member, GoodsDTO selectedGoods, int quantity);

    @RequiredArgsConstructor
    class CartServiceImple implements CartService {
        public final DataRepository<CartEntity> repository;
        private final GoodsService goodsService;
        private final MemberService memberService;


        @Override
        public void registerCart(CartEntity cart) {
            // CartEntity를 데이터베이스에 저장하는 로직 구현
            repository.insert(cart);
        }

        @Override
        public List<CartDTO> getCartList(MemberDTO member) {

            // MemberEntity로 변환
            MemberEntity memberEntity = memberService.findMemberEntityByMemberId(member.getMemberId());

            if (memberEntity == null) {
                return Collections.emptyList(); // 회원이 존재하지 않으면 빈 리스트 반환
            }

            // 회원 ID로 장바구니 목록 조회
            List<CartEntity> cartEntities = ((CartRepository) repository).findByMemberId(memberEntity.getMemberId());

            // CartEntity 리스트를 CartDTO 리스트로 변환
            return cartEntities.stream()
                    .map(CartDTO::fromEntity)
                    .collect(Collectors.toList());
        }
        @Override
        public void clearCart(MemberDTO member) {
            // MemberService를 사용하여 MemberEntity 찾기
//            MemberEntity memberEntity = memberService.findByMemberId(member.getMemberId());
//
//            // 해당 회원의 모든 장바구니 항목 삭제
//            repository.deleteByMemberId(memberEntity.getId());
        }

        @Override
        public CartDTO addToCart(MemberDTO member, GoodsDTO selectedGoods, int quantity) {
            // MemberEntity 찾기
            MemberEntity memberEntity = memberService.findMemberEntityByMemberId(member.getMemberId());
            if (memberEntity == null) {
                throw new IllegalArgumentException("회원을 찾을 수 없습니다.");
            }

            // GoodsEntity 찾기
            GoodsEntity goodsEntity = goodsService.findGoodsEntityByName(selectedGoods.getName());
            if (goodsEntity == null || goodsEntity.getStockQuantity() < quantity) {
                throw new IllegalArgumentException("상품이 없거나 재고가 부족합니다.");
            }

            // 상품 가격 정보를 가져오기
            BigDecimal pricePerUnit = goodsEntity.getCurrentValue().getAmount();

            // 상품 가격과 수량을 곱하여 총 가격 계산
            BigDecimal totalPrice = pricePerUnit.multiply(BigDecimal.valueOf(quantity));

            // 새로운 CartGoodsEntity 생성 및 설정
            CartGoodsEntity newCartGoods = CartGoodsEntity.builder()
                    .goods(goodsEntity)
                    .quantity(quantity)
                    .build();

            // 장바구니 (CartEntity) 생성 및 CartGoods 추가
            CartEntity cart = CartEntity.builder()
                    .member(memberEntity)
                    .totalPrice(totalPrice)  // 계산된 총 가격 사용
                    .cartGoods(Arrays.asList(newCartGoods)) // 현재는 단일 상품만 추가한다고 가정
                    .build();

            // 장바구니 저장
            repository.insert(cart);

            // 장바구니 정보를 DTO로 변환하여 반환
            return convertToDTO(cart);
        }

        private CartDTO convertToDTO(CartEntity cartEntity) {
            if (cartEntity == null) {
                return null;
            }

            // CartGoodsEntity 리스트를 CartGoodsDTO 리스트로 변환
            List<CartGoodsDTO> cartGoodsDTOs = cartEntity.getCartGoods() != null
                    ? cartEntity.getCartGoods().stream()
                    .map(this::convertCartGoodsEntityToDTO)
                    .collect(Collectors.toList())
                    : Collections.emptyList();

            // CartEntity의 다른 필드들을 CartDTO로 매핑
            return CartDTO.builder()
                    .totalPrice(cartEntity.getTotalPrice())
                    .member(MemberDTO.fromEntity(cartEntity.getMember()))
                    .orders(OrderDTO.fromEntity(cartEntity.getOrders())) // 주문 정보가 있다면 매핑
                    .cartGoods(cartGoodsDTOs)
                    .build();
        }

        private CartGoodsDTO convertCartGoodsEntityToDTO(CartGoodsEntity cartGoodsEntity) {
            if (cartGoodsEntity == null) {
                return null;
            }

            // GoodsEntity를 GoodsDTO로 변환
            GoodsDTO goodsDTO = cartGoodsEntity.getGoods() != null
                    ? GoodsDTO.fromEntity(cartGoodsEntity.getGoods())
                    : null;

            // CartGoodsEntity를 CartGoodsDTO로 변환
            return CartGoodsDTO.builder()
                    .quantity(cartGoodsEntity.getQuantity())
                    .totalPrice(cartGoodsEntity.getTotalPrice())
                    .goods(goodsDTO)
                    .build();
        }

    }
}
