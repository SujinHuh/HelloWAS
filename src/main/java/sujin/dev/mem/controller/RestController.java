package sujin.dev.mem.controller;

import lombok.RequiredArgsConstructor;
import sujin.dev.mem.domain.entity.CartEntity;
import sujin.dev.mem.domain.entity.GoodsEntity;
import sujin.dev.mem.domain.entity.MemberEntity;
import sujin.dev.mem.domain.model.*;
import sujin.dev.mem.domain.service.CartService;
import sujin.dev.mem.domain.service.GoodsService;
import sujin.dev.mem.domain.service.MemberService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

@RequiredArgsConstructor
public class RestController {

    private final MemberService memberService;
    private final CartService cartService;
    private final GoodsService goodsService;

    public void registerGoods(GoodsDTO goods) {
        //goods
        GoodsEntity goodsEntity = GoodsEntity.toEntity(goods);
        goodsService.registerGoods(goodsEntity);
        System.out.println();
        System.out.println("======= 상품 등록 완료 =======");
        System.out.println(String.format("%s 상품 등록 완료되었습니다.", goods.getName()));
    }


    // 회원목록
    public List<MemberDTO> getmemberList() {

        List<MemberDTO> members = memberService.getMembers();

        if (members == null) {
            throw new IllegalArgumentException("회원 목록이 없습니다.");
        }
        System.out.println("=== 회원목록 === ");
        for (MemberDTO member : members) {
            System.out.println("Name: " + member.getName() + ", Phone: " + member.getPhone());
        }
        return members;
    }

    public List<GoodsDTO> getGoodsList() {
        List<GoodsDTO> goodsList = goodsService.getGoods();

        if (goodsList == null || goodsList.isEmpty()) {
            System.out.println("상품 목록이 없습니다.");
            return Collections.emptyList();
        }

        System.out.println("=== 상품목록 === ");

        for (GoodsDTO goods : goodsList) {
            BigDecimal amount = goods.getCurrentValue().getAmount();
            Currency currency = goods.getCurrentValue().getCurrency();

            System.out.println("상품명: " + goods.getName() +
                    ", 상품 가격: " + (amount != null ? amount + " " + currency : "가격 정보 없음") +
                    ", 상품 개수:" + goods.getStockQuantity());
        }

        return goodsList;
    }


    public void registerMember(MemberDTO member) {
        // validation
        if (isNameLength(member)) {
            throw new IllegalArgumentException("이름은 20자를 초과할 수 없습니다.");
        }
        MemberEntity entity = MemberEntity.toEntity(member);
        memberService.registerMember(entity);

        System.out.println();
        System.out.println("===== 회원 가입 완료 =====");
        System.out.println(String.format("%s님 회원가입을 축하합니다.", member.getName()));
    }

    public ResponseResult<List<MemberDTO>> getMember(MemberDTO memberDTO) {
//        ResponseResult responseResult = new ResponseResult();
//        responseResult.setBody(service.getMembers());
//        responseResult.setCode(200);
//        responseResult.setMsg("success");
        // return responseResult;

        if (!memberDTO.getName().contains("KIM")) {
            return new ResponseResult<>
                    (501, "KIM Validation Fail", null);
        }

        return new ResponseResult<>
                (200, "success", memberService.getMembers());

    }

    private static boolean isNameLength(MemberDTO member) {
        return member.getName().length() > 20;
    }


    public void getCartList() {
        List<CartDTO> cartList = cartService.getCartList();

        if (cartList != null && !cartList.isEmpty()) {
            System.out.println("=== 장바구니 목록 === ");

            for (CartDTO cart : cartList) {
                GoodsEntity goods = cart.getGoods();
                MemberEntity member = cart.getMember();

                System.out.println("상품명: " + goods.getName() +
                        ", 상품 가격: " + goods.getCurrentValue().getAmount() + " " + goods.getCurrentValue().getCurrency() +
                        ", 상품 개수: " + goods.getStockQuantity() +
                        ", 회원명: " + member.getName() +
                        ", 전화번호: " + member.getPhone());
            }
        } else {
            System.out.println("장바구니에 상품이 없습니다.");
        }
    }

    public MemberDTO findMemberByName(String memberName) {
        // MemberService를 통해 회원 찾기
        MemberDTO foundMember = memberService.findMemberByName(memberName);

        if (foundMember != null) {
            System.out.println("회원이 있습니다. 회원 이름: " + foundMember.getName());
        } else {
            System.out.println("해당하는 회원이 없습니다.");
        }

        return foundMember;
    }

    public void placeOrder(OrderDTO orderDTO) {

    }
}


